package com.muvari.restaurantmediator.mediator;

import com.muvari.restaurantmediator.R;
import com.muvari.restaurantmediator.dragndrop.DragController;
import com.muvari.restaurantmediator.dragndrop.DragDropPresenter;
import com.muvari.restaurantmediator.dragndrop.DragSource;
import com.muvari.restaurantmediator.dragndrop.DropTarget;
import com.muvari.restaurantmediator.dragndrop.ImageCell;
import com.muvari.restaurantmediator.dragndrop.ImageCellAdapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SurveyActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.scrollable_activity);
		if (savedInstanceState == null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
	
			SurveyFragment fragment = new SurveyFragment();
			fragmentTransaction.add(R.id.scrollview, fragment,
					SurveyFragment.SURVEY_FRAGMENT_TAG);
			fragmentTransaction.commit();
		}

	}

	public static class SurveyFragment extends Fragment implements
			View.OnLongClickListener, View.OnClickListener, DragDropPresenter,
			View.OnTouchListener {

		public static final String SURVEY_FRAGMENT_TAG = "survey_fragment";
		private static final String LIKES_ID = "likes_id";
		private static final String DISLIKES_ID = "dislikes_id";

		private DragController mDragController;
		private Vibrator mVibrator;
		private static final int VIBRATE_DURATION = 35;

		private GridView likesGrid;
		private GridView dislikesGrid;
		private GridView poolGrid;
		private GridView expandGrid;
		private int[] likes = {-1,-1,-1};
		private int[] dislikes = {-1,-1,-1};
		
		private ToggleButton expandButton;

		public SurveyFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.survey_fragment, container,
					false);

			if(savedInstanceState != null) {
				likes = savedInstanceState.getIntArray(LIKES_ID);
				dislikes = savedInstanceState.getIntArray(DISLIKES_ID);
			}
			// When a drag starts, we vibrate so the user gets some feedback.
			mVibrator = (Vibrator) getActivity().getSystemService(
					Context.VIBRATOR_SERVICE);

			// This activity will listen for drag-drop events.
			// The listener used is a DragController. Set it up.
			mDragController = new DragController(this);

			// Set up the grid view with an ImageCellAdapter and have it use the
			// DragController.
			likesGrid = (GridView) view.findViewById(R.id.likes).findViewById(
					R.id.image_grid_view);
			likesGrid.setAdapter(new ImageCellAdapter(getActivity(),
					mDragController, likes, true));

			dislikesGrid = (GridView) view.findViewById(R.id.dislikes)
					.findViewById(R.id.image_grid_view);
			
			dislikesGrid.setAdapter(new ImageCellAdapter(getActivity(),
					mDragController, dislikes, false));

			((TextView) view.findViewById(R.id.dislikes).findViewById(
					R.id.title)).setText(R.string.dislikes_text);
			;

			poolGrid = (GridView) view.findViewById(R.id.cat_pool);
			poolGrid.setAdapter(new ImageCellAdapter(getActivity(),
					mDragController, 24, likes, dislikes));
			
			expandGrid = (GridView) view.findViewById(R.id.cat_pool_expand);
			expandGrid.setAdapter(new ImageCellAdapter(getActivity(),
					mDragController, 96, likes, dislikes));
			
			
			OnClickListener expandListener = new OnClickListener() {

				@Override
				public void onClick(View v) {
					expandPool();
				}
				
			};
			expandButton = (ToggleButton) view.findViewById(R.id.expand_button);
			TextView text = (TextView) view.findViewById(R.id.cat_pool_text);
			text.setOnClickListener(expandListener);
			expandButton.setOnClickListener(expandListener);
			
			
			if(savedInstanceState != null) {
				if (savedInstanceState.getBoolean("EXPANDED")) {
					expandPool();
				}
			}
			return view;
		}
		
		private void moveChip(ImageCell target, ImageCell source) {
			target.onDrop(source);
			source.onDropCompleted(target, true);
		}
			
		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			for (int i = 0; i < likesGrid.getChildCount(); i++) {
				ImageCell cell = (ImageCell) likesGrid.getChildAt(i);
				if (cell.mEmpty) {
					likes[i] = -1;
				} else {
					likes[i] = cell.getmCat().getId();
				}
			}
			for (int i = 0; i < dislikesGrid.getChildCount(); i++) {
				ImageCell cell = (ImageCell) dislikesGrid.getChildAt(i);
				if (cell.mEmpty) {
					dislikes[i] = -1;
				} else {
					dislikes[i] = cell.getmCat().getId();
				}
			}
			outState.putIntArray(LIKES_ID, likes);
			outState.putIntArray(DISLIKES_ID, dislikes);
			outState.putBoolean("EXPANDED", expandButton.isChecked());
		}

		public void expandPool() {
			if (expandGrid.getVisibility() == View.GONE) {
				expandGrid.setVisibility(View.VISIBLE);
				//CommonMethods.expand(expandGrid);
				RelativeLayout.LayoutParams p = ((RelativeLayout.LayoutParams)expandButton.getLayoutParams());
				p.addRule(RelativeLayout.BELOW, R.id.cat_pool_expand);
				expandButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse, 0);
			} else {
				//CommonMethods.collapse(expandGrid);
				expandGrid.setVisibility(View.GONE);
				RelativeLayout.LayoutParams p = ((RelativeLayout.LayoutParams)expandButton.getLayoutParams());
				p.addRule(RelativeLayout.BELOW, R.id.cat_pool);
				expandButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand, 0);
			}
		}

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
		       if (getActivity().findViewById(R.id.name_edit).hasFocus()) {
		    	   getActivity().findViewById(R.id.name_edit).clearFocus();
		        }
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
		public void onClick(View v) {
			ImageCell chip = (ImageCell) v;
			GridView grid = (GridView) v.getParent();
			ImageCellAdapter adapter = (ImageCellAdapter) grid.getAdapter();
			if (adapter.isPool()) {
				chip.onDragStarted();
				for (int i = 0; i < likesGrid.getChildCount(); i++) {
					ImageCell cell = (ImageCell) likesGrid.getChildAt(i);
					if (cell.mEmpty) {
						moveChip(cell, chip);
						return;
					}
				}
				for (int i = 0; i < dislikesGrid.getChildCount(); i++) {
					ImageCell cell = (ImageCell) dislikesGrid.getChildAt(i);
					if (cell.mEmpty) {
						moveChip(cell, chip);
						return;
					}
				}
				toast("Choices filled");

			} else {
				for (int i = 0; i < poolGrid.getChildCount(); i++) {
					ImageCell cell = (ImageCell) poolGrid.getChildAt(i);
					if (cell.mEmpty) {
						moveChip(cell, chip);
						return;
					}
				}
				for (int i = 0; i < expandGrid.getChildCount(); i++) {
					ImageCell cell = (ImageCell) expandGrid.getChildAt(i);
					if (cell.mEmpty) {
						moveChip(cell, chip);
						return;
					}
				}
			}
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
