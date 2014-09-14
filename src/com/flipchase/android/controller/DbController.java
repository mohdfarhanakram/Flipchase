/**
 * 
 */
package com.flipchase.android.controller;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.flipchase.android.database.FlipchaseDbOperation;
import com.flipchase.android.listener.DbListener;
import com.flipchase.android.model.DbControllerResponse;
import com.flipchase.android.model.Item;

/**
 * @author m.farhan
 *
 */


public class DbController extends AsyncTask<Void, Void, Void>{

	private Context mContext;
	private DbListener listener;
	private int mEvent;
	private Object mObject;
	private DbControllerResponse response;
	private FlipchaseDbOperation mDb;

	public DbController(Context context,Object object,int event,DbListener listener){
		mContext = context;
		mDb = new FlipchaseDbOperation(context);
		mObject = object;
		mEvent = event;
		this.listener = listener;
		response = new DbControllerResponse();
		response.setEvent(event);
	}

	@Override
	protected Void doInBackground(Void... params) {

		try{
			switch (mEvent) {
			case DbEvent.FETCH_LIST:
				response.setResponseObject(mDb.getItemList());
				break;
			case DbEvent.INSERT_LIST_DATA:	
				response.setResponseObject(mDb.insertItemListTable((Item)mObject));
				break;
			case DbEvent.CREATE_LIST_DATA:	
				response.setResponseObject(mDb.createListTable((Item)mObject));
				break;
			case DbEvent.FETCH_SUB_LIST:
				response.setResponseObject(mDb.getItemsBasedOnId((String)mObject));
				break;
			default:
				break;
			}

		}catch(Exception e){
			Log.e("Controller error", e.getMessage());
			response.setResponseObject(null);
		}

		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		listener.onDatabaseOperationDone(response);
	}

}
