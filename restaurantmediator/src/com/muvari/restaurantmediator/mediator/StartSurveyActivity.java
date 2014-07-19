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
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

/**
 * Starting Activity. Includes basic instructions and where you state location and member of group
 * @author Mark
 *
 */
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
			
			addressText.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					addressText.setError(null);
					return false;
				}

			});
			
			//Starts the Surveys
			Button start = (Button)view.findViewById(R.id.start_button);
			start.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (addressText.getText().toString().length() < 5) {
						addressText.setError("Please enter a valid address");
					} else {
					Intent intent = new Intent(getActivity(), SurveyActivity.class);
					intent.putExtra(ADDRESS_TAG, addressText.getText().toString());
					intent.putExtra(NUM_PEOPLE_TAG, picker.getValue());
					intent.putExtra(CUR_PERSON_TAG, 1);
					startActivity(intent);
					}
				}
				
			});
			
			//Use GPS to detect a location
			Button detect = (Button)view.findViewById(R.id.detect_location);
			detect.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

					String locationProvider = LocationManager.NETWORK_PROVIDER;

					Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
					
					Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
					List<Address> addresses;
					try {
						addresses = geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
						addressText.setText(addresses.get(0).getAddressLine(0) + " " + addresses.get(0).getAddressLine(1));
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
			});
			
			
			return view;
		}
	}

}
