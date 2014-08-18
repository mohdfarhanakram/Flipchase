package com.flipchase.android.view.activity;

import java.util.List;
import java.util.Stack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.edmodo.cropper.cropwindow.CropOverlayView;
import com.edmodo.cropper.cropwindow.edge.Edge;
import com.flipchase.android.R;
import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.extlibpro.FlipViewController;
import com.flipchase.android.extlibpro.FlipViewController.ViewFlipListener;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.parcels.CataloguePageItem;
import com.flipchase.android.parcels.CataloguePagesChunk;
import com.flipchase.android.util.Utils;
import com.flipchase.android.view.adapter.CataloguePageAdapter;
import com.flipchase.android.view.widget.TouchImageView;

public class FlipHorizontalLayoutActivity extends BaseActivity implements ViewFlipListener,DialogInterface.OnClickListener {
  

	private FlipViewController flipView;
	private CataloguePageAdapter cataloguePageAdapter;
	private String catalogueId;
	private Stack<CataloguePagesChunk> mProductDataStack;
	
	private View currentlyVisibleView=null;
	private boolean mIsCropWindowVisible = false;

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
		cataloguePageAdapter = new CataloguePageAdapter(this);
		flipView.setAdapter(cataloguePageAdapter);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			catalogueId = extras.getString("catalogueId");
		}
		
		//setContentView(flipView);
		

		
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
			default:
				break;
			}
			return true;
		}


	public void loadMoreCataloguepagesChunk(int pageId) {
		showProgressDialog("Loading More Catalogues Pages...");
		String refinedURL = URLConstants.GET_CATALOGUE_PAGES_FOR_CATEGORY_URL
				.replace("{catalogueid}", catalogueId);
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
			String refinedURL = URLConstants.GET_CATALOGUE_PAGES_FOR_CATEGORY_URL.replace("{catalogueid}", catalogueId);
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
					updateStackCatalogItemChunkData(cataloguePagesChunk);
					handleCatalogueImageLoadingData(cataloguePagesChunk);
					break;
				default:
					break;
				}
			}
		}
	}

	/** Load 2 Images and wait for some time so that these images loads successfully **/
	private void handleCatalogueImageLoadingData(final CataloguePagesChunk cataloguePagesChunk) {
		final List<CataloguePageItem> items = cataloguePagesChunk.getItems();
		if (items == null || items.size() == 0) {
			cataloguePageAdapter.setAllItemsLoaded(true);
			return;
		}
		CataloguePageItem c1 = items.get(0);
		c1.loadBitmap(this);
		/*
		if (items.size() > 1) {
			CataloguePageItem c2 = items.get(1);
			c2.loadBitmap(this);
		}
		*/
		showProgressDialog("Loading Catalogues Offers Images");
		new Handler().postDelayed(new Runnable() {
			public void run() {
				loadBitmaps(items);
				cataloguePageAdapter.setItems(items, cataloguePagesChunk.getPageId());
				removeProgressDialog();
			}
		}, 2000); // DK: Depending upon the network
	}

	private void loadBitmaps(List<CataloguePageItem> items) {
		for (CataloguePageItem item : items) {
			item.loadBitmap(this);
		}
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
        
        return croppedBitmap;
		
	}
	
	/*
	 *  Save list item dialog to save selected items from the image.
	 */
	private final Dialog createFormDialog(Bitmap bitmp) {

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.layout_item_add_details, null);
		ImageView imgView = (ImageView)layout.findViewById(R.id.itmeImage);
		if(bitmp!=null)
			imgView.setImageBitmap(bitmp);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("List: Add Details");
		builder.setPositiveButton("Create",this);
		builder.setNegativeButton("Cancel", this);
		builder.setView(layout);
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
		// TODO Auto-generated method stub
		
	}
	
}
