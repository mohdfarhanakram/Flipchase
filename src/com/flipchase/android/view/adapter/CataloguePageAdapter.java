package com.flipchase.android.view.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.flipchase.android.R;
import com.flipchase.android.extlibpro.FlipBookLog;
import com.flipchase.android.extlibpro.IO;
import com.flipchase.android.extlibpro.CataloguesData;
import com.flipchase.android.extlibpro.UI;
import com.flipchase.android.view.activity.ImageDisplayActivity;

public class CataloguePageAdapter extends BaseAdapter {

  private LayoutInflater inflater;

  private int repeatCount = 1;

  private List<CataloguesData.Data> travelData;

  private Context activityContext;
  
  public CataloguePageAdapter(Context context) {
	activityContext = context;
    inflater = LayoutInflater.from(context);
    travelData = new ArrayList<CataloguesData.Data>(CataloguesData.IMG_DESCRIPTIONS);
  }

  @Override
  public int getCount() {
    return travelData.size() * repeatCount;
  }

  public int getRepeatCount() {
    return repeatCount;
  }

  public void setRepeatCount(int repeatCount) {
    this.repeatCount = repeatCount;
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View layout = convertView;
    if (convertView == null) {
      layout = inflater.inflate(R.layout.complex1, null);
      FlipBookLog.d("created new view from adapter: %d", position);
    }

    final CataloguesData.Data data = travelData.get(position % travelData.size());

    UI
    .<ImageView>findViewById(layout, R.id.photo)
    .setImageBitmap(IO.readBitmap(inflater.getContext().getAssets(), data.imageFilename));
    ImageView iv =(ImageView) layout.findViewById(R.id.photo);
    iv.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        	Intent i = new Intent(activityContext, ImageDisplayActivity.class);      
        	i.putExtra("selectedImageName", data.imageFilename);
        	activityContext.startActivity(i);
        }
    });
    
    /*
    UI
        .<TextView>findViewById(layout, R.id.title)
        .setText(AphidLog.format("%d. %s", position, data.title));

    
    UI
        .<ImageView>findViewById(layout, R.id.photo)
        .setImageBitmap(IO.readBitmap(inflater.getContext().getAssets(), data.imageFilename));

    UI
        .<TextView>findViewById(layout, R.id.description)
        .setText(Html.fromHtml(data.description));

    UI
        .<Button>findViewById(layout, R.id.wikipedia)
        .setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(data.link)
            );
            inflater.getContext().startActivity(intent);
          }
        });
    */
    return layout;
  }

  public void removeData(int index) {
    if (travelData.size() > 1) {
      travelData.remove(index);
    }
  }
}
