package com.flipchase.android.parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SortBy implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static enum SortByOption {
		NEAR_BY("Near By"), NEW("New"), POPULAR("Popular");
		
		private final String value;
		
		private SortByOption(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value;
		}
	};
	
	public SortBy() {}
	
	public SortBy(SortByOption sortByOption) {
		sortOptions = new ArrayList<SortByData>();
		for(SortByOption sortOption :SortByOption.values()) {
			SortByData sortByData = null;
			if(sortByOption == sortOption) {
				sortByData = new SortByData(sortOption.value, true, sortOption.name());
			} else {
				sortByData = new SortByData(sortOption.name(), false, sortOption.name());
			}
			sortOptions.add(sortByData);
		}
	}
	
    private List<SortByData> sortOptions;
	
	public List<SortByData> getSortOptions() {
		return sortOptions;
	}

	public void setSortOptions(List<SortByData> sortOptions) {
		this.sortOptions = sortOptions;
	}

	public static class SortByData {

		private String name;
		
		private Boolean selected;
		
		private String id;

		public SortByData() {}
		
		public SortByData(String name, Boolean selected, String id) {
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
