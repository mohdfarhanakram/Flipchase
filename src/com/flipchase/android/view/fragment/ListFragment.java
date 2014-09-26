/**
 * 
 */
package com.flipchase.android.view.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.flipchase.android.R;
import com.flipchase.android.controller.DbController;
import com.flipchase.android.controller.DbEvent;
import com.flipchase.android.listener.DbListener;
import com.flipchase.android.listener.LongPressListener;
import com.flipchase.android.model.DbControllerResponse;
import com.flipchase.android.model.Item;
import com.flipchase.android.util.StringUtils;
import com.flipchase.android.view.activity.BaseActivity;
import com.flipchase.android.view.adapter.ListAdapter;

/**
 * @author m.farhan
 *
 */
@SuppressLint("NewApi")
public class ListFragment extends BaseFragment implements DbListener,LongPressListener{

	private View mView;
	private ArrayList<Item> mItemList = new ArrayList<Item>();
	private ListView mListView;
	private ActionMode mActionMode ;
	private int selectedIndex = -1;
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_list_layout, container, false);

		mListView = (ListView)mView.findViewById(R.id.item_list_view);

		DbController controller = new DbController(getActivity(), null, DbEvent.FETCH_LIST,this);
		controller.execute();
		return mView;
	}


	@Override
	public void onDatabaseOperationDone(DbControllerResponse response) {
		mView.findViewById(R.id.progress_bar_layout).setVisibility(View.GONE);
		if(response!=null){
			switch (response.getEvent()) {
			case DbEvent.FETCH_LIST:
				ArrayList<Item> itemList = (ArrayList<Item>)response.getResponseObject();
				if(itemList!=null && itemList.size()>0){
					mItemList = itemList;
					mView.findViewById(R.id.empty_content_view).setVisibility(View.GONE);
					mView.findViewById(R.id.main_content_view).setVisibility(View.VISIBLE);
					selectedIndex = -1;
					drawListView(false,selectedIndex);


				}else{
					mView.findViewById(R.id.empty_content_view).setVisibility(View.VISIBLE);
					mView.findViewById(R.id.main_content_view).setVisibility(View.GONE);
				}
				break;
			case DbEvent.INSERT_IN_MASTER_TABLE:
				removeProgressDialog();
				String id = (String)response.getResponseObject();
				if(!StringUtils.isNullOrEmpty(id)){
					Item item = new Item();
					item.setId(id);
					item.setName(id);
					item.setCount(0);
					mItemList.add(item);
					selectedIndex = -1;
					drawListView(false,selectedIndex);

				}else{

				}
				break;
			default:
				break;
			}
		}

	}


	public void createList(String listName){

		Item item = new Item();
		item.setId(listName);
		item.setName(listName);
		item.setCount(0);

		if(mItemList.indexOf(item)==-1){
			showProgressDialog("List is being created.");
			DbController controller = new DbController(getActivity(), listName, DbEvent.INSERT_IN_MASTER_TABLE,this);
			controller.execute();

		}else{
			Toast.makeText(getActivity(), listName +" is already persent in the list.", Toast.LENGTH_SHORT).show();
		}
	}


	public void startActionMode(){
		if (mActionMode != null) {
			return;
		}
		((BaseActivity)getActivity()).startSupportActionMode(mActionModeCallback);
	}


	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// Called when the action mode is created; startActionMode() was called
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = getActivity().getMenuInflater();
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
	@Override
	public void onLongPressed(Item item) {
		selectedIndex = mItemList.indexOf(item);
		startActionMode();

	}

	private void drawListView(boolean isCheckBoxShown,int selectedIndex){
		mListView.setAdapter(new ListAdapter(getActivity(),this, mItemList,isCheckBoxShown,selectedIndex));
	}


	private void deleteSelectedList(){

		int count = mListView.getChildCount();
		ArrayList<String> stringList = new ArrayList<String>();
		for(int i=0; i<count; i++ ){
			View view = mListView.getChildAt(i);

			CheckBox checkBox = (CheckBox)view.findViewById(R.id.list_chk_box);
			if(checkBox.isChecked()){
				LinearLayout layout = (LinearLayout)view.findViewById(R.id.main_layout);
				Item item = (Item)layout.getTag();
				stringList.add(item.getId());
				mItemList.remove(item);
			}

		}
		
		if(stringList.size()>0){
			
			selectedIndex = -1;
			drawListView(false,selectedIndex);
			
			String[] mStringArray = new String[stringList.size()];
			mStringArray = stringList.toArray(mStringArray);
			
			DbController controller = new DbController(getActivity(), mStringArray, DbEvent.DELETE_SELECTED_LIST,this);
			controller.execute();
		}
	}
	
	public void refreshList(){

		DbController controller = new DbController(getActivity(), null, DbEvent.FETCH_LIST,this);
		controller.execute();
	}
	
	
	



}
