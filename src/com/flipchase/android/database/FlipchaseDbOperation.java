/**
 * 
 */
package com.flipchase.android.database;

import java.io.IOException;
import java.util.ArrayList;

import com.flipchase.android.model.Item;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
		ArrayList<Item> savedItemList = new ArrayList<Item>();
		
		//String query = "SELECT id,list_name,COUNT(*) FROM Item_List_Table  GROUP BY id";
		
		String query = "SELECT * FROM Item_List_Master";
		
		try{
			
			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();
			
			Log.e("Sql query",query);
			Cursor cursor = database.rawQuery(query, null);
			
			if (cursor!=null && cursor.getCount() != 0) {
				Log.e("Sql query cursor size",cursor.getCount()+"");

				if (cursor.moveToFirst()) {
					do {
						Item item = new Item();
						item.setId(cursor.getString(0));
						item.setName(cursor.getString(1));
						item.setCount(cursor.getInt(2));
						savedItemList.add(item);
                       
					} while (cursor.moveToNext());
				}
				cursor.close();
			}
			
			/*for(int i=0; i<savedItemList.size(); i++){
				Item item = savedItemList.get(i);
				item.setSubItemList(getItemsBasedOnId(item.getId(),database));
			}*/
			
			dbHelper.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return savedItemList;	
	}
	
	
	public ArrayList<Item> getItemsBasedOnId(String id){
		ArrayList<Item> subItemList = new ArrayList<Item>();
		try{
			
			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();
			
			String subQuery = "SELECT * FROM Item_List_Table  WHERE id="+id;

			Log.e("Sql sub query",subQuery);
			Cursor cursor = database.rawQuery(subQuery, null);
			
			if (cursor!=null && cursor.getCount() != 0) {
				Log.e("Sql sub query cursor size",cursor.getCount()+"");

				if (cursor.moveToFirst()) {
					do {
						Item item = new Item();
						item.setId(cursor.getString(0));
						item.setName(cursor.getString(1));
						item.setTitle(cursor.getString(2));
						item.setSubTitle(cursor.getString(3));
						item.setQuantity(cursor.getString(4));
						item.setReminder(cursor.getInt(5));
						item.setExpiry(cursor.getString(6));
						item.setImageInByte(cursor.getBlob(7));
						subItemList.add(item);
	                   
					} while (cursor.moveToNext());
				}
				cursor.close();
			}
			
			dbHelper.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return subItemList;
	}
	
	
	/*public boolean saveListItemDataInDb(Item item){

		try{
			
			
			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();
			
			ContentValues contentValue = new ContentValues();   
			contentValue.put("id",  item.getId());
			contentValue.put("list_name", item.getName()); 
			contentValue.put("item_title", item.getTitle());
			contentValue.put("item", item.getSubTitle());
			contentValue.put("quantity", item.getQuantity());
			contentValue.put("reminder", item.getReminder());
			contentValue.put("expiry_date", item.getExpiry());
			contentValue.put("image", item.getImageInByte());
			
			database.insert("Item_List_Table", null, contentValue);
			
			dbHelper.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}*/
	
	
	public boolean insertItemListTable(Item item){

		try{
			
			
			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();
			
			ContentValues contentValue = new ContentValues();  
			contentValue.put("uid",  System.currentTimeMillis());
			contentValue.put("item_title", item.getTitle());
			contentValue.put("item", item.getSubTitle());
			contentValue.put("quantity", item.getQuantity());
			contentValue.put("reminder", item.getReminder());
			contentValue.put("expiry_date", item.getExpiry());
			contentValue.put("image", item.getImageInByte());
			contentValue.put("id",  item.getId());
			
			database.insert("Item_List_Table", null, contentValue);
			
			dbHelper.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	public boolean insertItemMasterTable(String id){

		try{
			
			
			dbHelper.openDataBase();
			SQLiteDatabase database = dbHelper.getWritableDatabase();
			
			ContentValues contentValue = new ContentValues();   
			contentValue.put("id",  id);
			contentValue.put("list_name", id); 
			contentValue.put("list_count", 0);
			
			database.insert("Item_List_Master", null, contentValue);
			
			dbHelper.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	

   
}


