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
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.domain.Retailer;
import com.flipchase.android.util.PicassoEx;

public class RetailerAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Activity mContext;
    private List<Retailer> items;

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
    public RetailerAdapter(Activity context) {
        inflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setItems(List<Retailer> items) {
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
        Retailer itemDetail = items.get(position);
        holder.retailerTitle.setText(itemDetail.getName());
        picassoLoad(URLConstants.IMAGE_SERVER_URL + itemDetail.getPhoto_path(), holder.retailerThumbnail);

        // picassoLoad(itemDetail.getImage(), holder.itemPic);
        return row;
    }

    private View getRowView() {
        View row = inflater.inflate(R.layout.retailer_row_list_item, null, false);
        ViewHolder holder = new ViewHolder();
        holder.retailerTitle = (TextView) row.findViewById(R.id.retailerTitle);
        holder.retailerThumbnail = (ImageView) row.findViewById(R.id.retailerThumbnail);
        //holder.rating = (TextView) row.findViewById(R.id.rating);
        //holder.distance = (TextView) row.findViewById(R.id.distance);
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
    	public ImageView retailerThumbnail;
        public TextView retailerTitle;
        //public TextView rating;
        //public TextView distance;
    }

    public void setImageHeightWidth(int imageHeight, int imageWidth) {
        this.mImageHeight = imageHeight;
        this.mImageWidth = imageWidth;
    }

}
