/**
 * 
 */
package com.flipchase.android.view.fragment;

import java.util.ArrayList;

import com.flipchase.android.R;
import com.flipchase.android.controller.DbController;
import com.flipchase.android.controller.DbEvent;
import com.flipchase.android.listener.DbListener;
import com.flipchase.android.model.DbControllerResponse;
import com.flipchase.android.model.Item;
import com.flipchase.android.util.StringUtils;
import com.flipchase.android.view.adapter.ListAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author m.farhan
 *
 */
public class ListFragment extends BaseFragment implements DbListener{
	
	private View mView;
	private ArrayList<Item> mItemList = new ArrayList<Item>();
	private ListAdapter mListAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_list_layout, container, false);
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
					
					ListView listView = (ListView)mView.findViewById(R.id.item_list_view);
					listView.setAdapter(new ListAdapter(getActivity(), itemList));
					
			
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
}
