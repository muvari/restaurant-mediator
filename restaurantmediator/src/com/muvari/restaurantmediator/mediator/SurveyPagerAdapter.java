package com.muvari.restaurantmediator.mediator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

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
		return new SurveyFragment();
	}
	
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment frag = (Fragment) super.instantiateItem(container, position);
		frags[position] = (SurveyFragment) frag;
		return frag;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        frags[position] = null;
        super.destroyItem(container, position, object);
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
