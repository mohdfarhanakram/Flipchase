package com.flipchase.android.view.activity;

import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.flipchase.android.R;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.domain.Retailer;
import com.flipchase.android.domain.Store;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.view.adapter.RetailerStoreAdapter;

public class RetailerStoresActivity  extends BaseActivity implements View.OnClickListener {

	private ListView listview;
	private RetailerStoreAdapter retailerStoreAdapter;
	private String retailerId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retailer_stores);
		
		Retailer retailer = (Retailer) getIntent().getSerializableExtra("retailer");
        if (retailer != null) {
        	retailerId = retailer.getId();
        }
        listview = (ListView) findViewById(R.id.store_list_view);
        retailerStoreAdapter = new RetailerStoreAdapter(this, Collections.EMPTY_LIST);
        listview.setAdapter(retailerStoreAdapter);
        setStoreItemClick();
	}
	
	/**
	 * On screen load this method is called, so call the webservice to fetch data
	 */
	@Override
    protected void requestData(int event, Object data) {
        super.requestData(event, data);
        switch (event) {
            default:
            		showProgressDialog("Loading Stores Of Retailer...");
            		String refinedURL = URLConstants.GET_STORES_FOR_RETAILER_URL.replace("{retailerId}", retailerId);
            		fetchData(refinedURL, FlipchaseApi.GET_STORES_FOR_RETAILER, null);
                break;
        }
    }
	
	@Override
    public void updateUi(ServiceResponse response) {
		super.updateUi(response);
		removeProgressDialog();
        if (response.getErrorCode() == ServiceResponse.EXCEPTION) {
            showCommonError("Error Occured While Loading Stores...");
        } else if (response.getErrorCode() == ServiceResponse.SUCCESS) {
            if (response.getFlipChaseBaseModel().isSuccess()) {
            	switch (response.getEventType()) {
        		case FlipchaseApi.GET_STORES_FOR_RETAILER:
        			List<Store> stores = (List<Store>)response.getResponseObject();
        			retailerStoreAdapter.setItems(stores);
        			break;
        		default:
        			break;
        		}
            }
        }
    }
	
	/**
     * set item click listener
     */
    private void setStoreItemClick() {
    	listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
            		long arg3) {
            	Intent i = new Intent(RetailerStoresActivity.this,
    					StoreMapViewActivity.class);
            	Store store = (Store) adapterView.getItemAtPosition(position);
            	i.putExtra("store", store);  
            	startActivity(i);
            }
        });
    }
    
	@Override
	public void onClick(View v) {
	}

}
