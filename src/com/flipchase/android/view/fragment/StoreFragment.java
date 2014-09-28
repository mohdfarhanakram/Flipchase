/**
 * 
 */
package com.flipchase.android.view.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flipchase.android.R;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.domain.Retailer;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.network.volley.toolbox.ImageLoader;
import com.flipchase.android.parcels.StoreCatalogue;
import com.flipchase.android.util.Utils;
import com.flipchase.android.view.activity.HomeActivity;
import com.flipchase.android.view.activity.RetailerStoresActivity;
import com.flipchase.android.view.adapter.RetailerAdapter;
import com.flipchase.android.view.widget.CustomFontTextView;
import com.flipchase.android.view.widget.RetailerListView;

/**
 * @author m.farhan
 *
 */
public class StoreFragment extends BaseFragment {
	
 	private RetailerListView mItemsListView;
    private String mRefineUrl = "";
    private String mFilterUrl = "";
    private StoreCatalogue mStoreData;
    private RetailerAdapter mItemsListAdapter;
    private ImageLoader mImageLoader;
    private LinearLayout mShowMoreLayout;
    private int mTotalCatalog = 0;
    private LinearLayout mRefineLayout;
    private ImageView mRefineTickImg;
    private CustomFontTextView mRefineButton;
    private CustomFontTextView mFilterButton;
    // private String finalURL = "";
    private boolean IS_LOADING_MORE_DATA;
    private boolean IS_ERROR_OCCURED_WHILE_LOADING_MORE_DATA;
    private int mImageWidth;
    private int mImageHeight;
    private int mPrevSelectedCatIndex =0;
    private String mCatUrl;
    private String mSelectedSubCatName;
    private View view;
	    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_items_grid, container, false);
		mRefineLayout = (LinearLayout) (view.findViewById(R.id.retailerRefineLayout));
        //initializeImageWidthHeight();
        mShowMoreLayout = (LinearLayout) view.findViewById(R.id.retailerLayoutShowMore);

        mItemsListView = (RetailerListView) view.findViewById(R.id.retailerListItems);
        if (null == mItemsListAdapter) {
        	mItemsListAdapter = new RetailerAdapter(getActivity());
        }
       // mItemsGridAdapter.setImageHeightWidth(mImageHeight, mImageWidth);
        mItemsListView.setAdapter(mItemsListAdapter);
        mItemsListAdapter.notifyDataSetChanged();
            mItemsListView.setOnScrollListener(new OnScrollListener() {
        	
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //    Log.d("ItemsGridFragment", firstVisibleItem + " " + visibleItemCount + " " + totalItemCount);
                if (IS_ERROR_OCCURED_WHILE_LOADING_MORE_DATA && firstVisibleItem + 4 <= totalItemCount) {
                    IS_ERROR_OCCURED_WHILE_LOADING_MORE_DATA = false;
                }
                if (firstVisibleItem + visibleItemCount == totalItemCount
                        && totalItemCount != 0 && totalItemCount < mTotalCatalog && !IS_LOADING_MORE_DATA && !IS_ERROR_OCCURED_WHILE_LOADING_MORE_DATA) {
                    //mShowMoreLayout.setVisibility(View.VISIBLE);
                    //mRefineLayout.setVisibility(View.GONE);
                    loadMoreData();
                }

            }
        });
        
        mRefineButton = (CustomFontTextView) view.findViewById(R.id.retailerRefineButton);
        mRefineButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //openFilterActivity();
            }
        });
		
        /*
        if(getArguments().getString(URLConstants.EXTRA_URL) != null)
            mRefineUrl = getArguments().getString(URLConstants.EXTRA_URL);
        else
        {
            //if(getArguments().getString(URLConstants.INTENT_SOURCE_ACTIVITY) != null && getArguments().getString(URLConstants.INTENT_SOURCE_ACTIVITY_EXTRA) != null)
            	//{
            	//Utils.postExceptionOnGA("Error in onCreateView in ItemGridFrag \r\n mRefineUrl=null  \r\n Calling Activity: "+getArguments().getString(Constants.INTENT_SOURCE_ACTIVITY)+" \r\n Extras: "+getArguments().getString(Constants.INTENT_SOURCE_ACTIVITY_EXTRA)+" \n sessionid: "+getSessionId(),false);
            	//}
            mRefineUrl="";
        }

		*/
        if (savedInstanceState != null) {  // this case is handled if do not keep activity is true, keep a boolean value that
        }

        mStoreData = ((HomeActivity) getActivity()).getCatalogDataIfDataIsExist();  //orientation of device is changed then get the exist data.

        if (mStoreData == null) {
        	if(Utils.isInternetAvailable(getActivity())){
        		((HomeActivity) getActivity()).updateRetailerCatalogData("");
        	}else{
        		view.findViewById(R.id.no_connection).setVisibility(View.VISIBLE);
    			view.findViewById(R.id.loader_view).setVisibility(View.GONE);
    			view.findViewById(R.id.main_view).setVisibility(View.GONE);
        	}
           
        } else {
            //drawCatalogData();
        }
        setStoreItemClick();
        /** DK:
        if (getActivity() instanceof HomeActivity) {
            Menu menu = ((HomeActivity) getActivity()).mMenu;
            buildActionBar(menu);
        }
         **/
        if (IS_LOADING_MORE_DATA) {
            mShowMoreLayout.setVisibility(View.VISIBLE);
            mRefineLayout.setVisibility(View.GONE);
        } else if (mStoreData != null) {
            mRefineLayout.setVisibility(View.VISIBLE);
        }
        return view;
	}

	@Override
	public void updateUi(ServiceResponse response) {
		super.updateUi(response);
		view.findViewById(R.id.loader_view).setVisibility(View.GONE);
		switch (response.getEventType()) {
		case FlipchaseApi.GET_ALL_RETAILERS:
			view.findViewById(R.id.main_view).setVisibility(View.VISIBLE);
			List<Retailer> retailers = (List<Retailer>)response.getResponseObject();
			 ((RetailerAdapter) mItemsListView.getAdapter()).setItems(retailers);
			 mItemsListAdapter.notifyDataSetChanged();
			break;
		case FlipchaseApi.GET_STORES_FOR_RETAILER:
			/* DK: WHEN SHOULD WE DOWNLOAD THE DATA
			List<Store> stores = (List<Store>)response.getResponseObject();
			Intent i = new Intent(getActivity(),
					RetailerStoresActivity.class);
			startActivity(i);
			*/
			break;
		default:
			break;
		}
	}
	
	/**
     * loads more Store data
     */
    private void loadMoreData() {
        String showMoreUrl = "";
        showMoreUrl=getUrl(mStoreData.getPageId() + 1);
        boolean isNetworkError = false;//DK: = fetchData(getTagBaseUrl() + showMoreUrl, ApiType.API_CATALOG_SHOW_MORE, null);
        if (isNetworkError) {
            IS_ERROR_OCCURED_WHILE_LOADING_MORE_DATA = true;
            mShowMoreLayout.setVisibility(View.GONE);
            mRefineLayout.setVisibility(View.VISIBLE);
            return;
        }
        IS_LOADING_MORE_DATA = true;
    }
    
    private String getUrl(int pageCode)
    {
        String baseUrl = "";//getRefineURL();
        //adding pagecode
        if(pageCode!=-1)
        {
            if(baseUrl.contains("?"))
            {
                baseUrl=baseUrl+"&page="+pageCode;
            }
            else
            {
                baseUrl=baseUrl+"?&page="+pageCode;
            }
        }
        return  baseUrl;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (mItemsListAdapter != null)
        	mItemsListAdapter.setContext(activity);
    }
    
    /**
     * handles the activoty life cycle callback when activity is killed
     */

    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * returns the instance of the ImageLoader
     *
     * @return
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
    
    /**
     * set item click listener
     */
    private void setStoreItemClick() {
    	mItemsListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
            		long arg3) {
            	Intent i = new Intent(getActivity(),
    					RetailerStoresActivity.class);
            	Retailer retailer = (Retailer) adapterView.getItemAtPosition(position);
            	i.putExtra("retailer", retailer); 
            	startActivity(i);
            }
        });
    }
    
    /**
     * This method is used to update the UI depending upon the response from the network calls
     *
     * @param response
     */
    public void updateUiDelegate(ServiceResponse response) {

        if (response.getErrorCode() == ServiceResponse.EXCEPTION) {
            removeProgressDialog();
            switch (response.getEventType()) {

                case FlipchaseApi.API_STORE_SHOW_MORE:
                    showCommonErrorDialog();
                    //updateStoreCatalogueShowMoreResponse(response);
                    break;
                default:
                    break;
            }

            handleErrorResponse(response);

        } else if (response.getErrorCode() == ServiceResponse.SUCCESS) {
            switch (response.getEventType()) {
                case FlipchaseApi.API_STORE_SHOW_MORE:
                	updateStoreCatalogueShowMoreResponse(response);
                    break;
            }
        } else if (response.getErrorCode() == ServiceResponse.MESSAGE_ERROR) {
            removeProgressDialog();
            String errorMsg = null;
            if (response.getErrorMessages() != null && response.getErrorMessages().size() > 0) {
                for (int i = 0; i < response.getErrorMessages().size(); i++) {
                    if (errorMsg == null) {
                        errorMsg = response.getErrorMessages().get(i);
                    } else {
                        errorMsg = errorMsg + response.getErrorMessages().get(i);
                    }
                }
            }
            switch (response.getEventType()) {
                default:
                    showWebErrorDialog(errorMsg);
            }
        }
    }

    /**
     * This method triggers the event to show the more products in the catalog
     *
     * @param response
     */
    private void updateStoreCatalogueShowMoreResponse(ServiceResponse response) {
        mShowMoreLayout.setVisibility(View.GONE);
        mRefineLayout.setVisibility(View.VISIBLE);
        IS_LOADING_MORE_DATA = false;
        if (response.getErrorCode() == ServiceResponse.SUCCESS) {
            StoreCatalogue storeCatalogue = null;
            if(response.getResponseObject()!=null)
            	storeCatalogue= (StoreCatalogue) response.getResponseObject();
            if(storeCatalogue == null)
            	storeCatalogue = new StoreCatalogue();

            mStoreData.getItems().addAll(storeCatalogue.getItems());
            //((StoreAdapter) mItemsGrid.getAdapter()).setItems(mStoreData.getItems());
            mTotalCatalog = mStoreData.getTotal();

            int pId = mStoreData.getPageId();
            pId = pId + 1;
            mStoreData.setPageId(pId);
            updateTopFragmentStoreCatalogueData(mStoreData);
        } else {
            IS_ERROR_OCCURED_WHILE_LOADING_MORE_DATA = true;
        }
    }

    private void handleErrorResponse(ServiceResponse response) {

        switch (response.getEventType()) {
            default:
            	showCommonErrorDialog();
                break;
        }

    }

}
