package com.flipchase.android.parcels;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.flipchase.android.domain.MobileAlert;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileAlertChunk implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer total = 0;
	
	private List<MobileAlert> items;
	
	private int pageId = 0;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<MobileAlert> getItems() {
		return items;
	}

	public void setItems(List<MobileAlert> items) {
		this.items = items;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

}