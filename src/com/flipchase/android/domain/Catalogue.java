package com.flipchase.android.domain;

public class Catalogue extends BaseDomain {

	private String publishDate;
	
	private String expiryDate;
	
	private Retailer retailer;
	
	boolean isPremium;
	
	private Photo photo;
	
	private String photoPath;
	
	private String photoThumb;
	
	private String photoThumbPath;
	
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

	public Retailer getRetailer() {
		return retailer;
	}

	public void setRetailer(Retailer retailer) {
		this.retailer = retailer;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPhotoThumb() {
		return photoThumb;
	}

	public void setPhotoThumb(String photoThumb) {
		this.photoThumb = photoThumb;
	}

	public String getPhotoThumbPath() {
		return photoThumbPath;
	}

	public void setPhotoThumbPath(String photoThumbPath) {
		this.photoThumbPath = photoThumbPath;
	}

}
