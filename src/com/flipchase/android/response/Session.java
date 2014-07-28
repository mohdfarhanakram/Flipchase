package com.flipchase.android.response;

//import com.google.gson.annotations.SerializedName;

public class Session {

	private String id;
	private String expire;
	private String token;
	private boolean loggedIn;
	private String apiToken = "";

    public String getCacheControlApp() {
        return cacheControlApp;
    }

    public void setCacheControlApp(String cacheControlApp) {
        this.cacheControlApp = cacheControlApp;
    }

    private String cacheControlApp;

	/**
	 * @return the isLoggedIn
	 */

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
	 * @return the apiToken
	 */
	public String getApiToken() {
		return apiToken;
	}

	/**
	 * @param apiToken the apiToken to set
	 */
	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
