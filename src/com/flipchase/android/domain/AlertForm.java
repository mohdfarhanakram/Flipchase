package com.flipchase.android.domain;

import java.util.List;

public class AlertForm {
	
	private String emailId;
	
	private List<AlertView> alertViews;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public List<AlertView> getAlertViews() {
		return alertViews;
	}
	public void setAlertViews(List<AlertView> alertViews) {
		this.alertViews = alertViews;
	}
}
