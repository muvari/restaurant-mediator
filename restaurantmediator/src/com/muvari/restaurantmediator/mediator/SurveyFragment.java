package com.muvari.restaurantmediator.mediator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.muvari.restaurantmediator.R;
import com.muvari.restaurantmediator.dragndrop.DragController;
import com.muvari.restaurantmediator.dragndrop.DragDropPresenter;
import com.muvari.restaurantmediator.dragndrop.DragSource;
import com.muvari.restaurantmediator.dragndrop.DropTarget;
import com.muvari.restaurantmediator.dragndrop.ImageCell;
import com.muvari.restaurantmediator.dragndrop.ImageCellAdapter;

/**
 * SurveyFragment - User is asked a few questions of their likes/dislikes
 * @author Mark
 *
 */
public class SurveyFragment extends Fragment implements View.OnLongClickListener, View.OnClickListener, DragDropPresenter, View.OnTouchListener {

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
	private RatingBar ratingBar;
	private RadioGroup distance;
	private int[] likes = { -1, -1, -1 };
	private int[] dislikes = { -1, -1, -1 };

	private ToggleButton expandButton;

	public SurveyFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.survey_fragment, container, false);

		if (savedInstanceState != null) {
			likes = savedInstanceState.getIntArray(LIKES_ID);
			dislikes = savedInstanceState.getIntArray(DISLIKES_ID);
		}
		// When a drag starts, we vibrate so the user gets some feedback.
		mVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

		// This activity will listen for drag-drop events.
		// The listener used is a DragController. Set it up.
		mDragController = new DragController(this);

		ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
		distance = (RadioGroup) view.findViewById(R.id.distance_bar);

		// Set up the grid view with an ImageCellAdapter and have it use the
		// DragController.
		likesGrid = (GridView) view.findViewById(R.id.likes).findViewById(R.id.image_grid_view);
		likesGrid.setAdapter(new ImageCellAdapter(getActivity(), mDragController, likes, true, this));

		dislikesGrid = (GridView) view.findViewById(R.id.dislikes).findViewById(R.id.image_grid_view);

		dislikesGrid.setAdapter(new ImageCellAdapter(getActivity(), mDragController, dislikes, false, this));

		((TextView) view.findViewById(R.id.dislikes).findViewById(R.id.title)).setText(R.string.dislikes_text);
		

		poolGrid = (GridView) view.findViewById(R.id.cat_pool);
		poolGrid.setAdapter(new ImageCellAdapter(getActivity(), mDragController, 24, likes, dislikes, this));

		expandGrid = (GridView) view.findViewById(R.id.cat_pool_expand);
		expandGrid.setAdapter(new ImageCellAdapter(getActivity(), mDragController, 96, likes, dislikes, this));
		
		Button doneButton = (Button) view.findViewById(R.id.done_button);
		doneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SurveyActivity act = (SurveyActivity) getActivity();
				ViewPager pager = act.getPager();
				if (pager.getCurrentItem() == (pager.getAdapter().getCount()-1)) {
					getActivity().startActivity(((SurveyActivity) getActivity()).generateSummaryIntent());
				} else {
					pager.setCurrentItem(pager.getCurrentItem() + 1);
				}
			}
			
		});
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

		if (savedInstanceState != null) {
			if (savedInstanceState.getBoolean("EXPANDED")) {
				expandPool();
			}
		}
		return view;
	}

	private void moveChip(ImageCell target, ImageCell source) {
		target.onDrop(source);
		source.onDropCompleted(target, true);
		
		//Update the views
		setLikes();
		setDislikes();
		((ImageCellAdapter)likesGrid.getAdapter()).setLikes(likes);
		((ImageCellAdapter)dislikesGrid.getAdapter()).setLikes(dislikes);
		((ImageCellAdapter)poolGrid.getAdapter()).setLikes(likes, dislikes);
		((ImageCellAdapter)expandGrid.getAdapter()).setLikes(likes, dislikes);
		likesGrid.invalidateViews();
		dislikesGrid.invalidateViews();
		poolGrid.invalidateViews();
		expandGrid.invalidateViews();
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setLikes();
		setDislikes();
		outState.putIntArray(LIKES_ID, likes);
		outState.putIntArray(DISLIKES_ID, dislikes);
		outState.putBoolean("EXPANDED", expandButton.isChecked());
	}
	
	private void setLikes() {
		for (int i = 0; i < likesGrid.getChildCount(); i++) {
			ImageCell cell = (ImageCell) likesGrid.getChildAt(i);
			if (cell.mEmpty) {
				likes[i] = -1;
			} else {
				likes[i] = cell.getmCat().getId();
			}
		}

	}
	
	private void setDislikes() {
	for (int i = 0; i < dislikesGrid.getChildCount(); i++) {
		ImageCell cell = (ImageCell) dislikesGrid.getChildAt(i);
		if (cell.mEmpty) {
			dislikes[i] = -1;
		} else {
			dislikes[i] = cell.getmCat().getId();
		}
	}
	}
	
	public int[] getLikes() {
		setLikes();
		return likes;
	}
	
	public int[] getDislikes() {
		setDislikes();
		return dislikes;
	}
	
	public float getRating() {
		return ratingBar.getRating();
	}
	
	public int getDistance() {
		int selectedId = distance.getCheckedRadioButtonId();
		RadioButton button = (RadioButton) distance.findViewById(selectedId);
		int distance;
		if (button == null) {
			distance = 25;
		} else {
			distance = Integer.parseInt(button.getText().toString());
		}
		return distance;
	}

	public void expandPool() {
		if (expandGrid.getVisibility() == View.GONE) {
			expandGrid.setVisibility(View.VISIBLE);
			// CommonMethods.expand(expandGrid);
			RelativeLayout.LayoutParams p = ((RelativeLayout.LayoutParams) expandButton.getLayoutParams());
			p.addRule(RelativeLayout.BELOW, R.id.cat_pool_expand);
			expandButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_collapse, 0);
		} else {
			// CommonMethods.collapse(expandGrid);
			expandGrid.setVisibility(View.GONE);
			RelativeLayout.LayoutParams p = ((RelativeLayout.LayoutParams) expandButton.getLayoutParams());
			p.addRule(RelativeLayout.BELOW, R.id.cat_pool);
			expandButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_action_expand, 0);
		}
	}

	@SuppressLint("ClickableViewAccessibility")
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
	public void onClick(View v) {
		ImageCell chip = (ImageCell) v;
		GridView grid = (GridView) v.getParent();
		ImageCellAdapter adapter = (ImageCellAdapter) grid.getAdapter();
		//Determine what grid the click came from
		if (adapter.isPool()) {
			
			//Check to see if there is a 'like' opening and insert it
			for (int i = 0; i < likesGrid.getChildCount(); i++) {
				ImageCell cell = (ImageCell) likesGrid.getChildAt(i);
				if (cell.mEmpty) {
					moveChip(cell, chip);
					return;
				}
			}
			
			//Check to see if there is a dislike opening and insert it
			for (int i = 0; i < dislikesGrid.getChildCount(); i++) {
				ImageCell cell = (ImageCell) dislikesGrid.getChildAt(i);
				if (cell.mEmpty) {
					moveChip(cell, chip);
					return;
				}
			}
			
			//If none are filled, pop the toast
			toast("Choices filled");

		} else {
			//Check if there is an opening in the primary grid
			for (int i = 0; i < poolGrid.getChildCount(); i++) {
				ImageCell cell = (ImageCell) poolGrid.getChildAt(i);
				if (cell.mEmpty) {
					moveChip(cell, chip);
					return;
				}
			}
			//Check if there is an opening in the expanded grid
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
		Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

}