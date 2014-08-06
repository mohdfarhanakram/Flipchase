package com.flipchase.android.domain;

import java.io.Serializable;

public class UserLocation implements Serializable {

	private static final long serialVersionUID = -7730127277030227473L;

	private String session_id;
	
	private Long city_id;
	
	private Long location_id;

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public Long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(Long location_id) {
		this.location_id = location_id;
	}
	
}
