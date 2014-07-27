/**
 * 
 */
package com.flipchase.android.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;

import com.flipchase.android.R;
import com.flipchase.android.dummyData.DummyData;
import com.flipchase.android.view.activity.FilterActivity;
import com.flipchase.android.view.adapter.CatalogueAdapter;

/**
 * @author m.farhan
 *
 */
public class DealsFragment extends BaseFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_deal, container, false);
		
		GridView catalogGridView = (GridView)view.findViewById(R.id.catalog_grid_view);
		catalogGridView.setAdapter(new CatalogueAdapter(getActivity(), DummyData.getDummyCatalogues()));
		view.findViewById(R.id.filterButton).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(),FilterActivity.class);
				startActivity(i);
				
			}
		});
		return view;
	}

} 
