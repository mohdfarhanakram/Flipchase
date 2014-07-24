
package com.flipchase.android.view.activity;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.flipchase.android.R;
import com.flipchase.android.view.fragment.ImageDisplayLargeFragment;

public class ImageDisplayActivity extends FragmentActivity {

    private static final String BUNDLE_POSITION = "position";

    private int position;

    private List<Page> pages;

    private String selectedImageName = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extension_activity);
        getActionBar().setTitle("Image display");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_POSITION)) {
            position = savedInstanceState.getInt(BUNDLE_POSITION);
        }
        pages = Arrays.asList(
                new Page("Large images", ImageDisplayLargeFragment.class)
        );
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	selectedImageName = extras.getString("selectedImageName");
        }
        updatePage();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_POSITION, position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public String getSelectedImageName() {
    	return selectedImageName;
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
        getActionBar().setSubtitle(pages.get(position).subtitle);
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
