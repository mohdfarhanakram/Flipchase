package com.flipchase.android.view.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.flipchase.android.R;
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.extlibpro.FlipBookLog;
import com.flipchase.android.parcels.CataloguePageItem;
import com.flipchase.android.view.activity.FlipHorizontalLayoutActivity;
import com.flipchase.android.view.activity.ImageDisplayActivity;

public class CataloguePageAdapter extends BaseAdapter {

  private LayoutInflater inflater;

  private int repeatCount = 1;

  private List<CataloguePageItem> catalogueData;

  private Context activityContext;
  
  private int pageId;
  private boolean isAllItemsLoaded = false;
  
  public CataloguePageAdapter(Context context, List<CataloguePageItem> catalogueData, int pageId) {
	activityContext = context;
    inflater = LayoutInflater.from(context);
    this.catalogueData = catalogueData;
    this.pageId = pageId;
  }

  public CataloguePageAdapter(Context context) {
		activityContext = context;
	    inflater = LayoutInflater.from(context);
  }
  
  public void setItems(List<CataloguePageItem> items, int pageId) {
      this.catalogueData = items;
      this.pageId = pageId;
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

    final CataloguePageItem data = catalogueData.get(position % catalogueData.size());

    ImageView iv =(ImageView) layout.findViewById(R.id.photo);
    iv.setImageBitmap(data.getcBitmap());
    //picassoLoad(URLConstants.IMAGE_SERVER_URL + data.getPhoto_path(), iv);
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
    
    if(position == catalogueData.size() -1 && !isAllItemsLoaded) {
    	((FlipHorizontalLayoutActivity) activityContext).loadMoreCataloguepagesChunk(pageId + 1);
    }
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

  
  /*
  private void picassoLoad(String url, ImageView imageView) {
	  //PicassoEx.getPicasso(activityContext).load(url).fit().into(imageView);
	PicassoEx.getPicasso(activityContext).load(url).config(Bitmap.Config.RGB_565).placeholder( android.R.drawable.dark_header).
	fit().into(imageView);
  }
  */
  
  public int getPageId() {
	return pageId;
  }

  public void setPageId(int pageId) {
	this.pageId = pageId;
  }

  
  public boolean isAllItemsLoaded() {
	return isAllItemsLoaded;
  }

  public void setAllItemsLoaded(boolean isAllItemsLoaded) {
	this.isAllItemsLoaded = isAllItemsLoaded;
  }

  public void removeData(int index) {
    if (catalogueData.size() > 1) {
    	catalogueData.remove(index);
    }
  }
}
