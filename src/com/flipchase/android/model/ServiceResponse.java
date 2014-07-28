/**
 *
 */
package com.flipchase.android.model;

import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONObject;

import com.flipchase.android.response.common.FlipChaseBaseModel;

/**
 *
 */
public class ServiceResponse {


    public static final int EXCEPTION = 1;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    public static final int VALIDATION_ERROR = 4;
    public static final int MESSAGE_ERROR = 5;
    public static final int SUCCESS = 6;
    public static final int AUTO_LOGIN_FAILED = 7;

    private JSONObject jsonResponse;
    private String errorText;
    private int errorCode = 3;

    private Exception exception;
    private Hashtable headers;
    private ArrayList<String> errorMessages;
    private FlipChaseBaseModel flipChaseBaseModel;
    private Object responseObject;
    private int eventType;

    public boolean isRetryLimitExceeded() {
        return isRetryLimitExceeded;
    }

    public void setRetryLimitExceeded(boolean isRetryLimitExceeded) {
        this.isRetryLimitExceeded = isRetryLimitExceeded;
    }

    private boolean isRetryLimitExceeded;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    private String addressId;


    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    public void setJsonResponse(JSONObject jsonResponse) {
        this.jsonResponse = jsonResponse;
    }

    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(ArrayList<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public FlipChaseBaseModel getFlipChaseBaseModel() {
		return flipChaseBaseModel;
	}

	public void setFlipChaseBaseModel(FlipChaseBaseModel flipChaseBaseModel) {
		this.flipChaseBaseModel = flipChaseBaseModel;
	}

	public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public Hashtable getHeaders() {
        return headers;
    }

    public void setHeaders(Hashtable headers) {
        this.headers = headers;
    }

    public boolean isError() {
        return getErrorCode() > 0;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

}
