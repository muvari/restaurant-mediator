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
import android.widget.EditText;
import android.widget.NumberPicker;

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

		setTitle("Start a New Decision");
	}
	
	public static class StartSurveyFragment extends Fragment {

		public static final String START_FRAGMENT_TAG = "start_fragment";
		public static final String ADDRESS_TAG = "address_tag";
		public static final String NUM_PEOPLE_TAG = "num_people_tag";
		public static final String CUR_PERSON_TAG = "cur_person_tag";
		
		private NumberPicker picker;
		private EditText addressText;
	
		public StartSurveyFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.start_survey_fragment, container,
					false);
			
			picker = (NumberPicker)view.findViewById(R.id.numberPicker1);
			picker.setMinValue(1);
			picker.setMaxValue(10);
			picker.setValue(2);
			
			addressText = (EditText)view.findViewById(R.id.editText1);
			
			Button start = (Button)view.findViewById(R.id.start_button);
			start.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), SurveyActivity.class);
					intent.putExtra(ADDRESS_TAG, addressText.getText().toString());
					intent.putExtra(NUM_PEOPLE_TAG, picker.getValue());
					intent.putExtra(CUR_PERSON_TAG, 1);
					startActivity(intent);
				}
				
			});
			

			return view;
		}
	}

}
