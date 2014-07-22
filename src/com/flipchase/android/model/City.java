/**
 * 
 */
package com.flipchase.android.model;

import java.util.ArrayList;

/**
 * @author m.farhan
 *
 */
public class City {

	private String state;
	private int displayOrder;
	private int id;
	private String name;
	private String displayName;
	private ArrayList<CityLocation> cityLocations = new ArrayList<CityLocation>();
	
	public ArrayList<CityLocation> getCityLocations() {
		return cityLocations;
	}
	public void setCityLocations(ArrayList<CityLocation> cityLocations) {
		this.cityLocations = cityLocations;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	
}
