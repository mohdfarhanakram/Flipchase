package com.flipchase.android.domain;

public class AlertView {

	private Long id;
	
	private String type;
	
	private String name;
	
	private String locationStr;
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLocationStr(String locationStr) {
		this.locationStr = locationStr;
	}
	public String getLocationStr() {
		return locationStr;
	}
	
}
