package com.flipchase.android.parcels;

import android.os.Parcel;
import android.os.Parcelable;

public class SortOption implements Parcelable {

    private String dir;
    private String label;
    private String solr;
    private String urlKey;
    private String sortKey;

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public void destroy() {
        dir = label = solr = urlKey = null;
    }

    public SortOption() {

    }

    public SortOption(SortOption sortOption) {
        dir = sortOption.getDir();
        label = sortOption.getLabel();
        solr = sortOption.getSolr();
        urlKey = sortOption.getUrlKey();
        sortKey = sortOption.getSortKey();
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSolr() {
        return solr;
    }

    public void setSolr(String solr) {
        this.solr = solr;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dir == null ? "" : dir);
        parcel.writeString(label == null ? "" : label);
        parcel.writeString(solr == null ? "" : solr);
        parcel.writeString(urlKey == null ? "" : urlKey);
        parcel.writeString(sortKey == null ? "" : sortKey);

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SortOption createFromParcel(Parcel in) {
            return new SortOption(in);
        }

        public SortOption[] newArray(int size) {
            return new SortOption[size];
        }
    };

    public SortOption(Parcel in) {
        dir = in.readString();
        label = in.readString();
        solr = in.readString();
        urlKey = in.readString();
        sortKey = in.readString();
    }
}
