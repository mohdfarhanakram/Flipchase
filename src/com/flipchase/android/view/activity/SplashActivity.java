package com.flipchase.android.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.flipchase.android.R;
import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.domain.CityLocationWrapper;
import com.flipchase.android.listener.FlipchaseLocationListener;
import com.flipchase.android.location.FlipchaseLocationTracker;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.persistance.AppSharedPreference;
import com.flipchase.android.util.StringUtils;
import com.flipchase.android.util.Utils;

public class SplashActivity extends BaseActivity implements
FlipchaseLocationListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_layout);

		new FlipchaseLocationTracker(this, this);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				 getCityList();

			}
		}, AppConstants.SPLASH_WAITING_TIME);

	}

	@Override
	public void updateUi(ServiceResponse response) {
		super.updateUi(response);
		if(response.getErrorCode() == ServiceResponse.SUCCESS){
			CityLocationWrapper cityLocationWrapper = (CityLocationWrapper) response.getResponseObject();

			if(cityLocationWrapper!=null && cityLocationWrapper.getCities()!=null && cityLocationWrapper.getCities().size()>0){
				// To cache the city json
				AppSharedPreference.putString(AppConstants.GET_ALL_CITIES_AND_LOCATIONS, response.getJsonResponse().toString(), this);

				String city = AppSharedPreference.getString(AppSharedPreference.USER_SELECTED_CITY, "",SplashActivity.this);
				String location = AppSharedPreference.getString(AppSharedPreference.USER_SELECTED_LOCATION, "",SplashActivity.this);
				if (StringUtils.isNullOrEmpty(city) || StringUtils.isNullOrEmpty(location)) {
					Intent i = new Intent(SplashActivity.this,SelectLocationActivity.class);
					i.putExtra(AppConstants.IS_COMING_FROM_SPLASH, true);
					startActivity(i);
					finish();
				} else {
					Intent i = new Intent(SplashActivity.this,HomeActivity.class);
					startActivity(i);
					finish();
				}

			}else{
				// Move to home screen as discussed with Deepak

				Intent i = new Intent(SplashActivity.this,HomeActivity.class);
				startActivity(i);
				finish();
			}


		}else{
			openAlertDialog("Some thing went wrong.");

		}

	}

	private void openAlertDialog(String title){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(title);
		builder.setCancelable(false);
		
		builder.setTitle("Flipchase");

		builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				getCityList();
			}
		});

		builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
			}
		});
		
		builder.create().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onGetLocation(double latitude, double longitude) {
		Log.e("Location", latitude + " " + longitude);

		AppSharedPreference.putFloat(AppSharedPreference.USER_DEVICE_LATITUDE,
				(float) latitude, this);
		AppSharedPreference.putFloat(AppSharedPreference.USER_DEVICE_LONGITUDE,
				(float) longitude, this);

	}

	@Override
	public void onLocationChanged(double latitude, double longitude) {

	}

	@Override
	public void onLocationError(String errorMsg) {

	}
	
	
	private void getCityList(){
		if(Utils.isInternetAvailable(this)){
		    fetchData(URLConstants.GET_ALL_CITIES_AND_LOCATIONS_URL, FlipchaseApi.GET_ALL_CITIES_AND_LOCATIONS, null);
		}else{
			openAlertDialog("Network connection is not available.");
		}
	}

}
