/**
 * 
 */
package com.flipchase.android.app;

import android.app.Application;
import android.graphics.Typeface;

/**
 * @author m.farhan
 *
 */
public class Flipchase extends Application{

	public static Typeface mTypeface = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mTypeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto_Regular.ttf");
	}
}
