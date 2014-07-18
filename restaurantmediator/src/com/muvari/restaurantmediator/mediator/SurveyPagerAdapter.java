package com.muvari.restaurantmediator.mediator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SurveyPagerAdapter extends FragmentStatePagerAdapter {
	private int count;
	private SurveyFragment[] frags;

	public SurveyPagerAdapter(FragmentManager fm, int count) {
		super(fm);
		this.count = count;
		frags = new SurveyFragment[count];
	}

	@Override
	public Fragment getItem(int arg0) {
		SurveyFragment frag = new SurveyFragment();
		frags[arg0] = frag;
		return frag;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "Person " + (position+1);
	}
	
	public SurveyFragment[] getFragments() {
		return frags;
	}
}
