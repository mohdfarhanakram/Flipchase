package com.flipchase.android.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.flipchase.android.domain.City;
import com.flipchase.android.domain.CityLocationWrapper;
import com.flipchase.android.domain.Location;
import com.flipchase.android.repository.LocationRepository;
import com.flipchase.android.repository.impl.LocationRepositoryImpl;
import com.flipchase.android.service.LocationService;

public class LocationServiceImpl implements LocationService {

	private Map<City, List<Location>> cityLocationMap;
	private CityLocationWrapper cityLocationWrapper;
	
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
		return cityLocationWrapper.getCities();
	}
	
	@Override
	public List<Location> getAllLocations() {
		return cityLocationWrapper.getLocations();
	}
	
	@Override
	public void updateLocation(Long cityId, Long locationId) {
	}

	public CityLocationWrapper getCityLocationWrapper() {
		return cityLocationWrapper;
	}

	public void setCityLocationWrapper(CityLocationWrapper cityLocationWrapper) {
		this.cityLocationWrapper = cityLocationWrapper;
		prepareCityWiseLocatioMap();
	}

	private void prepareCityWiseLocatioMap() {
		cityLocationMap = new HashMap<City, List<Location>>();
		List<City> cities = cityLocationWrapper.getCities();
		List<Location> locations = cityLocationWrapper.getLocations();
		for(City city : cities) {
			for(Location location : locations) {
				if(location.getCity().toString().equalsIgnoreCase(city.getId())) {
					List<Location> existingLocs = cityLocationMap.get(city);
					if(existingLocs == null) {
						existingLocs = new ArrayList<Location>();
					}
					existingLocs.add(location);
					cityLocationMap.put(city, existingLocs);
				}
			}
		}
		
	}
	
	@Override
	public City getFirstCity() {
		Iterator<City> itr = cityLocationMap.keySet().iterator();
		return itr.next();
	}

	@Override
	public Location getFirstLocationForCity(City city) {
		List<Location> locations = cityLocationMap.get(city);
		return locations.get(0);
	}

	@Override
	public List<Location> getLocationsForCity(City city) {
		return cityLocationMap.get(city);
	}
}
