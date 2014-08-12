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
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.domain.CatalogueDisplay;
import com.flipchase.android.model.ServiceResponse;
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_deal, container, false);
		
		catalogGridView = (GridView)view.findViewById(R.id.catalog_grid_view);
		catalogueAdapter = new CatalogueAdapter(getActivity(), Collections.EMPTY_LIST);
		
		catalogGridView.setAdapter(catalogueAdapter);
		view.findViewById(R.id.filterButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(),FilterActivity.class);
				startActivity(i);
			}
		});
		((HomeActivity) getActivity()).updateDealsCatalogueData("refinedURLWithPageIds");
		return view;
	}

	@Override
	public void updateUi(ServiceResponse response) {
		super.updateUi(response);
		switch (response.getEventType()) {
		case FlipchaseApi.GET_LATEST_CATALOGUES:
			List<CatalogueDisplay> latestCatalogues = (List<CatalogueDisplay>)response.getResponseObject();
			catalogueAdapter.setItems(latestCatalogues);
			break;
		default:
			break;
		}
	}
} 
