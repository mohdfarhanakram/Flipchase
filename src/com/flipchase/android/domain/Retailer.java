package com.flipchase.android.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Retailer extends BaseDomain {

	private Long photo;
	
	private String description;
	
	private String description1;
	
	private String photo_path;
	
	private Long mapicon ;
	
	private String mapicon_path ;
	
	private String url_name;
	
	public Long getPhoto() {
		return photo;
	}

	public void setPhoto(Long photo) {
		this.photo = photo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription1() {
		return description1;
	}

	public void setDescription1(String description1) {
		this.description1 = description1;
	}

	public String getPhoto_path() {
		return photo_path;
	}

	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}

	public Long getMapicon() {
		return mapicon;
	}

	public void setMapicon(Long mapicon) {
		this.mapicon = mapicon;
	}

	public String getMapicon_path() {
		return mapicon_path;
	}

	public void setMapicon_path(String mapicon_path) {
		this.mapicon_path = mapicon_path;
	}
	
	public String getUrl_name() {
		return url_name;
	}

	public void setUrl_name(String url_name) {
		this.url_name = url_name;
	}

}
