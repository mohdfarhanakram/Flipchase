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
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipchase.android.R;
import com.flipchase.android.listener.LongPressListener;
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
	private LongPressListener mListener;
	private boolean mIsCheckBoxShown;
	private int mSelectedIndex;
	
	public ListAdapter(Context context,LongPressListener listener,ArrayList<Item> itemList,boolean isCheckBoxShown,int selectedIndex){
		mContext = context;
		mItemList = itemList;
		mListener = listener;
		mSelectedIndex = selectedIndex;
		mIsCheckBoxShown = isCheckBoxShown;
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
			viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.list_chk_box);
			
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
				i.putExtra("list", mItemList);
				((BaseActivity)mContext).startActivityForResult(i, 10000);
			}
		});
		
		viewHolder.mainLayout.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Item item = (Item)v.getTag();
				mListener.onLongPressed(item);
				return true;
			}
		});
		
		viewHolder.listNameTxtView.setText(item.getName());
		viewHolder.itemNoTxtView.setText(item.getCount()+"");
		
		if(mIsCheckBoxShown){
			viewHolder.imageView.setVisibility(View.GONE);
			viewHolder.checkBox.setVisibility(View.VISIBLE);
		}else{
			viewHolder.imageView.setVisibility(View.VISIBLE);
			viewHolder.checkBox.setVisibility(View.GONE);
		}
		
		
		if(mSelectedIndex==position){
			viewHolder.checkBox.setChecked(true);
		}else{
			viewHolder.checkBox.setChecked(false);
		}
		
		
		
		//setBitmap(item.getImageInByte(),viewHolder.imageView);
		
		return convertView;
	}
	
	
	public class ViewHolder{
		
		public LinearLayout mainLayout;
		public ImageView imageView;
		public TextView listNameTxtView;
		public TextView itemNoTxtView;
		public CheckBox checkBox;
		
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
