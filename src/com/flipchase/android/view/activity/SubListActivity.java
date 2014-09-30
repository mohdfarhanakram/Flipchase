/**
 * 
 */
package com.flipchase.android.view.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.flipchase.android.R;
import com.flipchase.android.controller.DbController;
import com.flipchase.android.controller.DbEvent;
import com.flipchase.android.listener.DbListener;
import com.flipchase.android.listener.EditButtonClickListener;
import com.flipchase.android.listener.LongPressListener;
import com.flipchase.android.model.DbControllerResponse;
import com.flipchase.android.model.Item;
import com.flipchase.android.util.StringUtils;
import com.flipchase.android.view.adapter.ActionBarListAdapter;
import com.flipchase.android.view.adapter.SubListAdapter;
import com.flipchase.android.view.widget.CustomFontEditText;

import de.ankri.views.Switch;

/**
 * @author FARHAN
 *
 */
public class SubListActivity extends BaseActivity implements DbListener,LongPressListener,EditButtonClickListener,DialogInterface.OnClickListener{

	private ArrayList<Item> mSubItemList = new ArrayList<Item>();
	private ListView mListView;
	private String cataloId;
	private String catalogName;
	
	private String sCataloId;
	private String sCatalogName;
	
	private int selectedIndex = -1;
	private ActionMode mActionMode ;
	
	private ActionBar mBar;
	
	private ArrayList<Item> itemList = new ArrayList<Item>();
	
	private String uId;
	
	private String mPid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppThemeLight);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_list);
		mListView = (ListView)findViewById(R.id.subListview);

		cataloId = getIntent().getStringExtra("catalogId");
		catalogName = getIntent().getStringExtra("catalogName");
		
		sCatalogName = catalogName;
		sCataloId = cataloId;
		
		itemList = (ArrayList<Item>)getIntent().getSerializableExtra("list"); 
		mBar = getSupportActionBar();
		
		createActionSpinner();
		getDataBasedOnId(cataloId);
		
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
		getSupportActionBar().setTitle(null);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setSubtitle(null);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}else if(item.getItemId() == R.id.sublist_edit_btn){
			startActionMode();
		}
		return false;
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
		case DbEvent.UPDATE_SUB_LIST_DATA:
			boolean success = (Boolean)response.getResponseObject();
			if(success){
				getDataBasedOnId(cataloId);
			}else{
				Toast.makeText(this, "Some thing went wrong.", Toast.LENGTH_SHORT).show();
			}
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
		mListView.setAdapter(new SubListAdapter(this,this,this, mSubItemList,catalogName,isCheckBoxShown,selectedIndex));
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
			mActionMode = mode;
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
	private View mFormView;
	
	
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
	
	
	/**
	 * Create tab bar
	 */
	private void createActionSpinner() {
		int index= getSelectedIndex();
		Item item = itemList.get(index);
		final ActionBarListAdapter listAdapter = new ActionBarListAdapter(this,itemList,item.getName());
        mBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        //mBar.setListNavigationCallbacks(listAdapter,null);
        mBar.setListNavigationCallbacks(listAdapter, new ActionBar.OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int i, long l) {
            	Log.e("clicked Index", i+"");
            	
            	Item item = itemList.get(i);
            	listAdapter.setSubTitle(item.getName());
            	cataloId  = item.getId();
            	catalogName = item.getName();
            	
            	 sCatalogName = catalogName;
                 sCataloId = cataloId;
            	getDataBasedOnId(cataloId);
                return true;
            }
        });
        
        mBar.setSelectedNavigationItem(index);
        
    }

	
	private int getSelectedIndex(){
		int index = 0;
		for(int i =0; i<itemList.size(); i++){
			if(itemList.get(i).getName().equals(catalogName)){
				index = i;
				break;
			}
		}
		
		return index;
	}
	
	private String[] getStringArray(){
		String tabs[] = new String[itemList.size()];
		
		for(int i=0; i<itemList.size(); i++){
			tabs[i] = itemList.get(i).getName();
		}
		
		return tabs;
	}
	
	private void getDataBasedOnId(String id){
		showProgressDialog("Loading..");
		DbController controller = new DbController(this, id, DbEvent.FETCH_SUB_LIST, this);
		controller.execute();
	}

	@Override
	public void onEditButtonClickListener(Item item) {
		createFormDialog(item).show();
		
	}
	
	private final Dialog createFormDialog(Item item) {
		mActionMode.finish();
		uId = item.getUid();
		
		mPid = item.getId();
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mFormView = inflater.inflate(R.layout.layout_item_add_details, null);
		
		((CustomFontEditText)mFormView.findViewById(R.id.s_item_title)).setText(item.getTitle());
		((Switch)mFormView.findViewById(R.id.s_set_reminder)).setChecked(item.isReminder());
		((CustomFontEditText)mFormView.findViewById(R.id.s_quantity)).setText(item.getQuantity());
		((CustomFontEditText)mFormView.findViewById(R.id.s_item_name)).setText(item.getSubTitle());
		
		Spinner spinner = (Spinner)mFormView.findViewById(R.id.select_list_spinner);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
              sCatalogName = itemList.get(position).getName();
              sCataloId = itemList.get(position).getId();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, getSelectList());
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		
		spinner.setSelection(getSelectedIndex());

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("List: Add Details");
		builder.setPositiveButton("Update",this);
		builder.setNegativeButton("Cancel", this);
		builder.setView(mFormView);
		return builder.create();
	}
	
	private ArrayList<String> getSelectList(){
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0; i<itemList.size(); i++){
			list.add(itemList.get(i).getName());
		}
		return list;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case Dialog.BUTTON_NEGATIVE:
			dialog.dismiss();
			break;
		case Dialog.BUTTON_POSITIVE:
			updateList();
			break;

		default:
			break;
		}
		
	}
	
	private void updateList() {

		ArrayList<Object> dataList = new ArrayList<Object>();

		ImageView itemImage = (ImageView)mFormView.findViewById(R.id.itmeImage);
		String itemTitle = ((CustomFontEditText)mFormView.findViewById(R.id.s_item_title)).getText().toString();
		int reminder = ((Switch)mFormView.findViewById(R.id.s_set_reminder)).isChecked()==true?1:0;
		String quantity = ((CustomFontEditText)mFormView.findViewById(R.id.s_quantity)).getText().toString();
		String subItem = ((CustomFontEditText)mFormView.findViewById(R.id.s_item_name)).getText().toString();
		

		Item item = new Item();

		item.setTitle(itemTitle==null?"":itemTitle);
		item.setReminder(reminder);
		item.setQuantity(StringUtils.isNullOrEmpty(quantity)==true?"1":quantity);
		item.setSubTitle(subItem==null?"":subItem);
		item.setId(sCataloId);
		item.setName(sCatalogName);
		item.setImageInByte(null);
		
		item.setUid(uId);
		
		dataList.add(item);
		dataList.add(mPid);

		showProgressDialog("Loading..");
		DbController controller = new DbController(this, dataList, DbEvent.UPDATE_SUB_LIST_DATA, this);
		controller.execute();

	}
	
	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		finish();
	}
	
}
