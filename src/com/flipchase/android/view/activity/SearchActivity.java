/**
 * 
 */
package com.flipchase.android.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.flipchase.android.R;

/**
 * @author FARHAN
 *
 */
public class SearchActivity extends BaseActivity{

	private String searchKey = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_layout);

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
		getSupportActionBar().setTitle("Search Result");
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setSubtitle(searchKey);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void doSearch(String searchKey) {
		getSupportActionBar().setSubtitle(searchKey);
		// implement network call here for search;
	}


}
