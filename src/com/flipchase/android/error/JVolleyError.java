package com.flipchase.android.error;

import com.flipchase.android.network.volley.VolleyError;

public class JVolleyError extends VolleyError {
	
	private static final long serialVersionUID = 1L;
	
	private int mEventType;
	
    public int getEventType() {
        return mEventType;
    }

    public void setEventType(int mEventType) {
        this.mEventType = mEventType;
    }


    public JVolleyError(VolleyError error)
    {
        super(error.networkResponse);
    }
    
    public JVolleyError(String messg) {
        super(messg);
    }

    public JVolleyError() {
        super();
    }
}
