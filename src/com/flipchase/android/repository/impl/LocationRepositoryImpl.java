package com.flipchase.android.repository.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonParseException;

import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.domain.City;
import com.flipchase.android.domain.Location;
import com.flipchase.android.repository.LocationRepository;
import com.flipchase.android.util.ServiceHandler;

public class LocationRepositoryImpl extends BaseRepository implements LocationRepository {

	@Override
	public void initLocation(Location location) {
		String json = convertToJson(location);
		serviceHandler.makeServiceCall(URLConstants.INITIALIZE_LOCATION_URL, ServiceHandler.POST, json);
	}

	@Override
	public List<City> getAllCities() {
		String jsonStr = serviceHandler.makeServiceCall(URLConstants.GET_ALL_CITIES_URL, ServiceHandler.GET);
		City[] cities = null;
        try {
			jp = jsonFactory.createJsonParser(jsonStr);
			cities = objectMapper.readValue(jp, City[].class);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        return new ArrayList<City>(Arrays.asList(cities));
	}

	@Override
	public List<Location> getAllLocations() {
		String jsonStr = serviceHandler.makeServiceCall(URLConstants.GET_ALL_LOCATIONS_URL, ServiceHandler.GET);
		Location[] locations = null;
        try {
			jp = jsonFactory.createJsonParser(jsonStr);
			locations = objectMapper.readValue(jp, Location[].class);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        return new ArrayList<Location>(Arrays.asList(locations));
	}
	
	@Override
	public List<Location> getLocationsForCity(Long cityId) {
		String jsonStr = serviceHandler.makeServiceCall(URLConstants.GET_LOCATIONS_FOR_CITY, ServiceHandler.GET, null, cityId);
		Location[] locations = null;
        try {
			jp = jsonFactory.createJsonParser(jsonStr);
			locations = objectMapper.readValue(jp, Location[].class);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        return new ArrayList<Location>(Arrays.asList(locations));
	}

	@Override
	public void updateLocation(Long cityId, Long locationId) {
	}

}
