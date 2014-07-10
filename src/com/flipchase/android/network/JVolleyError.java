/**
 * 
 */
package com.flipchase.android.network;

import com.flipchase.android.network.volley.VolleyError;

/**
 * @author m.farhan
 *
 */
public class JVolleyError extends VolleyError {
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
