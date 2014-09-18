package com.flipchase.android.view.activity;

import java.io.IOException;
import java.util.Collections;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.flipchase.android.R;
import com.flipchase.android.parcels.CatalogueChunk;
import com.flipchase.android.util.Utils;
import com.flipchase.android.view.adapter.CatalogueAdapter;

public class AlertsActivityForExpiredCatalogues extends BaseActivity implements
		View.OnClickListener {

	private GridView catalogGridView;
	private CatalogueAdapter catalogueAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alerts_expired_catalogues);

		Bundle bundle = getIntent().getExtras();
		String alerts = bundle.getString("alerts");
		if (alerts != null) {
			System.out.println(alerts);
		}
		catalogGridView = (GridView)findViewById(R.id.expired_catalogues_grid_view);
		catalogueAdapter = new CatalogueAdapter(this, Collections.EMPTY_LIST);
		catalogGridView.setAdapter(catalogueAdapter);
		
		ObjectMapper objectMapper = new ObjectMapper();

		CatalogueChunk catalogueChunk = null;
		try {
			JSONObject jsobObject = new JSONObject(alerts);
			if (Utils.isJsonObject(jsobObject, "response")) {
	            JSONObject itemsJsonObject = jsobObject.getJSONObject("response");
	            String json = itemsJsonObject.toString();
	            JsonFactory jsonFactory = new JsonFactory();
	            try {
					JsonParser jp = jsonFactory.createJsonParser(json);
					catalogueChunk = objectMapper.readValue(jp, CatalogueChunk.class);
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		catalogueAdapter.setItems(catalogueChunk.getItems());
	}

	@Override
	public void onClick(View arg0) {
	}

}
