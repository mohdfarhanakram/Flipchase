/**
 * 
 */
package com.flipchase.android.view.activity;

import java.util.ArrayList;

import com.flipchase.android.R;
import com.flipchase.android.controller.DbController;
import com.flipchase.android.controller.DbEvent;
import com.flipchase.android.listener.DbListener;
import com.flipchase.android.model.DbControllerResponse;
import com.flipchase.android.model.Item;
import com.flipchase.android.view.adapter.SubListAdapter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * @author FARHAN
 *
 */
public class SubListActivity extends BaseActivity implements DbListener{

	private ListView mListView;
	private String id;
	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_list);
		mListView = (ListView)findViewById(R.id.subListview);

		id = getIntent().getStringExtra("catalogId");
		name = getIntent().getStringExtra("catalogName");  

		showProgressDialog("Loading..");
		DbController controller = new DbController(this, id, DbEvent.FETCH_SUB_LIST, this);
		controller.execute();
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
		getSupportActionBar().setTitle(name);
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


	@Override
	public void onDatabaseOperationDone(DbControllerResponse response) {
		removeProgressDialog();
		switch (response.getEvent()) {
		case DbEvent.FETCH_SUB_LIST:
			ArrayList<Item> subList = (ArrayList<Item>)response.getResponseObject();
			mListView.setAdapter(new SubListAdapter(this, subList,name));
			break;

		default:
			break;
		}

	}

}
