/**
 * 
 */
package com.flipchase.android.view.activity;

import java.util.Stack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.flipchase.android.R;
import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.parcels.StoreCatalogue;
import com.flipchase.android.view.adapter.HomeFragmentAdapter;

/**
 * @author m.farhan
 *
 */
public class HomeActivity extends BaseActivity implements ActionBar.TabListener ,OnClickListener,SearchView.OnQueryTextListener, SearchView.OnSuggestionListener{
	
	private ActionBar mBar;
	private ViewPager mHomeViewPager;
	
	private Stack<StoreCatalogue> mProductDataStack;
	private StoreCatalogue mLatestCatalogData;
	private HomeFragmentAdapter homeFragmentAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_layout);
		mHomeViewPager = (ViewPager)findViewById(R.id.home_view_pager);
		mBar = getSupportActionBar();
		createActionTabBar();
		createScreenSliderView();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		mMenu = menu;
		mMenu.clear();
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_action_bar_menu, menu);
		
		buildSearchView(R.id.flipchase_action_search, mMenu, this, this, true);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	public Stack<StoreCatalogue> getStoreCatalogueDataStack() {
		if (mProductDataStack == null)
			mProductDataStack = new Stack<StoreCatalogue>();
		return mProductDataStack;
	}
	
	
	/**
	 * Design ScreenSlider view 
	 */
	private void createScreenSliderView(){
		mHomeViewPager.setOnPageChangeListener(
	                new ViewPager.SimpleOnPageChangeListener() {
	                    @Override
	                    public void onPageSelected(int position) {
	                        // When swiping between pages, select the
	                        mBar.setSelectedNavigationItem(position);

	                    }
	                });
		homeFragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.home_tabs));
		mHomeViewPager.setOffscreenPageLimit(4);
		mHomeViewPager.setAdapter(homeFragmentAdapter);
	}
	
	
	/**
	 * Create tab bar
	 */
	private void createActionTabBar() {
        mBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        String tabs[] = getResources().getStringArray(R.array.home_tabs);
        
        if(tabs!=null && tabs.length>0){
        	for(int i=0; i<tabs.length; i++){
        		android.support.v7.app.ActionBar.Tab tab = mBar.newTab();
        		tab.setText(tabs[i]);
        		tab.setTabListener(this);
        		mBar.addTab(tab);
        	}
        }
    }

	public void updateDealsCatalogueData(String refineUrl) {
		showProgressDialog("Loading Deals Data...");
		fetchData(URLConstants.GET_LATEST_CATEGORY_URL, FlipchaseApi.GET_LATEST_CATALOGUES, null);
	}
	
	public void updateRetailerCatalogData(String refineUrl) {
        showProgressDialog("Loading Retailer Data...");
        /*
        if(mItemsGridFragment==null)
        {
            if(getIntent().getExtras().getString(Constants.INTENT_SOURCE_ACTIVITY) != null && getIntent().getExtras().getString(Constants.INTENT_SOURCE_ACTIVITY_EXTRA) != null)
                Utils.postExceptionOnGA("Error in updateCatalogData of CatalogActivity \r\n mItemsGridFragment=null  \r\n Calling Activity: "+getIntent().getExtras().getString(Constants.INTENT_SOURCE_ACTIVITY)+" \r\n Extras: "+getIntent().getExtras().getString(Constants.INTENT_SOURCE_ACTIVITY_EXTRA)+" \n sessionid: "+getSessionId(),false);
        }
        mItemsGridFragment.setRefineFilterUrl(refineUrl, "");
        */
        fetchData(URLConstants.GET_ALL_RETAILERS_URL, FlipchaseApi.GET_ALL_RETAILERS, null);
    }

	@Override
	public void updateUi(ServiceResponse response) {
		super.updateUi(response);
		removeProgressDialog();
		if (response.getErrorCode() == ServiceResponse.EXCEPTION) {
            showCommonError("Error Occured...");
        } else if (response.getErrorCode() == ServiceResponse.SUCCESS) {
            if (response.getFlipChaseBaseModel().isSuccess()) {
            	homeFragmentAdapter.updateFragmentUI(response);
            }
        }
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

	     mHomeViewPager.setCurrentItem(tab.getPosition());
		
	}


	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSuggestionClick(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSuggestionSelect(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.flipchase_location){
			Intent intent = new Intent(HomeActivity.this, SelectLocationActivity.class);
			intent.putExtra(AppConstants.IS_COMING_FROM_SPLASH, false);
			startActivity(intent);
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public StoreCatalogue getCatalogDataIfDataIsExist() {
        return mLatestCatalogData;
    }
}
