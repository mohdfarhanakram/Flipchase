/**
 * 
 */
package com.flipchase.android.constants;

/**
 * @author m.farhan
 *
 */
public interface FlipchaseApi {
	
	public static final String SERVER_URL_1 = "http://203.124.96.154:8080/";
	
	public static final String BASE_URL = SERVER_URL_1;
	
	public static final String GET_CATALOG_FOR_CATEGORY       = BASE_URL + "getCataloguesForCategory/";    // getCataloguesForCategory/<cityId>
	public static final String GET_LOCATION_FOR_CITY          = BASE_URL + "getLocationsForCity/";         // getLocationsForCity/<cityId>
	

}
