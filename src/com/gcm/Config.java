package com.gcm;

public interface Config {

	// used to share GCM regId with application server
	static final String APP_SERVER_URL = "http://10.0.2.2:8080/gcm?shareRegId=1";

	// Google Project Number
	static final String GOOGLE_PROJECT_ID = "720965521110";
	static final String MESSAGE_KEY = "message";
	static final String MESSAGE_TITLE = "title";

}
