package com.flipchase.android.parcels;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreItem implements Parcelable {

	private String city;
	private String location;
	private String retailer_id;
	private String address_line1;
	private String address_line2;
	private String phonenumber1;
	private String phonenumber2;
	private String phonenumber3;
	private String storeHours;
	private String latitude;
	private String longitude;
	private String locationName;
	private String cityName;
	private String distance;
	private String distanceStr;

    public void destroy() {
    	city = location = retailer_id = address_line1 = address_line2 = phonenumber1 = phonenumber2 = phonenumber3 = storeHours = null;
    }

    public StoreItem() {

    }

    public StoreItem(Parcel in) {
        String[] data = new String[11];
        in.readStringArray(data);
        city = data[0];
        location = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        String[] detail = new String[2];
        detail[0] = city == null ? "" : city;
        detail[1] = location == null ? "" : location;
        parcel.writeStringArray(detail);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public StoreItem createFromParcel(Parcel in) {
            return new StoreItem(in);
        }

        public StoreItem[] newArray(int size) {
            return new StoreItem[size];
        }
    };

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRetailer_id() {
		return retailer_id;
	}

	public void setRetailer_id(String retailer_id) {
		this.retailer_id = retailer_id;
	}

	public String getAddress_line1() {
		return address_line1;
	}

	public void setAddress_line1(String address_line1) {
		this.address_line1 = address_line1;
	}

	public String getAddress_line2() {
		return address_line2;
	}

	public void setAddress_line2(String address_line2) {
		this.address_line2 = address_line2;
	}

	public String getPhonenumber1() {
		return phonenumber1;
	}

	public void setPhonenumber1(String phonenumber1) {
		this.phonenumber1 = phonenumber1;
	}

	public String getPhonenumber2() {
		return phonenumber2;
	}

	public void setPhonenumber2(String phonenumber2) {
		this.phonenumber2 = phonenumber2;
	}

	public String getPhonenumber3() {
		return phonenumber3;
	}

	public void setPhonenumber3(String phonenumber3) {
		this.phonenumber3 = phonenumber3;
	}

	public String getStoreHours() {
		return storeHours;
	}

	public void setStoreHours(String storeHours) {
		this.storeHours = storeHours;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getDistanceStr() {
		return distanceStr;
	}

	public void setDistanceStr(String distanceStr) {
		this.distanceStr = distanceStr;
	}

}

