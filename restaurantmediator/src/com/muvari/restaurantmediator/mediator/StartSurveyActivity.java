package com.muvari.restaurantmediator.mediator;

import com.muvari.restaurantmediator.R;
import com.muvari.restaurantmediator.mediator.SurveyActivity.SurveyFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class StartSurveyActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.scrollable_activity);
		if (savedInstanceState == null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
	
			StartSurveyFragment fragment = new StartSurveyFragment();
			fragmentTransaction.add(R.id.scrollview, fragment,
					StartSurveyFragment.START_FRAGMENT_TAG);
			fragmentTransaction.commit();
		}

	}
	
	public static class StartSurveyFragment extends Fragment {

		public static final String START_FRAGMENT_TAG = "start_fragment";
	
		public StartSurveyFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.start_survey_fragment, container,
					false);
			Button start = (Button)view.findViewById(R.id.start_button);
			start.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), SurveyActivity.class);
					startActivity(intent);
				}
				
			});
			return view;
		}
	}

}
