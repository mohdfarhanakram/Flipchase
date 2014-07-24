
package com.flipchase.android.view.activity;

import android.app.Activity;
import android.os.Bundle;

import com.flipchase.android.extlibpro.FlipViewController;
import com.flipchase.android.view.adapter.CataloguePageAdapter;

public class FlipHorizontalLayoutActivity extends Activity {

	  private FlipViewController flipView;

	  /**
	   * Called when the activity is first created.
	   */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    //setTitle(R.string.activity_title);
	    flipView = new FlipViewController(this, FlipViewController.HORIZONTAL);
	    flipView.setAdapter(new CataloguePageAdapter(this));
	    setContentView(flipView);
	  }

	  @Override
	  protected void onResume() {
	    super.onResume();
	    flipView.onResume();
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    flipView.onPause();
	  }
	}
