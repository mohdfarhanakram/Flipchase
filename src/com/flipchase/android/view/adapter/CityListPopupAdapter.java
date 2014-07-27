package com.flipchase.android.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.flipchase.android.R;
import com.flipchase.android.domain.City;

public class CityListPopupAdapter extends ArrayAdapterItem {

	public CityListPopupAdapter(Context mContext, int layoutResourceId,
			List data) {
		super(mContext, layoutResourceId, data);
	}

	@Override
	protected void updateView(View convertView, Object objectItem) {
		super.updateView(convertView, objectItem);
		City city = (City)objectItem;
        TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
        textViewItem.setText(city.getName());
	}
}
