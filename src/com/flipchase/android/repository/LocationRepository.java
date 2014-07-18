package com.flipchase.android.repository;

import java.util.List;

import com.flipchase.android.domain.City;
import com.flipchase.android.domain.Location;

public interface LocationRepository {
	
	public void initLocation(Location location)	;
	
	public List<City> getAllCities();
	
	public List<Location> getLocationsForCity(Long cityId);
	
	public void updateLocation(Long cityId, Long locationId);
}
