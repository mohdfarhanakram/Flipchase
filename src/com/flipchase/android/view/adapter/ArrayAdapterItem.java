package com.flipchase.android.view.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

// here's our beautiful adapter
public class ArrayAdapterItem extends ArrayAdapter {

    Context mContext;
    int layoutResourceId;
    List data = null;

    public ArrayAdapterItem(Context mContext, int layoutResourceId, List data) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
	        // inflate the layout
	        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
	        convertView = inflater.inflate(layoutResourceId, parent, false);
        }
        
        Object objectItem = data.get(position);
        updateView(convertView, objectItem);
        return convertView;
    }
	
    	
    protected void updateView(View convertView, Object objectItem) {
    }
}
