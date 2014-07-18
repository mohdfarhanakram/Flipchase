package com.flipchase.android.domain;


public class Catalogue extends BaseDomain {

	private String status;
	
	private String publishDate;
	
	private String expiryDate;
	
	private Long retailer;
	
	private Boolean isPremium;
	
	private Long photo;
	
	private String photo_path;
	
	private Long photo_thumb;
	
	private String photo_thumb_path;
	
	private Long height;
	
	private Long width;
	
	private Long count = 0L;
	
	private Boolean enabled = Boolean.FALSE;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Long getRetailer() {
		return retailer;
	}

	public void setRetailer(Long retailer) {
		this.retailer = retailer;
	}

	public Boolean getIsPremium() {
		return isPremium;
	}

	public void setIsPremium(Boolean isPremium) {
		this.isPremium = isPremium;
	}

	public Long getPhoto() {
		return photo;
	}

	public void setPhoto(Long photo) {
		this.photo = photo;
	}

	public String getPhoto_path() {
		return photo_path;
	}

	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}

	public Long getPhoto_thumb() {
		return photo_thumb;
	}

	public void setPhoto_thumb(Long photo_thumb) {
		this.photo_thumb = photo_thumb;
	}

	public String getPhoto_thumb_path() {
		return photo_thumb_path;
	}

	public void setPhoto_thumb_path(String photo_thumb_path) {
		this.photo_thumb_path = photo_thumb_path;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
