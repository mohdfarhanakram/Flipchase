
package com.flipchase.android.view.activity;

import java.util.List;

import android.os.Bundle;

import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.domain.Catalogue;
import com.flipchase.android.domain.CataloguePage;
import com.flipchase.android.extlibpro.FlipViewController;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.view.adapter.CataloguePageAdapter;

public class FlipHorizontalLayoutActivity extends BaseActivity {

	  private FlipViewController flipView;
	  private CataloguePageAdapter cataloguePageAdapter;
	  private String catalogueId = "32";
	  
	  /**
	   * Called when the activity is first created.
	   */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    //setTitle(R.string.activity_title);
	    flipView = new FlipViewController(this, FlipViewController.HORIZONTAL);
	    cataloguePageAdapter = new CataloguePageAdapter(this);
	    flipView.setAdapter(cataloguePageAdapter);
	    Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	catalogueId = extras.getString("catalogueId");
        }
	    setContentView(flipView);
	  }

	  /**
		 * On screen load this method is called, so call the webservice to fetch data
		 */
		@Override
	    protected void requestData(int event, Object data) {
	        super.requestData(event, data);
	        switch (event) {
	            default:
	            	showProgressDialog("Loading Latest Catalogues...");
	            	String refinedURL = URLConstants.GET_CATALOGUE_PAGES_FOR_CATEGORY_URL.replace("{catalogueid}", catalogueId);
	                fetchData(refinedURL, FlipchaseApi.GET_CATALOGUE_PAGES_FOR_CATALOGUE, null);
	                break;
	        }
	    }

		@Override
	    public void updateUi(ServiceResponse response) {
			super.updateUi(response);
			removeProgressDialog();
	        if (response.getErrorCode() == ServiceResponse.EXCEPTION) {
	            showCommonError("Error Occured...");
	        } else if (response.getErrorCode() == ServiceResponse.SUCCESS) {
	            if (response.getFlipChaseBaseModel().isSuccess()) {
	            	switch (response.getEventType()) {
	        		case FlipchaseApi.GET_CATALOGUE_PAGES_FOR_CATALOGUE:
	        			List<CataloguePage> latestCatalogues = (List<CataloguePage>) response.getResponseObject();
	        			cataloguePageAdapter.setItems(latestCatalogues);
	        			break;
	        		default:
	        			break;
	        		}
	            }
	        }
	    }
		
	  @Override
	  protected void onResume() {
	    super.onResume();
	    flipView.onResume();
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    flipView.onPause();
	  }
	}
