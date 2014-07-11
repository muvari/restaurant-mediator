package com.muvari.restaurantmediator.dragndrop;

/*
 * Copyright (C) 2013 Wglxy.com
 *
 * Edited by muvari https://github.com/muvari/
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * (Note to other developers: The above note says you are free to do what you want with this code.
 *  Any problems are yours to fix. Wglxy.com is simply helping you get started. )
 */

import com.muvari.restaurantmediator.R;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 * This class is used with a GridView object. It provides a set of ImageCell
 * objects that support dragging and dropping. An View.OnDragListener object can
 * be provided when you create the adapter.
 * 
 */
public class ImageCellAdapter extends BaseAdapter {

	public ViewGroup mParentView = null;
	private Context mContext;
	private View.OnDragListener mDragListener = null;
	private int count = -1;

	public ImageCellAdapter(Context c) {
		mContext = c;
		mDragListener = null;
	}

	public ImageCellAdapter(Context c, View.OnDragListener l) {
		mContext = c;
		mDragListener = l;
	}
	
	public ImageCellAdapter(Context c, View.OnDragListener l, int count) {
		mContext = c;
		mDragListener = l;
		this.count = count;
	}

	public int getCount() {
		if (count >= 0) return count;
		Resources res = mContext.getResources();
		int numImages = res.getInteger(R.integer.num_images);
		return numImages;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * getView Return a view object for the grid.
	 * 
	 * @return ImageCell
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		Resources res = mContext.getResources();
		int cellWidth = res.getDimensionPixelSize(R.dimen.grid_cell_width);
		int cellHeight = res.getDimensionPixelSize(R.dimen.grid_cell_height);
		int cellPad = res.getDimensionPixelSize(R.dimen.grid_cell_padding);
		mParentView = parent;

		ImageCell v = null;
		if (convertView == null) {
			// If it's not recycled, create a new ImageCell.
			v = new ImageCell(mContext);
			v.setLayoutParams(new GridView.LayoutParams(cellWidth, cellHeight));
			v.setPadding(cellPad, cellPad, cellPad, cellPad);
		} else {
			v = (ImageCell) convertView;
		}

		v.mCellNumber = position;
		v.mGrid = (GridView) mParentView;
		v.mEmpty = true;
		v.mChip = null;
		v.setOnDragListener(mDragListener);
		v.setBackgroundResource(R.color.cell_empty);

		// Set up to relay events to the activity.
		// The activity decides which events trigger drag operations.
		// Activities like the Android Launcher require a long click to get a
		// drag operation started.
		FragmentActivity act = (FragmentActivity)mContext;
		Fragment frag = act.getSupportFragmentManager().findFragmentById(R.id.survey_fragment);
		v.setOnTouchListener((View.OnTouchListener) frag);
		v.setOnClickListener((View.OnClickListener) frag);
		v.setOnLongClickListener((View.OnLongClickListener) frag);

		return v;
	}

} 
