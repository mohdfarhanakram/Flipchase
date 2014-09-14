package com.flipchase.android.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipchase.android.R;
import com.flipchase.android.domain.MobileAlert;

public class AlertsAdapter extends BaseAdapter {

	private Context mContext;
	private List<MobileAlert> mItemList;
	private LayoutInflater mInflater;
	
	public AlertsAdapter(Context context, List<MobileAlert> itemList){
		mContext = context;
		mItemList = itemList;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mItemList==null?0:mItemList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		MobileAlert item = mItemList.get(position);
		
		ViewHolder viewHolder;
		if(convertView==null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.row_list_layout, parent, false);
			
			viewHolder.imageView = (ImageView)convertView.findViewById(R.id.list_image_view);
			viewHolder.listNameTxtView = (TextView)convertView.findViewById(R.id.list_name_txtva);
			viewHolder.itemNoTxtView = (TextView)convertView.findViewById(R.id.list_no_txtva);
			viewHolder.mainLayout = (LinearLayout)convertView.findViewById(R.id.main_layout);
			
			convertView.setTag(viewHolder);
			
			
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.listNameTxtView.setText(item.getName());
		viewHolder.itemNoTxtView.setText("1");
		return convertView;
	}
	
	
	public class ViewHolder{
		
		public LinearLayout mainLayout;
		public ImageView imageView;
		public TextView listNameTxtView;
		public TextView itemNoTxtView;
		
	}
	
}

