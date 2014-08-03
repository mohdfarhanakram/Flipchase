package com.flipchase.android.parcels;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import android.os.Parcel;
import android.os.Parcelable;

public class SortedBy implements Parcelable {

    private String defaultSorting = "popularity";
    private String sort = "";
    private String sortDirection = "";
    private Hashtable<String, SortOption> sortOptions = new Hashtable<String, SortOption>();

    public void destroy() {
        defaultSorting = sort = sortDirection = null;
        if (null != sortOptions && sortOptions.size() > 0) {
            Enumeration<String> enumeration = sortOptions.keys();
            SortOption sortOption = null;
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                sortOption = sortOptions.get(key);
                sortOption.destroy();
                sortOption = null;
                key = null;
            }
        }
        sortOptions = null;
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SortedBy createFromParcel(Parcel in) {
            return new SortedBy(in);
        }

        public SortedBy[] newArray(int size) {
            return new SortedBy[size];
        }
    };

    public SortedBy() {

    }

    public SortedBy(SortedBy sortedBy) {

        if (sortedBy.getSort().equalsIgnoreCase("false")) {
            sort = defaultSorting;
        } else {
            sort = sortedBy.getSort();
        }

        sortDirection = sortedBy.getSortDirection();

        if (sortedBy.getSortOptions() != null) {
            Enumeration<String> keys = (Enumeration<String>) sortedBy.getSortOptions().keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                sortOptions.put(key, sortedBy.getSortOptions().get(key));
            }

        }

    }

    public SortedBy(Parcel in) {
        defaultSorting = in.readString();
        sort = in.readString();
        sortDirection = in.readString();
        int size = in.readInt();
        if (size > 0) {
            sortOptions = new Hashtable<String, SortOption>();
            for (int i = 0; i < size; i++) {
                String key = in.readString();
                SortOption so = in.readParcelable(SortOption.class.getClassLoader());
                sortOptions.put(key, so);
            }
        } else {
            sortOptions = null;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int k) {
        parcel.writeString(defaultSorting);
        parcel.writeString(sort);
        parcel.writeString(sortDirection);
        if (null != sortOptions) {
            int size = sortOptions.size();
            parcel.writeInt(size);
            for (String key : sortOptions.keySet()) {
                SortOption so = sortOptions.get(key);
                parcel.writeString(key);
                parcel.writeParcelable(so, k);
            }
        } else {
            parcel.writeInt(0);
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public Hashtable<String, SortOption> getSortOptions() {
        return sortOptions;
    }

    public void setSortOptions(Hashtable<String, SortOption> sortOptions) {
        this.sortOptions = sortOptions;
    }

    public SortOption getSelectedSortOption() {
        if (sort.equalsIgnoreCase("false"))
            sort = defaultSorting;

        return getSortOptions().get(sort);
    }

    public ArrayList<SortOption> getSortOptionList() {
        return new ArrayList<SortOption>(sortOptions.values());
    }

    public int getSelectedOptionIndex() {
        ArrayList<SortOption> list = getSortOptionList();
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLabel().equalsIgnoreCase(getSelectedSortOption().getLabel())) {
                index = i;
                break;
            }
        }
        return index;
    }


}
