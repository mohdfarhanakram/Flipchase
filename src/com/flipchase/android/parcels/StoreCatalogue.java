package com.flipchase.android.parcels;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreCatalogue implements Parcelable {

    private int total = 0;
    private String headerText;
    //private Facet refine;
   // private ArrayList<Facet> filterFacetArrayList = new ArrayList<Facet>();
    //private SortedBy sortedBy;
    private ArrayList<StoreItem> items = new ArrayList<StoreItem>();
    private int pageId = 0;
    private boolean filterData = false;
    //private Hashtable<String, Facet> filtersHashTable = new Hashtable<String, Facet>();

    private String refineUrl;
    private String filterUrl;
    private String selectedDrawerValue = "";
    private String firstLevelValue = "";
    private String secondLevelValue = "";
    private boolean isNonExactSearch;

    public String getActiveBrand() {
        return activeBrand;
    }

    public void setActiveBrand(String activeBrand) {
        this.activeBrand = activeBrand;
    }

    private String activeBrand;
    public boolean isNonExactSearch() {
        return isNonExactSearch;
    }

    public void setNonExactSearch(boolean isNonExactSearch) {
        this.isNonExactSearch = isNonExactSearch;
    }
    public String getCatUrl() {
        return catUrl;
    }

    public void setCatUrl(String catUrl) {
        this.catUrl = catUrl;
    }

    private String catUrl;
    public String getFirstLevelValue() {
        return firstLevelValue;
    }

    public void setFirstLevelValue(String firstLevelValue) {
        this.firstLevelValue = firstLevelValue;
    }

    public String getSecondLevelValue() {
        return secondLevelValue;
    }

    public void setSecondLevelValue(String secondLevelValue) {
        this.secondLevelValue = secondLevelValue;
    }

    public String getSelectedDrawerValue() {
        return selectedDrawerValue;
    }

    public void setSelectedDrawerValue(String selectedDrawerValue) {
        this.selectedDrawerValue = selectedDrawerValue;
    }

    public String getFilterUrl() {
        return filterUrl;
    }

    public void setFilterUrl(String filterUrl) {
        this.filterUrl = filterUrl;
    }

    public String getRefineUrl() {
        return refineUrl;
    }

    public void setRefineUrl(String refineUrl) {
        this.refineUrl = refineUrl;
    }

    public boolean isFilterData() {
        return filterData;
    }

    public void setFilterData(boolean filterData) {
        this.filterData = filterData;
    }

    public void destroy() {
        if (null != items && items.size() > 0) {
            for (StoreItem detail : items) {
                detail.destroy();
                detail = null;
            }
            items = null;
        }
    }

    public StoreCatalogue() {
	}
    
    public StoreCatalogue(Parcel in) {
        total = in.readInt();
        String temp = in.readString();
        if (temp.equalsIgnoreCase("products")) {
            items = new ArrayList<StoreItem>();
            in.readTypedList(items, StoreItem.CREATOR);
        }

        temp = in.readString();

        refineUrl = in.readString();
        filterUrl = in.readString();
        selectedDrawerValue = in.readString();
        catUrl=in.readString();
        isNonExactSearch=in.readInt()==1;
        headerText=in.readString();
        activeBrand=in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(total);
        if (null != items) {
            parcel.writeString("products");
            parcel.writeTypedList(items);
        } else {
            parcel.writeString("no_producst");
        }
        parcel.writeString(refineUrl);
        parcel.writeString(filterUrl);
        parcel.writeString(selectedDrawerValue);
        parcel.writeString(catUrl);
        parcel.writeInt(isNonExactSearch==true?1:0);
        parcel.writeString(headerText);
        parcel.writeString(activeBrand);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public StoreCatalogue createFromParcel(Parcel in) {
            return new StoreCatalogue(in);
        }

        public StoreCatalogue[] newArray(int size) {
            return new StoreCatalogue[size];
        }
    };


    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<StoreItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<StoreItem> items) {
        this.items = items;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public StoreCatalogue(StoreCatalogue catalog) {

        total = catalog.getTotal();
        headerText = catalog.getHeaderText();
        items = catalog.getItems();
        pageId = catalog.getPageId();
        filterData = catalog.isFilterData();


        refineUrl = catalog.getRefineUrl();
        filterUrl = catalog.getFilterUrl();
        selectedDrawerValue = catalog.getSelectedDrawerValue();
        firstLevelValue = catalog.getFirstLevelValue();
        secondLevelValue = catalog.getSecondLevelValue();

    }


}

