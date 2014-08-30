package com.flipchase.android.view.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.flipchase.android.R;
import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.domain.Store;
import com.flipchase.android.util.StringUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class NearestCatalogueStoreActivity extends BaseActivity {

	private GoogleMap googleMap;
	private Store store = null;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catalogue_nearest_store_layout);
		
		this.store = (Store) getIntent().getSerializableExtra("store");
		initializeUI();
	}
	
	private void initializeUI() {
		TextView nearestCatalogueStoreAddress =(TextView)findViewById(R.id.nearestCatalogueStoreAddress);
		TextView nearestCatalogueStoreDistance =(TextView)findViewById(R.id.nearestCatalogueStoreDistance);
		TextView nearestCatalogueStorePhoneNumber =(TextView)findViewById(R.id.nearestCatalogueStorePhoneNumber);
		TextView nearestCatalogueStoreWorkingHours =(TextView)findViewById(R.id.nearestCatalogueStoreWorkingHours);
		TextView nearestCatalogueStoreName =(TextView)findViewById(R.id.nearestCatalogueStoreName);
		
		nearestCatalogueStoreName.setText(store.getName());
		if(StringUtils.isNullOrEmpty(store.getName())) {
			nearestCatalogueStoreName.setText("Store");
		}
		nearestCatalogueStoreAddress.setText(store.getAddress_line1());
		if(store.getDistance() != null) {
        	String distanceAsString = String.format(AppConstants.STORE_DISTANCE_PRECISION_FOR_KM, store.getDistance());
        	nearestCatalogueStoreDistance.setText(distanceAsString + AppConstants.DISTANCE_IN_KM);
        }
		nearestCatalogueStorePhoneNumber.setText(store.getPhonenumber1());
		nearestCatalogueStoreWorkingHours.setText(store.getStoreHours());
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
		
		final LatLng USER_CURRENT_LOCATION = new LatLng(store.getLatitude() ,
				store.getLongitude());

		Marker hamburg = googleMap.addMarker(new MarkerOptions().position(USER_CURRENT_LOCATION)
		        .title("Flipchase"));
		  
		    // Move the camera instantly to hamburg with a zoom of 15.
		    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(USER_CURRENT_LOCATION, 15));

		    // Zoom in, animating the camera.
		    googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	}
}
