package com.muvari.restaurantmediator.yelp;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.muvari.restaurantmediator.mediator.ChipFactory;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Code sample for accessing the Yelp API V2.
 * 
 * edited by muvari
 * 
 * This program demonstrates the capability of the Yelp API version 2.0 by using
 * the Search API to query for businesses by a search term and location, and the
 * Business API to query additional information about the top result from the
 * search query.
 * 
 * <p>
 * See <a href="http://www.yelp.com/developers/documentation">Yelp
 * Documentation</a> for more info.
 * 
 */
public class YelpAPI {

	private static final String API_HOST = "api.yelp.com";
	private static final int SEARCH_LIMIT = 5;
	private static final String SEARCH_PATH = "/v2/search";
	private static final String BUSINESS_PATH = "/v2/business";

	OAuthService service;
	Token accessToken;

	/**
	 * Setup the Yelp API OAuth credentials.
	 * 
	 * @param consumerKey
	 *            Consumer key
	 * @param consumerSecret
	 *            Consumer secret
	 * @param token
	 *            Token
	 * @param tokenSecret
	 *            Token secret
	 */
	public YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
		this.service = new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
		this.accessToken = new Token(token, tokenSecret);
	}

	/**
	 * Creates and sends a request to the Search API by term and location.
	 * <p>
	 * See <a
	 * href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp
	 * Search API V2</a> for more info.
	 * 
	 * @param term
	 *            <tt>String</tt> of the search term to be queried
	 * @param location
	 *            <tt>String</tt> of the location
	 * @return <tt>String</tt> JSON Response
	 */
	public String searchForBusinessesByLocation(String term, String category_filter, String radius_filter, String location) {
		OAuthRequest request = createOAuthRequest(SEARCH_PATH);
		if (!term.isEmpty())
			request.addQuerystringParameter("term", term);
		if (!location.isEmpty())
			request.addQuerystringParameter("location", location);
		if (!category_filter.isEmpty())
			request.addQuerystringParameter("category_filter", category_filter);
		if (!radius_filter.isEmpty())
			request.addQuerystringParameter("radius_filter", radius_filter);
		request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));

		// request.addQuerystringParameter("sort", String.valueOf(2));
		return sendRequestAndGetResponse(request);
	}

	/**
	 * Creates and sends a request to the Business API by business ID.
	 * <p>
	 * See <a
	 * href="http://www.yelp.com/developers/documentation/v2/business">Yelp
	 * Business API V2</a> for more info.
	 * 
	 * @param businessID
	 *            <tt>String</tt> business ID of the requested business
	 * @return <tt>String</tt> JSON Response
	 */
	public String searchByBusinessId(String businessID) {
		OAuthRequest request = createOAuthRequest(BUSINESS_PATH + "/" + businessID);
		return sendRequestAndGetResponse(request);
	}

	/**
	 * Creates and returns an {@link OAuthRequest} based on the API endpoint
	 * specified.
	 * 
	 * @param path
	 *            API endpoint to be queried
	 * @return <tt>OAuthRequest</tt>
	 */
	private OAuthRequest createOAuthRequest(String path) {
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + path);
		return request;
	}

	/**
	 * Sends an {@link OAuthRequest} and returns the {@link Response} body.
	 * 
	 * @param request
	 *            {@link OAuthRequest} corresponding to the API request
	 * @return <tt>String</tt> body of API response
	 */
	private String sendRequestAndGetResponse(OAuthRequest request) {
		Log.i("test", "Querying " + request.getCompleteUrl() + " ...");
		this.service.signRequest(this.accessToken, request);
		Response response = request.send();
		return response.getBody();
	}

	public static class TwoStepOAuth extends DefaultApi10a {

		@Override
		public String getAccessTokenEndpoint() {
			return null;
		}

		@Override
		public String getAuthorizationUrl(Token arg0) {
			return null;
		}

		@Override
		public String getRequestTokenEndpoint() {
			return null;
		}
	}

	/**
	 * AsyncTaskLoader to query Yelp APIs
	 * @author Mark
	 *
	 */
	public static class SimpleDBLoader extends AsyncTaskLoader<JSONObject> {

		private Context context;
		private String term; //Extra query
		private List<String> likes; //List of sorted and combined liked food categories
		private List<String> dislikeNames; //The list of disliked food categories
		private String rad; //Radius
		private String loc; //Location
		private float rat; //Rating

		public SimpleDBLoader(Context context) {
			super(context);
		}

		public SimpleDBLoader(Context context, String term, List<String> likes, List<String> dislikes, String rad, String loc, float rat) {
			super(context);
			this.context = context;
			this.term = term;
			this.likes = likes;
			dislikeNames = dislikes;
			this.rad = rad;
			this.loc = loc;
			this.rat = rat;
		}

		/**
		 * Start the search for a restaurant
		 */
		@Override
		public JSONObject loadInBackground() {
			YelpAPI yelpApi = new YelpAPI(RRPC.CONSUMER_KEY, RRPC.CONSUMER_SECRET, RRPC.TOKEN, RRPC.TOKEN_SECRET);
			return queryApi(yelpApi);
		}

		/**
		 * Recursive query function that keeps querying until a valid match is met
		 * 
		 * @param yelpApi
		 * @return
		 */
		private JSONObject queryApi(YelpAPI yelpApi) {
			String cuisine = "";
			if (likes.size() > 0)
				cuisine = likes.get(0);
			
			//Base Case: End of List
			if (cuisine.equals(""))
				cuisine = "restaurants";

			//Search the top of the cuisine queue
			String searchResponseJSON = yelpApi.searchForBusinessesByLocation(term, cuisine, rad, loc);

			//Parse the response
			JSONParser parser = new JSONParser();
			JSONObject response = null;
			try {
				response = (JSONObject) parser.parse(searchResponseJSON);
			} catch (ParseException pe) {
				Log.i("test", "Error: could not parse JSON response:");
				Log.i("test", searchResponseJSON);
			}

			
			JSONArray businesses = (JSONArray) response.get("businesses");
			if (businesses != null && businesses.size() > 0) {
				//Loop through the results from the query
				for (int i = 0; i < businesses.size(); i++) {

					JSONObject bus = (JSONObject) businesses.get(i);
					//If the business is closed, or rated too poorly, move on
					if (!(Boolean) bus.get("is_closed") || ((Float)bus.get("rating")).floatValue() < rat) {
						JSONArray cats = (JSONArray) bus.get("categories");
						boolean disliked = false;
						//Make sure that none of the categories are disliked by any user
						for (int j = 0; j < cats.size(); j++) {
							if (dislikeNames.contains(cats.get(j))) {
								disliked = true;
								break;
							}
						}
						if (!disliked) return bus;
					}
				}
			} 
			
			//If we got here, it means none of the results in the query were valid, so we need to 
			//create a new query and try again with the next cuisine in the list
			likes.remove(0);
			return queryApi(yelpApi);
			
		}
	}

}
