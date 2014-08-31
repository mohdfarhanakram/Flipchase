package com.flipchase.android.parcels;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestSortFilterOptions implements Serializable{ 

	private static final long serialVersionUID = -4407896514499102726L;
	
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
