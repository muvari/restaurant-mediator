package com.muvari.restaurantmediator.mediator;

import java.util.ArrayList;
import java.util.HashMap;

import com.muvari.restaurantmediator.R;
import com.muvari.restaurantmediator.mediator.StartSurveyActivity.StartSurveyFragment;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class SurveyActivity extends FragmentActivity {

	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	
	public static final String LIKES_KEY = "likes_key";
	public static final String DISLIKES_KEY = "dislikes_key";
	public static final String DISTANCE_KEY = "distance_key";
	public static final String RATING_KEY = "rating_key";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager_activity);

		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setPageMargin(32);
		mPager.setPageTransformer(true, new ZoomOutPageTransformer());
		mPagerAdapter = new SurveyPagerAdapter(getSupportFragmentManager(), getIntent().getIntExtra(StartSurveyFragment.NUM_PEOPLE_TAG, 1));
		mPager.setAdapter(mPagerAdapter);

		//Bind the title indicator to the adapter
		TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.titles);
		titleIndicator.setViewPager(mPager);
		titleIndicator.setSelectedColor(getResources().getColor(android.R.color.black));
		titleIndicator.setTextColor(getResources().getColor(android.R.color.black));
	}

	@Override
	public void onBackPressed() {
		if (mPager.getCurrentItem() == 0) {
			super.onBackPressed();
		} else {
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
	}
	
	public ViewPager getPager() {
		return mPager;
	}
	
	
	@SuppressLint("UseSparseArrays")
	public Intent generateSummaryIntent() {
		Intent intent = new Intent(this, SummaryActivity.class);
		int count = mPager.getChildCount();
		float[] ratings = new float[count];
		int[] distances = new int[count];
		HashMap<Integer, Integer> likes = new HashMap<Integer, Integer>();
		ArrayList<Integer> dislikes = new ArrayList<Integer>();
		
		SurveyPagerAdapter ad = (SurveyPagerAdapter) mPager.getAdapter();
		SurveyFragment[] frags = ad.getFragments();
		for (int i = 0; i < mPager.getChildCount(); i++) {
			ratings[i] = frags[i].getRating();
			distances[i] = frags[i].getDistance();
			
			for (int j : frags[i].getLikes()) {
				Integer likeId = Integer.valueOf(j);
				if (likeId > -1) {
					if (likes.containsKey(likeId)) {
						likes.put(j, likes.get(j)+1);
					} else {
						likes.put(j, 1);
					}
				}
			}
			
			for (int j : frags[i].getDislikes()) {
				Integer dislikeId = Integer.valueOf(j);
				if (dislikeId > -1) {
					if (!dislikes.contains(dislikeId))
						dislikes.add(dislikeId);
				}
			}
		}
		
		intent.putExtra(DISTANCE_KEY, distances);
		intent.putExtra(RATING_KEY, ratings);
		intent.putExtra(LIKES_KEY, likes);
		intent.putExtra(DISLIKES_KEY, dislikes);
		intent.putExtra(StartSurveyFragment.ADDRESS_TAG, getIntent().getStringExtra(StartSurveyFragment.ADDRESS_TAG));
		return intent;
	}

}
