/**
 * 
 */
package com.flipchase.android.view.activity;

import com.flipchase.android.R;
import com.flipchase.android.view.adapter.HomeFragmentAdapter;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;

/**
 * @author m.farhan
 *
 */
public class HomeActivity extends BaseActivity implements ActionBar.TabListener{
	
	private ActionBar mBar;
	private ViewPager mHomeViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_layout);
		mHomeViewPager = (ViewPager)findViewById(R.id.home_view_pager);
		mBar = getSupportActionBar();
		createActionTabBar();
		createScreenSliderView();
	}
	
	
	/**
	 * Design ScreenSlider view 
	 */
	private void createScreenSliderView(){
		mHomeViewPager.setOnPageChangeListener(
	                new ViewPager.SimpleOnPageChangeListener() {
	                    @Override
	                    public void onPageSelected(int position) {
	                        // When swiping between pages, select the
	                        mBar.setSelectedNavigationItem(position);

	                    }
	                });
		mHomeViewPager.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.home_tabs)));
	}
	
	
	/**
	 * Create tab bar
	 */
	private void createActionTabBar() {
        mBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        String tabs[] = getResources().getStringArray(R.array.home_tabs);
        
        if(tabs!=null && tabs.length>0){
        	for(int i=0; i<tabs.length; i++){
        		android.support.v7.app.ActionBar.Tab tab = mBar.newTab();
        		tab.setText(tabs[i]);
        		tab.setTabListener(this);
        		mBar.addTab(tab);
        	}
        }
    }


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

	     mHomeViewPager.setCurrentItem(tab.getPosition());
		
	}


	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
