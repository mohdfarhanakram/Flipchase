package com.flipchase.android.view.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flipchase.android.R;
import com.flipchase.android.domain.Store;
import com.flipchase.android.util.PicassoEx;

public class RetailerStoreAdapter extends BaseAdapter {

	private LayoutInflater inflater;
    private Activity mContext;
    private List<Store> items;

    private int mImageHeight;
    private int mImageWidth;

    private String mSuffix;
    private static int VIEW_TYPE_FIRST_ROW =0;
    private static int VIEW_TYPE_LAST_ROW =2;
    private static int VIEW_TYPE_NORMAL_ROW =1;
    /**
     * Construct a new {@link ItemsGridAdapter}.
     *
     * @param context
     */
    public RetailerStoreAdapter(Activity context) {
        inflater = LayoutInflater.from(context);
        mContext = context;
    }

    public RetailerStoreAdapter(Activity context, List<Store> list) {
    	inflater = LayoutInflater.from(context);
        mContext = context;
        this.items = list;
	}
    
    public void setItems(List<Store> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
    	return 1;
        //int colCount = mContext.getResources().getInteger(1);
        //return position < colCount ? VIEW_TYPE_FIRST_ROW : (getRowNo(position)==getRowNo(items.size()-1)? VIEW_TYPE_LAST_ROW : VIEW_TYPE_NORMAL_ROW);
    }

    /**
     * gets the obejct of the item
     *
     * @param position : position of the item in the list
     * @return
     */
    @Override
    public Object getItem(int position) {
    	return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int getRowNo(int position) {
        return position;
    }

    /**
     * displays the view for the data at the specified position in the data set
     *
     * @param position    : index of item whose view we want
     * @param convertView : the view to be used
     * @param parent      : the parent to which this view will be attached to
     * @return view of the item
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        ViewHolder holder;

        if (convertView == null) {
            //    Log.d("getView","inflating....position"+position);
            row = getRowView();
            holder = (ViewHolder) row.getTag();
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Store itemDetail = items.get(position);
        holder.storeTitle.setText(itemDetail.getName());
        holder.storeAddress.setText("Delhi, 242 Rajiv Chownk Dummy");
        holder.storeDistance.setText("9.5 km Dummy");
        //picassoLoad();
        return row;
    }

    private View getRowView() {
        View row = inflater.inflate(R.layout.retailer_store_row_list_item, null, false);
        ViewHolder holder = new ViewHolder();
        holder.storeTitle = (TextView) row.findViewById(R.id.storeTitle);
        holder.storeThumbnail = (ImageView) row.findViewById(R.id.storeThumbnail);
        holder.storeAddress = (TextView) row.findViewById(R.id.storeAddress);
        holder.storeDistance = (TextView) row.findViewById(R.id.storeDistance);
        row.setTag(holder);
        return row;
    }

    public void setContext(Activity context) {
        mContext = context;
    }

    private void picassoLoad(String url, ImageView imageView) {
		PicassoEx.getPicasso(mContext).load(url).config(Bitmap.Config.RGB_565).placeholder(R.drawable.flip).fit().into(imageView);
	}
    
    /**
     * View Holder for grid row view.
     */
    public static class ViewHolder {
    	public ImageView storeThumbnail;
        public TextView storeTitle;
        public TextView storeAddress;
        public TextView storeDistance;
    }

    public void setImageHeightWidth(int imageHeight, int imageWidth) {
        this.mImageHeight = imageHeight;
        this.mImageWidth = imageWidth;
    }

}
