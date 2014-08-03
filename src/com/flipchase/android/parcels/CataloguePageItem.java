package com.flipchase.android.parcels;

import android.os.Parcel;
import android.os.Parcelable;


public class CataloguePageItem implements Parcelable {

	private String id;
	
	private String name;
	
	private String displayName;
	
	private Long pagenum;
	
	private String title;

	private Long catalogue_id;

	private Long retailer_id;

	private Long photo;

	private Long photo_thumb;

	private Long photo_mini;

	private String photo_mini_path;

	private String photo_thumb_path;

	private String photo_path;

	private Long height;

	private Long width;

	private Long count = 0L;

	private Boolean enabled = Boolean.TRUE;

	public CataloguePageItem() {
	}

	public void destroy() {
		//SET TO NULL ALL FIELDS
    }
	
	public CataloguePageItem(Parcel in) {
		String[] data = new String[3];
        in.readStringArray(data);
        id = data[0];
        name = data[1];
        photo_path = data[2];

    }
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		String[] detail = new String[3];
        detail[0] = id == null ? "" : id;
        detail[1] = name == null ? "" : name;
        detail[2] = photo_path == null ? "" : photo_path;
        parcel.writeStringArray(detail);
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CataloguePageItem createFromParcel(Parcel in) {
            return new CataloguePageItem(in);
        }
        public CataloguePageItem[] newArray(int size) {
            return new CataloguePageItem[size];
        }
    };
    
	public Long getPhoto_thumb() {
		return photo_thumb;
	}

	public void setPhoto_thumb(Long photo_thumb) {
		this.photo_thumb = photo_thumb;
	}

	public Long getPagenum() {
		return pagenum;
	}

	public void setPagenum(Long pagenum) {
		this.pagenum = pagenum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCatalogue_id() {
		return catalogue_id;
	}

	public void setCatalogue_id(Long catalogue_id) {
		this.catalogue_id = catalogue_id;
	}

	public Long getRetailer_id() {
		return retailer_id;
	}

	public void setRetailer_id(Long retailer_id) {
		this.retailer_id = retailer_id;
	}

	public void setPhoto(Long photo) {
		this.photo = photo;
	}

	public Long getPhoto() {
		return photo;
	}

	public Long getPhoto_mini() {
		return photo_mini;
	}

	public void setPhoto_mini(Long photo_mini) {
		this.photo_mini = photo_mini;
	}

	public String getPhoto_mini_path() {
		return photo_mini_path;
	}

	public void setPhoto_mini_path(String photo_mini_path) {
		this.photo_mini_path = photo_mini_path;
	}

	public String getPhoto_thumb_path() {
		return photo_thumb_path;
	}

	public void setPhoto_thumb_path(String photo_thumb_path) {
		this.photo_thumb_path = photo_thumb_path;
	}

	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}

	public String getPhoto_path() {
		return photo_path;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getEnabled() {
		return enabled;
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

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getCount() {
		return count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
