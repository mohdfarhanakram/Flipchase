/**
 * 
 */
package com.flipchase.android.view.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flipchase.android.R;
import com.flipchase.android.listener.EditButtonClickListener;
import com.flipchase.android.model.Item;

/**
 * @author FARHAN
 *
 */

public class ActionBarListAdapter extends BaseAdapter {
    private ArrayList<Item> data;
    private Context mContext;
    private LayoutInflater mInflater;
    private String mHeaderText;
    private String mSubTitle;
    public ActionBarListAdapter(Context ctx,ArrayList<Item> list,String subTitle)
    {
        data=list;
        mContext=ctx;
        mInflater=((Activity)ctx).getLayoutInflater();
        mSubTitle = subTitle;
    }

    @Override
    public View getDropDownView(int i, View convertView, ViewGroup viewGroup) {
        TextView titleView=null;
        //TextView countView=null;
        if(convertView==null)
        {
            convertView=mInflater.inflate( R.layout.list_drop_down_layout,viewGroup,false);

        }
        titleView=(TextView)convertView.findViewById(R.id.title_text_view);

        convertView.setTag(i);
        Item itemData=data.get(i);
        titleView.setText(itemData.getName());
        return convertView;
    }
    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public Object getItem(int i) {
        return data.get(i);
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        TextView titleView=null;
        //TextView subTitleView=null;
        if(convertView==null)
        {
            convertView=mInflater.inflate( R.layout.list_drop_down_title_view,viewGroup,false);
        }

        titleView=(TextView)convertView.findViewById(R.id.title_text_view);
        titleView.setText(mSubTitle);
        return convertView;
    }
    
    public void setSubTitle(String subTitle){
    	 mSubTitle = subTitle;
    	 notifyDataSetChanged();
    }

}

