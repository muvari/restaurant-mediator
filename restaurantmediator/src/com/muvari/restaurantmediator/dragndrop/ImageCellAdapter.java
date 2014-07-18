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
import com.muvari.restaurantmediator.mediator.ChipFactory;
import com.muvari.restaurantmediator.mediator.SurveyFragment;

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
	private boolean pool = false;
	private boolean likeAdapter = true;
	private int[] likes;
	private SurveyFragment fragment;

	public boolean isPool() {
		return pool;
	}

	public void setPool(boolean pool) {
		this.pool = pool;
	}

	public ImageCellAdapter(Context c) {
		mContext = c;
		mDragListener = null;
	}

	public ImageCellAdapter(Context c, View.OnDragListener l, int[] likes, boolean likeAdapter, SurveyFragment frag) {
		mContext = c;
		mDragListener = l;
		this.likes = likes;
		this.likeAdapter = likeAdapter;
		this.fragment = frag;
	}
	
	public ImageCellAdapter(Context c, View.OnDragListener l, int count, int[] likes, int[] dislikes, SurveyFragment frag) {
		mContext = c;
		mDragListener = l;
		this.count = count;
		pool = true;
		this.likes = likes;
		this.fragment = frag;
		
		this.likes = new int[likes.length + dislikes.length];
		System.arraycopy(likes, 0, this.likes, 0, likes.length);
		System.arraycopy(dislikes, 0, this.likes, likes.length, dislikes.length);
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
		//TODO: Use inheritance and override getView for different Adapters
		ImageCell v = null;
		if (pool) {
			if (count == ChipFactory.PRIMARY_CHIPS) {
				if (contains(position)) {
					v = createEmptyCell(position, convertView, parent);
				} else {
					v = ChipFactory.createCatChip(mContext, position, true);
				}
			} else {
				if (contains(position+ChipFactory.PRIMARY_CHIPS)) {
					v = createEmptyCell(position, convertView, parent);
				} else {
					v = ChipFactory.createCatChip(mContext, position, false);
				}
			}
		} else {
			
			if (likes[position] >= 0) {
				int num = likes[position];
				if (likes[position] >= ChipFactory.PRIMARY_CHIPS) {
					num = num - ChipFactory.PRIMARY_CHIPS;
				}
				v = ChipFactory.createCatChip(mContext, num, likes[position] < ChipFactory.PRIMARY_CHIPS ? true : false);
			} else {
				v = createEmptyCell(position, convertView, parent);
			}
			
		}

		v.setOnTouchListener(fragment);
		v.setOnClickListener(fragment);
		v.setOnLongClickListener(fragment);

		return v;
	}
	
	private boolean contains(int pos) {
		for (int i = 0; i < likes.length; i++) {
			if (likes[i] == pos)
				return true;
		}
		return false;
	}
	private ImageCell createEmptyCell(int position, View convertView, ViewGroup parent) {
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

} 
