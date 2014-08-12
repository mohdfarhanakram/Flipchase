package com.flipchase.android.view.activity;

import java.util.List;
import java.util.Stack;

import android.os.Bundle;
import android.os.Handler;

import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.extlibpro.FlipViewController;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.parcels.CataloguePageItem;
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

		// setTitle(R.string.activity_title);
		flipView = new FlipViewController(this, FlipViewController.HORIZONTAL);
		cataloguePageAdapter = new CataloguePageAdapter(this);
		flipView.setAdapter(cataloguePageAdapter);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			catalogueId = extras.getString("catalogueId");
		}
		setContentView(flipView);
	}

	public void loadMoreCataloguepagesChunk(int pageId) {
		showProgressDialog("Loading More Catalogues Pages...");
		String refinedURL = URLConstants.GET_CATALOGUE_PAGES_FOR_CATEGORY_URL
				.replace("{catalogueid}", catalogueId);
		refinedURL = refinedURL + "?pageid=" + pageId;
		fetchData(refinedURL,
		FlipchaseApi.GET_CATALOGUE_PAGES_FOR_CATALOGUE, null);
		fetchData(refinedURL,FlipchaseApi.GET_CATALOGUE_PAGES_FOR_CATALOGUE, null);
	}
	
	@Override
	protected void requestData(int event, Object data) {
		super.requestData(event, data);
		switch (event) {
		default:
			showProgressDialog("Loading Catalogues Pages...");
			String refinedURL = URLConstants.GET_CATALOGUE_PAGES_FOR_CATEGORY_URL.replace("{catalogueid}", catalogueId);
			refinedURL = refinedURL + "?pageid=1";
			fetchData(refinedURL,FlipchaseApi.GET_CATALOGUE_PAGES_FOR_CATALOGUE, null);
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
					CataloguePagesChunk cataloguePagesChunk = (CataloguePagesChunk) response
							.getResponseObject();
					updateStackCatalogItemChunkData(cataloguePagesChunk);
					handleCatalogueImageLoadingData(cataloguePagesChunk);
					break;
				default:
					break;
				}
			}
		}
	}

	/** Load 2 Images and wait for some time so that these images loads successfully **/
	private void handleCatalogueImageLoadingData(final CataloguePagesChunk cataloguePagesChunk) {
		final List<CataloguePageItem> items = cataloguePagesChunk.getItems();
		if (items == null || items.size() == 0) {
			cataloguePageAdapter.setAllItemsLoaded(true);
			return;
		}
		CataloguePageItem c1 = items.get(0);
		c1.loadBitmap(this);
		/*
		if (items.size() > 1) {
			CataloguePageItem c2 = items.get(1);
			c2.loadBitmap(this);
		}
		*/
		showProgressDialog("Loading Catalogues Offers Images");
		new Handler().postDelayed(new Runnable() {
			public void run() {
				loadBitmaps(items);
				cataloguePageAdapter.setItems(items, cataloguePagesChunk.getPageId());
				removeProgressDialog();
			}
		}, 2000); // DK: Depending upon the network
	}

	private void loadBitmaps(List<CataloguePageItem> items) {
		for (CataloguePageItem item : items) {
			item.loadBitmap(this);
		}
	}

	/**
     * adds the product that the user has visited to the stack
     *
     * @param productData : instance of Catalog
     */
    public void pushFragmentCatalogData() {
        if (isStackFull()) {
        	mProductDataStack.pop();
        }
        //((BaseActivity) getActivity()).getCatalogDataStack().push(productData);
    }

    /**
     * checks if the stack to add the shorlisted products is full or not
     *
     * @return : boolean true / false
     * true : if stack has the space , false : if stack is filled
     */
    private boolean isStackFull() {
        int size = mProductDataStack.size();
        if (size == AppConstants.MAX_CATALOG_STACK_SIZE) {
            return true;
        }
        return false;
    }

    
	public void updateStackCatalogItemChunkData(CataloguePagesChunk productData) {
		if (mProductDataStack == null) {
			mProductDataStack = new Stack<CataloguePagesChunk>();
		} else if (isStackFull()) {
			mProductDataStack.pop();
		}
		mProductDataStack.push(productData);
	}

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
	 */
	public CataloguePagesChunk popFragmentCatalogData() {
		if (mProductDataStack != null) {
			if (mProductDataStack.size() == 1) {
				mProductDataStack.pop();
				return null;
			}
			if (mProductDataStack.size() > 1) {
				mProductDataStack.pop();// remove the top Catalog
				CataloguePagesChunk data = null;
				do {
					data = mProductDataStack.peek();
					if (data != null) {// DK: && data.getItems().size() == 0) {
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
