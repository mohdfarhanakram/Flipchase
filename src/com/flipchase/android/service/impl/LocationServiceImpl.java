package com.flipchase.android.service.impl;

import java.util.List;

import com.flipchase.android.domain.City;
import com.flipchase.android.domain.Location;
import com.flipchase.android.repository.LocationRepository;
import com.flipchase.android.repository.impl.LocationRepositoryImpl;
import com.flipchase.android.service.LocationService;

public class LocationServiceImpl implements LocationService {

	private LocationRepository locationRepository;
    
	public LocationServiceImpl() {
		locationRepository = new LocationRepositoryImpl();
	}
	
	@Override
	public void initLocation(Location location) {
		locationRepository.initLocation(location);
	}
	
	@Override
	public List<City> getAllCities() {
		return locationRepository.getAllCities();
	}
	
	@Override
	public List<Location> getAllLocations() {
		return locationRepository.getAllLocations();
	}
	
	@Override
	public List<Location> getLocationsForCity(Long cityId) {
		return locationRepository.getLocationsForCity(cityId);
	}

	@Override
	public List<Location> getLocationsForCity(String cityId) {
		if(cityId != null) {
			return getLocationsForCity(Long.parseLong(cityId));
		}
		return null;
	}
	
	@Override
	public void updateLocation(Long cityId, Long locationId) {
	}

}
