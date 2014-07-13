/**
 * 
 */
package com.flipchase.android.view.adapter;

import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.view.fragment.BaseFragment;
import com.flipchase.android.view.fragment.CouponFragment;
import com.flipchase.android.view.fragment.DealsFragment;
import com.flipchase.android.view.fragment.ListFragment;
import com.flipchase.android.view.fragment.StoreFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author m.farhan
 *
 */
public class HomeFragmentAdapter extends FragmentStatePagerAdapter{
	
	private String[] mTabOptionList;

	public HomeFragmentAdapter(FragmentManager fm,String[] tabOptionList) {
		super(fm);
		
		mTabOptionList = tabOptionList;
	}

	@Override
	public Fragment getItem(int index) {
		
		String tabOption = mTabOptionList[index];
		
		BaseFragment fragment=null;
		
		if(tabOption.equalsIgnoreCase(AppConstants.DEALS_FRAGMENT)){
			fragment = new DealsFragment();
		}else if(tabOption.equalsIgnoreCase(AppConstants.STORE_FRAGMENT)){
			fragment = new StoreFragment();
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

}
