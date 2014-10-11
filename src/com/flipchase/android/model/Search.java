/**
 * 
 */
package com.flipchase.android.model;

import java.util.ArrayList;

import com.flipchase.android.domain.CatalogueDisplay;
import com.flipchase.android.domain.Retailer;
import com.flipchase.android.parcels.CatalogueChunk;

/**
 * @author FARHAN
 *
 */
public class Search {

	private ArrayList<CatalogueDisplay> catalogSearchList;
	private ArrayList<Retailer> storeSearchList;
	public ArrayList<CatalogueDisplay> getCatalogSearchList() {
		return catalogSearchList;
	}
	public void setCatalogSearchList(ArrayList<CatalogueDisplay> catalogSearchList) {
		this.catalogSearchList = catalogSearchList;
	}
	public ArrayList<Retailer> getStoreSearchList() {
		return storeSearchList;
	}
	public void setStoreSearchList(ArrayList<Retailer> storeSearchList) {
		this.storeSearchList = storeSearchList;
	}
	
	
	
}
