/**
 * 
 */
package com.flipchase.android.view.activity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.flipchase.android.R;
import com.flipchase.android.R.id;
import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.domain.City;
import com.flipchase.android.domain.Location;
import com.flipchase.android.dummyData.DummyData;
import com.flipchase.android.persistance.AppSharedPreference;
import com.flipchase.android.util.StringUtils;
import com.flipchase.android.view.adapter.CityListPopupAdapter;
import com.flipchase.android.view.adapter.LocationListPopupAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * @author m.farhan
 *
 */
public class SelectLocationActivity extends BaseActivity implements View.OnClickListener{

	private AlertDialog alertDialogCities;
	private AlertDialog alertDialogLocations;
	
	private String userCurrentPresentCity;
	private String userCurrentPresentLocation;
	
	private City mCity = null;
	private Location mLocation = null;

	// Google Map
	private GoogleMap googleMap;
	private boolean mIsComingFromSplash;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_location_layout);
		
		mIsComingFromSplash = getIntent().getBooleanExtra(AppConstants.IS_COMING_FROM_SPLASH, true);
		
		if(mIsComingFromSplash){
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					searchUserCurrentCityAndLocation();
					init();
				}
			}, AppConstants.SPLASH_WAITING_TIME);
		} else {
			String selectedCityId = AppSharedPreference.getString(AppSharedPreference.USER_SELECTED_CITY, "", this);
			mCity = DummyData.getCityFromId(selectedCityId);
			String selectedLocationId = AppSharedPreference.getString(AppSharedPreference.USER_SELECTED_LOCATION, "", this);
			mLocation = DummyData.getLocationFromId(selectedLocationId);
			init();
		}
	}

	private void init(){

		findViewById(R.id.location_finder_spinner).setVisibility(View.GONE);
		findViewById(R.id.find_location_layout).setVisibility(View.VISIBLE);

		TextView locationTextView =(TextView)findViewById(R.id.current_location);

		TextView citySpinner = (TextView)findViewById(R.id.select_city_list);
		TextView locationSpinner = (TextView)findViewById(R.id.select_location_list);

		locationSpinner.setOnClickListener(this);
		citySpinner.setOnClickListener(this);
		((Button)findViewById(R.id.done)).setOnClickListener(this);

		/* TODO : Future we will use this as selected in drop down */
		if(!StringUtils.isNullOrEmpty(userCurrentPresentCity) && !StringUtils.isNullOrEmpty(userCurrentPresentLocation)){
			//citySpinner.setText(userCurrentPresentCity);
			//locationSpinner.setText(userCurrentPresentLocation);
			locationTextView.setText("Your current city is = " + userCurrentPresentCity + " and location is = " + userCurrentPresentLocation);
		}else{
			locationTextView.setText("Unable to find your location. Please select city & location manually from the drop down list.");
			//setDefaultCityAndLocation();
		}

        //if(StringUtils.isNullOrEmpty(mCity) || StringUtils.isNullOrEmpty(mLocation)) {
        //	setDefaultCityAndLocation();
       // }
        setSpinnerTextViewdata();
        
       
	}

	private void showCitiesPopup() {
		ArrayAdapter adapter = new CityListPopupAdapter(this, R.layout.list_view_row_item, DummyData.getCities());
        ListView listViewCityItems = new ListView(this);
        listViewCityItems.setAdapter(adapter);
        listViewCityItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
					long arg3) {
				City selectedCity = (City) adapterView.getItemAtPosition(position);
				refreshAddress(selectedCity);
				alertDialogCities.hide();
			}
        });
        alertDialogCities = new AlertDialog.Builder(this)
	        .setView(listViewCityItems)
	        .setTitle("Select City")
	        .show();
	}
	
	private void refreshAddress(City selectedCity) {
		mCity = selectedCity;
		mLocation = DummyData.getLocationsForCity(mCity).get(0);
		((TextView)findViewById(R.id.select_city_list)).setText(mCity.getName());
		((TextView)findViewById(R.id.select_location_list)).setText(mLocation.getName());
	}
	
	private void refreshLocation(Location selectedLocation) {
		mLocation = selectedLocation;
		((TextView)findViewById(R.id.select_location_list)).setText(mLocation.getName());
	}
	
	private void showLocationPopup() {
		ArrayAdapter locationAdapter = new LocationListPopupAdapter(this, R.layout.list_view_row_item, 
				DummyData.getLocationsForCity(mCity));
        ListView listViewLocationItems = new ListView(this);
        listViewLocationItems.setAdapter(locationAdapter);
        listViewLocationItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
					long arg3) {
				Location selectedLocation = (Location) adapterView.getItemAtPosition(position);
				refreshLocation(selectedLocation);
				alertDialogLocations.hide();
			}
        });
        alertDialogLocations = new AlertDialog.Builder(this)
        .setView(listViewLocationItems)
        .setTitle("Select Location")
        .show();
	}
	
	private void searchUserCurrentCityAndLocation()
	{
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		try
		{
			List<Address> addresses = geocoder.getFromLocation(AppSharedPreference.getFloat(AppSharedPreference.USER_DEVICE_LATITUDE, 0.0f, this),
					AppSharedPreference.getFloat(AppSharedPreference.USER_DEVICE_LONGITUDE, 0.0f, this), 1);
			if(null == addresses || addresses.size()==0){
				return ;
			}
			Address currentPresentAddress = addresses.get(0);
			userCurrentPresentLocation = currentPresentAddress.getSubLocality();
			userCurrentPresentCity = currentPresentAddress.getLocality(); 
		} catch (IOException e) {
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case id.select_city_list:
			showCitiesPopup();
			break;
		case id.select_location_list:
			showLocationPopup();
			break;
		case id.done:
			AppSharedPreference.putString(AppSharedPreference.USER_SELECTED_CITY, mCity.getId(), this);
			AppSharedPreference.putString(AppSharedPreference.USER_SELECTED_LOCATION, mLocation.getId(), this);
			AppSharedPreference.putFloat(AppSharedPreference.USER_DEVICE_LATITUDE, Float.parseFloat(mCity.getLatitude().toString()) , this);
			AppSharedPreference.putFloat(AppSharedPreference.USER_DEVICE_LONGITUDE, Float.parseFloat(mCity.getLongitude().toString()) , this);
			setUpMap();
			/*
			if(mIsComingFromSplash){
				Intent i = new Intent(SelectLocationActivity.this, HomeActivity.class);
				startActivity(i);
			}
			finish();
			*/
			break;
		default:
			break;
		}

	}


	private void setDefaultCityAndLocation(){
	}

	private void setSpinnerTextViewdata(){
		//((TextView)findViewById(R.id.select_city_list)).setText(mCity);
		//((TextView)findViewById(R.id.select_location_list)).setText(mLocation);
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();


			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
			
			if(googleMap!=null){
				setUpMap();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	private void setUpMap() {
		// Hide the zoom controls as the button panel will cover it.
		googleMap.getUiSettings().setZoomControlsEnabled(false);
		
		final LatLng USER_CURRENT_LOCATION = new LatLng(AppSharedPreference.getFloat(AppSharedPreference.USER_DEVICE_LATITUDE, 0, this),
				AppSharedPreference.getFloat(AppSharedPreference.USER_DEVICE_LONGITUDE, 0, this));

		Marker hamburg = googleMap.addMarker(new MarkerOptions().position(USER_CURRENT_LOCATION)
		        .title("Flipchase"));
		  
		    // Move the camera instantly to hamburg with a zoom of 15.
		    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(USER_CURRENT_LOCATION, 15));

		    // Zoom in, animating the camera.
		    googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	}

	private boolean checkReady() {
		if (googleMap == null) {
			Toast.makeText(this, "Map is not ready", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	
	@Override
    protected void requestData(int event, Object data) {
        super.requestData(event, data);
        fetchData(null, 1);
    }
}

