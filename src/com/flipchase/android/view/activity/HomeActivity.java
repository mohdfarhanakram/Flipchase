/**
 * 
 */
package com.flipchase.android.view.activity;

import com.flipchase.android.R;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;

/**
 * @author m.farhan
 *
 */
public class HomeActivity extends BaseActivity implements ActionBar.TabListener{
	
	private ActionBar bar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		createActionTabBar();
	}
	
	
	/**
	 * Create tab bar
	 */
	private void createActionTabBar() {
        bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        String tabs[] = getResources().getStringArray(R.array.home_tabs);
        
        if(tabs!=null && tabs.length>0){
        	for(int i=0; i<tabs.length; i++){
        		android.support.v7.app.ActionBar.Tab tab = bar.newTab();
        		tab.setText(tabs[i]);
        		tab.setTabListener(this);
        		bar.addTab(tab);
        	}
        }
    }


	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

}
