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
 * @author m.farhan
 *
 */
public class CouponFragment extends BaseFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_coupons_layout, container, false);
		return view;
	}
}
