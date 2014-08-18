package com.flipchase.android.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Toast;

public class Utils {

	public static boolean isInternetAvailable(Context context) {
        boolean isInternetAvailable = false;

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();

            if (networkInfo != null && (networkInfo.isConnected())) {
                isInternetAvailable = true;
            }
        } catch (Exception exception) {
            // Do Nothing
        }

        return isInternetAvailable;
    }
	
	/**
     * Check particular node is array.
     *
     * @param jsonObject
     * @return true if particular node is Array.
     */
    public static boolean isJsonArray(JSONObject jsonObject, String key) {

        if (!jsonObject.isNull(key)) {

            try {
                jsonObject.getJSONArray(key);
                return true;
            } catch (JSONException e) {
                return false;
            }

        }
        return false;
    }

    /**
     * Check particular node is object.
     *
     * @param jsonObject
     * @return true if particular node is Object.
     */
    public static boolean isJsonObject(JSONObject jsonObject, String key) {

        if (!jsonObject.isNull(key)) {

            try {
                jsonObject.getJSONObject(key);
                return true;
            } catch (JSONException e) {
                return false;
            }

        }
        return false;
    }

    /**
     * Check particular node is having particular key.
     *
     * @param jsonObject
     * @return true if particular node is having key.
     */
    public static boolean isJsonKeyAvailable(JSONObject jsonObject, String key) {

        return jsonObject.has(key);

    }
    
    public static void putStringinPrefs(Context mContext, String mKey, String mVal) {
        SharedPreferences.Editor prefsEditorr = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        try {
            prefsEditorr.putString(mKey, mVal.toString());
            prefsEditorr.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /*
	 *  Create image and save in file system.
	 */
	public static File createImage(Context context,int height, int width, View view, String fileName) {
	    Bitmap bitmapCategory = getBitmapFromView(view, height, width);
	    return createFile(context,bitmapCategory, fileName);
	}

	/*
	 *  save bitmap image in phone memory
	 */
	public static File createFile(Context context,Bitmap bitmap, String fileName) {

	    File externalStorage = Environment.getExternalStorageDirectory();
	    String sdcardPath = externalStorage.getAbsolutePath();
	    File reportImageFile = new File(sdcardPath + "/YourFolderName" + fileName + ".jpg");
	    try {
	        if (reportImageFile.isFile()) {
	            reportImageFile.delete();
	        }
	        if (reportImageFile.createNewFile()) {
	            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
	            FileOutputStream fo = new FileOutputStream(reportImageFile);
	            fo.write(bytes.toByteArray());
	            bytes.close();
	            fo.close();

	            return reportImageFile;
	        }
	    } catch (Exception e) {
	        Toast.makeText(context, "Unable to create Image.Try again", Toast.LENGTH_SHORT).show();
	    }
	    return null;
	}

	/*
	 *  Take view screen shots
	 */
	public static Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

	    Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(returnedBitmap);
	    Drawable bgDrawable = view.getBackground();
	    if (bgDrawable != null)
	        bgDrawable.draw(canvas);
	    else
	        canvas.drawColor(Color.WHITE);

	    view.measure(MeasureSpec.makeMeasureSpec(totalWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY));
	    view.layout(0, 0, totalWidth, totalHeight);
	    view.draw(canvas);
	    return returnedBitmap;
	}
	
    
}
