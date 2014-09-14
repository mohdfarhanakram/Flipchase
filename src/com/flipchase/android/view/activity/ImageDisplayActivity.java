
package com.flipchase.android.view.activity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.edmodo.cropper.cropwindow.CropOverlayView;
import com.edmodo.cropper.cropwindow.edge.Edge;
import com.flipchase.android.R;
import com.flipchase.android.controller.DbController;
import com.flipchase.android.controller.DbEvent;
import com.flipchase.android.domain.Catalogue;
import com.flipchase.android.domain.Store;
import com.flipchase.android.listener.DbListener;
import com.flipchase.android.model.DbControllerResponse;
import com.flipchase.android.model.Item;
import com.flipchase.android.util.PicassoEx;
import com.flipchase.android.util.Utils;
import com.flipchase.android.view.widget.CustomFontEditText;
import com.flipchase.android.view.widget.TouchImageView;

import de.ankri.views.Switch;

public class ImageDisplayActivity extends BaseActivity implements DialogInterface.OnClickListener,DbListener{

	private static final String BUNDLE_POSITION = "position";

	private int position;

	private List<Page> pages;

	private String selectedImageURL = null;
	private Store store;
	private Catalogue catalogue;
	private boolean mIsCropWindowVisible = false;

	private View mFormView;
	private ArrayList<Item> selectlist = new ArrayList<Item>();
	
	private String catalogId;
	private String catalogName;
	
	private byte[] croppedImageByte;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppThemeLight);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.extension_activity);
		getSupportActionBar().setTitle("Image display");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		this.store = (Store) getIntent().getSerializableExtra("store");
		this.catalogue = (Catalogue) getIntent().getSerializableExtra("catalog");
		
		catalogId = catalogue.getId();
		catalogName = catalogue.getName();

		showProgressDialog("Loading..");
		DbController controller = new DbController(this, null, DbEvent.FETCH_LIST, this);
		controller.execute();

		/*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.violetsky);


		((FlipchaseCropImageView)findViewById(R.id.cropImageView)).setImageBitmap(bitmap);*/



		/*if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_POSITION)) {
            position = savedInstanceState.getInt(BUNDLE_POSITION);
        }
        pages = Arrays.asList(
               // new Page("Large images", ImageDisplayLargeFragment.class)
        		 new Page("Large images", ExtensionPinFragment.class)
        );*/
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			selectedImageURL = extras.getString("selectedImageURL");
			picassoLoad(selectedImageURL, (TouchImageView)findViewById(R.id.zoomImage));
		}
		//updatePage();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		mMenu = menu;
		mMenu.clear();
		MenuInflater inflater = getMenuInflater();
		if(mIsCropWindowVisible)
			inflater.inflate(R.menu.crop_window_actionbar, menu);
		else
			inflater.inflate(R.menu.display_image_action_bar, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(BUNDLE_POSITION, position);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.show_image_crop_window:
			showCropWindow(true);
			break;
		case R.id.action_image_crop:
			createFormDialog(getCroppedImage()).show();
			break;
		case android.R.id.home:
			onBackPressed();
		case R.id.action_location:
			showStoreLocation();
		default:
			break;
		}
		return true;
	}

	private void showStoreLocation() {
		Intent i = new Intent(ImageDisplayActivity.this,
				NearestCatalogueStoreActivity.class);
		i.putExtra("store", store);  
		startActivity(i);
	}

	public String getSelectedImageName() {
		return selectedImageURL;
	}

	public void next() {
		position++;
		updatePage();
	}

	public void previous() {
		position--;
		updatePage();
	}

	private void updatePage() {
		if (position > pages.size() - 1) {
			return;
		}


		/*getSupportActionBar().setSubtitle(pages.get(position).subtitle);
		try {
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.frame, (Fragment)pages.get(position).clazz.newInstance())
			.commit();
		} catch (Exception e) {
			Log.e("something", "Failed to load fragment", e);
			Toast.makeText(this, "Whoops, couldn't load the fragment!", Toast.LENGTH_SHORT).show();
		}*/
	}

	private static final class Page {
		private final String subtitle;
		private final Class<?> clazz;
		private Page(String subtitle, Class<?> clazz) {
			this.subtitle = subtitle;
			this.clazz = clazz;
		}
	}


	private void showCropWindow(boolean isCropWindowVisible){
		mIsCropWindowVisible = isCropWindowVisible;

		if(isCropWindowVisible){

			TouchImageView imageview = (TouchImageView)findViewById(R.id.zoomImage);
			final Rect result = new Rect(0, 0,imageview.getWidth(),imageview.getHeight());
			CropOverlayView cropImageView = (CropOverlayView)findViewById(R.id.cropOverlayView);
			cropImageView.setVisibility(View.VISIBLE);
			cropImageView.setBitmapRect(result);

		}else{
			findViewById(R.id.cropOverlayView).setVisibility(View.GONE);
		}

		supportInvalidateOptionsMenu();

	}

	@Override
	public void onBackPressed() {
		if(mMenu!=null && mMenu.size()==1){
			showCropWindow(false);
			return;
		}
		finish();
	}

	/*
	 *  Save list item dialog to save selected items from the image.
	 */
	private final Dialog createFormDialog(Bitmap bitmp) {

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mFormView = inflater.inflate(R.layout.layout_item_add_details, null);
		ImageView imgView = (ImageView)mFormView.findViewById(R.id.itmeImage);
		if(bitmp!=null)
			imgView.setImageBitmap(bitmp);

		Spinner spinner = (Spinner)mFormView.findViewById(R.id.select_list_spinner);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
              catalogId = selectlist.get(position).getId();
              catalogName = selectlist.get(position).getName();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, getSelectList());
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("List: Add Details");
		builder.setPositiveButton("Create",this);
		builder.setNegativeButton("Cancel", this);
		builder.setView(mFormView);
		return builder.create();
	}


	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case Dialog.BUTTON_NEGATIVE:
			dialog.dismiss();
			break;
		case Dialog.BUTTON_POSITIVE:
			createAndSaveList();
			break;

		default:
			break;
		}

	}

	private void createAndSaveList() {

		//ArrayList<Item> dataList = new ArrayList<Item>();

		ImageView itemImage = (ImageView)mFormView.findViewById(R.id.itmeImage);
		String itemTitle = ((CustomFontEditText)mFormView.findViewById(R.id.s_item_title)).getText().toString();
		int reminder = ((Switch)mFormView.findViewById(R.id.s_set_reminder)).isChecked()==true?1:0;
		String quantity = ((CustomFontEditText)mFormView.findViewById(R.id.s_quantity)).getText().toString();
		String subItem = ((CustomFontEditText)mFormView.findViewById(R.id.s_item_name)).getText().toString();
		

		Item item = new Item();
		item.setQuantity(quantity);
		item.setTitle(itemTitle);
		item.setReminder(reminder);
		item.setQuantity(quantity);
		item.setSubTitle(subItem);
		item.setId(catalogId);
		item.setName(catalogName);
		item.setImageInByte(croppedImageByte);

		//dataList.add(item);

		showProgressDialog("Loading..");
		DbController controller = new DbController(this, item, DbEvent.CREATE_LIST_DATA, this);
		controller.execute();

	}


	/*
	 *  Cropped selected area of the image.
	 */
	private Bitmap getCroppedImage(){

		Display display = getWindowManager().getDefaultDisplay();
		@SuppressWarnings("deprecation")
		int width = display.getWidth();
		int height = ((TouchImageView)findViewById(R.id.zoomImage)).getMeasuredHeight();

		Bitmap imageViewBitmap = Utils.getBitmapFromView(findViewById(R.id.zoomImage), height, width);

		final Rect displayedImageRect = new Rect(0, 0,imageViewBitmap.getWidth(),imageViewBitmap.getHeight());

		// Get the scale factor between the actual Bitmap dimensions and the
		// displayed dimensions for width.
		final float actualImageWidth = imageViewBitmap.getWidth();
		final float displayedImageWidth = displayedImageRect.width();
		final float scaleFactorWidth = actualImageWidth / displayedImageWidth;

		// Get the scale factor between the actual Bitmap dimensions and the
		// displayed dimensions for height.
		final float actualImageHeight = imageViewBitmap.getHeight();
		final float displayedImageHeight = displayedImageRect.height();
		final float scaleFactorHeight = actualImageHeight / displayedImageHeight;

		// Get crop window position relative to the displayed image.
		final float cropWindowX = Edge.LEFT.getCoordinate() - displayedImageRect.left;
		final float cropWindowY = Edge.TOP.getCoordinate() - displayedImageRect.top;
		final float cropWindowWidth = Edge.getWidth();
		final float cropWindowHeight = Edge.getHeight();

		// Scale the crop window position to the actual size of the Bitmap.
		final float actualCropX = cropWindowX * scaleFactorWidth;
		final float actualCropY = cropWindowY * scaleFactorHeight;
		final float actualCropWidth = cropWindowWidth * scaleFactorWidth;
		final float actualCropHeight = cropWindowHeight * scaleFactorHeight;

		// Crop the subset from the original Bitmap.
		final Bitmap croppedBitmap = Bitmap.createBitmap(imageViewBitmap,
				(int) actualCropX,
				(int) actualCropY,
				(int) actualCropWidth,
				(int) actualCropHeight);
		
		Bitmap temp = croppedBitmap;
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		temp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		croppedImageByte = stream.toByteArray();
		
		return croppedBitmap;

	}

	private void picassoLoad(String url, ImageView imageView) {
		PicassoEx.getPicasso(this).load(url).config(Bitmap.Config.RGB_565).placeholder(R.drawable.flip).fit().into(imageView);
		//PicassoEx.getPicasso(mContext).load(url).get()
	}


	@Override
	public void onDatabaseOperationDone(DbControllerResponse response) {
		removeProgressDialog();
		switch (response.getEvent()) {
		case DbEvent.FETCH_LIST:
			Item compareItem = new Item();
			compareItem.setId(catalogue.getId());
			compareItem.setName(catalogue.getName());
			compareItem.setCount(0);

			if((ArrayList<Item>)response.getResponseObject()!=null){
				selectlist = (ArrayList<Item>)response.getResponseObject();
			}

			if(selectlist.indexOf(compareItem)==-1){
				selectlist.add(0, compareItem);
			}
			break;
		case DbEvent.CREATE_LIST_DATA:
			boolean sucess = (Boolean)response.getResponseObject();
			if(sucess){
				Toast.makeText(this, "List is Created Successfully", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(ImageDisplayActivity.this, HomeActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(i);
			}else{
				Toast.makeText(this, "List is not created, please try again.", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}

	}

	private ArrayList<String> getSelectList(){
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0; i<selectlist.size(); i++){
			list.add(selectlist.get(i).getName());
		}
		return list;
	}




}
