/**
 * 
 */
package com.flipchase.android.model;

import java.util.HashMap;

/**
 * @author m.farhan
 *
 */
public class CityDumyData {

	public static HashMap<String,City> getAllCity(){
		HashMap<String,City> cityList = new HashMap<String,City>();
		
		
		
	    City city1 = new City();
	    city1.setState("Delhi");
		city1.setId(1);
		city1.setName("Delhi");
		city1.setDisplayOrder(1);
		city1.setDisplayName("");

		CityLocation cityLocation1 = new CityLocation();
		cityLocation1.setId(1);
		cityLocation1.setDisplayName("");
		cityLocation1.setName("Kundli");
		
		CityLocation cityLocation2 = new CityLocation();
		cityLocation2.setId(20);
		cityLocation2.setDisplayName("");
		cityLocation2.setName("Sector 3, Rohini");
		
		CityLocation cityLocation3 = new CityLocation();
		cityLocation3.setId(21);
		cityLocation3.setDisplayName("");
		cityLocation3.setName("Model Town");
		
		CityLocation cityLocation4 = new CityLocation();
		cityLocation4.setId(22);
		cityLocation4.setDisplayName("");
		cityLocation4.setName("Lakshmi Nagar");
		
		city1.getCityLocations().add(cityLocation1);
		city1.getCityLocations().add(cityLocation2);
		city1.getCityLocations().add(cityLocation3);
		city1.getCityLocations().add(cityLocation4);
		
		City city2 = new City();
		city2.setState("Maharashtra");
		city2.setId(5);
		city2.setName("Mumbai");
		city2.setDisplayOrder(2);
		city2.setDisplayName("");
		
		CityLocation cityLocation5 = new CityLocation();
		cityLocation5.setId(23);
		cityLocation5.setDisplayName("");
		cityLocation5.setName("Nariman Point");
		
		CityLocation cityLocation6 = new CityLocation();
		cityLocation6.setId(24);
		cityLocation6.setDisplayName("");
		cityLocation6.setName("Colaba");
		
		CityLocation cityLocation7 = new CityLocation();
		cityLocation7.setId(25);
		cityLocation7.setDisplayName("");
		cityLocation7.setName("Borivali");
		
		CityLocation cityLocation8 = new CityLocation();
		cityLocation8.setId(26);
		cityLocation8.setDisplayName("");
		cityLocation8.setName("Andheri East");
		
		city2.getCityLocations().add(cityLocation5);
		city2.getCityLocations().add(cityLocation6);
		city2.getCityLocations().add(cityLocation7);
		city2.getCityLocations().add(cityLocation8);
		
		cityList.put("Delhi", city1);
		cityList.put("Mumbai", city2);
		
		return cityList;
	}
}
