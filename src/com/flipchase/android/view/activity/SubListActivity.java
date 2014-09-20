/**
 * 
 */
package com.flipchase.android.view.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.flipchase.android.R;
import com.flipchase.android.controller.DbController;
import com.flipchase.android.controller.DbEvent;
import com.flipchase.android.listener.DbListener;
import com.flipchase.android.listener.LongPressListener;
import com.flipchase.android.model.DbControllerResponse;
import com.flipchase.android.model.Item;
import com.flipchase.android.view.adapter.ListAdapter;
import com.flipchase.android.view.adapter.SubListAdapter;

/**
 * @author FARHAN
 *
 */
public class SubListActivity extends BaseActivity implements DbListener,LongPressListener{

	private ArrayList<Item> mSubItemList = new ArrayList<Item>();
	private ListView mListView;
	private String id;
	private String name;
	private int selectedIndex = -1;
	private ActionMode mActionMode ;

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
		mMenu = menu;
		getMenuInflater().inflate(R.menu.sublist_actionbar_menu, mMenu);
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
		}else if(item.getItemId() == R.id.sublist_edit_btn){
			startActionMode();
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onDatabaseOperationDone(DbControllerResponse response) {
		removeProgressDialog();
		switch (response.getEvent()) {
		case DbEvent.FETCH_SUB_LIST:
			mSubItemList = (ArrayList<Item>)response.getResponseObject();
			selectedIndex = -1;
			drawListView(false,selectedIndex);
			break;

		default:
			break;
		}

	}

	public void startActionMode(){
		if (mActionMode != null) {
			return;
		}
		startSupportActionMode(mActionModeCallback);
	}

	private void drawListView(boolean isCheckBoxShown,int selectedIndex){
		mListView.setAdapter(new SubListAdapter(this,this, mSubItemList,name,isCheckBoxShown,selectedIndex));
	}

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// Called when the action mode is created; startActionMode() was called
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.list_fragment_contextual_menu, menu);
			return true;
		}

		// Called each time the action mode is shown. Always called after onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			mActionMode = mode;
			drawListView(true,selectedIndex);
			return true; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.action_delete:
				deleteSelectedList();
				mode.finish(); // Action picked, so close the CAB
				return true;
			default:
				return false;
			}
		}

		// Called when the user exits the action mode
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			selectedIndex = -1;
			drawListView(false,selectedIndex);
			mActionMode = null;
		}
	};
	
	
	private void deleteSelectedList(){

		int count = mListView.getChildCount();
		ArrayList<String> stringList = new ArrayList<String>();
		for(int i=0; i<count; i++ ){
			View view = mListView.getChildAt(i);

			CheckBox checkBox = (CheckBox)view.findViewById(R.id.sub_list_chk_box);
			if(checkBox.isChecked()){
				LinearLayout layout = (LinearLayout)view.findViewById(R.id.main_layout);
				Item item = (Item)layout.getTag();
				stringList.add(item.getId());
				mSubItemList.remove(item);
			}

		}
		
		if(stringList.size()>0){
			
			selectedIndex = -1;
			drawListView(false,selectedIndex);
			
			String[] mStringArray = new String[stringList.size()];
			mStringArray = stringList.toArray(mStringArray);
			
			ArrayList<Object> data = new ArrayList<Object>();
			
			data.add(mStringArray);
			data.add(count);
			
			DbController controller = new DbController(this, data, DbEvent.DELETE_SELECTED_SUB_LIST,this);
			controller.execute();
		}
	}

	@Override
	public void onLongPressed(Item item) {
		selectedIndex = mSubItemList.indexOf(item);
		startActionMode();
		
	}

}
