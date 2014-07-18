package com.muvari.restaurantmediator.mediator;

import com.muvari.restaurantmediator.R;
import com.muvari.restaurantmediator.mediator.StartSurveyActivity.StartSurveyFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class SurveyActivity extends FragmentActivity {

	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;

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

		setTitle("Person " + getIntent().getIntExtra(StartSurveyFragment.CUR_PERSON_TAG, 1));
	}

	@Override
	public void onBackPressed() {
		if (mPager.getCurrentItem() == 0) {
			super.onBackPressed();
		} else {
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
	}

}
