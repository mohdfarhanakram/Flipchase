package com.flipchase.android.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.flipchase.android.R;
import com.flipchase.android.domain.Location;

public class LocationListPopupAdapter extends ArrayAdapterItem {

	public LocationListPopupAdapter(Context mContext, int layoutResourceId,
			List data) {
		super(mContext, layoutResourceId, data);
	}

	@Override
	protected void updateView(View convertView, Object objectItem) {
		super.updateView(convertView, objectItem);
		Location location = (Location)objectItem;
        TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
        textViewItem.setText(location.getName());
	}
}
