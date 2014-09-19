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
import com.flipchase.android.view.fragment.BaseFragment;
import com.flipchase.android.view.fragment.DealsFragment;
import com.flipchase.android.view.fragment.ListFragment;
import com.flipchase.android.view.fragment.StoreFragment;

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
	
	private int pagerIndex=0;
	
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
		buildActionBar();
		return super.onCreateOptionsMenu(menu);
	}
	
	private void buildActionBar(){
		mMenu.clear();
		MenuInflater inflater = getMenuInflater();
		if(pagerIndex==0 || pagerIndex==1){
			inflater.inflate(R.menu.home_action_bar_menu, mMenu);
			buildSearchView(getString(R.string.search_hint_text),R.id.flipchase_action_search, mMenu, this, this, true,0);
		}else if(pagerIndex==2){
			inflater.inflate(R.menu.home_list_action_bar, mMenu);
			buildSearchView("Add New List",R.id.flipchase_new_list, mMenu,this, this, true,1);
		}else{
			inflater.inflate(R.menu.home_action_default, mMenu);
		}
		
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
	                        pagerIndex = position;
	                        buildActionBar();

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

	public void updateAlertsData(String refineUrl) {
		showProgressDialog("Loading Alerts...");
		fetchData(URLConstants.GET_MOBILE_ALERTS_CATALOGUES_URL, FlipchaseApi.GET_MOBILE_ALERTS_CATALOGUES, null);
	}
	
	public void updateDealsCatalogueData(String refineUrl) {
		showProgressDialog("Loading Deals Data...");
		fetchData(URLConstants.GET_LATEST_CATEGORY_URL, FlipchaseApi.GET_LATEST_CATALOGUES, null);
	}
	
	public void updateRetailerCatalogData(String refineUrl) {
        showProgressDialog("Loading Retailer Data...");
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
		
		switch (item.getItemId()) {
		case R.id.flipchase_location:
			Intent intent = new Intent(HomeActivity.this, SelectLocationActivity.class);
			intent.putExtra(AppConstants.IS_COMING_FROM_SPLASH, false);
			startActivity(intent);
			break;
		case R.id.flipchase_edit_list:
			((ListFragment)getSupportFragmentManager().getFragments().get(pagerIndex)).startActionMode();
			break;
		default:
			break;
		}
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
	
	
	public void doSearch(String searchKey){
		BaseFragment baseFragment = (BaseFragment)getSupportFragmentManager().getFragments().get(pagerIndex);
		if(baseFragment instanceof DealsFragment || baseFragment instanceof StoreFragment){
			Intent i = new Intent(this,SearchActivity.class);
			i.putExtra("SEARCH_KEY", searchKey);
			startActivity(i);
		}
	}

	public void createList(String listName){
		if(getSupportFragmentManager().getFragments().get(pagerIndex) instanceof ListFragment){
			ListFragment fragment = (ListFragment)getSupportFragmentManager().getFragments().get(pagerIndex);
			fragment.createList(listName);
		}
	}

}
