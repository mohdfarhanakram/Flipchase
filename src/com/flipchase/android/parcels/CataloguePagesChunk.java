package com.flipchase.android.parcels;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class CataloguePagesChunk implements Parcelable {

	private int total = 0;
    private String headerText;
    private SortedBy sortedBy;
    private List<CataloguePageItem> items = new ArrayList<CataloguePageItem>();
    private int pageId = 0;
    private boolean filterData = false;
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
    	/*
        if (null != items && items.size() > 0) {
            for (CatalogItem detail : items) {
                detail.destroy();
                detail = null;
            }
            items = null;
        }
        if (null != refine) {
            refine.destroy();
            refine = null;
        }
        if (null != filterFacetArrayList && filterFacetArrayList.size() > 0) {
            for (Facet filterFacet : filterFacetArrayList) {
                filterFacet.destroy();
                filterFacet = null;
            }
        }
        filterFacetArrayList = null;
        if (null != sortedBy) {
            sortedBy.destroy();
        }
        sortedBy = null;
        */
    }

    public CataloguePagesChunk() {
    	
    }

    public CataloguePagesChunk(Parcel in) {
        total = in.readInt();
        String temp = in.readString();
        if (temp.equalsIgnoreCase("products")) {
            items = new ArrayList<CataloguePageItem>();
            in.readTypedList(items, CataloguePageItem.CREATOR);
        }
        pageId = in.readInt();
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
        parcel.writeInt(pageId);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CataloguePagesChunk createFromParcel(Parcel in) {
            return new CataloguePagesChunk(in);
        }

        public CataloguePagesChunk[] newArray(int size) {
            return new CataloguePagesChunk[size];
        }
    };


    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public SortedBy getSortedBy() {
        return sortedBy;
    }

    public void setSortedBy(SortedBy sortedBy) {
        this.sortedBy = sortedBy;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CataloguePageItem> getItems() {
        return items;
    }

    public void setItems(List<CataloguePageItem> items) {
        this.items = items;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public CataloguePagesChunk(CataloguePagesChunk cataloguePagesChunk) {
        total = cataloguePagesChunk.getTotal();
        items = cataloguePagesChunk.getItems();
        pageId = cataloguePagesChunk.getPageId();
    }

}
