package com.muvari.restaurantmediator.yelp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.muvari.restaurantmediator.mediator.SummaryActivity.SummaryFragment;

/**
 * AsyncTaskLoader to query Yelp APIs
 * 
 * @author Mark
 * 
 */
public class SimpleYelpLoader extends AsyncTaskLoader<JSONObject> {

	private String term; // Extra query
	private List<String> likes; // List of sorted and combined liked food
								// categories
	private List<String> dislikeNames; // The list of disliked food
										// categories
	private String rad; // Radius
	private String loc; // Location
	private float rat; // Rating

	private float originalRating;
	private List<String> originalLikes;

	public SimpleYelpLoader(Context context) {
		super(context);
	}

	public SimpleYelpLoader(Context context, String term, List<String> likes, List<String> dislikes, String rad, String loc, float rat) {
		super(context);
		this.term = term;
		this.likes = likes;
		dislikeNames = dislikes;
		this.rad = rad;
		this.loc = loc;
		this.rat = rat;
		originalLikes = new ArrayList<String>(likes);
		originalRating = rat + 0;
	}

	/**
	 * Start the search for a restaurant
	 */
	@Override
	public JSONObject loadInBackground() {
		YelpAPI yelpApi = new YelpAPI(RRPC.CONSUMER_KEY, RRPC.CONSUMER_SECRET, RRPC.TOKEN, RRPC.TOKEN_SECRET);
		return queryApi(yelpApi, likes, rat, rad);
	}

	/**
	 * Recursive query function that keeps querying until a valid match is
	 * met
	 * 
	 * @param yelpApi
	 * @return
	 */
	private JSONObject queryApi(YelpAPI yelpApi, List<String> likes, float rat, String rad) {
		String cuisine = "";
		if (likes.size() > 0)
			cuisine = likes.get(0);

		// Base Case: End of List
		if (cuisine.equals(""))
			cuisine = "restaurants";

		// Search the top of the cuisine queue
		String searchResponseJSON = yelpApi.searchForBusinessesByLocation(term, cuisine, rad, loc);

		// Parse the response
		JSONParser parser = new JSONParser();
		JSONObject response = null;
		try {
			response = (JSONObject) parser.parse(searchResponseJSON);
		} catch (ParseException pe) {
			Log.i("test", "Error: could not parse JSON response:");
			Log.i("test", searchResponseJSON);
		}

		JSONArray businesses = (JSONArray) response.get("businesses");

		// Randomize Order of the businesses
		businesses = randomizeBusinesses(businesses);

		if (businesses != null && businesses.size() > 0) {
			// Loop through the results from the query
			for (int i = 0; i < businesses.size(); i++) {

				JSONObject bus = (JSONObject) businesses.get(i);
				// If the business is closed, or rated too poorly, move on
				Boolean isClosed = (Boolean) bus.get("is_closed");
				Double rating = ((Double) bus.get("rating"));
				if (!isClosed && rating > rat) {
					JSONArray cats = (JSONArray) bus.get("categories");
					boolean disliked = false;
					// Make sure that none of the categories are disliked by
					// any user
					for (int j = 0; j < cats.size(); j++) {
						JSONArray cat = (JSONArray) cats.get(j);
						if (dislikeNames.contains((cat.get(1)))) {
							disliked = true;
							break;
						}
					}
					if (!disliked)
						return bus;
				}
			}
		}

		// If we got here, it means none of the results in the query were
		// valid, so we need to
		// create a new query and try again with the next cuisine in the
		// list
		if (likes.size() != 0) {
			likes.remove(0);
			return queryApi(yelpApi, likes, rat, rad);
		} else if (rat > 0) { // If the list is empty, lower rating
								// threshold, start over
			rat -= 1;
			return queryApi(yelpApi, originalLikes, rat, rad);
		} else { // If rating is lowest, raise the radius, start over
			float f = Float.parseFloat(rad) + (5*SummaryFragment.METERS_IN_MILE);
			return queryApi(yelpApi, originalLikes, originalRating, f + "");
		}

	}

	@SuppressWarnings("unchecked")
	private JSONArray randomizeBusinesses(JSONArray array) {
		Random rnd = new Random();
		for (int i = array.size() - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			Object a = array.get(index);
			array.set(index, array.get(i));
			array.set(i, a);
		}
		return array;
	}
}