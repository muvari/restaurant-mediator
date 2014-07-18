package com.muvari.restaurantmediator.mediator;

import java.util.HashMap;

import com.muvari.restaurantmediator.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SummaryActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.scrollable_activity);
		if (savedInstanceState == null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
	
			
			SummaryFragment fragment = new SummaryFragment();
			fragment.setArguments(getIntent().getExtras());
			fragmentTransaction.add(R.id.scrollview, fragment,
					SummaryFragment.SUMMARY_FRAGMENT_TAG);
			fragmentTransaction.commit();
		}
	}
	
	public static class SummaryFragment extends Fragment {

		public static final String SUMMARY_FRAGMENT_TAG = "summary_fragment";

		private float[] ratings;
		private int[] distances;
		private HashMap<Integer, Integer> likes;
		private HashMap<Integer, Integer> dislikes;
		
		private static final float METERS_IN_MILE = (float) 1609.34;

		@SuppressWarnings("unchecked")
		public SummaryFragment() {
			ratings = (float[]) getArguments().get(SurveyActivity.RATING_KEY);
			distances = (int[]) getArguments().get(SurveyActivity.DISTANCE_KEY);
			likes = (HashMap<Integer, Integer>) getArguments().get(SurveyActivity.LIKES_KEY);
			dislikes = (HashMap<Integer, Integer>) getArguments().get(SurveyActivity.DISLIKES_KEY);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.summary_fragment, container,
					false);
			
			
			return view;
		}
		
		private void generateYelpQuery() {
			
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
	}

}
