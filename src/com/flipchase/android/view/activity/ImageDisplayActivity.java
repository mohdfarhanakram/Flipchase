
package com.flipchase.android.view.activity;

import java.util.Arrays;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
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

import com.edmodo.cropper.cropwindow.CropOverlayView;
import com.edmodo.cropper.util.ImageViewUtil;
import com.flipchase.android.R;
import com.flipchase.android.view.fragment.ExtensionPinFragment;

import com.flipchase.android.view.widget.FlipchaseCropImageView;

import com.flipchase.android.view.widget.TouchImageView;
import com.edmodo.cropper.CropImageView;

public class ImageDisplayActivity extends BaseActivity{

    private static final String BUNDLE_POSITION = "position";

    private int position;

    private List<Page> pages;

    private String selectedImageURL = null;
    
    private boolean mIsCropWindowVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extension_activity);
        getSupportActionBar().setTitle("Image display");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.violetsky);

        
        ((FlipchaseCropImageView)findViewById(R.id.cropImageView)).setImageBitmap(bitmap);

       
        
        /*if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_POSITION)) {
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
        updatePage();*/
    }
  
 
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		mMenu = menu;
		mMenu.clear();
		MenuInflater inflater = getMenuInflater();
		if(mIsCropWindowVisible)
		   inflater.inflate(R.menu.crop_window_actionbar, menu);
		else
		  inflater.inflate(R.menu.display_image_action_bar, menu);
	   
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
    	case R.id.show_image_crop_window:
    		showCropWindow(true);
    		break;
		case R.id.action_image_crop:
			break;
		case android.R.id.home:
			onBackPressed();
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
    
    
    private void showCropWindow(boolean isCropWindowVisible){
    	mIsCropWindowVisible = isCropWindowVisible;
    	
    	if(isCropWindowVisible){
    		
    		TouchImageView imageview = (TouchImageView)findViewById(R.id.zoomImage);
			final Rect result = new Rect(0, 0,imageview.getWidth(),imageview.getHeight());
			CropOverlayView cropImageView = (CropOverlayView)findViewById(R.id.cropOverlayView);
			cropImageView.setVisibility(View.VISIBLE);
			cropImageView.setBitmapRect(result);
    		
    	}else{
    		findViewById(R.id.cropOverlayView).setVisibility(View.GONE);
    	}
    	
    	supportInvalidateOptionsMenu();
    	
    }
    
    @Override
    public void onBackPressed() {
    	if(mMenu!=null && mMenu.size()==1){
    		showCropWindow(false);
    		supportInvalidateOptionsMenu();
    		return;
    	}
    	finish();
    }
    
}
