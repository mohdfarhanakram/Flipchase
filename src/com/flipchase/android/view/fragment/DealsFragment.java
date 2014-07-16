/**
 * 
 */
package com.flipchase.android.view.fragment;

import com.flipchase.android.R;
import com.flipchase.android.dummyData.DummyData;
import com.flipchase.android.view.adapter.CatalogueAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.GridView;

/**
 * @author m.farhan
 *
 */
public class DealsFragment extends BaseFragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_deal, container, false);
		
		GridView catalogGridView = (GridView)view.findViewById(R.id.catalog_grid_view);
		catalogGridView.setAdapter(new CatalogueAdapter(getActivity(), DummyData.getDummyCatalogues()));
		return view;
	}

}
