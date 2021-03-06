package com.flipchase.android.parcels;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.flipchase.android.domain.CatalogueDisplay;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CatalogueChunk implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer total = 0;
	
	private List<CatalogueDisplay> items;
	
	private int pageId = 0;

	private SortBy sortBy;
	
	private FilterBy filterBy;
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<CatalogueDisplay> getItems() {
		return items;
	}

	public void setItems(List<CatalogueDisplay> items) {
		this.items = items;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

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
