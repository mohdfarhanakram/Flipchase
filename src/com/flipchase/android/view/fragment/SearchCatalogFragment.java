/**
 * 
 */
package com.flipchase.android.view.fragment;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.GridView;

import com.flipchase.android.R;
import com.flipchase.android.domain.CatalogueDisplay;
import com.flipchase.android.model.Search;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.parcels.CatalogueChunk;
import com.flipchase.android.util.Utils;
import com.flipchase.android.view.activity.FilterActivity;
import com.flipchase.android.view.activity.HomeActivity;
import com.flipchase.android.view.adapter.CatalogueAdapter;

/**
 * @author FARHAN
 *
 */
public class SearchCatalogFragment extends BaseFragment{

	private View view;
	private GridView catalogGridView;
	private CatalogueAdapter catalogueAdapter;

	private CatalogueChunk catalogueChunk;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_search_catalogue, container, false);
		
		catalogGridView = (GridView)view.findViewById(R.id.catalog_grid_view);
		catalogueAdapter = new CatalogueAdapter(getActivity(), Collections.EMPTY_LIST);
		catalogGridView.setAdapter(catalogueAdapter);
		
		if(!Utils.isInternetAvailable(getActivity())){
			view.findViewById(R.id.no_connection).setVisibility(View.VISIBLE);
			view.findViewById(R.id.loader_view).setVisibility(View.GONE);
			view.findViewById(R.id.main_view).setVisibility(View.GONE);
		}
		return view;
		
	}
	
	@Override
	public void updateUi(ServiceResponse response) {
		super.updateUi(response);
		view.findViewById(R.id.loader_view).setVisibility(View.GONE);
		view.findViewById(R.id.no_connection).setVisibility(View.GONE);
		
		
		
		ArrayList<CatalogueDisplay> searchRes = (ArrayList<CatalogueDisplay>)response.getResponseObject();
		
		if(searchRes.size()>0){
			view.findViewById(R.id.main_view).setVisibility(View.VISIBLE);
			view.findViewById(R.id.no_result_view).setVisibility(View.GONE);
			
			catalogueAdapter = new CatalogueAdapter(getActivity(), searchRes);
			catalogGridView.setAdapter(catalogueAdapter);
		}else{
			view.findViewById(R.id.main_view).setVisibility(View.GONE);
			view.findViewById(R.id.no_result_view).setVisibility(View.VISIBLE);
		}
		
		
	}

}
