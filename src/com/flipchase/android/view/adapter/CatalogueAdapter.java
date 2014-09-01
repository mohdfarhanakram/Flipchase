/**
 * 
 */
package com.flipchase.android.view.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.flipchase.android.R;
import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.domain.CatalogueDisplay;
import com.flipchase.android.util.PicassoEx;
import com.flipchase.android.view.activity.FlipHorizontalLayoutActivity;
import com.flipchase.android.view.widget.CustomFontTextView;

/**
 * @author m.farhan
 *
 */
public class CatalogueAdapter extends BaseAdapter{

	private Context mContext;
	private List<CatalogueDisplay> mCataArrayList;
	private LayoutInflater mInflater;

	public CatalogueAdapter(Context context, List<CatalogueDisplay> cataArrayList){
		mContext = context;
		mCataArrayList = cataArrayList;
		mInflater = LayoutInflater.from(context);
	}

	public void setItems(List<CatalogueDisplay> items) {
        this.mCataArrayList = items;
        notifyDataSetChanged();
    }
	
	@Override
	public int getCount() {
		return mCataArrayList==null ? 0 : mCataArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return mCataArrayList.get(position);
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

		final CatalogueDisplay catalogue = mCataArrayList.get(position);
		if(convertView==null){

			row = mInflater.inflate(R.layout.layout_catalog_grid_view, null, false);
			holder = new ViewHolder();

			holder.catalogueImageView = (ImageView)row.findViewById(R.id.catalogImg);
			holder.catalogueName = (CustomFontTextView)row.findViewById(R.id.catalog_item_name);
			holder.catalogueExpiry = (CustomFontTextView)row.findViewById(R.id.cata_expiry_time_txtv);
			holder.distance = (CustomFontTextView)row.findViewById(R.id.cata_store_distance);
			row.setTag(holder);
			
			ImageView iv = holder.catalogueImageView;
		    iv.setOnClickListener(new View.OnClickListener() {
		        public void onClick(View v) {
		        	Intent i = new Intent(mContext, FlipHorizontalLayoutActivity.class);  
		        	i.putExtra("catalogue", catalogue.getCatalogue());
		        	i.putExtra("store", catalogue.getStore());
		        	mContext.startActivity(i);
		        }
		    });

		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		holder.catalogueName.setText(catalogue.getCatalogue().getName());
		holder.catalogueExpiry.setText(catalogue.getCatalogue().getExpiryDate());
		if(catalogue.getStore().getDistance() != null) {
        	String distanceAsString = String.format(AppConstants.STORE_DISTANCE_PRECISION_FOR_KM, catalogue.getStore().getDistance());
        	holder.distance.setText(distanceAsString + AppConstants.DISTANCE_IN_KM);
        } else {
        	holder.distance.setText("Distance not found...");
        }
		
		picassoLoad(URLConstants.IMAGE_SERVER_URL + catalogue.getCatalogue().getPhoto_thumb_path(), holder.catalogueImageView);

		return row;
	}


	private void picassoLoad(String url, ImageView imageView) {
		PicassoEx.getPicasso(mContext).load(url).config(Bitmap.Config.RGB_565).placeholder(R.drawable.flip).fit().into(imageView);
		//PicassoEx.getPicasso(mContext).load(url).get()
	}
	public class ViewHolder{
		public ImageView catalogueImageView;
		public CustomFontTextView catalogueName;
		public CustomFontTextView catalogueExpiry;
		public CustomFontTextView distance;
	}

}
