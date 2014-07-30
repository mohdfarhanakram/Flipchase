package com.flipchase.android.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.flipchase.android.cache.FlipChaseObjectsCache;
import com.flipchase.android.domain.City;
import com.flipchase.android.domain.CityLocationWrapper;
import com.flipchase.android.domain.Location;
import com.flipchase.android.service.LocationService;

public class LocationServiceImpl implements LocationService {

	private Map<City, List<Location>> cityLocationMap;
	private CityLocationWrapper cityLocationWrapper ;
    
	public LocationServiceImpl() {
		this.cityLocationWrapper = FlipChaseObjectsCache.getInstance().getCityLocationWrapper();
		if(cityLocationWrapper != null) {
			prepareCityWiseLocatioMap(cityLocationWrapper);
		}
	}
	
	@Override
	public void initLocation(Location location) {
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

	@Override
	public void setCityLocationWrapper(CityLocationWrapper cityLocationWrapper) {
		this.cityLocationWrapper = cityLocationWrapper;
		prepareCityWiseLocatioMap(cityLocationWrapper);
		FlipChaseObjectsCache.getInstance().setCityLocationWrapper(cityLocationWrapper);
	}

	private void prepareCityWiseLocatioMap(CityLocationWrapper cityLocationWrapper) {
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

	@Override
	public City getCityWithId(String cityId) {
		List<City> cities = getAllCities();
		for(City city : cities) {
			if(city.getId().equalsIgnoreCase(cityId)) {
				return city;
			}
		}
		return null;
	}

	@Override
	public Location getLocationWithId(String locationId, City city) {
		List<Location> locations = getLocationsForCity(city);
		for(Location location : locations) {
			if(location.getId().equalsIgnoreCase(locationId)) {
				return location;
			}
		}
		return null;
	}

}
