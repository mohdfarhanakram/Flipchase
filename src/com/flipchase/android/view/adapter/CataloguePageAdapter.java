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
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.domain.CataloguePage;
import com.flipchase.android.extlibpro.FlipBookLog;
import com.flipchase.android.util.PicassoEx;
import com.flipchase.android.view.activity.ImageDisplayActivity;

public class CataloguePageAdapter extends BaseAdapter {

  private LayoutInflater inflater;

  private int repeatCount = 1;

  private List<CataloguePage> catalogueData;

  private Context activityContext;
  
  public CataloguePageAdapter(Context context, List<CataloguePage> catalogueData) {
	activityContext = context;
    inflater = LayoutInflater.from(context);
    this.catalogueData = catalogueData;
  }

  public CataloguePageAdapter(Context context) {
		activityContext = context;
	    inflater = LayoutInflater.from(context);
  }
  
  public void setItems(List<CataloguePage> items) {
      this.catalogueData = items;
      notifyDataSetChanged();
  }
  
  @Override
  public int getCount() {
	  return catalogueData == null ? 0 : catalogueData.size() * repeatCount;
    //return catalogueData.size() * repeatCount;
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
      layout = inflater.inflate(R.layout.horizontal_flip_book_layout, null);
      FlipBookLog.d("created new view from adapter: %d", position);
    }

    final CataloguePage data = catalogueData.get(position % catalogueData.size());

    ImageView iv =(ImageView) layout.findViewById(R.id.photo);
    picassoLoad(URLConstants.IMAGE_SERVER_URL + data.getPhoto_path(), iv);
    /*
    UI
    .<ImageView>findViewById(layout, R.id.photo)
    .setImageBitmap(IO.readBitmap(inflater.getContext().getAssets(), data.imageFilename));
    */
    iv.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        	Intent i = new Intent(activityContext, ImageDisplayActivity.class);      
        	i.putExtra("selectedImageURL", URLConstants.IMAGE_SERVER_URL + data.getPhoto_path());
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

  private void picassoLoad(String url, ImageView imageView) {
		PicassoEx.getPicasso(activityContext).load(url).config(Bitmap.Config.RGB_565).placeholder(R.drawable.flip).fit().into(imageView);
  }
  
  public void removeData(int index) {
    if (catalogueData.size() > 1) {
    	catalogueData.remove(index);
    }
  }
}
