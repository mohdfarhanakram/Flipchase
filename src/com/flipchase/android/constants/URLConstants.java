package com.flipchase.android.constants;

/** TODO: will me moved in properties **/
/** ? query parameter replacement **/
public final class URLConstants {

	public static final String BASE_URL = "http://10.0.2.2:8080";
	//public static final String BASE_URL = "http://192.168.1.11:8080";
	//public static final String BASE_URL = "http://203.124.96.154:8080";
		
	public static final String BASE_CATALOGUE_URL = BASE_URL + "/catalogue";
	//public static final String BASE_CATALOGUE_URL = BASE_URL;
	public static final String BASE_LOCATION_URL = BASE_URL + "/location";
	
	public static final String GET_ALL_CATEGORY_URL = "http://10.0.2.2:8080/catalogue/getCataloguesForCategory/1";
	

	public static final String GET_LOCATIONS_FOR_CITY_URL = "http://203.124.96.154:8080//getLocationsForCity/1";
	
	/************************** CATALOGUE ***********************************************/
	public static final String GET_LATEST_CATEGORY_URL = BASE_CATALOGUE_URL + "/getLatestCatalogues";
	public static final String GET_CATALOGUE_PAGES_FOR_CATEGORY_URL = BASE_CATALOGUE_URL + "/getCataloguePages/{catalogueid}";

	/************************** LOCATION ***********************************************/
	public static final String INITIALIZE_LOCATION_URL = BASE_LOCATION_URL + "/initSettings";
	public static final String GET_ALL_CITIES_URL = BASE_LOCATION_URL + "/getAllCities";
	public static final String GET_ALL_LOCATIONS_URL = BASE_LOCATION_URL + "/getAllLocations";
	public static final String GET_LOCATIONS_FOR_CITY = BASE_LOCATION_URL + "/getLocationsForCity/{cityid}";
	public static final String GET_ALL_CITIES_AND_LOCATIONS_URL = BASE_LOCATION_URL + "/getAllCitiAndLocations";
}
