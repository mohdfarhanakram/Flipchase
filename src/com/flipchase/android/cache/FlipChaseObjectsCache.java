package com.flipchase.android.cache;

import com.flipchase.android.domain.CityLocationWrapper;

public final class FlipChaseObjectsCache {

	private static FlipChaseObjectsCache flipChaseObjectsCache;
	
	private CityLocationWrapper cityLocationWrapper;
	
	private FlipChaseObjectsCache() {}
	
	public static FlipChaseObjectsCache getInstance() {
		if(flipChaseObjectsCache == null) {
			flipChaseObjectsCache = new FlipChaseObjectsCache();
		}
		return flipChaseObjectsCache;
	}
	
	public CityLocationWrapper getCityLocationWrapper() {
		return cityLocationWrapper;
	}

	public void setCityLocationWrapper(CityLocationWrapper cityLocationWrapper) {
		this.cityLocationWrapper = cityLocationWrapper;
	}
	
}
