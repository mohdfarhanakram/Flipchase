/**
 * 
 */
package com.flipchase.android.view.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;
import android.widget.Toast;

import com.flipchase.android.R;
import com.flipchase.android.controller.DbController;
import com.flipchase.android.controller.DbEvent;
import com.flipchase.android.listener.DbListener;
import com.flipchase.android.model.DbControllerResponse;
import com.flipchase.android.model.Item;
import com.flipchase.android.util.StringUtils;
import com.flipchase.android.view.activity.BaseActivity;
import com.flipchase.android.view.adapter.ListAdapter;

/**
 * @author m.farhan
 *
 */
public class ListFragment extends BaseFragment implements DbListener{
	
	private View mView;
	private ArrayList<Item> mItemList = new ArrayList<Item>();
	private ListAdapter mListAdapter;
	private ListView mListView;
	private ActionMode mActionMode ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_list_layout, container, false);
		
		mListView = (ListView)mView.findViewById(R.id.item_list_view);
		
		/*mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		mListView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

			@Override
			public boolean onCreateActionMode(android.view.ActionMode mode,
					Menu menu) {
				MenuInflater inflater = getActivity().getMenuInflater();
			    inflater.inflate(R.menu.list_fragment_contextual_menu, menu);
				return true;
			}


			@Override
			public boolean onActionItemClicked(android.view.ActionMode mode,
					MenuItem item) {
				// Respond to clicks on the actions in the CAB
		        switch (item.getItemId()) {
		            case R.id.action_delete:
		                //deleteSelectedItems();
		                mode.finish(); // Action picked, so close the CAB
		                return true;
		            default:
		                return false;
		        }
			}
			
			

			@Override
			public boolean onPrepareActionMode(android.view.ActionMode mode,Menu menu) {
				return false;
			}

			@Override
			public void onDestroyActionMode(android.view.ActionMode mode) {
				
			}

			@Override
			public void onItemCheckedStateChanged(android.view.ActionMode mode,int position, long id, boolean checked) {
				
			}

		    @Override
		    public void onItemCheckedStateChanged(ActionMode mode, int position,
		                                          long id, boolean checked) {
		        // Here you can do something when items are selected/de-selected,
		        // such as update the title in the CAB
		    }

		    @Override
		    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		        // Respond to clicks on the actions in the CAB
		        switch (item.getItemId()) {
		            case R.id.menu_delete:
		                deleteSelectedItems();
		                mode.finish(); // Action picked, so close the CAB
		                return true;
		            default:
		                return false;
		        }
		    }

		    @Override
		    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		        // Inflate the menu for the CAB
		        MenuInflater inflater = mode.getMenuInflater();
		        inflater.inflate(R.menu.context, menu);
		        return true;
		    }

		    @Override
		    public void onDestroyActionMode(ActionMode mode) {
		        // Here you can make any necessary updates to the activity when
		        // the CAB is removed. By default, selected items are deselected/unchecked.
		    }

		    @Override
		    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		        // Here you can perform updates to the CAB due to
		        // an invalidate() request
		        return false;
		    }
		});*/
		
		
		DbController controller = new DbController(getActivity(), null, DbEvent.FETCH_LIST,this);
		controller.execute();
		return mView;
	}
	
	/*@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
	    inflater.inflate(R.menu.list_fragment_contextual_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        case R.id.action_delete:
	            editNote(info.id);
	            return true;

	        default:
	            return super.onContextItemSelected(item);
	    }
	}*/

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
					
					mListView.setAdapter(new ListAdapter(getActivity(), itemList));
					
			
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
					ListView listView = (ListView)mView.findViewById(R.id.item_list_view);
					listView.setAdapter(new ListAdapter(getActivity(), mItemList));
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
	        return false; // Return false if nothing is done
	    }

	    // Called when the user selects a contextual menu item
	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.action_delete:
	                //shareCurrentItem();
	                mode.finish(); // Action picked, so close the CAB
	                return true;
	            default:
	                return false;
	        }
	    }

	    // Called when the user exits the action mode
	    @Override
	    public void onDestroyActionMode(ActionMode mode) {
	        mActionMode = null;
	    }
	};
	
	
	
}
