package com.flipchase.android.parser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.flipchase.android.domain.City;
import com.flipchase.android.domain.CityLocationWrapper;
import com.flipchase.android.domain.Location;
import com.flipchase.android.domain.Retailer;
import com.flipchase.android.util.Utils;

public class FlipChaseJsonParser {

   //private static final Gson gson = new Gson();
	//Arrays works up to 10 time faster than TypeRefence
	
	protected static ObjectMapper objectMapper = new ObjectMapper();
	
    public static List<City> parseCities(JSONObject jsonObjectData) throws JSONException {
    	City[] cities = null;
    	if (Utils.isJsonArray(jsonObjectData, "response")) {
            JSONArray itemsJsonArray = jsonObjectData.getJSONArray("response");
            String json = itemsJsonArray.toString();
            JsonFactory jsonFactory = new JsonFactory();
            try {
				JsonParser jp = jsonFactory.createJsonParser(json);
				cities = objectMapper.readValue(jp, City[].class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    	return Arrays.asList(cities);
    }


    public static List<Location> parseLocations(JSONObject jsonObjectData) throws JSONException {
    	Location[] locations = null;
    	if (Utils.isJsonArray(jsonObjectData, "response")) {
            JSONArray itemsJsonArray = jsonObjectData.getJSONArray("response");
            String json = itemsJsonArray.toString();
            JsonFactory jsonFactory = new JsonFactory();
            try {
				JsonParser jp = jsonFactory.createJsonParser(json);
				locations = objectMapper.readValue(jp, Location[].class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    	return Arrays.asList(locations);
    }
    
    public static CityLocationWrapper parseCityAndLocations(JSONObject jsonObjectData) throws JSONException {
    	CityLocationWrapper cityLocationWrapper = null;
    	if (Utils.isJsonObject(jsonObjectData, "response")) {
    		JSONObject itemJsonObject = jsonObjectData.getJSONObject("response");
            String json = itemJsonObject.toString();
            JsonFactory jsonFactory = new JsonFactory();
            try {
				JsonParser jp = jsonFactory.createJsonParser(json);
				cityLocationWrapper = objectMapper.readValue(jp, CityLocationWrapper.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    	return cityLocationWrapper;
    }

    public static List<Retailer> parseRetailers(JSONObject jsonObjectData) throws JSONException {
    	Retailer[] retailers = null;
    	if (Utils.isJsonArray(jsonObjectData, "response")) {
            JSONArray itemsJsonArray = jsonObjectData.getJSONArray("response");
            String json = itemsJsonArray.toString();
            JsonFactory jsonFactory = new JsonFactory();
            try {
				JsonParser jp = jsonFactory.createJsonParser(json);
				retailers = objectMapper.readValue(jp, Retailer[].class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    	return Arrays.asList(retailers);
    }
}