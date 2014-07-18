package com.flipchase.android.domain;


public class Alert extends BaseDomain {

	private Long category;
	private Long product;
	private Long brand;
	private Long sub_category;
	private Long retailer;
	private Long email;
	private String searchString; 
	private Long city_id;
	private Long location_id;
	
	public Long getRetailer() {
		return retailer;
	}
	public void setRetailer(Long retailer) {
		this.retailer = retailer;
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
	public Long getCategory() {
		return category;
	}
	public void setCategory(Long category) {
		this.category = category;
	}
	public Long getProduct() {
		return product;
	}
	public void setProduct(Long product) {
		this.product = product;
	}
	public Long getBrand() {
		return brand;
	}
	public void setBrand(Long brand) {
		this.brand = brand;
	}
	public Long getSub_category() {
		return sub_category;
	}
	public void setSub_category(Long sub_category) {
		this.sub_category = sub_category;
	}
	public Long getEmail() {
		return email;
	}
	public void setEmail(Long email) {
		this.email = email;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
