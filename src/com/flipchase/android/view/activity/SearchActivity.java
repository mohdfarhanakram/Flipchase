/**
 * 
 */
package com.flipchase.android.view.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.flipchase.android.R;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.domain.CatalogueDisplay;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.view.adapter.SearchFragmentAdapter;

/**
 * @author FARHAN
 *
 */
public class SearchActivity extends BaseActivity{

	private String searchKey = "";
	
	private PagerTitleStrip mPagerTitleStrip;
	private ViewPager mViewPager;
	private SearchFragmentAdapter mSearchFragmentAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_layout);
		
		mPagerTitleStrip = (PagerTitleStrip)findViewById(R.id.pager_title_strip);
		mViewPager = (ViewPager)findViewById(R.id.pager);
		
		mSearchFragmentAdapter = new SearchFragmentAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.search_tabs));
		mViewPager.setAdapter(mSearchFragmentAdapter);

		searchKey = getIntent().getStringExtra("SEARCH_KEY");
		
		doSearch(searchKey);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		buildActionBar(menu);
		return super.onCreateOptionsMenu(menu);

	} 

	public void buildActionBar(Menu menu) {
		if (null == menu) return;
		mMenu = menu;
		mMenu.clear();
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.search_activity_menu, mMenu);
	    buildSearchView(getString(R.string.search_hint_text),R.id.flipchase_action_search, mMenu, this, this, true,0);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle(searchKey);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setSubtitle("0 Result");
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void doSearch(String searchKey) {
		getSupportActionBar().setTitle(searchKey);
		getSupportActionBar().setSubtitle("0 Result");
		// implement network call here for search;
		String query = "";
        try {
            query = URLEncoder.encode(searchKey,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
		fetchData(URLConstants.GET_SEARCH_URL+query, FlipchaseApi.API_SEARCH_RESULT, null);
	}
	
	
	@Override
	public void updateUi(ServiceResponse response) {
		super.updateUi(response);
		if (response.getErrorCode() == ServiceResponse.EXCEPTION) {
            showCommonError("Error Occured...");
        } else if (response.getErrorCode() == ServiceResponse.SUCCESS ) {
            if (response.getFlipChaseBaseModel().isSuccess() && response.getEventType()==FlipchaseApi.API_SEARCH_RESULT) {
            	mSearchFragmentAdapter.updateFragmentUI(response);
            	ArrayList<CatalogueDisplay> searchRes = (ArrayList<CatalogueDisplay>)response.getResponseObject();
            	
            	getSupportActionBar().setSubtitle(searchRes.size()+" Result");
            }
        }
	}


}
