package com.muvari.restaurantmediator.mediator;

import com.muvari.restaurantmediator.R;
import com.muvari.restaurantmediator.dragndrop.DragController;
import com.muvari.restaurantmediator.dragndrop.DragDropPresenter;
import com.muvari.restaurantmediator.dragndrop.DragSource;
import com.muvari.restaurantmediator.dragndrop.DropTarget;
import com.muvari.restaurantmediator.dragndrop.ImageCellAdapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scrollable_activity);
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		SurveyFragment fragment = new SurveyFragment();
		fragmentTransaction.add(R.id.scrollview, fragment, SurveyFragment.SURVEY_FRAGMENT_TAG);
		fragmentTransaction.commit();
		
	}

	public static class SurveyFragment extends Fragment implements
			View.OnLongClickListener, View.OnClickListener, DragDropPresenter,
			View.OnTouchListener {
		
		public static final String SURVEY_FRAGMENT_TAG = "survey_fragment";

		private DragController mDragController;
		private Vibrator mVibrator;
		private static final int VIBRATE_DURATION = 35;

		public SurveyFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.survey_fragment, container, false);
			

			// When a drag starts, we vibrate so the user gets some feedback.
			mVibrator = (Vibrator) getActivity().getSystemService(
					Context.VIBRATOR_SERVICE);

			// This activity will listen for drag-drop events.
			// The listener used is a DragController. Set it up.
			mDragController = new DragController(this);

			// Set up the grid view with an ImageCellAdapter and have it use the
			// DragController.
			GridView gridView = (GridView) view.findViewById(R.id.likes).findViewById(R.id.image_grid_view);
			gridView.setAdapter(new ImageCellAdapter(getActivity(),
					mDragController));

			// Set up the grid view with an ImageCellAdapter and have it use the
			// DragController.
			GridView gridView2 = (GridView) view.findViewById(R.id.dislikes).findViewById(R.id.image_grid_view);
			gridView2.setAdapter(new ImageCellAdapter(getActivity(),
					mDragController));
			
			((TextView) view.findViewById(R.id.dislikes).findViewById(R.id.title)).setText(R.string.dislikes_text);;

			GridView catPool = (GridView) view.findViewById(
					R.id.cat_pool);
			catPool.setAdapter(new ImageCellAdapter(getActivity(),
					mDragController, 9));
			
			return view;
		}
		
		private void loadCategories() {
			
		}

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			return false;
		}

		@Override
		public boolean isDragDropEnabled() {
			return true;
		}

		@Override
		public void onDragStarted(DragSource source) {
			mVibrator.vibrate(VIBRATE_DURATION);
		}

		@Override
		public void onDropCompleted(DropTarget target, boolean success) {

		}

		@Override
		public void onClick(View arg0) {
			//toast("test");
		}

		@Override
		public boolean onLongClick(View v) {
			return startDrag(v);
		}

		public boolean startDrag(View v) {
			// We are starting a drag-drop operation.
			// Set up the view and let our controller handle it.
			v.setOnDragListener(mDragController);
			mDragController.startDrag(v);
			return true;
		}

		public void toast(String msg) {
			Toast.makeText(getActivity().getApplicationContext(), msg,
					Toast.LENGTH_SHORT).show();
		}

	}
}
