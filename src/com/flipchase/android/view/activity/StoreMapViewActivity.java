package com.flipchase.android.view.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.flipchase.android.R;
import com.flipchase.android.domain.Store;
import com.flipchase.android.persistance.AppSharedPreference;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class StoreMapViewActivity extends BaseActivity {

	private GoogleMap googleMap;
	private Store store = null;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_map_layout);
		
		this.store = (Store) getIntent().getSerializableExtra("store");
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
