package com.muvari.restaurantmediator.mediator;

import android.content.Context;

public class FoodCategory {
	
	private int id;
	private int icon;
	private int color;
	private int name;
	private int queryName;
	
	public FoodCategory(int id, int icon, int color, int name, int qName) {
		this.setId(id);
		this.icon = icon;
		this.color = color;
		this.name = name;
		this.queryName = qName;
	}
	
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public String getName(Context context) {
		return context.getResources().getString(name);
	}

	public String getQueryName(Context context) {
		return context.getResources().getString(queryName);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
