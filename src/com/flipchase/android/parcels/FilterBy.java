package com.flipchase.android.parcels;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.flipchase.android.util.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterBy implements Serializable {

	private static final long serialVersionUID = 1L;

	public FilterBy() {}
	
	
	public String[] getFilterByArray() {
		String options[] = new String[getFilterOptions().size()];
    	for(int i=0; i<getFilterOptions().size(); i++) {
    		options[i] = getFilterOptions().get(i).getName();
    	}
    	
    	return options;
    }
	
	public boolean[] getSelectedFilterBooleanArray() {
		boolean options[] = new boolean[getFilterOptions().size()];
    	for(int i=0; i<getFilterOptions().size(); i++) {
    		options[i] = getFilterOptions().get(i).getSelected();
    	}
    	
    	return options;
    }
	
	public String getSelectedString(){
		String selectedString = "";
		
		for(int i=0; i<getFilterOptions().size(); i++) {
    		if(getFilterOptions().get(i).getSelected()){
    			selectedString = selectedString + " "+getFilterOptions().get(i).getName()+",";
    		}
    	}
		
		selectedString = selectedString.trim();
		
		if(!StringUtils.isNullOrEmpty(selectedString))
			selectedString = selectedString.substring(0, selectedString.length()-2);
		
		return selectedString.trim();

	}

	
	public void setSelected(boolean[] selectedBoolean) {
		for(int i=0; i<getFilterOptions().size(); i++) {
    		getFilterOptions().get(i).setSelected(selectedBoolean[i]);
    	}
    }
	
	public void selectAll(boolean selected) {
		for(FilterByData filterByData : filterOptions) {
			filterByData.setSelected(selected);
		}
	}

    private List<FilterByData> filterOptions;
	
    public List<FilterByData> getFilterOptions() {
		return filterOptions;
	}

	public void setFilterOptions(List<FilterByData> filterOptions) {
		this.filterOptions = filterOptions;
	}
	
	public static class FilterByData implements Serializable{
		
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
