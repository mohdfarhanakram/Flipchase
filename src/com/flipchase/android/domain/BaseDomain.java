package com.flipchase.android.domain;

import java.io.Serializable;

public abstract class BaseDomain implements Serializable {

	private static final long serialVersionUID = 2453732863760632378L;

	protected String id;
	
	protected String name;
	
	protected String displayName;

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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public boolean equals(Object obj) {
		BaseDomain bd = (BaseDomain) obj;
		return this.id.equals(bd.getId());
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	
	@Override
	public String toString() {
		return id;
	}
}
