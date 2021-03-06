package com.flipchase.android.constants;

/** TODO: will me moved in properties **/
/** ? query parameter replacement **/
public final class URLConstants {

	//public static final String BASE_URL = "http://10.0.2.2:8080";
	//public static final String BASE_URL = "http://192.168.1.8:8080";
	public static final String BASE_URL = "http://flipchase.in";
	public static final String IMAGE_SERVER_URL = "http://flipchase.s3-website-ap-southeast-1.amazonaws.com/";	
	public static final String BASE_CATALOGUE_URL = BASE_URL + "/catalogue";
	public static final String BASE_LOCATION_URL = BASE_URL + "/location";
	public static final String BASE_RETAILER_URL = BASE_URL + "/retailer";
	public static final String BASE_GSM_URL = BASE_URL + "/gcm";
	public static final String BASE_MOBILE_ALERTS_URL = BASE_URL + "/mobilealerts";
	
	
	/************************** INIT WEBSERVICE FOR APP ***********************************************/
	public static final String INITIALIZE_LOCATION_URL = BASE_LOCATION_URL + "/initSettings";
	
	/************************** CATALOGUE ***********************************************/
	public static final String GET_LATEST_CATEGORY_URL = BASE_CATALOGUE_URL + "/getLatestCatalogues";
	public static final String GET_CATALOGUE_PAGES_FOR_CATEGORY_URL = BASE_CATALOGUE_URL + "/getCataloguePages/{catalogueid}";

	/************************** LOCATION ***********************************************/
	public static final String GET_ALL_CITIES_URL = BASE_LOCATION_URL + "/getAllCities";
	public static final String GET_ALL_LOCATIONS_URL = BASE_LOCATION_URL + "/getAllLocations";
	public static final String GET_LOCATIONS_FOR_CITY_URL = BASE_LOCATION_URL + "/getLocationsForCity/{cityid}";
	public static final String GET_ALL_CITIES_AND_LOCATIONS_URL = BASE_LOCATION_URL + "/getAllCitiAndLocations";
	public static final String SAVE_USER_CITY_AND_LOCATION_URL = BASE_LOCATION_URL + "/saveUserLocation";
	
	/************************** RETAILERS ***********************************************/
	public static final String GET_ALL_RETAILERS_URL = BASE_RETAILER_URL + "/getAllRetailers";
	public static final String GET_STORES_FOR_RETAILER_URL = BASE_RETAILER_URL + "/getStoresForRetailer/{retailerId}";
	
	/************************* GSM *******************************************************/
	public static final String SAVE_GSM_USER_URL = BASE_GSM_URL + "/saveGCMUser";
	
	/************************* MOBILE ALERTS *******************************************************/
	public static final String GET_MOBILE_ALERTS_URL = BASE_MOBILE_ALERTS_URL + "/getMobileAlertsForUser";
	public static final String GET_MOBILE_ALERTS_CATALOGUES_URL = BASE_MOBILE_ALERTS_URL + "/getMobileAlertsCataloguesForUser";
	public static final String SAVE_MOBILE_ALERT_URL = BASE_MOBILE_ALERTS_URL + "/saveMobileAlert";
	
	public static final String EXTRA_URL = "url";
	public static final String INTENT_SOURCE_ACTIVITY = "source_activity";
	
	
	public static final String GET_SUGGESTION_API = "http://54.179.169.97/solr/mysuggest?q=(";
	public static final String GET_SUGGESTION_SUFFIX_API = ")&wt=json";
	
	public static final String GET_SEARCH_URL = "http://flipchase.in/search/";
}
