/**
 * 
 */
package com.flipchase.android.listener;

/**
 * @author m.farhan
 *
 */
public interface FlipchaseLocationListener {
	public void onGetLocation(double latitude, double longitude);
    public void onLocationChanged(double latitude, double longitude);
    public void onLocationError(String errorMsg);
}
