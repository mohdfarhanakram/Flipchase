/**
 * 
 */
package com.flipchase.android.model;

/**
 * @author m.farhan
 *
 */
public class DbControllerResponse {

	private int event;
	private Object responseObject;
	public int getEvent() {
		return event;
	}
	public void setEvent(int event) {
		this.event = event;
	}
	public Object getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}
}
