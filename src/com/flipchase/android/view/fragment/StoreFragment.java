/**
 * 
 */
package com.flipchase.android.view.fragment;

import java.util.List;

import android.app.Activity;
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
import com.flipchase.android.view.activity.HomeActivity;
import com.flipchase.android.view.adapter.StoreAdapter;
import com.flipchase.android.view.widget.CustomFontTextView;
import com.flipchase.android.view.widget.StoreListView;

/**
 * @author m.farhan
 *
 */
public class StoreFragment extends BaseFragment {
	
 	private StoreListView mItemsGrid;
    private String mRefineUrl = "";
    private String mFilterUrl = "";
    private StoreCatalogue mStoreData;
    private StoreAdapter mItemsGridAdapter;
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
	    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_items_grid, container, false);
		mRefineLayout = (LinearLayout) (view.findViewById(R.id.storeRefineLayout));
        //initializeImageWidthHeight();
        mShowMoreLayout = (LinearLayout) view.findViewById(R.id.storeLayoutShowMore);

        mItemsGrid = (StoreListView) view.findViewById(R.id.storeListItems);
        if (null == mItemsGridAdapter) {
            mItemsGridAdapter = new StoreAdapter(getActivity());
        }
       // mItemsGridAdapter.setImageHeightWidth(mImageHeight, mImageWidth);
            mItemsGrid.setAdapter(mItemsGridAdapter);
            mItemsGridAdapter.notifyDataSetChanged();
        mItemsGrid.setOnScrollListener(new OnScrollListener() {
        	
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
        
        mRefineButton = (CustomFontTextView) view.findViewById(R.id.storeRefineButton);
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
            ((HomeActivity) getActivity()).updateRetailerCatalogData("");
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
		List<Retailer> retailers = (List<Retailer>)response.getResponseObject();
		 ((StoreAdapter) mItemsGrid.getAdapter()).setItems(retailers);
		 mItemsGridAdapter.notifyDataSetChanged();
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
        if (mItemsGridAdapter != null)
            mItemsGridAdapter.setContext(activity);
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
     * set grid item click listener
     */
    private void setStoreItemClick() {
        mItemsGrid.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View View,
                                    int position, long arg3) {
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
