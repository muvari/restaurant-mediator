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
import com.muvari.restaurantmediator.yelp.SimpleYelpLoader;
import android.annotation.SuppressLint;
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
import android.view.Window;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Final Activity that displays a Webview of the selected restaurant on yelp
 * @author Mark
 *
 */
public class SummaryActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_PROGRESS);
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
		
		public static final float METERS_IN_MILE = (float) 1609.34;

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
		
		/**
		 * Finds the rating that any user will enjoy
		 * @return
		 */
		private float getMaxRating() {
			float max = 0;
			for (float f : ratings) {
				if (f > max)
					max = f;
			}
			return max;
		}
		
		/**
		 * Finds the distance that any user will enjoy
		 * @return
		 */
		private int getMinDistance() {
			int min = 25;
			for (int i : distances) {
				if (i < min)
					min = i;
			}
			return (int) Math.min(40000, (min * METERS_IN_MILE));
		}
		
		/**
		 * Sorts a HashMap of Food ids and the # of occurences into an ordered list
		 * @param wordCount
		 * @return
		 */
		private List<Integer> getLikesInOrder(Map<Integer, Integer> wordCount) {

		    // Convert map to list of <Integer,Integer> entries
		    List<Map.Entry<Integer, Integer>> list = 
		        new ArrayList<Map.Entry<Integer, Integer>>(wordCount.entrySet());

		    // Sort list by integer values
		    Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
		        public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
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
		
		/**
		 * Gets the sorted list from getLikesInOrder, and combines the categories with equal occurences into one comma separated item
		 * Also removes food categories that any user disliked
		 * 
		 * @param sortedIds
		 * @return
		 */
		private List<String> combineEqualLikes(List<Integer> sortedIds) {
			ArrayList<String> sortedCategories = new ArrayList<String>();
			if (sortedIds.size() == 0)
				return sortedCategories;
			
			String groupedCats = "";
			int prevOccurences = likes.get(sortedIds.get(0).intValue());
			for (Integer i : sortedIds) {
				String category = ChipFactory.getStringFromId(getActivity(), i.intValue());
				int occurences = likes.get(i);
				
				if (prevOccurences != occurences) {
					sortedCategories.add(groupedCats);
					prevOccurences = occurences;
					groupedCats = "";
				} else {
					if (!dislikes.contains(i)) {
						if (!groupedCats.equals("")) 
							groupedCats += ",";
						groupedCats += category;
					}
				}
			}
			if (!groupedCats.equals(""))
				sortedCategories.add(groupedCats);
			
			return sortedCategories;
		}

		@Override
		public Loader<JSONObject> onCreateLoader(int arg0, Bundle arg1) {
			return new SimpleYelpLoader(getActivity(), "", combineEqualLikes(getLikesInOrder(likes)), ChipFactory.getStringsFromIds(getActivity(), dislikes), ""+getMinDistance(), address, getMaxRating());
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
