/**
 * 
 */
package com.flipchase.android.database;

import java.io.IOException;
import java.util.ArrayList;

import com.flipchase.android.model.Item;

import android.content.Context;
import android.util.Log;

/**
 * @author m.farhan
 *
 */
public class FlipchaseDbOperation {
	
	private Context mContext;
	private DatabaseHelper dbHelper;
	
	public FlipchaseDbOperation(Context ctx) {
		Log.i("inside", "GetSetDatabase()");
		mContext = ctx;
		dbHelper = new DatabaseHelper(ctx);

		try {
			dbHelper.createDataBase();
			Log.i("DATABASE CRETAED", "CRETAED");
		} catch (IOException i) {
			throw new Error("Unable to create Database");
		}
	}
	
	public ArrayList<Item> getItemList(){
		ArrayList<Item> list = new ArrayList<Item>();
		
		return list;	
	}
	
	

   
}


