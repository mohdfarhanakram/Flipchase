
package com.flipchase.android.view.activity;

import java.util.List;
import java.util.Stack;

import android.os.Bundle;
import android.os.Handler;

import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.domain.CataloguePage;
import com.flipchase.android.extlibpro.FlipViewController;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.parcels.CataloguePagesChunk;
import com.flipchase.android.view.adapter.CataloguePageAdapter;

public class FlipHorizontalLayoutActivity extends BaseActivity {

	  private FlipViewController flipView;
	  private CataloguePageAdapter cataloguePageAdapter;
	  private String catalogueId;
	  private Stack<CataloguePagesChunk> mProductDataStack;
	  
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
	            	refinedURL = refinedURL + "?pageid=1";
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
	        			CataloguePagesChunk cataloguePagesChunk = (CataloguePagesChunk) response.getResponseObject();
	        			updateTopFragmentCatalogData(cataloguePagesChunk);
	        			cataloguePageAdapter.setItems(cataloguePagesChunk.getItems());
	        			//handleCatalogueImageLoadingData(latestCatalogues);
	        			break;
	        		default:
	        			break;
	        		}
	            }
	        }
	    }
		
	private void handleCatalogueImageLoadingData(final List<CataloguePage> latestCatalogues) {
		if(latestCatalogues == null || latestCatalogues.size() == 0) {
			return;
		}
		CataloguePage c1 = latestCatalogues.get(0);
		c1.loadBitmap(this);
		if(latestCatalogues != null && latestCatalogues.size() > 1) {
			CataloguePage c2 = latestCatalogues.get(1);
			c2.loadBitmap(this);
		}
		showProgressDialog("Loading Catalogues Books Pages");
		new Handler().postDelayed(new Runnable() {
            public void run() {
            	loadBitmaps(latestCatalogues);
            	//cataloguePageAdapter.setItems(latestCatalogues);
            	removeProgressDialog();
            }
        }, 2000);
	}
	private void loadBitmaps(List<CataloguePage> latestCatalogues) {
		for(CataloguePage cataloguePage : latestCatalogues) {
			cataloguePage.loadBitmap(this);
		}
	}
	
	/**
     * updates the fragment stack by removing the first product and
     * adding the last product seen
     *
     * @param productData : : instance of Catalog
     */
    public void updateTopFragmentCatalogData(CataloguePagesChunk productData) {
    	if(mProductDataStack == null) {
    		mProductDataStack = new Stack<CataloguePagesChunk>();
    	} else if(mProductDataStack.size() > 0) {
        	mProductDataStack.pop();
    	}

        mProductDataStack.push(productData);
    }
    /*
    public void loadMoreData() {
        String showMoreUrl = "";
        showMoreUrl=getUrl(mCatalogData.getPageId() + 1);
        boolean isNetworkError = fetchData(getTagBaseUrl() + showMoreUrl, ApiType.API_CATALOG_SHOW_MORE, null);
        if (isNetworkError) {
            IS_ERROR_OCCURED_WHILE_LOADING_MORE_DATA = true;
            mShowMoreLayout.setVisibility(View.GONE);
            mRefineLayout.setVisibility(View.VISIBLE);
            return;
        }
        IS_LOADING_MORE_DATA = true;
    }
    */
    
    public Stack<CataloguePagesChunk> getCatalogDataStack() {
        if (mProductDataStack == null)
            mProductDataStack = new Stack<CataloguePagesChunk>();
        return mProductDataStack;
    }
	 
    public void onDestoryOfThisActivity() {
    	CataloguePagesChunk data = popFragmentCatalogData();
    }
    
	/*
	 * public Catalog popFragmentCatalogData() {
	 * 
	 */
	public CataloguePagesChunk popFragmentCatalogData() {
		if(mProductDataStack != null) {
			if (mProductDataStack.size() == 1) {
				mProductDataStack.pop();
				return null;
			}
			if (mProductDataStack.size() > 1) {
				mProductDataStack.pop();// remove the top Catalog
				CataloguePagesChunk data = null;
				do {
					data = mProductDataStack.peek();
					if (data != null) {//DK: && data.getItems().size() == 0) {
						mProductDataStack.pop();
						data = null;
					}
				} while ((data == null) && mProductDataStack.size() > 0);
				return data;
			}
		}
		return null;
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
