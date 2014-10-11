/**
 * 
 */
package com.flipchase.android.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flipchase.android.R;

/**
 * @author FARHAN
 *
 */


public class SearchStoreFragment extends BaseFragment{

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_search_store, container, false);
		
		return view;
	}

}
