/**
 * 
 */
package com.flipchase.android.view.activity;

import com.flipchase.android.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author m.farhan
 *
 */
public class FilterActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		buildActionBar(menu);
		return super.onCreateOptionsMenu(menu);
	} 

	public void buildActionBar(Menu menu) {
		if (null == menu) return;
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle(getResources().getString(R.string.filter_txt));
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setSubtitle(null);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}


}
