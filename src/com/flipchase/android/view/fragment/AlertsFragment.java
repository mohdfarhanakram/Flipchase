package com.flipchase.android.view.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.flipchase.android.R;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.domain.MobileAlert;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.parcels.MobileAlertChunk;
import com.flipchase.android.view.activity.HomeActivity;
import com.flipchase.android.view.adapter.AlertsAdapter;

public class AlertsFragment extends BaseFragment {

	View mView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_alerts_layout, container, false);
		
		((HomeActivity) getActivity()).updateAlertsData("");
		return mView;
	}
	
	@Override
	public void updateUi(ServiceResponse response) {
		super.updateUi(response);
		switch (response.getEventType()) {
		case FlipchaseApi.GET_MOBILE_ALERTS:
			MobileAlertChunk alertsChunk = (MobileAlertChunk) response.getResponseObject();
			List<MobileAlert> itemList = alertsChunk.getItems();
			if(itemList!=null && itemList.size()>0){
				mView.findViewById(R.id.empty_content_view).setVisibility(View.GONE);
				mView.findViewById(R.id.main_content_view).setVisibility(View.VISIBLE);
				
				ListView listView = (ListView)mView.findViewById(R.id.item_list_view);
				listView.setAdapter(new AlertsAdapter(getActivity(), itemList));
				
		
			}else{
				mView.findViewById(R.id.empty_content_view).setVisibility(View.VISIBLE);
				mView.findViewById(R.id.main_content_view).setVisibility(View.GONE);
			}
			break;
		default:
			break;
		}
	}
}
