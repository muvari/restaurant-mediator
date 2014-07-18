package com.muvari.restaurantmediator.mediator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SurveyPagerAdapter extends FragmentStatePagerAdapter {
	private int count;

	public SurveyPagerAdapter(FragmentManager fm, int count) {
		super(fm);
		this.count = count;
	}

	@Override
	public Fragment getItem(int arg0) {
		return new SurveyFragment();
	}

	@Override
	public int getCount() {
		return count;
	}

}
