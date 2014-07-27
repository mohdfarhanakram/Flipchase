/**
 * 
 */
package com.flipchase.android.view.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.flipchase.android.R;
import com.flipchase.android.app.Flipchase;
import com.flipchase.android.util.Utils;
import com.flipchase.android.view.widget.FlipdchaseSearchView;
import com.flipchase.android.view.widget.FlipdchaseSearchView.OnSearchViewCollapsedEventListener;
import com.flipchase.android.view.widget.FlipdchaseSearchView.OnSearchViewExpandedEventListener;

/**
 * @author m.farhan
 *
 */
abstract class BaseActivity extends ActionBarActivity implements OnSearchViewCollapsedEventListener, OnSearchViewExpandedEventListener,View.OnFocusChangeListener, SearchView.OnQueryTextListener, SearchView.OnSuggestionListener{
	
	protected Menu mMenu;
	private MenuItem mSearchMenuItem;
	private static final int SEARCH = 1000120;
	private FlipdchaseSearchView mSearchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (isDataExists()) {
                populateViews();
            } else {
            	fetchData();
            }
        } else {
        	fetchData();
        }
    }
    
	public boolean fetchData() {
		boolean returnVal = false;
		if (Utils.isInternetAvailable(this)) {
			requestAndAssignData();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					updateUI();
				}
			});
		}
		return returnVal;
	}
    
	protected void updateUI() {
		
	}
	
	protected void requestAndAssignData() {
		
	}
	
    protected void requestData() {

    }

    protected boolean isDataExists() {
        return false;
    }
    
    protected void populateViews() {

    }
        	
	/**
     * Helper method for creating search view
     *
     * @param searchId           menu item id
     * @param menu               menu object
     * @param lstnr              on query listener object
     * @param suggestionListener
     * @param listener           action expand listener
     * @param showSuggestions
     */
    public void buildSearchView(int searchId, Menu menu, SearchView.OnQueryTextListener lstnr, SearchView.OnSuggestionListener suggestionListener, boolean showSuggestions) {

        mSearchMenuItem = menu.findItem(searchId);
        if (null == mSearchMenuItem)
            mSearchMenuItem = menu.add(0, SEARCH, 0, "").setIcon(R.drawable.ic_action_search);

        MenuItemCompat.setShowAsAction(mSearchMenuItem, MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        mSearchView = new FlipdchaseSearchView((((ActionBarActivity) this)).getSupportActionBar().getThemedContext());
        mSearchView.setOnSearchViewCollapsedEventListener(this);
        mSearchView.setOnSearchViewExpandedEventListener(this);

        mSearchView.setQueryHint(getString(R.string.search_hint_text));

       /* if (this.getClass().getSimpleName().equalsIgnoreCase("HomeActivity")) {
            //TO-Do set search hint test based on activity ex:
        	mSearchView.setQueryHint(getString(R.string.search_hint_text));

        }*/
        mSearchView.setIconified(true);
        mSearchView.setOnQueryTextListener(lstnr);

        AutoCompleteTextView searchText = (AutoCompleteTextView) mSearchView.findViewById(R.id.search_src_text);
        searchText.setGravity(Gravity.BOTTOM);
        searchText.setTextColor(getResources().getColor(R.color.search_txt_color));
        searchText.setHintTextColor(getResources().getColor(R.color.hint_text_color));
        searchText.setCursorVisible(true);
        searchText.setThreshold(0);
//        searchText.setDropDownBackgroundResource(R.color.white);
        if (null != Flipchase.mTypeface) {
        	Flipchase.mTypeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto_Regular.ttf");
        }
        searchText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        searchText.setTypeface(Flipchase.mTypeface);
        MenuItemCompat.setActionView(mSearchMenuItem, mSearchView);

        searchText.setOnFocusChangeListener(this);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);

        View searchplate = (View) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        //searchplate.setBackgroundResource(R.drawable.apptheme_search_view_edit_text_holo_light);

        ImageView searchCloseIcon = (ImageView) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchCloseIcon.setImageResource(R.drawable.clear_searchs);

        ImageView searchIcon = (ImageView) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(R.drawable.abc_ic_search);

        searchText.setImeActionLabel("Search", EditorInfo.IME_ACTION_SEARCH);
       /* if (showSuggestions) {
            SearchBoxApadter searchBoxApadter = new SearchBoxApadter(this, R.layout.textview);
            searchText.setAdapter(searchBoxApadter);
        }**/
        mSearchView.setOnSuggestionListener(suggestionListener);
    }

    /**
     * Search view Callback interface implementation
     */
	@Override
	public void onSearchViewExpanded() {
		 if (null != mMenu) {
	            //getSupportActionBar().setIcon(R.drawable.ic_menu_logo);
	            int size = mMenu.size();
	            for (int i = 0; i < size; i++) {
	                mMenu.getItem(i).setVisible(false);
	            }
	        }
	}

	/**
     * Search view Callback interface implementation
     */
	@Override
	public void onSearchViewCollapsed() {
		if (null != mMenu) {
            int size = mMenu.size();
            for (int i = 0; i < size; i++) {
                mMenu.getItem(i).setVisible(true);
            }
            restoreAdapter();
        }
	}
	
	@Override
	public boolean onSuggestionClick(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSuggestionSelect(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void restoreAdapter() {
    }


	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		
	}

}
