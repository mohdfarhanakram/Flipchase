package com.flipchase.android.service;

import java.util.List;

import com.flipchase.android.domain.City;
import com.flipchase.android.domain.CityLocationWrapper;
import com.flipchase.android.domain.Location;

public interface LocationService {

	public void initLocation(Location location);
	
	public List<City> getAllCities();
	public List<Location> getAllLocations();
	
	public List<Location> getLocationsForCity(City city);
	public City getCityWithId(String cityId);
	public Location getLocationWithId(String locationId, City city);
	public void updateLocation(Long cityId, Long locationId);
	
	public void setCityLocationWrapper(CityLocationWrapper cityLocationWrapper);
	/** Default position **/
	public City getFirstCity();
	public Location getFirstLocationForCity(City city);
	
}
