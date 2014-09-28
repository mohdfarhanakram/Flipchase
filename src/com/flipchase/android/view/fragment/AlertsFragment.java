package com.flipchase.android.view.fragment;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.flipchase.android.R;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.domain.CatalogueDisplay;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.parcels.CatalogueChunk;
import com.flipchase.android.util.Utils;
import com.flipchase.android.view.activity.HomeActivity;
import com.flipchase.android.view.adapter.CatalogueAdapter;

public class AlertsFragment extends BaseFragment {

	private GridView catalogGridView;
	private CatalogueAdapter catalogueAdapter;
	
	View mView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_alerts_layout, container, false);
		
		catalogGridView = (GridView)mView.findViewById(R.id.alerts_grid_view);
		catalogueAdapter = new CatalogueAdapter(getActivity(), Collections.EMPTY_LIST);
		catalogGridView.setAdapter(catalogueAdapter);
		if(Utils.isInternetAvailable(getActivity())){
			((HomeActivity) getActivity()).updateAlertsData("");
		}else{
			mView.findViewById(R.id.no_connection).setVisibility(View.VISIBLE);
			mView.findViewById(R.id.loader_view).setVisibility(View.GONE);
			mView.findViewById(R.id.main_view).setVisibility(View.GONE);
		}
		
		return mView;
	}
	
	@Override
	public void updateUi(ServiceResponse response) {
		super.updateUi(response);
		mView.findViewById(R.id.loader_view).setVisibility(View.GONE);
		switch (response.getEventType()) {
		case FlipchaseApi.GET_MOBILE_ALERTS_CATALOGUES:
			CatalogueChunk catalogueChunk = (CatalogueChunk)response.getResponseObject();
			if(catalogueChunk != null) {
				mView.findViewById(R.id.main_view).setVisibility(View.VISIBLE);
				List<CatalogueDisplay> latestCatalogues = catalogueChunk.getItems();
				catalogueAdapter.setItems(latestCatalogues);
			}
		default:
			break;
		}
	}
}
