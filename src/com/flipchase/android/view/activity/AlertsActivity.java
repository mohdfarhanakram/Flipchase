package com.flipchase.android.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.flipchase.android.R;
import com.flipchase.android.domain.MobileAlert;
import com.flipchase.android.view.adapter.AlertsNotificationsAdapter;

public class AlertsActivity extends BaseActivity implements
		View.OnClickListener {

	private ListView listview;
	private AlertsNotificationsAdapter alertListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retailer_stores);

		Bundle bundle = getIntent().getExtras();
		String alerts = bundle.getString("alerts");
		if (alerts != null) {
			System.out.println(alerts);
		}
		alertListAdapter = new AlertsNotificationsAdapter(this);
		String[] alertsArray = alerts.split("\\s*,\\s*");
		List<MobileAlert> alertItems = null;
		if (alertsArray != null) {
			alertItems = new ArrayList<MobileAlert>();
			for (String name : alertsArray) {
				MobileAlert mAlert = new MobileAlert();
				mAlert.setName(name);
				alertItems.add(mAlert);
			}
		}
		listview = (ListView) findViewById(R.id.store_list_view);
		listview.setAdapter(alertListAdapter);
		alertListAdapter.setItems(alertItems);
	}

	@Override
	public void onClick(View arg0) {
	}

}
