package com.flipchase.android.response;

//import com.google.gson.annotations.SerializedName;

public class MetaData {

	private String md5;
	//@SerializedName("base_url")
	private String baseUrl;
	//@SerializedName("action_name")
	private String actionName;

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

}
