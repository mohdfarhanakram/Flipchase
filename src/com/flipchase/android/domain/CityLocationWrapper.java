package com.flipchase.android.domain;

import java.util.List;

public class CityLocationWrapper {

	List<City> cities;
	
	List<Location> locations;

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
	
}
