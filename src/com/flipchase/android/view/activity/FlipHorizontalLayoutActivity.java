package com.flipchase.android.view.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.edmodo.cropper.cropwindow.CropOverlayView;
import com.edmodo.cropper.cropwindow.edge.Edge;
import com.flipchase.android.R;
import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.controller.DbController;
import com.flipchase.android.controller.DbEvent;
import com.flipchase.android.domain.Catalogue;
import com.flipchase.android.domain.CataloguePage;
import com.flipchase.android.domain.MobileAlert;
import com.flipchase.android.domain.Store;
import com.flipchase.android.extlibpro.FlipViewController;
import com.flipchase.android.extlibpro.FlipViewController.ViewFlipListener;
import com.flipchase.android.listener.DbListener;
import com.flipchase.android.model.DbControllerResponse;
import com.flipchase.android.model.Item;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.network.VolleyGenericRequest;
import com.flipchase.android.parcels.CataloguePageItem;
import com.flipchase.android.parcels.CataloguePagesChunk;
import com.flipchase.android.util.StringUtils;
import com.flipchase.android.util.Utils;
import com.flipchase.android.view.adapter.CataloguePageAdapter;
import com.flipchase.android.view.widget.CustomFontEditText;
import com.squareup.picasso.LruCache;

import de.ankri.views.Switch;

public class FlipHorizontalLayoutActivity extends BaseActivity implements ViewFlipListener,DialogInterface.OnClickListener,DbListener {
  

	private FlipViewController flipView;
	private CataloguePageAdapter cataloguePageAdapter;
	private Catalogue catalogue;
	private Store store;
	private Stack<CataloguePagesChunk> mProductDataStack;
	private CataloguePagesChunk productData;
	
	private View currentlyVisibleView=null;
	private boolean mIsCropWindowVisible = false;
	private View mFormView;
	private String catalogId;
	private String catalogName;
	
	private ArrayList<Item> selectlist = new ArrayList<Item>();
	private Bitmap saveImage;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppThemeLight);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flip_horzontal_layout);
		// setTitle(R.string.activity_title);
		flipView = new FlipViewController(this, FlipViewController.HORIZONTAL);

		((LinearLayout)findViewById(R.id.flip_layout)).addView(flipView);
		flipView.setOnViewFlipListener(this);
		
		this.catalogue = (Catalogue) getIntent().getSerializableExtra("catalogue");
		this.store = (Store) getIntent().getSerializableExtra("store");
		cataloguePageAdapter = new CataloguePageAdapter(this);
		cataloguePageAdapter.setStore(store);
		cataloguePageAdapter.setCatalogue(catalogue);
		flipView.setAdapter(cataloguePageAdapter);
		

		catalogId = catalogue.getId();
		catalogName = catalogue.getName();

		showProgressDialog("Loading..");
		DbController controller = new DbController(this, null, DbEvent.FETCH_LIST, this);
		controller.execute();

		
		/*setContentView(flipView);
		
		View view = LayoutInflater.from(this).inflate(R.layout.layout_crop_image, null);
	    addContentView(view, null);*/
				
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
			break;
		case R.id.action_location:
			showStoreLocation();
			break;
		case R.id.action_favourite:
			saveAlert();
			break;
		default:
			break;
		}
		return true;
	}

	private void saveAlert() {
		showProgressDialog("Saving alert...");
		MobileAlert mobileAlert = new MobileAlert();
		mobileAlert.setName(catalogue.getName());
		mobileAlert.setDisplayName(catalogue.getName());
		mobileAlert.setCatalogueId(new Long(catalogue.getId()));
		mobileAlert.setRetailerId(catalogue.getRetailer());
		//cataloguePageAdapter.getCuurentCataloguePageId
		String jsonString = convertObjectToJsonString(mobileAlert);
		postData(URLConstants.SAVE_MOBILE_ALERT_URL, FlipchaseApi.SAVE_MOBILE_ALERT, jsonString, VolleyGenericRequest.ContentType.JSON, null);
	}
	
	private void showStoreLocation() {
		Intent i = new Intent(FlipHorizontalLayoutActivity.this,
				NearestCatalogueStoreActivity.class);
    	i.putExtra("store", store);  
    	startActivity(i);
	}
	
	public void loadMoreCataloguepagesChunk(int pageId) {
		showProgressDialog("Loading More Catalogues Pages...");
		String refinedURL = URLConstants.GET_CATALOGUE_PAGES_FOR_CATEGORY_URL
				.replace("{catalogueid}", catalogue.getId());
		refinedURL = refinedURL + "?pageid=" + pageId;
		fetchData(refinedURL,
		FlipchaseApi.GET_CATALOGUE_PAGES_FOR_CATALOGUE, null);
		fetchData(refinedURL,FlipchaseApi.GET_CATALOGUE_PAGES_FOR_CATALOGUE, null);
	}
	
	@Override
	protected void requestData(int event, Object data) {
		super.requestData(event, data);
		switch (event) {
		default:
			showProgressDialog("Loading Catalogues Pages...");
			String refinedURL = URLConstants.GET_CATALOGUE_PAGES_FOR_CATEGORY_URL.replace("{catalogueid}", catalogue.getId());
			refinedURL = refinedURL + "?pageid=1";
			fetchData(refinedURL,FlipchaseApi.GET_CATALOGUE_PAGES_FOR_CATALOGUE, null);
			break;
		}
	}

	@Override
	public void updateUi(ServiceResponse response) {
		super.updateUi(response);
		removeProgressDialog();
		if (response.getErrorCode() == ServiceResponse.EXCEPTION) {
			showCommonError("Error Occured...");
		} else if (response.getErrorCode() == ServiceResponse.SUCCESS) {
			if (response.getFlipChaseBaseModel().isSuccess()) {
				switch (response.getEventType()) {
				case FlipchaseApi.GET_CATALOGUE_PAGES_FOR_CATALOGUE:
					CataloguePagesChunk cataloguePagesChunk = (CataloguePagesChunk) response
							.getResponseObject();
					this.productData = cataloguePagesChunk;
					//updateStackCatalogItemChunkData(cataloguePagesChunk);
					handleCatalogueImageLoadingData(cataloguePagesChunk);
					break;
				case FlipchaseApi.SAVE_MOBILE_ALERT:
					removeProgressDialog();
					showMessageDialog("Alert saved successfully.");
					break;
				default:
					break;
				}
			}
		}
	}

	/** Load 2 Images and wait for some time so that these images loads successfully **/
	private void handleCatalogueImageLoadingData(final CataloguePagesChunk cataloguePagesChunk) {
		final List<CataloguePage> items = cataloguePagesChunk.getItems();
		if (items == null || items.size() == 0) {
			cataloguePageAdapter.setAllItemsLoaded(true);
			return;
		}
		List<CataloguePageItem> cataloguePageItems = new ArrayList<CataloguePageItem>();
		for(CataloguePage cp : items) {
			CataloguePageItem cpi = new CataloguePageItem();
			cpi.setCatalogue_id(cp.getCatalogue_id());
			cpi.setCount(cp.getCount());
			cpi.setDisplayName(cp.getDisplayName());
			cpi.setId(cp.getId());
			cpi.setName(cp.getName());
			cpi.setPagenum(cp.getPagenum());
			cpi.setPhoto_mini_path(cp.getPhoto_mini_path());
			cpi.setPhoto_path(cp.getPhoto_path());
			cpi.setPhoto_thumb_path(cp.getPhoto_thumb_path());
			cataloguePageItems.add(cpi);
		}
		cataloguePageAdapter.setItems(cataloguePageItems, cataloguePagesChunk.getPageId());
		
		CataloguePageItem c1 = cataloguePageItems.get(0);
		c1.loadBitmap(this);
		
		CataloguePageItem c2 = cataloguePageItems.get(1);
		c2.loadBitmap(this);
		
		loadBitmaps(cataloguePageItems);
		showProgressDialog("Loading more offers...");
		new Handler().postDelayed(new Runnable() {
			public void run() {
				removeProgressDialog();
			}
		}, 3000);
		
	}

	private void loadBitmaps(List<CataloguePageItem> items) {
		for (CataloguePageItem item : items) {
			item.loadBitmap(this);
		}
	}

	public void updateView() {
		int count = cataloguePageAdapter.getDownloadedImageCount();
		if(count % 10 == 0 || count == cataloguePageAdapter.getCount() - 1) {
			cataloguePageAdapter.notifyDataSetChanged();//setItems(productData.getItems(), productData.getPageId());
		}
		++count;
		cataloguePageAdapter.setDownloadedImageCount(count);
	}
	
	/**
     * adds the product that the user has visited to the stack
     *
     * @param productData : instance of Catalog
     */
    public void pushFragmentCatalogData() {
        if (isStackFull()) {
        	mProductDataStack.pop();
        }
        //((BaseActivity) getActivity()).getCatalogDataStack().push(productData);
    }

    /**
     * checks if the stack to add the shorlisted products is full or not
     *
     * @return : boolean true / false
     * true : if stack has the space , false : if stack is filled
     */
    private boolean isStackFull() {
        int size = mProductDataStack.size();
        if (size == AppConstants.MAX_CATALOG_STACK_SIZE) {
            return true;
        }
        return false;
    }

    
	public void updateStackCatalogItemChunkData(CataloguePagesChunk productData) {
		if (mProductDataStack == null) {
			mProductDataStack = new Stack<CataloguePagesChunk>();
		} else if (isStackFull()) {
			mProductDataStack.pop();
		}
		mProductDataStack.push(productData);
	}

	public Stack<CataloguePagesChunk> getCatalogDataStack() {
		if (mProductDataStack == null)
			mProductDataStack = new Stack<CataloguePagesChunk>();
		return mProductDataStack;
	}

	public void onDestoryOfThisActivity() {
		CataloguePagesChunk data = popFragmentCatalogData();
	}

	/*
	 * public Catalog popFragmentCatalogData() {
	 */
	public CataloguePagesChunk popFragmentCatalogData() {
		if (mProductDataStack != null) {
			if (mProductDataStack.size() == 1) {
				mProductDataStack.pop();
				return null;
			}
			if (mProductDataStack.size() > 1) {
				mProductDataStack.pop();// remove the top Catalog
				CataloguePagesChunk data = null;
				do {
					data = mProductDataStack.peek();
					if (data != null) {// DK: && data.getItems().size() == 0) {
						mProductDataStack.pop();
						data = null;
					}
				} while ((data == null) && mProductDataStack.size() > 0);
				return data;
			}
		}
		return null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		flipView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		flipView.onPause();
	}

	@Override
	public void onViewFlipped(View view, int position) {
		currentlyVisibleView = view;
		
	} 
	
	 /*
     *  Cropped selected area of the image.
     */
	private Bitmap getCroppedImage(){
		
		Display display = getWindowManager().getDefaultDisplay();
		@SuppressWarnings("deprecation")
		int width = display.getWidth();
		int height = ((LinearLayout)findViewById(R.id.flip_layout)).getMeasuredHeight();
		
		Bitmap imageViewBitmap = Utils.getBitmapFromView(findViewById(R.id.flip_layout), height, width);

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
        
        saveImage = croppedBitmap;
        
        return croppedBitmap;
		
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
	
	private void showCropWindow(boolean isCropWindowVisible){
		mIsCropWindowVisible = isCropWindowVisible;

		if(isCropWindowVisible){

			LinearLayout layout = (LinearLayout)findViewById(R.id.flip_layout);
			final Rect result = new Rect(0, 0,layout.getWidth(),layout.getHeight());
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
		item.setTitle(itemTitle==null?"":itemTitle);
		item.setReminder(reminder);
		item.setQuantity(StringUtils.isNullOrEmpty(quantity)==true?"1":quantity);
		item.setSubTitle(subItem==null?"":subItem);
		item.setId(catalogId);
		item.setName(catalogName);
		item.setImageInByte(null);
		//item.setImageInByte(croppedImageByte);
		
		//dataList.add(item);

		showProgressDialog("Loading..");
		DbController controller = new DbController(this, item, DbEvent.CREATE_LIST_DATA, this);
		controller.execute();

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
			String id = (String)response.getResponseObject();
			if(!StringUtils.isNullOrEmpty(id)){
				
				com.squareup.picasso.LruCache lcache = new LruCache(this);
				lcache.set(id, saveImage);
				
				/*ImageView imgView = (ImageView)mFormView.findViewById(R.id.itmeImage);
		        ImageCacher imageCacher=new ImageCacher(this, -1);
		        imageCacher.loadImage("farhan", imgView);*/
				
				//addBitmapToMemoryCache(id, saveImage);
				
				
				Toast.makeText(this, "List is Created Successfully", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(FlipHorizontalLayoutActivity.this, HomeActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
