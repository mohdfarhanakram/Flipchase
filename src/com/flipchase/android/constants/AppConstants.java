/**
 * 
 */
package com.flipchase.android.constants;

/**
 * @author m.farhan
 *
 */
public class AppConstants {
	
	public static final long SPLASH_WAITING_TIME = 3*1000;
	public final static int API_TIMEOUT = 20*1000;
	
	public final static String CATALOGUE_FRAGMENT   = "Catalogue";
	public final static String STORE_FRAGMENT   = "Store";
	public final static String LIST_FRAGMENT    = "List";
	public final static String COUPANS_FRAGMENT = "Coupons";
	
	public final static String IS_COMING_FROM_SPLASH = "coming_from_splash";
	
	public static String RESPONSE_INIT= "response_init";
    public static String RESPONSE_GET_CITIES = "response_get_cities";
    public static String RESPONSE_GET_LOCATIONS = "response_get_locations";
    public static String RESPONSE_GET_LOCATIONS_FOR_CITY = "response_get_locations_for_city";
    
    public static String RESPONSE_GET_LATEST_CATALOGUES = "response_get_latest_catalogue";
    public static String RESPONSE_GET_REATILERS = "response_get_retailers";
    
    public final static String STORES_FOR_RETAILER = "stores_for_retailer";
    
    /** Alerts **/
    public static final String NETWORK_CONNECTION_ERROR = "Please Check your network connection";
    
    public static final int MAX_CATALOG_STACK_SIZE = 3;
    
    public static final String STORE_DISTANCE_PRECISION_FOR_KM = "%.2f";
    public static final String DISTANCE_IN_KM = " Km.";

}
