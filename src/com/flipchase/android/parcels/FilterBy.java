package com.flipchase.android.parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FilterBy implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum FilterByOption {
		ALL("All"), ELECTRONICS("Electronics"), MEN_WOMEN_CLOTHING("Men Women Clothing"), FOOTWEAR("Footwear"), FURNITURE("Furniture");
		
		private final String value;
		
		private FilterByOption(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value;
		}
	};
	
	public FilterBy() {}
	
	public FilterBy(FilterByOption filterByOption) {
		filterOptions = new ArrayList<FilterByData>();
		for(FilterByOption filterOption :FilterByOption.values()) {
			FilterByData filterByData = null;
			if(filterByOption == filterOption) {
				filterByData = new FilterByData(filterOption.value, true, filterOption.name());
			} else {
				filterByData = new FilterByData(filterOption.value, false, filterOption.name());
			}
			filterOptions.add(filterByData);
		}
	}
	
    private List<FilterByData> filterOptions;
	
    public List<FilterByData> getFilterOptions() {
		return filterOptions;
	}

	public void setFilterOptions(List<FilterByData> filterOptions) {
		this.filterOptions = filterOptions;
	}
	
	public static class FilterByData {
		
		private String name;
		
		private Boolean selected;
		
		private String id;

		public FilterByData() {}
		
		public FilterByData(String name, Boolean selected, String id) {
			this.name = name;
			this.selected = selected;
			this.id = id;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Boolean getSelected() {
			return selected;
		}

		public void setSelected(Boolean selected) {
			this.selected = selected;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
	}
}
