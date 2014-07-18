package com.muvari.restaurantmediator.mediator;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.muvari.restaurantmediator.R;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

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
			fragmentTransaction.add(R.id.scrollview, fragment,
					SummaryFragment.SUMMARY_FRAGMENT_TAG);
			fragmentTransaction.commit();
		}
	}
	
	public static class SummaryFragment extends Fragment {

		public static final String SUMMARY_FRAGMENT_TAG = "summary_fragment";

	
		public SummaryFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.start_survey_fragment, container,
					false);
			
			
			return view;
		}
	}

}
