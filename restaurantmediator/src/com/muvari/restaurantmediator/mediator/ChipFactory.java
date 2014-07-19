package com.muvari.restaurantmediator.mediator;

import com.muvari.restaurantmediator.R;
import com.muvari.restaurantmediator.dragndrop.ImageCell;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChipFactory {
	public static String[] CATS = { "Italian", "Chinese", "Burgers", "Indian", "Steaks" };
	public static int PRIMARY_CHIPS = 24;

	// Icon Drawable, Color, Visible name, query name
	private static final int ICON = 0;
	private static final int COLOR = 1;
	private static final int NAME = 2;
	private static final int SEARCH_NAME = 3;

	// Priority Categories
	public static Integer[][] PRIORITY_CATS = { 
			{ R.drawable.italian, R.color.light_red, R.string.asian_fusion, R.string.q_asian_fusion },
			{ R.drawable.italian, R.color.light_red, R.string.breakfast, R.string.q_breakfast },
			{ R.drawable.italian, R.color.light_red, R.string.burgers, R.string.q_burgers },
			{ R.drawable.italian, R.color.light_red, R.string.cafes, R.string.q_cafes },
			{ R.drawable.italian, R.color.light_red, R.string.chinese, R.string.q_chinese },
			{ R.drawable.italian, R.color.light_red, R.string.fast_food, R.string.q_fast_food },
			{ R.drawable.italian, R.color.light_red, R.string.greek, R.string.q_greek },
			{ R.drawable.italian, R.color.light_red, R.string.indian, R.string.q_indian },
			{ R.drawable.italian, R.color.light_red, R.string.italian, R.string.q_italian },
			{ R.drawable.italian, R.color.light_red, R.string.japanese, R.string.q_japanese },
			{ R.drawable.italian, R.color.light_red, R.string.korean, R.string.q_korean },
			{ R.drawable.italian, R.color.light_red, R.string.mediterranean, R.string.q_mediterranean },
			{ R.drawable.italian, R.color.light_purple, R.string.mexican, R.string.q_mexican },
			{ R.drawable.italian, R.color.light_red, R.string.american_new, R.string.q_american_new },
			{ R.drawable.italian, R.color.light_orange, R.string.pizza, R.string.q_pizza },
			{ R.drawable.italian, R.color.light_blue, R.string.sandwiches, R.string.q_sandwiches },
			{ R.drawable.italian, R.color.light_red, R.string.seafood, R.string.q_seafood },
			{ R.drawable.italian, R.color.light_red, R.string.southern, R.string.q_southern },
			{ R.drawable.italian, R.color.light_red, R.string.steak, R.string.q_steak },
			{ R.drawable.italian, R.color.light_red, R.string.sushi, R.string.q_sushi },
			{ R.drawable.italian, R.color.light_red, R.string.thai, R.string.q_thai },
			{ R.drawable.italian, R.color.light_green, R.string.american_trad, R.string.q_american_trad },
			{ R.drawable.italian, R.color.light_red, R.string.vegan, R.string.q_vegan },
			{ R.drawable.italian, R.color.light_red, R.string.vegetarian, R.string.q_vegetarian }, };
	
	//The Rest
	public static Integer[][] OTHER_CATS = { 
		{ R.drawable.italian, R.color.light_red, R.string.afghan, R.string.q_afghan },
		{ R.drawable.italian, R.color.light_red, R.string.african, R.string.q_african },
		{ R.drawable.italian, R.color.light_red, R.string.senegalese, R.string.q_Senegalese },
		{ R.drawable.italian, R.color.light_red, R.string.southafrican, R.string.q_southafrican },
		{ R.drawable.italian, R.color.light_red, R.string.arabian, R.string.q_arabian },
		{ R.drawable.italian, R.color.light_red, R.string.argentine, R.string.q_argentine },
		{ R.drawable.italian, R.color.light_red, R.string.armenian, R.string.q_armenian },
		{ R.drawable.italian, R.color.light_red, R.string.australian, R.string.q_australian },
		{ R.drawable.italian, R.color.light_red, R.string.austrian, R.string.q_austrian },
		{ R.drawable.italian, R.color.light_red, R.string.bangladeshi, R.string.q_bangladeshi },
		{ R.drawable.italian, R.color.light_red, R.string.bbq, R.string.q_bbq },
		{ R.drawable.italian, R.color.light_red, R.string.basque, R.string.q_basque },
		{ R.drawable.italian, R.color.light_purple, R.string.belgian, R.string.q_belgian },
		{ R.drawable.italian, R.color.light_red, R.string.brasseries, R.string.q_brasseries },
		{ R.drawable.italian, R.color.light_orange, R.string.brazilian, R.string.q_brazilian },
		{ R.drawable.italian, R.color.light_blue, R.string.british, R.string.q_british },
		{ R.drawable.italian, R.color.light_red, R.string.buffets, R.string.q_buffets },
		{ R.drawable.italian, R.color.light_red, R.string.burmese, R.string.q_burmese },
		{ R.drawable.italian, R.color.light_red, R.string.cafeteria, R.string.q_cafeteria },
		{ R.drawable.italian, R.color.light_red, R.string.cajun, R.string.q_cajun },
		{ R.drawable.italian, R.color.light_red, R.string.cambodian, R.string.q_cambodian },
		{ R.drawable.italian, R.color.light_green, R.string.caribbean, R.string.q_caribbean },
		{ R.drawable.italian, R.color.light_red, R.string.dominican, R.string.q_dominican },
		{ R.drawable.italian, R.color.light_red, R.string.haitian, R.string.q_haitian },
		{ R.drawable.italian, R.color.light_red, R.string.puertorican, R.string.q_puertorican },
		{ R.drawable.italian, R.color.light_red, R.string.trinidadian, R.string.q_trinidadian },
		{ R.drawable.italian, R.color.light_red, R.string.catalan, R.string.q_catalan },
		{ R.drawable.italian, R.color.light_red, R.string.cheesesteaks, R.string.q_cheesesteaks },
		{ R.drawable.italian, R.color.light_red, R.string.chicken_wings, R.string.q_chicken_wings },
		{ R.drawable.italian, R.color.light_red, R.string.cantonese, R.string.q_cantonese },
		{ R.drawable.italian, R.color.light_red, R.string.dim_sum, R.string.q_dim_sum },
		{ R.drawable.italian, R.color.light_red, R.string.shanghainese, R.string.q_shanghainese },
		{ R.drawable.italian, R.color.light_red, R.string.sechuan, R.string.q_sechuan },
		{ R.drawable.italian, R.color.light_red, R.string.comfort_food, R.string.q_comfort_food },
		{ R.drawable.italian, R.color.light_red, R.string.creperies, R.string.q_creperies },
		{ R.drawable.italian, R.color.light_red, R.string.cuban, R.string.q_cuban },
		{ R.drawable.italian, R.color.light_red, R.string.czech, R.string.q_czech },
		{ R.drawable.italian, R.color.light_purple, R.string.delis, R.string.q_delis },
		{ R.drawable.italian, R.color.light_red, R.string.diners, R.string.q_diners },
		{ R.drawable.italian, R.color.light_orange, R.string.ethiopian, R.string.q_ethiopian },
		{ R.drawable.italian, R.color.light_blue, R.string.filipino, R.string.q_filipino },
		{ R.drawable.italian, R.color.light_red, R.string.fishnchips, R.string.q_fishnchips },
		{ R.drawable.italian, R.color.light_red, R.string.fondue, R.string.q_fondue },
		{ R.drawable.italian, R.color.light_red, R.string.food_court, R.string.q_food_court },
		{ R.drawable.italian, R.color.light_red, R.string.food_stands, R.string.q_food_stands },
		{ R.drawable.italian, R.color.light_red, R.string.french, R.string.q_french },
		{ R.drawable.italian, R.color.light_green, R.string.gastropubs, R.string.q_gastropubs },
		{ R.drawable.italian, R.color.light_red, R.string.german, R.string.q_german },
		{ R.drawable.italian, R.color.light_red, R.string.gluten_free, R.string.q_gluten_free },
		{ R.drawable.italian, R.color.light_red, R.string.halal, R.string.q_halal },
		{ R.drawable.italian, R.color.light_red, R.string.hawaiian, R.string.q_hawaiian },
		{ R.drawable.italian, R.color.light_red, R.string.himalyan, R.string.q_himalyan },
		{ R.drawable.italian, R.color.light_red, R.string.hot_dogs, R.string.q_hot_dogs },
		{ R.drawable.italian, R.color.light_red, R.string.hot_pot, R.string.q_hot_pot },
		{ R.drawable.italian, R.color.light_red, R.string.hungarian, R.string.q_hungarian },
		{ R.drawable.italian, R.color.light_red, R.string.iberian, R.string.q_iberian },
		{ R.drawable.italian, R.color.light_red, R.string.indonesian, R.string.q_indonesian },
		{ R.drawable.italian, R.color.light_red, R.string.irish, R.string.q_irish },
		{ R.drawable.italian, R.color.light_red, R.string.ramen, R.string.q_ramen },
		{ R.drawable.italian, R.color.light_red, R.string.kosher, R.string.q_kosher },
		{ R.drawable.italian, R.color.light_red, R.string.laotian, R.string.q_laotian },
		{ R.drawable.italian, R.color.light_purple, R.string.latin, R.string.q_latin },
		{ R.drawable.italian, R.color.light_red, R.string.columbian, R.string.q_columbian },
		{ R.drawable.italian, R.color.light_orange, R.string.salvadoran, R.string.q_salvadoran },
		{ R.drawable.italian, R.color.light_blue, R.string.venezuelan, R.string.q_venezuelan },
		{ R.drawable.italian, R.color.light_red, R.string.raw_food, R.string.q_raw_food },
		{ R.drawable.italian, R.color.light_red, R.string.malaysian, R.string.q_malaysian },
		{ R.drawable.italian, R.color.light_red, R.string.falafel, R.string.q_falafel },
		{ R.drawable.italian, R.color.light_red, R.string.middle_eastern, R.string.q_middle_eastern },
		{ R.drawable.italian, R.color.light_red, R.string.egyptian, R.string.q_egyptian },
		{ R.drawable.italian, R.color.light_green, R.string.lebanese, R.string.q_lebanese },
		{ R.drawable.italian, R.color.light_red, R.string.modern_european, R.string.q_modern_european },
		{ R.drawable.italian, R.color.light_red, R.string.mongolian, R.string.q_mongolian },
		{ R.drawable.italian, R.color.light_red, R.string.moroccan, R.string.q_moroccan },
		{ R.drawable.italian, R.color.light_red, R.string.pakistani, R.string.q_pakistani },
		{ R.drawable.italian, R.color.light_red, R.string.persian, R.string.q_persian },
		{ R.drawable.italian, R.color.light_red, R.string.peruvian, R.string.q_peruvian },
		{ R.drawable.italian, R.color.light_red, R.string.polish, R.string.q_polish },
		{ R.drawable.italian, R.color.light_red, R.string.portuguese, R.string.q_portuguese },
		{ R.drawable.italian, R.color.light_red, R.string.russian, R.string.q_russian },
		{ R.drawable.italian, R.color.light_red, R.string.salad, R.string.q_salad },
		{ R.drawable.italian, R.color.light_red, R.string.scandinavian, R.string.q_scandinavian },
		{ R.drawable.italian, R.color.light_red, R.string.scottish, R.string.q_scottish },
		{ R.drawable.italian, R.color.light_red, R.string.singaporean, R.string.q_singaporean },
		{ R.drawable.italian, R.color.light_red, R.string.slovakian, R.string.q_slovakian },
		{ R.drawable.italian, R.color.light_purple, R.string.soul_food, R.string.q_soul_food },
		{ R.drawable.italian, R.color.light_red, R.string.soup, R.string.q_soup },
		{ R.drawable.italian, R.color.light_orange, R.string.spanish, R.string.q_spanish },
		{ R.drawable.italian, R.color.light_blue, R.string.taiwanese, R.string.q_taiwanese },
		{ R.drawable.italian, R.color.light_red, R.string.tapas, R.string.q_tapas },
		{ R.drawable.italian, R.color.light_red, R.string.tapas_small, R.string.q_tapas_small },
		{ R.drawable.italian, R.color.light_red, R.string.tex_mex, R.string.q_tex_mex },
		{ R.drawable.italian, R.color.light_red, R.string.turkish, R.string.q_turkish },
		{ R.drawable.italian, R.color.light_red, R.string.ukrainian, R.string.q_ukrainian },
		{ R.drawable.italian, R.color.light_green, R.string.uzbek, R.string.q_uzbek },
		{ R.drawable.italian, R.color.light_red, R.string.vietnamese, R.string.q_vietnamese }};

	public static ImageCell createCatChip(Context context, int num, boolean primary) {
		Integer[][] pool = primary ? PRIORITY_CATS : OTHER_CATS;
		int id = primary ? num : num+PRIMARY_CHIPS;
		ImageCell newView = new ImageCell(context);
		RelativeLayout chip = new RelativeLayout(context);
		LayoutInflater.from(context).inflate(R.layout.cat_chip, chip);

		Integer[] cat_info = pool[num];
		FoodCategory object = new FoodCategory(id, cat_info[ICON], cat_info[COLOR], cat_info[NAME], cat_info[SEARCH_NAME]);
		((ImageView) (chip.findViewById(R.id.chip_image))).setImageResource(object.getIcon());
		((TextView) (chip.findViewById(R.id.chip_text))).setText(object.getName(context));

		// Temp add different colors
		int[] colors = { R.color.light_blue, R.color.light_red, R.color.light_green, R.color.light_orange, R.color.light_purple };
		((TextView) (chip.findViewById(R.id.chip_text))).setBackgroundResource(colors[num % 5]);

		newView.mEmpty = false;
		newView.mCellNumber = id;
		newView.mChip = chip;
		newView.setmCat(object);
		newView.addView(chip);

		return newView;
	}
	
	public static String getStringFromId(Context context, int id) {
		boolean primary = true;
		if (id > PRIMARY_CHIPS) {
			id -= PRIMARY_CHIPS;
			primary = false;
		}
		Integer[] cuisine = primary ? PRIORITY_CATS[id] : OTHER_CATS[id];
		return context.getResources().getString(cuisine[SEARCH_NAME]);
	}

}
