
package com.flipchase.android.view.activity;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.flipchase.android.R;
import com.flipchase.android.view.fragment.ExtensionPinFragment;

public class ImageDisplayActivity extends BaseActivity{

    private static final String BUNDLE_POSITION = "position";

    private int position;

    private List<Page> pages;

    private String selectedImageURL = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extension_activity);
        getSupportActionBar().setTitle("Image display");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayOptions(ActionBar.NAVIGATION_MODE_LIST);
       
        
        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_POSITION)) {
            position = savedInstanceState.getInt(BUNDLE_POSITION);
        }
        pages = Arrays.asList(
               // new Page("Large images", ImageDisplayLargeFragment.class)
        		 new Page("Large images", ExtensionPinFragment.class)
        );
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	selectedImageURL = extras.getString("selectedImageURL");
        }
        updatePage();
    }
  
 
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		mMenu = menu;
		mMenu.clear();
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.display_image_action_bar, menu);
		
		 /*View customNav = LayoutInflater.from(this).inflate(R.layout.custom_action_menu, null);
	     getSupportActionBar().setCustomView(customNav);*/
	        
		return super.onCreateOptionsMenu(menu);
	}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_POSITION, position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.action_image_crop:
			break;
		default:
			break;
		}
        //finish();
        return true;
    }

    public String getSelectedImageName() {
    	return selectedImageURL;
    }
    
    public void next() {
        position++;
        updatePage();
    }

    public void previous() {
        position--;
        updatePage();
    }

    private void updatePage() {
        if (position > pages.size() - 1) {
            return;
        }

        
        getSupportActionBar().setSubtitle(pages.get(position).subtitle);
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, (Fragment)pages.get(position).clazz.newInstance())
                    .commit();
        } catch (Exception e) {
            Log.e("something", "Failed to load fragment", e);
            Toast.makeText(this, "Whoops, couldn't load the fragment!", Toast.LENGTH_SHORT).show();
        }
    }

    private static final class Page {
        private final String subtitle;
        private final Class<?> clazz;
        private Page(String subtitle, Class<?> clazz) {
            this.subtitle = subtitle;
            this.clazz = clazz;
        }
    }

}
