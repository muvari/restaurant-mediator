package com.muvari.restaurantmediator.dragndrop;

import com.muvari.restaurantmediator.mediator.ChipFactory;
import com.muvari.restaurantmediator.mediator.SurveyFragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * ImageCellAdapter for the 2 Pool grids (the primary, and extended list)
 * @author Mark
 *
 */
public class PoolCellAdapter extends ImageCellAdapter {

	public PoolCellAdapter(Context c, View.OnDragListener l, int count, int[] likes, int[] dislikes, SurveyFragment frag) {
		super(c, l, likes, frag);
		this.count = count;
		pool = true;

		this.likes = new int[likes.length + dislikes.length];
		System.arraycopy(likes, 0, this.likes, 0, likes.length);
		System.arraycopy(dislikes, 0, this.likes, likes.length, dislikes.length);
	}

	/**
	 * getView Return a view object for the grid.
	 * 
	 * @return ImageCell
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageCell v = null;
		
		if (count == ChipFactory.PRIMARY_CHIPS) { //If the primary grid
			if (contains(position)) {
				v = createEmptyCell(position, convertView, parent);
			} else {
				v = ChipFactory.createCatChip(mContext, position, true);
			}
		} else { //If the extended grid
			if (contains(position + ChipFactory.PRIMARY_CHIPS)) {
				v = createEmptyCell(position, convertView, parent);
			} else {
				v = ChipFactory.createCatChip(mContext, position, false);
			}
		}

		v.setOnTouchListener(fragment);
		v.setOnClickListener(fragment);
		v.setOnLongClickListener(fragment);

		return v;
	}
	
	public void setLikes(int[] likes, int[] dislikes) {
		this.likes = new int[likes.length + dislikes.length];
		System.arraycopy(likes, 0, this.likes, 0, likes.length);
		System.arraycopy(dislikes, 0, this.likes, likes.length, dislikes.length);
	}
}
