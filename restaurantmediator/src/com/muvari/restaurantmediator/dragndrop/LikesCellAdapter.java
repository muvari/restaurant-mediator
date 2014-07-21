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
public class LikesCellAdapter extends ImageCellAdapter {

	public LikesCellAdapter(Context c, View.OnDragListener l, int[] likes, SurveyFragment frag) {
		super(c, l, likes, frag);
	}

	/**
	 * getView Return a view object for the grid.
	 * 
	 * @return ImageCell
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageCell v = null;

		if (likes[position] >= 0) {
			int num = likes[position];
			if (likes[position] >= ChipFactory.PRIMARY_CHIPS) {
				num = num - ChipFactory.PRIMARY_CHIPS;
			}
			v = ChipFactory.createCatChip(mContext, num, likes[position] < ChipFactory.PRIMARY_CHIPS ? true : false);
		} else {
			v = createEmptyCell(position, convertView, parent);
		}

		v.setOnTouchListener(fragment);
		v.setOnClickListener(fragment);
		v.setOnLongClickListener(fragment);

		return v;
	}
}
