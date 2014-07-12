package com.muvari.restaurantmediator.mediator;

import com.muvari.restaurantmediator.R;
import com.muvari.restaurantmediator.dragndrop.ImageCell;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChipFactory {
	public static String[] CATS = { "Italian", "Chinese", "Burgers", "Indian",
			"Steaks" };

	// Icon Drawable, Color, Visible name, query name
	private static final int ICON = 0;
	private static final int COLOR = 1;
	private static final int NAME = 2;
	private static final int SEARCH_NAME = 3;

	// Priority Categories
	public static Integer[][] PRIORITY_CATS = {
			{ R.drawable.italian, R.color.light_blue, R.string.sandwiches,
					R.string.q_sandwiches },
			{ R.drawable.italian, R.color.light_red, R.string.fast_food,
					R.string.q_fast_food },
			{ R.drawable.italian, R.color.light_green, R.string.american_trad,
					R.string.q_american_trad },
			{ R.drawable.italian, R.color.light_orange, R.string.pizza,
					R.string.q_pizza },
			{ R.drawable.italian, R.color.light_purple, R.string.mexican,
					R.string.mexican },
			{ R.drawable.italian, R.color.light_red, R.string.burgers,
					R.string.q_burgers },
			{ R.drawable.italian, R.color.light_red, R.string.chinese,
					R.string.q_chinese },
			{ R.drawable.italian, R.color.light_red, R.string.american_new,
					R.string.q_american_new },
			{ R.drawable.italian, R.color.light_red, R.string.italian,
					R.string.q_italian },
			{ R.drawable.italian, R.color.light_red, R.string.japanese,
					R.string.q_japanese },
			{ R.drawable.italian, R.color.light_red, R.string.indian,
					R.string.q_indian },
			{ R.drawable.italian, R.color.light_red, R.string.breakfast,
					R.string.q_breakfast },
			{ R.drawable.italian, R.color.light_red, R.string.sushi,
					R.string.q_sushi },
			{ R.drawable.italian, R.color.light_red, R.string.asian_fusion,
					R.string.q_asian_fusion },
			{ R.drawable.italian, R.color.light_red, R.string.mediterranean,
					R.string.q_mediterranean },
			{ R.drawable.italian, R.color.light_red, R.string.steak,
					R.string.q_steak },
			{ R.drawable.italian, R.color.light_red, R.string.cafes,
					R.string.q_cafes },
			{ R.drawable.italian, R.color.light_red, R.string.greek,
					R.string.q_greek },
			{ R.drawable.italian, R.color.light_red, R.string.vegan,
					R.string.q_vegan },
			{ R.drawable.italian, R.color.light_red, R.string.vegetarian,
					R.string.vegetarian },
			{ R.drawable.italian, R.color.light_red, R.string.seafood,
					R.string.q_seafood },
			{ R.drawable.italian, R.color.light_red, R.string.southern,
					R.string.q_southern },
			{ R.drawable.italian, R.color.light_red, R.string.thai,
					R.string.q_thai },
			{ R.drawable.italian, R.color.light_red, R.string.korean,
					R.string.q_korean } };

	public static ImageCell createCatChip(Context context, int num) {
		ImageCell newView = new ImageCell(context);
		RelativeLayout chip = new RelativeLayout(context);
		LayoutInflater.from(context).inflate(R.layout.cat_chip, chip);
		
		Integer[] cat_info = PRIORITY_CATS[num];
		FoodCategory object= new FoodCategory(num, cat_info[ICON], cat_info[COLOR], cat_info[NAME], cat_info[SEARCH_NAME]);
		((ImageView) (chip.findViewById(R.id.chip_image))).setImageResource(object.getIcon());
		((TextView) (chip.findViewById(R.id.chip_text))).setText(object.getName(context));
		
		//Temp add different colors
		int[] colors = {R.color.light_blue, R.color.light_red, R.color.light_green, R.color.light_orange, R.color.light_purple};
		((TextView) (chip.findViewById(R.id.chip_text))).setBackgroundResource(colors[num % 5]);

		newView.mEmpty = false;
		newView.mCellNumber = num;
		newView.mChip = chip;
		newView.setmCat(object);
		newView.addView(chip);

		return newView;
	}

}
