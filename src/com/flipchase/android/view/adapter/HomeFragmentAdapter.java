/**
 * 
 */
package com.flipchase.android.view.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.flipchase.android.R;
import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.domain.Retailer;
import com.flipchase.android.domain.Store;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.view.fragment.BaseFragment;
import com.flipchase.android.view.fragment.CouponFragment;
import com.flipchase.android.view.fragment.DealsFragment;
import com.flipchase.android.view.fragment.ListFragment;
import com.flipchase.android.view.fragment.StoreFragment;

/**
 * @author m.farhan
 *
 */
public class HomeFragmentAdapter extends FragmentStatePagerAdapter{
	
	private String[] mTabOptionList;
	private FragmentManager fragmentManager;
	private StoreFragment storeFragment;
	
	public HomeFragmentAdapter(FragmentManager fm,String[] tabOptionList) {
		super(fm);
		this.fragmentManager = fm;
		mTabOptionList = tabOptionList;
	}

	@Override
	public Fragment getItem(int index) {
		
		String tabOption = mTabOptionList[index];
		
		BaseFragment fragment = null;
		
		if(tabOption.equalsIgnoreCase(AppConstants.CATALOGUE_FRAGMENT)){
			fragment = new DealsFragment();
		}else if(tabOption.equalsIgnoreCase(AppConstants.STORE_FRAGMENT)){
			storeFragment = new StoreFragment();
			fragment = storeFragment;
		}else if(tabOption.equalsIgnoreCase(AppConstants.LIST_FRAGMENT)){
			fragment = new ListFragment();
		}else if(tabOption.equalsIgnoreCase(AppConstants.COUPANS_FRAGMENT)){
			fragment = new CouponFragment();
		}else{
			fragment = new BaseFragment();
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return mTabOptionList==null?0:mTabOptionList.length;
	}

	public void updateFragmentUI(ServiceResponse response) {
		switch (response.getEventType()) {
		case FlipchaseApi.GET_ALL_RETAILERS:
			List<Retailer> retailers = (List<Retailer>)response.getResponseObject();
			storeFragment.updateUi(response);
			break;
		case FlipchaseApi.GET_STORES_FOR_RETAILER:
			List<Store> stores = (List<Store>)response.getResponseObject();
			break;
		default:
			break;
		}
	}
	
}
