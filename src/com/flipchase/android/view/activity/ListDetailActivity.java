/**
 * 
 */
package com.flipchase.android.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.flipchase.android.R;
import com.flipchase.android.cache.DiskLruImageCache;
import com.flipchase.android.model.Item;

/**
 * @author FARHAN
 *
 */
public class ListDetailActivity extends BaseActivity{
	private Item item;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_detail_activity);
		
		item = (Item) getIntent().getSerializableExtra("item");
		
		((TextView)findViewById(R.id.list_name_txtva)).setText(item.getTitle());
		((TextView)findViewById(R.id.list_title_name_txtv)).setText(item.getName()+" * "+item.getSubTitle());
		
		DiskLruImageCache cache = new DiskLruImageCache(this, "flipchase");
		ImageView imageView = (ImageView)findViewById(R.id.detail_image_view);
		Bitmap bitmap = cache.getBitmap(item.getUid()+"farhan");
		if(bitmap!=null)
			imageView.setImageBitmap(bitmap);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		buildActionBar(menu);
		return super.onCreateOptionsMenu(menu);

	} 

	public void buildActionBar(Menu menu) {
		if (null == menu) return;
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle(item.getTitle());
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setSubtitle(null);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}



}
