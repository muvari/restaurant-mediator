package com.muvari.restaurantmediator.mediator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.muvari.restaurantmediator.R;
import com.muvari.restaurantmediator.mediator.StartSurveyActivity.StartSurveyFragment;
import com.muvari.restaurantmediator.yelp.RRPC;
import com.muvari.restaurantmediator.yelp.YelpAPI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SummaryActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.scrollable_activity);
		if (savedInstanceState == null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
	
			
			SummaryFragment fragment = SummaryFragment.newInstance(getIntent().getExtras());
			fragmentTransaction.add(R.id.scrollview, fragment,
					SummaryFragment.SUMMARY_FRAGMENT_TAG);
			fragmentTransaction.commit();
		}
	}
	
	public static class SummaryFragment extends Fragment implements LoaderCallbacks<org.json.simple.JSONObject> {

		public static final String SUMMARY_FRAGMENT_TAG = "summary_fragment";

		private float[] ratings;
		private int[] distances;
		private HashMap<Integer, Integer> likes;
		private ArrayList<Integer> dislikes;
		private String address;
		
		private TextView text;
		private WebView webView;
		private ProgressBar progress;
		
		private static final float METERS_IN_MILE = (float) 1609.34;

		@SuppressWarnings("unchecked")
		public static SummaryFragment newInstance(Bundle bundle) {
			SummaryFragment frag = new SummaryFragment();
			frag.ratings = (float[]) bundle.get(SurveyActivity.RATING_KEY);
			frag.distances = (int[]) bundle.get(SurveyActivity.DISTANCE_KEY);
			frag.likes = (HashMap<Integer, Integer>) bundle.get(SurveyActivity.LIKES_KEY);
			frag.dislikes = (ArrayList<Integer>) bundle.get(SurveyActivity.DISLIKES_KEY);
			frag.address = (String) bundle.get(StartSurveyFragment.ADDRESS_TAG);
			return frag;
		}
		
		public SummaryFragment() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.summary_fragment, container,
					false);
			
			getLoaderManager().initLoader(0, null, this).forceLoad();
			text = ((TextView)view.findViewById(R.id.ending));
			webView = ((WebView)view.findViewById(R.id.webView1));
			progress = ((ProgressBar)view.findViewById(R.id.progress));
			return view;
		}
		
		private float getMaxRating() {
			float max = 0;
			for (float f : ratings) {
				if (f > max)
					max = f;
			}
			return max;
		}
		
		private int getMinDistance() {
			int min = 25;
			for (int i : distances) {
				if (i < min)
					min = i;
			}
			return (int) Math.min(40000, (min * METERS_IN_MILE));
		}
		
		private List<Integer> getLikesInOrder(Map<Integer, Integer> wordCount) {

		    // Convert map to list of <String,Integer> entries
		    List<Map.Entry<Integer, Integer>> list = 
		        new ArrayList<Map.Entry<Integer, Integer>>(wordCount.entrySet());

		    // Sort list by integer values
		    Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
		        public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
		            // compare o2 to o1, instead of o1 to o2, to get descending freq. order
		            return (o2.getValue()).compareTo(o1.getValue());
		        }
		    });

		    // Populate the result into a list
		    List<Integer> result = new ArrayList<Integer>();
		    for (Map.Entry<Integer, Integer> entry : list) {
		        result.add(entry.getKey());
		    }
		    return result;
		}
		
		private String determineCuisine(List<Integer> sortedIds) {
			String cuisine = "";
			for (int i = 0; i < sortedIds.size(); i++) {
				if (!dislikes.contains(sortedIds.get(i))) {
					cuisine = ChipFactory.getStringFromId(getActivity(), sortedIds.get(i));
					break;
				}
			}
			return cuisine;
		}

		@Override
		public Loader<JSONObject> onCreateLoader(int arg0, Bundle arg1) {
			return new YelpAPI.SimpleDBLoader(getActivity(), "", determineCuisine(getLikesInOrder(likes)), ""+getMinDistance(), address);
		}

		@SuppressLint("SetJavaScriptEnabled")
		@Override
		public void onLoadFinished(Loader<JSONObject> arg0, JSONObject arg1) {
			String url = arg1.get("mobile_url").toString();
			progress.setVisibility(View.GONE);
			text.setVisibility(View.GONE);
			webView.setVisibility(View.VISIBLE);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.loadUrl(url);
		}

		@Override
		public void onLoaderReset(Loader<JSONObject> arg0) {
			
		}
	}

}
