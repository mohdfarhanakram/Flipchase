package com.flipchase.android.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.flipchase.android.domain.CatalogueDisplay;
import com.flipchase.android.domain.City;
import com.flipchase.android.domain.CityLocationWrapper;
import com.flipchase.android.domain.Location;
import com.flipchase.android.domain.Retailer;
import com.flipchase.android.domain.Store;
import com.flipchase.android.parcels.CatalogueChunk;
import com.flipchase.android.parcels.CataloguePagesChunk;
import com.flipchase.android.parcels.MobileAlertChunk;
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
    		JSONArray cities = itemJsonObject.getJSONArray("cities");
    		JSONArray locations = itemJsonObject.getJSONArray("locations");
    		
    		cityLocationWrapper = new CityLocationWrapper();
    		List<City> cityList = new ArrayList<City>();
    		List<Location> locationList = new ArrayList<Location>();
    		
    		cityLocationWrapper.setCities(cityList);
    		cityLocationWrapper.setLocations(locationList);
			for (int i = 0; i < cities.length(); i++) {
				JSONObject childJSONObject = cities.getJSONObject(i);
				City city = new City();
				city.setId(childJSONObject.getString("id"));
				city.setName(childJSONObject.getString("name"));
				city.setLongitude(childJSONObject.getDouble("longitude"));
				city.setLatitude(childJSONObject.getDouble("latitude"));
				cityList.add(city);
			}
			for (int i = 0; i < locations.length(); i++) {
				JSONObject childJSONObject = locations.getJSONObject(i);
				Location location = new Location();
				location.setId(childJSONObject.getString("id"));
				location.setName(childJSONObject.getString("name"));
				location.setLongitude(childJSONObject.getDouble("longitude"));
				location.setLatitude(childJSONObject.getDouble("latitude"));
				location.setCity(childJSONObject.getLong("city"));
				locationList.add(location);
			}
			/*
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
			*/
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
    
    public static List<Store> parseStoresForRetailers(JSONObject jsonObjectData) throws JSONException {
    	Store[] stores = null;
    	if (Utils.isJsonArray(jsonObjectData, "response")) {
            JSONArray itemsJsonArray = jsonObjectData.getJSONArray("response");
            String json = itemsJsonArray.toString();
            JsonFactory jsonFactory = new JsonFactory();
            try {
				JsonParser jp = jsonFactory.createJsonParser(json);
				stores = objectMapper.readValue(jp, Store[].class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    	return Arrays.asList(stores);
    }
    
    public static CatalogueChunk parseLatestCatalogues(JSONObject jsonObjectData) throws JSONException {
    	CatalogueChunk catalogues = null;
    	if (Utils.isJsonObject(jsonObjectData, "response")) {
    		JSONObject itemsJsonObject = jsonObjectData.getJSONObject("response");
            String json = itemsJsonObject.toString();
            JsonFactory jsonFactory = new JsonFactory();
            try {
				JsonParser jp = jsonFactory.createJsonParser(json);
				catalogues = objectMapper.readValue(jp, CatalogueChunk.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    	return catalogues;
    }
    
    public static CataloguePagesChunk parseCataloguePagesForCatalogue(JSONObject jsonObjectData) throws JSONException {
    	CataloguePagesChunk cataloguePagesChunk = null;
    	if (Utils.isJsonObject(jsonObjectData, "response")) {
            JSONObject itemsJsonObject = jsonObjectData.getJSONObject("response");
            String json = itemsJsonObject.toString();
            JsonFactory jsonFactory = new JsonFactory();
            try {
				JsonParser jp = jsonFactory.createJsonParser(json);
				cataloguePagesChunk = objectMapper.readValue(jp, CataloguePagesChunk.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    	return cataloguePagesChunk;
    }
    
    public static MobileAlertChunk parseAlerts(JSONObject jsonObjectData) throws JSONException {
    	MobileAlertChunk alerts = null;
    	if (Utils.isJsonObject(jsonObjectData, "response")) {
    		JSONObject itemsJsonObject = jsonObjectData.getJSONObject("response");
            String json = itemsJsonObject.toString();
            JsonFactory jsonFactory = new JsonFactory();
            try {
				JsonParser jp = jsonFactory.createJsonParser(json);
				alerts = objectMapper.readValue(jp, MobileAlertChunk.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    	return alerts;
    }
    
    public static CatalogueChunk parseAlertsCatalogues(JSONObject jsonObjectData) throws JSONException {
    	CatalogueChunk catalogues = null;
    	if (Utils.isJsonObject(jsonObjectData, "response")) {
    		JSONObject itemsJsonObject = jsonObjectData.getJSONObject("response");
            String json = itemsJsonObject.toString();
            JsonFactory jsonFactory = new JsonFactory();
            try {
				JsonParser jp = jsonFactory.createJsonParser(json);
				catalogues = objectMapper.readValue(jp, CatalogueChunk.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    	return catalogues;
    }
    
    
    public static ArrayList<String> parseSearchSuggestion(JSONObject jsonObjectData) throws JSONException{
    	ArrayList<String> suggestionList = new ArrayList<String>();
    	try{
    		
    		JSONObject jObj = jsonObjectData.optJSONObject("spellcheck");
    		JSONArray suggestionJArray = jObj.optJSONArray("suggestions");
    		if(suggestionJArray!=null && suggestionJArray.length()>1){
    			JSONObject jsObj = suggestionJArray.optJSONObject(1);
    			
    			JSONArray sugJArry = jsObj.optJSONArray("suggestion");
    			
    			if(sugJArry!=null){
    				for(int i=0; i<sugJArry.length(); i++){
    					suggestionList.add(sugJArry.optString(i));
    				}
    			}
    		}
    		
    		
    	}catch(Exception e){}
    	
    	
    	return suggestionList;
    }
    
    
    
    public static ArrayList<CatalogueDisplay> parseSearchCatalogueResult(JSONObject jsonObjectData) throws JSONException {
    	
    	ArrayList<CatalogueDisplay> list = new ArrayList<CatalogueDisplay>();
    	try{
    		JSONArray itemsJsonArray = jsonObjectData.getJSONArray("response");
    		
    		if(itemsJsonArray!=null){
    			for(int i=0; i<itemsJsonArray.length(); i++){
    				try{
    					 String json = itemsJsonArray.getJSONObject(i).toString();
    					 JsonFactory jsonFactory = new JsonFactory();
    					 JsonParser jp = jsonFactory.createJsonParser(json);
    					 
    					 CatalogueDisplay catalogueDisplay = objectMapper.readValue(jp, CatalogueDisplay.class);
    					 
    					 if(catalogueDisplay!=null){
    						 list.add(catalogueDisplay);
    					 }
    					 
    					
    				}catch(Exception e){
    					
    				}
    			}
    		}
    		
    		
    	}catch(Exception e){
    		
    	}
    	
    	return list;
    }
    
    
    
}