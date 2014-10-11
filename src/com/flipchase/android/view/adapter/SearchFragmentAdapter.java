/**
 * 
 */
package com.flipchase.android.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.view.fragment.BaseFragment;
import com.flipchase.android.view.fragment.DealsFragment;
import com.flipchase.android.view.fragment.SearchCatalogFragment;
import com.flipchase.android.view.fragment.SearchStoreFragment;
import com.flipchase.android.view.fragment.StoreFragment;

/**
 * @author FARHAN
 *
 */


public class SearchFragmentAdapter extends FragmentStatePagerAdapter{
	
	private String[] mTabOptionList;
	private FragmentManager fragmentManager;
	private SearchCatalogFragment searchCatalogFragment;
	private SearchStoreFragment searchStoreFragment;
	
	
	public SearchFragmentAdapter(FragmentManager fm,String[] tabOptionList) {
		super(fm);
		this.fragmentManager = fm;
		mTabOptionList = tabOptionList;
	}

	//TODO: Every time page loads it calls parser .. need to remove this
	@Override
	public Fragment getItem(int index) {
		
		String tabOption = mTabOptionList[index];
		
		BaseFragment fragment = null;
		
		if(tabOption.equalsIgnoreCase(AppConstants.CATALOGUE_FRAGMENT)){
			searchCatalogFragment = new SearchCatalogFragment();
			fragment = searchCatalogFragment;
		}else if(tabOption.equalsIgnoreCase(AppConstants.STORE_FRAGMENT)){
			searchStoreFragment = new SearchStoreFragment();
			fragment = searchStoreFragment;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return mTabOptionList==null?0:mTabOptionList.length;
	}

	public void updateFragmentUI(ServiceResponse response) {
		
		searchCatalogFragment.updateUi(response);
		
	}
	
	 @Override  
     public CharSequence getPageTitle(int position) {  
		 return mTabOptionList[position];

     }  
   
	
}

