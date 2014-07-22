/**
 * 
 */
package com.flipchase.android.view.activity;

import com.flipchase.android.R;
import com.flipchase.android.listener.FlipchaseLocationListener;
import com.flipchase.android.location.FlipchaseLocationTracker;

import android.os.Bundle;

/**
 * @author m.farhan
 *
 */
public class SelectLocationActivity extends BaseActivity implements FlipchaseLocationListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_select_location_layout);
    	new FlipchaseLocationTracker(this, this);
    }

	@Override
	public void onGetLocation(double latitude, double longitude) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(double latitude, double longitude) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationError(String errorMsg) {
		// TODO Auto-generated method stub
		
	}
}
