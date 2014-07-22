/**
 * 
 */
package com.flipchase.android.location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.flipchase.android.listener.FlipchaseGetAddressListener;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author m.farhan
 *
 */

public class FlipchaseGetAddressController extends AsyncTask<Void, Void, Void> {
	private Context mContext; 
	private double mLatitude;
	private double mLongitude;
	private FlipchaseGetAddressListener mListener;
	
	
	public FlipchaseGetAddressController(Context context,FlipchaseGetAddressListener listener,double latitude,double longitude) {
		super();
		mContext = context;
		mListener = listener;
		mLatitude = latitude;
		mLongitude = longitude;
	}

	
	@Override
	protected Void doInBackground(Void... params) {
		Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		// Get the current location from the input parameter list
		List<Address> addresses = null;
		try {
			/*
			 * Return 1 address.
			 */
			addresses = geocoder.getFromLocation(mLatitude,mLongitude, 1);
		} catch (IOException e1) {
			Log.e("LocationSampleActivity","IO Exception in getFromLocation()");
			e1.printStackTrace();
			mListener.onGetAddressError("IO Exception trying to get address");
		} catch (IllegalArgumentException e2) {
			// Error message to post in the log
			String errorString = "Illegal arguments " +
					Double.toString(mLatitude) +
					" , " +
					Double.toString(mLongitude) +
					" passed to address service";
			Log.e("LocationSampleActivity", errorString);
			e2.printStackTrace();
			mListener.onGetAddressError(errorString);
		}
		// If the reverse geocode returned an address
		if (addresses != null && addresses.size() > 0) {
			// Get the first address
			Address address = addresses.get(0);
			/*
			 * Format the first line of address (if available),
			 * city, and country name.
			 */
/*			String addressText = String.format(
					"%s, %s, %s",
					// If there's a street address, add it
					address.getMaxAddressLineIndex() > 0 ?
							address.getAddressLine(0) : "",
							// Locality is usually a city
							address.getLocality(),
							// The country of the address
							address.getCountryName());
			Log.e("Find Address", addressText);*/
			mListener.onGetAddress(address.getLocality());
			return null;
		} else {
			return null;
		}
	}

	
}

