/**
 * 
 */
package com.flipchase.android.view.fragment;

import com.flipchase.android.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author m.farhan
 *
 */
public class StoreFragment extends BaseFragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_store_layout, container, false);
		return view;
	}

}
