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
import com.muvari.restaurantmediator.mediator.SurveyFragment;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 * This class is used with a GridView object. It provides a set of ImageCell
 * objects that support dragging and dropping. An View.OnDragListener object can
 * be provided when you create the adapter.
 * 
 */
public abstract class ImageCellAdapter extends BaseAdapter {

	public ViewGroup mParentView = null;
	protected Context mContext;
	protected View.OnDragListener mDragListener = null;
	protected int count = -1;
	protected boolean pool = false;
	protected int[] likes;
	protected SurveyFragment fragment;

	public ImageCellAdapter(Context c, View.OnDragListener l, int[] likes, SurveyFragment frag) {
		mContext = c;
		mDragListener = l;
		this.likes = likes;
		this.fragment = frag;
	}

	public int getCount() {
		if (count >= 0)
			return count;
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
	public abstract View getView(int position, View convertView, ViewGroup parent);

	/**
	 * When coming back to a view, or rotating the device, check if there was a
	 * chip there previously
	 * 
	 * @param pos
	 * @return
	 */
	protected boolean contains(int pos) {
		for (int i = 0; i < likes.length; i++) {
			if (likes[i] == pos)
				return true;
		}
		return false;
	}

	/**
	 * Creates a generic empty cell
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	protected ImageCell createEmptyCell(int position, View convertView, ViewGroup parent) {
		mParentView = parent;
		ImageCell v = null;
		Resources res = mContext.getResources();
		int cellHeight = res.getDimensionPixelSize(R.dimen.grid_cell_height);
		v = new ImageCell(mContext);
		v.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, cellHeight));

		v.mCellNumber = position;
		v.mGrid = (GridView) mParentView;
		v.mEmpty = true;
		v.mChip = null;
		v.setOnDragListener(mDragListener);
		v.setBackgroundResource(R.color.cell_empty);
		return v;
	}

	public boolean isPool() {
		return pool;
	}

	public void setLikes(int[] likes) {
		this.likes = likes;
	}

}
