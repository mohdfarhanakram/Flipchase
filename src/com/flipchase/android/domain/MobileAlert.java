package com.flipchase.android.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileAlert extends BaseDomain {

	private static final long serialVersionUID = 3737859707687492068L;

	private Long cityId;
	
	private Long locationId;
	
	private Long gcmUserId;
	
	private Long catalogueId;
	
	private Long cataloguePageId;
	
	private Long retailerId;

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getGcmUserId() {
		return gcmUserId;
	}

	public void setGcmUserId(Long gcmUserId) {
		this.gcmUserId = gcmUserId;
	}

	public Long getCatalogueId() {
		return catalogueId;
	}

	public void setCatalogueId(Long catalogueId) {
		this.catalogueId = catalogueId;
	}

	public Long getCataloguePageId() {
		return cataloguePageId;
	}

	public void setCataloguePageId(Long cataloguePageId) {
		this.cataloguePageId = cataloguePageId;
	}

	public Long getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(Long retailerId) {
		this.retailerId = retailerId;
	}
}
