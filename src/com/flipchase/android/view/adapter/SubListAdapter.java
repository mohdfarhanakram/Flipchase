/**
 * 
 */
package com.flipchase.android.view.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipchase.android.R;
import com.flipchase.android.model.Item;
import com.flipchase.android.util.StringUtils;
import com.flipchase.android.view.activity.BaseActivity;
import com.flipchase.android.view.activity.ListDetailActivity;
import com.flipchase.android.view.activity.SubListActivity;

/**
 * @author FARHAN
 *
 */

public class SubListAdapter extends BaseAdapter{
	
	private Context mContext;
	private ArrayList<Item> mItemList;
	private LayoutInflater mInflater;
	private String name;
	
	public SubListAdapter(Context context,ArrayList<Item> itemList,String name){
		mContext = context;
		mItemList = itemList;
		mInflater = LayoutInflater.from(mContext);
		this.name = name;
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
			convertView = mInflater.inflate(R.layout.row_sub_list_layout, parent, false);

			viewHolder.imageView = (ImageView)convertView.findViewById(R.id.sub_list_image_view);
			viewHolder.listNameTxtView = (TextView)convertView.findViewById(R.id.list_name_txtva);
			viewHolder.itemTitleTxtView = (TextView)convertView.findViewById(R.id.list_title_name_txtv);
			viewHolder.itemSubTitleTxtView = (TextView)convertView.findViewById(R.id.list_sub_title_txtv);
			
			viewHolder.mainLayout = (LinearLayout)convertView.findViewById(R.id.main_layout);
			
			convertView.setTag(viewHolder);
			
			
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		
		viewHolder.mainLayout.setTag(item);
		viewHolder.mainLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext,ListDetailActivity.class);
				Item item = (Item)v.getTag();
				item.setName(name);
				i.putExtra("item", item);
				
				mContext.startActivity(i);
			}
		});
		
		viewHolder.listNameTxtView.setText(name);
		if(!StringUtils.isNullOrEmpty(item.getTitle())){
			viewHolder.itemTitleTxtView.setText(item.getTitle());
			viewHolder.itemTitleTxtView.setVisibility(View.VISIBLE);
		}else{
			viewHolder.itemTitleTxtView.setVisibility(View.GONE);
		}
		
		if(!StringUtils.isNullOrEmpty(item.getSubTitle())){
			viewHolder.itemSubTitleTxtView.setText(item.getSubTitle());
			viewHolder.itemSubTitleTxtView.setVisibility(View.VISIBLE);
		}else{
			viewHolder.itemSubTitleTxtView.setVisibility(View.GONE);
		}
		
		Bitmap bitmap = ((BaseActivity)mContext).getBitmapFromMemCache(item.getUid());
		if(bitmap!=null)
			viewHolder.imageView.setImageBitmap(bitmap);
		
		//setBitmap(item.getImageInByte(),viewHolder.imageView);
		
		return convertView;
	}
	
	
	public class ViewHolder{
		
		public LinearLayout mainLayout;
		public ImageView imageView;
		public TextView listNameTxtView;
		public TextView itemTitleTxtView;
		public TextView itemSubTitleTxtView;
		
	}
	
	/*public void setBitmap(byte[] bitmapdata,ImageView imagView){
		if(bitmapdata==null)
			return;
		try{
			Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata .length);
			if(bitmap!=null)
				imagView.setImageBitmap(bitmap);
		}catch(Exception e){
			
		}
		
	}
*/
}

