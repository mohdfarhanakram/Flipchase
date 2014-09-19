/**
 * 
 */
package com.flipchase.android.view.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.util.LruCache;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.flipchase.android.R;
import com.flipchase.android.app.Flipchase;
import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.listener.IScreenView;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.network.JVolleyError;
import com.flipchase.android.network.VolleyGenericRequest;
import com.flipchase.android.network.VolleyHelper;
import com.flipchase.android.network.volley.Response;
import com.flipchase.android.network.volley.VolleyError;
import com.flipchase.android.parcels.StoreCatalogue;
import com.flipchase.android.parser.BaseParser;
import com.flipchase.android.parser.IParser;
import com.flipchase.android.persistance.AppSharedPreference;
import com.flipchase.android.util.StringUtils;
import com.flipchase.android.util.Utils;
import com.flipchase.android.view.widget.CustomFontEditText;
import com.flipchase.android.view.widget.FlipdchaseSearchView;
import com.flipchase.android.view.widget.FlipdchaseSearchView.OnSearchViewCollapsedEventListener;
import com.flipchase.android.view.widget.FlipdchaseSearchView.OnSearchViewExpandedEventListener;


/**
 * @author m.farhan
 *
 */
@SuppressLint("NewApi")
public abstract class BaseActivity extends ActionBarActivity implements OnSearchViewCollapsedEventListener, OnSearchViewExpandedEventListener,
View.OnFocusChangeListener, SearchView.OnQueryTextListener, SearchView.OnSuggestionListener ,
Response.Listener, Response.ErrorListener, IScreenView {

	public Menu mMenu;
	private MenuItem mSearchMenuItem;
	private MenuItem mAddListItem;
	private static final int SEARCH = 1000120;
	private FlipdchaseSearchView mSearchView;
	private CustomFontEditText mAddListEdit;
	protected static HashMap<String, Object> mRetainedObjects = new HashMap<String, Object>();
	public ProgressDialog mProgressDialog;

	private LruCache<String, Bitmap> mMemoryCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			restoreParcelableData(savedInstanceState);
			Object retainedObj = mRetainedObjects.get(getClass().getName());
			if (retainedObj != null)
				restoreNonParcelableData(retainedObj);
			mRetainedObjects.remove(getClass().getName());
		}
		super.onCreate(savedInstanceState);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		// Get max available VM memory, exceeding this amount will throw an
		// OutOfMemory exception. Stored in kilobytes as LruCache takes an
		// int in its constructor.
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = maxMemory / 8;

		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// The cache size will be measured in kilobytes rather than
				// number of items.
				return bitmap.getByteCount() / 1024;
			}
		};
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (savedInstanceState != null) {
			//we are restoring from previous state
			//instance created because of device configuration changes(rotation)
			if (isDataExists()) {
				populateViews();
			} else {
				//check if our previous request is still pending
				//if yes then we have to update the callbacks
				//else make a fresh request
				//TODO: refine this condition
				if (!VolleyHelper.getInstance(this).updateListeners(this)) {
					requestData(-1, null);
					return;
				}
			}
			//updating listener for pending requests
			VolleyHelper.getInstance(this).updateListeners(this);
		} else {
			//we don't have any previous state to restore
			// perhaps this must be default creation
			requestData(-1, null);
		}
	}

	/**
	 * Helper method for making all network requests
	 *
	 * @param event event for data
	 * @param data  request data
	 */
	protected void requestData(int event, Object data) {
	}

	protected boolean isDataExists() {
		return false;
	}

	protected void populateViews() {

	}

	/**
	 * Callback method for restoring saved parcelable dat
	 *
	 * @param savedInstanceState bundle object containing the saved data
	 */
	protected void restoreParcelableData(Bundle savedInstanceState) {

	}


	/**
	 * Callback method for restoring the non parcelable objects across orientation change
	 *
	 * @param retainedObject
	 */
	protected void restoreNonParcelableData(Object retainedObject) {

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		saveParcelableInstances(outState);
		mRetainedObjects.put(getClass().getName(), getNonParcelableInstances());
		super.onSaveInstanceState(outState);


	}

	/**
	 * Callback method for saving the parcelable instances across Orientation changes
	 *
	 * @param bundle Bundle object to put the data in
	 */
	protected void saveParcelableInstances(Bundle bundle) {

	}

	/**
	 * This method should return the non parcelable instance to be retained across Orientation changes
	 *
	 * @return Non Parcelable objects
	 */
	protected Object getNonParcelableInstances() {
		return null;
	}

	/**
	 * Utility function for displaying progress dialog
	 *
	 * @param bodyText message to be shown
	 */

	public void showProgressDialog(String bodyText) {
		if (Utils.isInternetAvailable(this)) {
			if (mProgressDialog == null) {
				mProgressDialog = new ProgressDialog(BaseActivity.this);
				mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				mProgressDialog.setCancelable(false);
				mProgressDialog.setOnKeyListener(new Dialog.OnKeyListener() {
					@Override
					public boolean onKey(DialogInterface dialog, int keyCode,
							KeyEvent event) {
						if (keyCode == KeyEvent.KEYCODE_CAMERA
								|| keyCode == KeyEvent.KEYCODE_SEARCH) {
							return true; //
						}
						return false;
					}
				});
			}

			mProgressDialog.setMessage(bodyText);

			if (!mProgressDialog.isShowing()) {
				mProgressDialog.show();
			}
		}
	}

	/**
	 * Utility function to remove progress dialog
	 */
	public void removeProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing() && !isFinishing()) {
			mProgressDialog.dismiss();
		}
	}

	/**
	 * Helper function to obtain cached json data based on event type
	 *
	 * @param eventType
	 * @return
	 */
	public String getJSONForRequest(int eventType) {
		String request = null;
		switch (eventType) {
		case FlipchaseApi.INIT_REQUEST:
			request = AppConstants.RESPONSE_INIT;
			break;
		case FlipchaseApi.GET_ALL_CITIES:
			request = AppConstants.RESPONSE_GET_CITIES;
			break;

		case FlipchaseApi.GET_ALL_LOCATIONS:
			request = AppConstants.RESPONSE_GET_LOCATIONS; 
			break;
		case FlipchaseApi.GET_LATEST_CATALOGUES:
			request = AppConstants.RESPONSE_GET_LATEST_CATALOGUES;
			break;
		case FlipchaseApi.GET_ALL_RETAILERS:
			request = AppConstants.RESPONSE_GET_REATILERS;
			break;
		case FlipchaseApi.GET_ALL_CITIES_AND_LOCATIONS: 
			request = AppConstants.GET_ALL_CITIES_AND_LOCATIONS;
			break;
		default:
			return "";
		}
		return AppSharedPreference.getString(request, "", this);

	}

	/**
	 * Helper method for making a http post request
	 *
	 * @param url         request url
	 * @param eventType   request event type
	 * @param map         post body params as map
	 * @param postData    string/json post body
	 * @param contentType content type for distinguishing json/plain text request
	 * @param parser      parser object tobe used for response parsing
	 */
	private void postData(String url, int eventType, HashMap<String, String> map, String postData, int contentType, IParser parser) {
		url = addSessionId(url, eventType);
		try {
			VolleyGenericRequest req = null;
			if (map != null) {
				req = new VolleyGenericRequest(VolleyGenericRequest.ContentType.FORM_ENCODED_DATA, url, map, this, this, this);
			} else
				req = new VolleyGenericRequest(contentType, url, postData, this, this, this);
			req.setEventType(eventType);

			req.setParser(parser == null ? new BaseParser() : parser);

			VolleyHelper.getInstance(this).addRequestInQueue(req);
			//   Log.d("URL:  ", url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Helper method for making a http post request
	 *
	 * @param url         request url
	 * @param eventType   request event type
	 * @param postData    string/json post body
	 * @param contentType content type for distinguishing json/plain text request
	 * @param parser      parser object tobe used for response parsing
	 */
	public void postData(String url, int eventType, String postData, int contentType, IParser parser) {
		postData(url, eventType, null, postData, contentType, parser);

	}

	/**
	 * Helper method for making a http post request
	 *
	 * @param url       request url
	 * @param eventType request event type
	 * @param map       post body params as map
	 * @param parser    parser object tobe used for response parsing
	 */
	public void postData(String url, int eventType, HashMap<String, String> map, IParser parser) {
		postData(url, eventType, map, null, VolleyGenericRequest.ContentType.FORM_ENCODED_DATA, parser);

	}

	private String addSessionId(String url, int eventType) {
		String sessionId = getSessionId();
		if (!StringUtils.isNullOrEmpty(sessionId)) {
			url += url.contains("?") == true ? "&session[id]=" + sessionId : "?session[id]=" + sessionId;
			return url;
		}
		return url;
	}

	/**
	 * Helper method to make Http get data from server.
	 *
	 * @param url       request url
	 * @param eventType request event type
	 * @param parser    parser object to be used for response parsing
	 */

	public boolean fetchData(String url, final int eventType, IParser parser) {
		boolean returnVal = false;
		if (Utils.isInternetAvailable(this)) {
			final IParser parser1 = parser == null ? new BaseParser() : parser;
			String cachedResponse = getJSONForRequest(eventType);
			if (StringUtils.isNullOrEmpty(cachedResponse)) {
				//url = addSessionId(url, eventType);
				VolleyGenericRequest req = new VolleyGenericRequest(url, this, this, this);

				req.setEventType(eventType);
				req.setParser(parser1);
				//TODO  req.setRequestTimeOut(Constants.API_TIMEOUT);
				VolleyHelper.getInstance(this).addRequestInQueue(req);
			} else {
				final String tempResponse = cachedResponse;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						onResponse(parser1.parseData(eventType, tempResponse));
					}
				});
			}
		} else {
			removeProgressDialog();
			showCommonError(AppConstants.NETWORK_CONNECTION_ERROR);
			returnVal = true;
		}
		return returnVal;
	}

	/**
	 * Volley response callback method
	 *
	 * @param objResponse response object
	 */

	@Override
	public void onResponse(Object objResponse) {
		// if (isFinishing() || mDestroyed)
		//     return;
		ServiceResponse resp = (ServiceResponse) objResponse;

		if (resp.getFlipChaseBaseModel() == null) {
			removeProgressDialog();
			showCommonError("Common Error Message");
			//DK: Depending upon Screen we can update the UI
			//updateUi(resp);
		} else {
			updateUi(resp);
		}
	}

	private String getErrorMessage(ServiceResponse response) {
		String errorMsg = "";
		if (response.getErrorMessages() != null && response.getErrorMessages().size() > 0) {
			for (int i = 0; i < response.getErrorMessages().size(); i++) {
				if (errorMsg == null) {
					errorMsg = response.getErrorMessages().get(i);
				} else {
					errorMsg = errorMsg + response.getErrorMessages().get(i);
				}
			}
		}
		return errorMsg;
	}

	/**
	 * Volley error response callback method
	 *
	 * @param error error object
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		//if (isFinishing() || mDestroyed)
		//    return;
		JVolleyError jError = (JVolleyError) error;
		ServiceResponse response = new ServiceResponse();
		response.setEventType(jError.getEventType());
		response.setErrorCode(ServiceResponse.EXCEPTION);
		response.setErrorText("Volley Error");

		if (response.getFlipChaseBaseModel() == null) {
			removeProgressDialog();
			showCommonError("Common Error Message");
			updateUi(response);
		} else {
			updateUi(response);
		}
	}

	/**
	 * Utility function for showing common error dialog.
	 *
	 * @param message
	 */

	public void showCommonError(String message) {
		if (TextUtils.isEmpty(message)) {
			message = "Common Error Message";
		}
		Toast.makeText(this, message, 3000).show();
	}

	/**
	 * Utility function for showing common error dialog.
	 *
	 * @param message message to be shown
	 */
	public void showWebErrorDialog(String message) {
		showCommonError(message);
		removeProgressDialog();
	}

	/**
	 * Util function for calculating screen width
	 *
	 * @return calculated screen width
	 */
	public int getScreenWidth() {

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		return displaymetrics.widthPixels;
	}

	/**
	 * returns catalog stack data
	 *
	 * @return
	 */
	public Stack<StoreCatalogue> getStoreCatalogueDataStack() {
		return null;
	}

	/**
	 * Helper method for creating search view
	 *
	 * @param searchId           menu item id
	 * @param menu               menu object
	 * @param lstnr              on query listener object
	 * @param suggestionListener
	 * @param listener           action expand listener
	 * @param showSuggestions
	 */
	public void buildSearchView(String hintText,int searchId, Menu menu, SearchView.OnQueryTextListener lstnr, SearchView.OnSuggestionListener suggestionListener, boolean showSuggestions,int type) {

		mSearchMenuItem = menu.findItem(searchId);
		if (null == mSearchMenuItem)
			mSearchMenuItem = menu.add(0, SEARCH, 0, "").setIcon(R.drawable.ic_action_search);

		MenuItemCompat.setShowAsAction(mSearchMenuItem, MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		mSearchView = new FlipdchaseSearchView((((ActionBarActivity) this)).getSupportActionBar().getThemedContext());
		mSearchView.setOnSearchViewCollapsedEventListener(this);
		mSearchView.setOnSearchViewExpandedEventListener(this);

		mSearchView.setQueryHint(hintText);

		/* if (this.getClass().getSimpleName().equalsIgnoreCase("HomeActivity")) {
            //TO-Do set search hint test based on activity ex:
        	mSearchView.setQueryHint(getString(R.string.search_hint_text));

        }*/
		mSearchView.setIconified(true);
		mSearchView.setOnQueryTextListener(lstnr);

		AutoCompleteTextView searchText = (AutoCompleteTextView) mSearchView.findViewById(R.id.search_src_text);
		searchText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					createList( v.getText().toString());
				}else if(actionId == EditorInfo.IME_ACTION_SEARCH){
					doSearch(v.getText().toString());
				}
				return false;
			}
		});
		searchText.setGravity(Gravity.BOTTOM);
		searchText.setTextColor(getResources().getColor(R.color.search_txt_color));
		searchText.setHintTextColor(getResources().getColor(R.color.hint_text_color));
		searchText.setCursorVisible(true);
		searchText.setThreshold(0);
		//        searchText.setDropDownBackgroundResource(R.color.white);
		if (null != Flipchase.mTypeface) {
			Flipchase.mTypeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto_Regular.ttf");
		}
		searchText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		searchText.setTypeface(Flipchase.mTypeface);
		MenuItemCompat.setActionView(mSearchMenuItem, mSearchView);

		searchText.setOnFocusChangeListener(this);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);

		View searchplate = (View) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
		//searchplate.setBackgroundResource(R.drawable.apptheme_search_view_edit_text_holo_light);

		ImageView searchIcon = (ImageView) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);

		if(type==0){
			searchText.setImeActionLabel("Search", EditorInfo.IME_ACTION_SEARCH);
			ImageView searchCloseIcon = (ImageView) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
			searchCloseIcon.setImageResource(R.drawable.clear_searchs);
			searchIcon.setImageResource(R.drawable.abc_ic_search);
			searchIcon.setVisibility(View.VISIBLE);
		}else{
			searchText.setImeActionLabel("Create", EditorInfo.IME_ACTION_DONE);
			searchIcon.setVisibility(View.GONE);
		}
		/* if (showSuggestions) {
            SearchBoxApadter searchBoxApadter = new SearchBoxApadter(this, R.layout.textview);
            searchText.setAdapter(searchBoxApadter);
        }**/
		mSearchView.setOnSuggestionListener(suggestionListener);
	}




	public void addNewListTextView(String hintText,int searchId, Menu menu) {

		mAddListItem = menu.findItem(searchId);
		/*if (null == mAddListItem)
        	mAddListItem = menu.add(0, SEARCH, 0, "").setIcon(R.drawable.ic_action_new);*/

		MenuItemCompat.setShowAsAction(mAddListItem, MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		mAddListEdit = new CustomFontEditText((((ActionBarActivity) this)).getSupportActionBar().getThemedContext());
		mAddListEdit.setHint(hintText);
		mAddListEdit.setGravity(Gravity.BOTTOM);
		mAddListEdit.setTextColor(getResources().getColor(R.color.search_txt_color));
		mAddListEdit.setHintTextColor(getResources().getColor(R.color.hint_text_color));
		mAddListEdit.setCursorVisible(true);

		/* if (this.getClass().getSimpleName().equalsIgnoreCase("HomeActivity")) {
            //TO-Do set search hint test based on activity ex:
        	mSearchView.setQueryHint(getString(R.string.search_hint_text));

        }*/
		/*mSearchView.setIconified(true);
        mSearchView.setOnQueryTextListener(lstnr);*/

		/* AutoCompleteTextView searchText = (AutoCompleteTextView) mSearchView.findViewById(R.id.search_src_text);
        searchText.setGravity(Gravity.BOTTOM);
        searchText.setTextColor(getResources().getColor(R.color.search_txt_color));
        searchText.setHintTextColor(getResources().getColor(R.color.hint_text_color));
        searchText.setCursorVisible(true);
        searchText.setThreshold(0);*/
		//        searchText.setDropDownBackgroundResource(R.color.white);
		if (null != Flipchase.mTypeface) {
			Flipchase.mTypeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto_Regular.ttf");
		}
		mAddListEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		mAddListEdit.setTypeface(Flipchase.mTypeface);
		MenuItemCompat.setActionView(mAddListItem, mAddListEdit);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);

		/*View searchplate = (View) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        //searchplate.setBackgroundResource(R.drawable.apptheme_search_view_edit_text_holo_light);

        ImageView searchCloseIcon = (ImageView) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchCloseIcon.setImageResource(R.drawable.clear_searchs);

        ImageView searchIcon = (ImageView) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(R.drawable.abc_ic_search);*/

		mAddListEdit.setImeActionLabel("Create", EditorInfo.IME_ACTION_DONE);
		/* if (showSuggestions) {
            SearchBoxApadter searchBoxApadter = new SearchBoxApadter(this, R.layout.textview);
            searchText.setAdapter(searchBoxApadter);
        }**/
		//mSearchView.setOnSuggestionListener(suggestionListener);
	}

	/**
	 * Search view Callback interface implementation
	 */
	@Override
	public void onSearchViewExpanded() {
		if (null != mMenu) {
			//getSupportActionBar().setIcon(R.drawable.ic_menu_logo);
			int size = mMenu.size();
			for (int i = 0; i < size; i++) {
				mMenu.getItem(i).setVisible(false);
			}
		}
	}

	/**
	 * Search view Callback interface implementation
	 */
	@Override
	public void onSearchViewCollapsed() {
		if (null != mMenu) {
			int size = mMenu.size();
			for (int i = 0; i < size; i++) {
				mMenu.getItem(i).setVisible(true);
			}
			restoreAdapter();
		}
	}

	@Override
	public boolean onSuggestionClick(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSuggestionSelect(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void restoreAdapter() {
	}


	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {

		return false;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub

	}

	/**
	 * Shows message dialog
	 *
	 * @param errormsg message to be shown
	 */
	public void showMessageDialog(String errormsg) {
		showCommonError(errormsg);
	}

	@Override
	protected void onRestart() {
		//isAppComingFromBackgroundToForground
		super.onRestart();
	}

	public String getSessionId() {
		return "ses11";
		//return StaticDataDao.getInstance(this).getSessionId();
	}

	//TODO: If we receive response from server it saves in prefs but if from prefs then again it re saves it
	@Override
	public void updateUi(ServiceResponse response) {
		if (response.getErrorCode() == ServiceResponse.SUCCESS) {
			switch (response.getEventType()) {
			case FlipchaseApi.GET_LATEST_CATALOGUES:
				//Utils.putStringinPrefs(this, AppConstants.RESPONSE_GET_LATEST_CATALOGUES, response.getJsonResponse().toString());
				break;
			case FlipchaseApi.GET_ALL_RETAILERS:
				//Utils.putStringinPrefs(this, AppConstants.RESPONSE_GET_REATILERS, response.getJsonResponse().toString());
				break;
			default: 
				break;
			}
		}
	}

	private String getScreenName() {
		String screenName = "";
		if (this instanceof SplashActivity) {
			screenName = "Splash";
		} else if (this instanceof SelectLocationActivity) {
			screenName = "Select Location";
		} 
		return screenName;
	}

	protected String convertObjectToJsonString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}

	public void doSearch(String searckKey){

	}

	public void createList(String listName){

	}

	/*public void loadBitmap(int resId, ImageView imageView) {
        final String imageKey = String.valueOf(resId);

        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        } else {
            mImageView.setImageResource(R.drawable.image_placeholder);
            BitmapWorkerTask task = new BitmapWorkerTask(mImageView);
            task.execute(resId);
        }
    }*/

}
