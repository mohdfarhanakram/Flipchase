/**
 * 
 */
package com.flipchase.android.view.adapter;

import java.util.ArrayList;

import com.flipchase.android.R;
import com.flipchase.android.domain.Catalogue;
import com.flipchase.android.util.PicassoEx;
import com.flipchase.android.view.activity.FlipHorizontalLayoutActivity;
import com.flipchase.android.view.widget.CustomFontTextView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @author m.farhan
 *
 */
public class CatalogueAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<Catalogue> mCataArrayList;
	private LayoutInflater mInflater;

	public CatalogueAdapter(Context context, ArrayList<Catalogue> cataArrayList){
		mContext = context;
		mCataArrayList = cataArrayList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mCataArrayList==null ? 0 : mCataArrayList.size();
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

		View row = convertView;
		ViewHolder holder;

		final Catalogue catalogue = mCataArrayList.get(position);
		if(convertView==null){

			row = mInflater.inflate(R.layout.layout_catalog_grid_view, null, false);
			holder = new ViewHolder();

			holder.catalogueImageView = (ImageView)row.findViewById(R.id.catalogImg);
			holder.catalogueName = (CustomFontTextView)row.findViewById(R.id.catalog_item_name);
			holder.catalogueExpiry = (CustomFontTextView)row.findViewById(R.id.cata_expiry_time_txtv);
			row.setTag(holder);
			
			//DK:
			ImageView iv = holder.catalogueImageView;
		    iv.setOnClickListener(new View.OnClickListener() {
		        public void onClick(View v) {
		        	Intent i = new Intent(mContext, FlipHorizontalLayoutActivity.class);      
		        	mContext.startActivity(i);
		        }
		    });

		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		holder.catalogueName.setText(catalogue.getDisplayName());
		holder.catalogueExpiry.setText(catalogue.getExpiryDate());
		
		picassoLoad(catalogue.getPhoto_thumb_path(), holder.catalogueImageView);

		return row;
	}


	private void picassoLoad(String url, ImageView imageView) {

		PicassoEx.getPicasso(mContext).load(url).config(Bitmap.Config.RGB_565).placeholder(R.drawable.flip).fit().into(imageView);
	}




	public class ViewHolder{
		public ImageView catalogueImageView;
		public CustomFontTextView catalogueName;
		public CustomFontTextView catalogueExpiry;
	}

}
