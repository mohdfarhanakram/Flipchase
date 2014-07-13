/**
 * 
 */
package com.flipchase.android.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.flipchase.android.view.widget.FlipdchaseSearchView.OnSearchViewCollapsedEventListener;
import com.flipchase.android.view.widget.FlipdchaseSearchView.OnSearchViewExpandedEventListener;

/**
 * @author m.farhan
 *
 */
public class BaseActivity extends ActionBarActivity implements OnSearchViewExpandedEventListener,OnSearchViewCollapsedEventListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onSearchViewExpanded() {
		
	}

	@Override
	public void onSearchViewCollapsed() {
		
	}

}
