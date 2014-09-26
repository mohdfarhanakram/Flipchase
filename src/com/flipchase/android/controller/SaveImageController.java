/**
 * 
 */
package com.flipchase.android.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.flipchase.android.util.StorageUtils;

/**
 * @author FARHAN
 *
 */

public class SaveImageController extends AsyncTask<byte[], Void, Void> {
	
	private String mId;;
	private Context mContext;
	
	public SaveImageController(Context context,String id){
		mId = id;
		mContext = context;
	}

	@Override
	protected Void doInBackground(byte[]... data) {
		FileOutputStream outStream = null;

		// Write to SD Card
		try {
           
			File dir = StorageUtils.getOwnCacheDirectory(mContext,"flipchase");
			
			String fileName = String.format("%d.jpg", mId);
			File outFile = new File(dir, fileName);
			
			outStream = new FileOutputStream(outFile);
			outStream.write(data[0]);
			outStream.flush();
			outStream.close();
			
			Log.d("SaveImageController", "onPictureTaken - wrote bytes: " + data.length + " to " + outFile.getAbsolutePath());
			
			refreshGallery(outFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}
	
	
	private void refreshGallery(File file) {
		Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    mediaScanIntent.setData(Uri.fromFile(file));;
	}
	
}
