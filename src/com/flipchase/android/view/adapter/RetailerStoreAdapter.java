package com.flipchase.android.view.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flipchase.android.R;
import com.flipchase.android.domain.Store;

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
        return null;
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
        holder.textViewItem.setText(itemDetail.getName());
//        picassoLoad(Utils.changeImageUrl(itemDetail.getImage(),mSuffix), holder.itemPic);
        // picassoLoad(itemDetail.getImage(), holder.itemPic);
        return row;
    }

    private View getRowView() {
        View row = inflater.inflate(R.layout.retailer_store_row_list_item, null, false);
        ViewHolder holder = new ViewHolder();
        holder.textViewItem = (TextView) row.findViewById(R.id.textViewItem);
        row.setTag(holder);
        return row;
    }

    public void setContext(Activity context) {
        mContext = context;
    }

    /**
     * View Holder for grid row view.
     */
    public static class ViewHolder {
        public TextView textViewItem;
    }

    public void setImageHeightWidth(int imageHeight, int imageWidth) {
        this.mImageHeight = imageHeight;
        this.mImageWidth = imageWidth;
    }

}