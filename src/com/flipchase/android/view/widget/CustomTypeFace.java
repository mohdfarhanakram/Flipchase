/**
 * 
 */
package com.flipchase.android.view.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

/**
 * @author m.farhan
 *
 */
public class CustomTypeFace {

    public Typeface bold;
    public Typeface normal;

    public static CustomTypeFace customTypeFace;

    public CustomTypeFace(Context context) {

        String name = "Roboto-";
        Log.e("INITIALISING", "CONTEXT: " + context + " " + context.getAssets() + "name " + name);
        bold = Typeface.createFromAsset(context.getAssets(), "fonts/" + name + "Bold.ttf");
        normal = Typeface.createFromAsset(context.getAssets(), "fonts/" + name + "Regular.ttf");
    }
}
