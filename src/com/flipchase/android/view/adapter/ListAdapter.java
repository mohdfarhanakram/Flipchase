/**
 * 
 */
package com.flipchase.android.view.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipchase.android.R;
import com.flipchase.android.model.Item;
import com.flipchase.android.view.activity.BaseActivity;
import com.flipchase.android.view.activity.SubListActivity;

/**
 * @author m.farhan
 *
 */
public class ListAdapter extends BaseAdapter{
	
	private Context mContext;
	private ArrayList<Item> mItemList;
	private LayoutInflater mInflater;
	
	public ListAdapter(Context context,ArrayList<Item> itemList){
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
		
		Item item = mItemList.get(position);
		
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
		
		viewHolder.mainLayout.setTag(item);
		viewHolder.mainLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext,SubListActivity.class);
				Item item = (Item)v.getTag();
				i.putExtra("catalogId", item.getId());
				i.putExtra("catalogName",item.getName());
				mContext.startActivity(i);
			}
		});
		
		viewHolder.listNameTxtView.setText(item.getName());
		viewHolder.itemNoTxtView.setText(item.getCount()+"");
		
		
		
		//setBitmap(item.getImageInByte(),viewHolder.imageView);
		
		return convertView;
	}
	
	
	public class ViewHolder{
		
		public LinearLayout mainLayout;
		public ImageView imageView;
		public TextView listNameTxtView;
		public TextView itemNoTxtView;
		
	}
	
	public void setBitmap(byte[] bitmapdata,ImageView imagView){
		if(bitmapdata==null)
			return;
		try{
			Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata .length);
			if(bitmap!=null)
				imagView.setImageBitmap(bitmap);
		}catch(Exception e){
			
		}
		
	}

}
