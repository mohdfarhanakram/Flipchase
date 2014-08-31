package com.flipchase.android.parcels;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SortBy implements Serializable {
	
	private static final long serialVersionUID = 1L;
	

    private List<SortByData> sortOptions;
	
    public void setSelected(String id) {
    	for(SortByData sortByData : sortOptions) {
    		if(sortByData.getId().equalsIgnoreCase(id)) {
    			sortByData.setSelected(true);
    		} else {
    			sortByData.setSelected(false);
    		}
    	}
    }
    
    public String[] getSortByArray() {
		String[] sortOptions = new String[getSortOptions().size()];
		for(int i = 0; i < getSortOptions().size(); i++){
			SortByData option = getSortOptions().get(i);
			sortOptions[i] = option.getName();
		}
		
		return sortOptions;
	}
    
    public int getSortBySelectedIndex(){
    	int selectedIndex = 0;
		String[] sortOptions = new String[getSortOptions().size()];
		for(int i = 0; i < getSortOptions().size(); i++){
			SortByData option = getSortOptions().get(i);
			if(option.getSelected())
			     return selectedIndex;
		}
		
		return selectedIndex;
	}
    
	public List<SortByData> getSortOptions() {
		return sortOptions;
	}

	public void setSortOptions(List<SortByData> sortOptions) {
		this.sortOptions = sortOptions;
	}

	public static class SortByData implements Serializable{

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
