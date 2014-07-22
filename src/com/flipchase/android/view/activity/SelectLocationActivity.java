/**
 * 
 */
package com.flipchase.android.view.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.flipchase.android.R;
import com.flipchase.android.R.id;
import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.listener.FlipchaseGetAddressListener;
import com.flipchase.android.listener.FlipchaseLocationListener;
import com.flipchase.android.location.FlipchaseGetAddressController;
import com.flipchase.android.location.FlipchaseLocationTracker;
import com.flipchase.android.model.City;
import com.flipchase.android.model.CityDumyData;
import com.flipchase.android.model.CityLocation;
import com.flipchase.android.persistance.AppSharedPreference;
import com.flipchase.android.util.StringUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author m.farhan
 *
 */
public class SelectLocationActivity extends BaseActivity implements View.OnClickListener{

	private AlertDialog.Builder builderCity;
	private AlertDialog.Builder builderLocation;

	private String mFindCity="";
	private String mLocation="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_location_layout);
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				init();
			}
		}, AppConstants.SPLASH_WAITING_TIME);


		
		

	}
	
	
	private void init(){
		
		getCityFromLocation();

		findViewById(R.id.location_finder_spinner).setVisibility(View.GONE);
		findViewById(R.id.find_location_layout).setVisibility(View.VISIBLE);

		TextView locationTextView =(TextView)findViewById(R.id.current_location);

		TextView citySpinner = (TextView)findViewById(R.id.select_city_list);
		TextView locationSpinner = (TextView)findViewById(R.id.select_location_list);

		locationSpinner.setOnClickListener(this);
		citySpinner.setOnClickListener(this);
		((Button)findViewById(R.id.done)).setOnClickListener(this);
		
		if(!StringUtils.isNullOrEmpty(mFindCity)){

			if(isCityMatchFromCityList()){

				citySpinner.setText(mFindCity);
				mLocation = getLocationListBasedOnCity().get(0).getName();
				locationSpinner.setText(mLocation);

				locationTextView.setText("Your current city is "+mFindCity+" & location "+mLocation);
				

			}else{
				
				locationTextView.setText("Your current city is "+mFindCity+" currently no offers & deals available for "+mFindCity+" city so please select city & location manually from the drop down list.");
				
				setCityLocation();
			}

		}else{
			
			locationTextView.setText("Unable to find your location. Please select city & location manually from the drop down list.");
			setCityLocation();
		}

		setSpinnerTextViewdata();
		
		builderCity = new AlertDialog.Builder(this);
		builderCity.setTitle("Select City");
		final String[] citylist = getCityListStringArray();
		builderCity.setSingleChoiceItems(citylist, -1, new  DialogInterface.OnClickListener(){
			public void  onClick(DialogInterface dialog, int item){

				dialog.dismiss();
				try
				{
					mFindCity = citylist[item];
					String[] locList = getCityLocation(mFindCity);
					mLocation = locList[0];
					locationBuilder(locList);
					setSpinnerTextViewdata();

				} catch(Exception e) { Log.e("ChooseCityActivity", "" + e); }
			}
		});
		
		
		final String[] locationList = getCityLocation(mFindCity);
		locationBuilder(locationList);
	}


	private boolean isCityMatchFromCityList(){
		HashMap<String,City> cityList = CityDumyData.getAllCity();
		return cityList.containsKey(mFindCity.trim());
	}

	private ArrayList<CityLocation> getLocationListBasedOnCity(){
		if(isCityMatchFromCityList()){
			HashMap<String,City> cityList = CityDumyData.getAllCity();
			return cityList.get(mFindCity).getCityLocations();
		}
		return null;	
	}


	private void getCityFromLocation()
	{
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		try
		{
			List<Address> addresses = geocoder.getFromLocation(AppSharedPreference.getFloat(AppSharedPreference.USER_DEVICE_LATITUDE, 0.0f, this),
					AppSharedPreference.getFloat(AppSharedPreference.USER_DEVICE_LONGITUDE, 0.0f, this), 1);
			if(null == addresses || addresses.size()==0){
				mFindCity = "";
				return ;
			}
			Address obj = addresses.get(0);
			mFindCity = obj.getLocality(); 
			Log.e("City Nmae", mFindCity);


		} catch (IOException e) {

			mFindCity = "";

		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case id.select_city_list:
			builderCity.create().show();
			break;
		case id.select_location_list:
			builderLocation.create().show();
			break;
		case id.done:
			Intent i = new Intent(SelectLocationActivity.this, HomeActivity.class);
			startActivity(i);
			finish();
			break;
		default:
			break;
		}

	}
	
	private String[] getCityListStringArray(){
		Map<String, City> cityMap = CityDumyData.getAllCity();
		int i = 0;
		String [] cityList = new String[cityMap.size()];
		
		Set entries = cityMap.entrySet();
        Iterator iterator = entries.iterator();
        
        while(iterator.hasNext()){

            Map.Entry mapping = (Map.Entry)iterator.next();
            cityList[i] = mapping.getKey().toString();
            i++;
        }
        
        return cityList;
	}
	
	private String getFirstCityName(){
		Map<String, City> cityMap = CityDumyData.getAllCity();
		Set entries = cityMap.entrySet();
        Iterator iterator = entries.iterator();
        
        return ((Map.Entry)iterator.next()).getKey().toString();
	}
	
	private String[] getCityLocation(String city){
		
		ArrayList<CityLocation> locatioArrayList = CityDumyData.getAllCity().get(city.trim()).getCityLocations();
		
		String [] locationList = new String[locatioArrayList.size()];
		
		for(int i=0; i<locatioArrayList.size(); i++){
			locationList[i] = locatioArrayList.get(i).getName();
		}
		return locationList;
	}
	
	private void setCityLocation(){
		mFindCity = getFirstCityName().trim();
		mLocation = CityDumyData.getAllCity().get(mFindCity).getCityLocations().get(0).getName();
	}
	
	private void setSpinnerTextViewdata(){
		((TextView)findViewById(R.id.select_city_list)).setText(mFindCity);
		((TextView)findViewById(R.id.select_location_list)).setText(mLocation);
	}
	
	private void locationBuilder(String[] list){
		builderLocation = new AlertDialog.Builder(this);
		builderLocation.setTitle("Select Location");
		
		builderLocation.setSingleChoiceItems(list, -1, new  DialogInterface.OnClickListener(){
			public void  onClick(DialogInterface dialog, int item){

				dialog.dismiss();
				try
				{
					String[] locList = getCityLocation(mFindCity);
					mLocation = locList[item];
					setSpinnerTextViewdata();

				} catch(Exception e) { Log.e("ChooseCityActivity", "" + e); }
			}
		});

	}

}





