package com.flipchase.android.service;

import java.util.List;

import com.flipchase.android.domain.City;
import com.flipchase.android.domain.Location;

public interface LocationService {

	public void initLocation(Location location);
	
	public List<City> getAllCities();
	public List<Location> getAllLocations();
	
	public List<Location> getLocationsForCity(Long cityId);
	public List<Location> getLocationsForCity(String cityId);
	
	public void updateLocation(Long cityId, Long locationId);
}
