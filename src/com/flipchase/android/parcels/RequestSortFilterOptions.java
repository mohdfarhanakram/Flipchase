package com.flipchase.android.parcels;

import java.io.Serializable;

public class RequestSortFilterOptions implements Serializable{ 

	private SortBy sortBy;
	private FilterBy filterBy;
	
	public SortBy getSortBy() {
		return sortBy;
	}
	public void setSortBy(SortBy sortBy) {
		this.sortBy = sortBy;
	}
	public FilterBy getFilterBy() {
		return filterBy;
	}
	public void setFilterBy(FilterBy filterBy) {
		this.filterBy = filterBy;
	}



}
