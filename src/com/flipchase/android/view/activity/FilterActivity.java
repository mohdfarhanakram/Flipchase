/**
 * 
 */
package com.flipchase.android.view.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.flipchase.android.R;
import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.parcels.CatalogueChunk;
import com.flipchase.android.parcels.FilterBy;
import com.flipchase.android.parcels.RequestSortFilterOptions;
import com.flipchase.android.parcels.SortBy;
import com.flipchase.android.util.StringUtils;
import com.flipchase.android.view.widget.CustomFontTextView;

/**
 * @author m.farhan
 *
 */
public class FilterActivity extends BaseActivity implements OnClickListener,DialogInterface.OnClickListener,DialogInterface.OnMultiChoiceClickListener{

	private int mSortBySelectedOptionIndex;;
	private Dialog mSortByFilterDialog;
	private Dialog mFilterByDialog;
	private String[] mSortByOptions;
	private String[] mFilterByOptions;

	private boolean[] mFilterBySelected;

	private CatalogueChunk catalogueChunk;

	private SortBy sortBy;
	private FilterBy filterBy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_layout);
		findViewById(R.id.txtv_single_selection).setOnClickListener(this);
		findViewById(R.id.txtv_multiselection_selection).setOnClickListener(this);
		findViewById(R.id.applyFilterButton).setOnClickListener(this);
		findViewById(R.id.cancelFilterButton).setOnClickListener(this);

		sortBy = (SortBy)getIntent().getSerializableExtra("sortby");
		filterBy = (FilterBy)getIntent().getSerializableExtra("filterby");

		drawSortAndFilter();


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
		getSupportActionBar().setTitle(getResources().getString(R.string.filter_txt));
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

	private final Dialog createSortByDialog() {

		mSortByOptions = sortBy.getSortByArray();
		mSortBySelectedOptionIndex = sortBy.getSortBySelectedIndex();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.sort_by));
		builder.setSingleChoiceItems(mSortByOptions, mSortBySelectedOptionIndex, this);
		builder.setPositiveButton("OK",this);
		builder.setNegativeButton("Cancel", this);
		return builder.create();
	}

	private final Dialog createFilterByDialog() {
		mFilterByOptions = filterBy.getFilterByArray();
		mFilterBySelected = filterBy.getSelectedFilterBooleanArray(); 

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.filter_by));
		builder.setMultiChoiceItems(mFilterByOptions, mFilterBySelected, (OnMultiChoiceClickListener) this);
		builder.setPositiveButton("OK",this);
		builder.setNegativeButton("Cancel", this);
		
		mFilterByDialog =  builder.create();
		
		final ListView dialogListView = ((AlertDialog) mFilterByDialog).getListView();
		dialogListView.setOnItemClickListener(new OnItemClickListener() {  
	             @Override  
	             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
	                 boolean isChecked = dialogListView.isItemChecked(position);  
	                /* if (position == 0) {  
	                     if(selectAll) {  
	                         for (int i = 1; i < length; i++) { // we start with first element after "Select all" choice  
	                             if (isChecked && !listView.isItemChecked(i)   
	                                     || !isChecked && listView.isItemChecked(i)) {  
	                                 listView.performItemClick(listView, i, 0);      
	                             }  
	                         }          
	                     }  
	                 } else {      
	                     if (!isChecked && listView.isItemChecked(0)) {  
	                         // if other item is unselected while "Select all" is selected, unselect "Select all"   
	                         // false, performItemClick, true is a must in order for this code to work  
	                         selectAll = false;  
	                         listView.performItemClick(listView, 0, 0);          
	                         selectAll = true;  
	                     }  
	                 }  */
	             }

				/*@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {					// TODO Auto-generated method stub
					
				} */ 
	         });
	     
	     return mFilterByDialog;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txtv_single_selection:
			mSortByFilterDialog = createSortByDialog();
			mSortByFilterDialog.show();
			break;
		case R.id.txtv_multiselection_selection:
			mFilterByDialog = createFilterByDialog();
			mFilterByDialog.show();
			break;
		case R.id.applyFilterButton:
			Intent intent = getIntent();
			intent.putExtra(AppConstants.FILTER_URL, getComposedFilterUrl());
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.cancelFilterButton:
			setResult(RESULT_CANCELED,getIntent());
			finish();
			break;
		default:
			break;
		}

	}


	@Override
	public void onClick(DialogInterface dialog, int which) {

		switch (which) {
		case Dialog.BUTTON_NEGATIVE: // Cancel button selected, do nothing
			dialog.cancel();
			break;

		case Dialog.BUTTON_POSITIVE: // OK button selected, send the data back
			dialog.dismiss();

			String id = sortBy.getSortOptions().get(mSortBySelectedOptionIndex).getId();
			sortBy.setSelected(id);

			filterBy.setSelected(mFilterBySelected);
			updateMultiselectionUI();
			((CustomFontTextView)findViewById(R.id.txtv_single_selection)).setText(sortBy.getSortOptions().get(mSortBySelectedOptionIndex).getName());
			break;

		default: // choice item selected
			mSortBySelectedOptionIndex = which;

			break;
		}
	}


	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		switch (which) {
		case Dialog.BUTTON_NEGATIVE: // Cancel button selected, do nothing
			dialog.cancel();
			break;

		case Dialog.BUTTON_POSITIVE: // OK button selected, send the data back

			dialog.dismiss();
			break;

		default: // choice item selected
			mFilterBySelected[which]=isChecked;
			if(which==0){
				filterBy.selectAll(isChecked);
				mFilterBySelected = filterBy.getSelectedFilterBooleanArray();

			}
			break;

		}

	}

	private void selectCheckBoxes(boolean isChecked) {
		final ListView dialogListView = ((AlertDialog) mFilterByDialog).getListView();

		for (int position = 0; position < dialogListView.getChildCount(); position++) {

				// Check items, disable and make them unclickable
				((CheckedTextView)dialogListView.getChildAt(position)).setChecked(isChecked);
				//dialogListView.getChildAt(position).setEnabled(false);
				//dialogListView.getChildAt(position).setClickable(true);
		}
	}


	private void updateMultiselectionUI(){
		String selectedString = filterBy.getSelectedString();

		if(StringUtils.isNullOrEmpty(selectedString)){
			((CustomFontTextView)findViewById(R.id.txtv_multiselection_selection)).setText("Selecte Category");
		}else{
			((CustomFontTextView)findViewById(R.id.txtv_multiselection_selection)).setText(selectedString);
		}
	}

	private String getComposedFilterUrl(){
		
		RequestSortFilterOptions obj = new RequestSortFilterOptions();
		obj.setFilterBy(filterBy);
		obj.setSortBy(sortBy);
		String jsonString = convertObjectToJsonString(obj);
		return jsonString;
	}

	private void drawSortAndFilter(){
		mSortByOptions = sortBy.getSortByArray();
		mSortBySelectedOptionIndex = sortBy.getSortBySelectedIndex(); 
		((CustomFontTextView)findViewById(R.id.txtv_single_selection)).setText(mSortByOptions[mSortBySelectedOptionIndex]);

		mFilterByOptions = filterBy.getFilterByArray();
		mFilterBySelected = filterBy.getSelectedFilterBooleanArray(); 
		updateMultiselectionUI();
	}


}
