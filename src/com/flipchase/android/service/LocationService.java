package com.flipchase.android.service;

import java.util.List;

import com.flipchase.android.domain.Location;

public interface LocationService {

	public List<Location> getLocationForCity(String cityId);
}
