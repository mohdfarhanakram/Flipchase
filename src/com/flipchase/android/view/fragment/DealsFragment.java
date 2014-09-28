/**
 * 
 */
package com.flipchase.android.view.fragment;

import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;

import com.flipchase.android.R;
import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.domain.CatalogueDisplay;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.network.VolleyGenericRequest;
import com.flipchase.android.parcels.CatalogueChunk;
import com.flipchase.android.util.Utils;
import com.flipchase.android.view.activity.BaseActivity;
import com.flipchase.android.view.activity.FilterActivity;
import com.flipchase.android.view.activity.HomeActivity;
import com.flipchase.android.view.adapter.CatalogueAdapter;

/**
 * @author m.farhan
 *
 */
public class DealsFragment extends BaseFragment {
	
	private GridView catalogGridView;
	private CatalogueAdapter catalogueAdapter;
	
	private int FILTER_URL_REQUEST_CODE = 1000;
	
	private CatalogueChunk catalogueChunk;
	
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_deal, container, false);
		
		catalogGridView = (GridView)view.findViewById(R.id.catalog_grid_view);
		catalogueAdapter = new CatalogueAdapter(getActivity(), Collections.EMPTY_LIST);
		
		catalogGridView.setAdapter(catalogueAdapter);
		view.findViewById(R.id.filterButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(catalogueChunk!=null && catalogueChunk.getSortBy()!=null && catalogueChunk.getFilterBy()!=null){
					Intent i = new Intent(getActivity(),FilterActivity.class);
					i.putExtra("sortby", catalogueChunk.getSortBy());
					i.putExtra("filterby", catalogueChunk.getFilterBy());
					startActivityForResult(i, FILTER_URL_REQUEST_CODE);
				}
				
			}
		});
		
		if(Utils.isInternetAvailable(getActivity())){
			((HomeActivity) getActivity()).updateDealsCatalogueData("refinedURLWithPageIds");
		}else{
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
		switch (response.getEventType()) {
		case FlipchaseApi.GET_LATEST_CATALOGUES:
			catalogueChunk = (CatalogueChunk)response.getResponseObject();
			if(catalogueChunk != null) {
				view.findViewById(R.id.main_view).setVisibility(View.VISIBLE);
				List<CatalogueDisplay> latestCatalogues = catalogueChunk.getItems();
				catalogueAdapter.setItems(latestCatalogues);
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == FILTER_URL_REQUEST_CODE){
			if(resultCode == getActivity().RESULT_OK){
				String jsonString = data.getStringExtra(AppConstants.FILTER_URL);
				
				((BaseActivity)getActivity()).postData(URLConstants.GET_LATEST_CATEGORY_URL, FlipchaseApi.GET_LATEST_CATALOGUES, jsonString, VolleyGenericRequest.ContentType.JSON, null);
			}
		}
	}
} 
