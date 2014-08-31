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

/**
 * @author 201101012
 *
 */


public class DbController extends AsyncTask<Void, Void, Void>{

	private Context mContext;
	private DbListener listener;
	private int mEvent;
    private ArrayList<Object> mDataList;
	private DbControllerResponse response;
    private FlipchaseDbOperation mDb;
	 
	public DbController(Context context,ArrayList<Object> dataList,int event){
		mContext = context;
		mDb = new FlipchaseDbOperation(context);
		mDataList = dataList;
		mEvent = event;
		this.listener = listener;
		response = new DbControllerResponse();
		response.setEvent(event);
	}

	@Override
	protected Void doInBackground(Void... params) {

		try{
			switch (mEvent) {
			case DbEvent.CREATE_LIST:
				response.setResponseObject(mDb.getItemList());
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
