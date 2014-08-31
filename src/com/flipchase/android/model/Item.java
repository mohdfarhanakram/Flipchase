/**
 * 
 */
package com.flipchase.android.model;

import java.util.ArrayList;

/**
 * @author m.farhan
 *
 */
public class Item {
	
	private String id;
	private String name;
	private String title;
	private String subTitle;
	private String quantity;
	private boolean reminder;
	private String expiry;
	
	private int count;
	ArrayList<Item> subItemList = new ArrayList<Item>();
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public ArrayList<Item> getSubItemList() {
		return subItemList;
	}
	public void setSubItemList(ArrayList<Item> subItemList) {
		this.subItemList = subItemList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public boolean isReminder() {
		return reminder;
	}
	public void setReminder(boolean reminder) {
		this.reminder = reminder;
	}
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	
	

}
