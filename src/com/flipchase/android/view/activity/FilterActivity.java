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

import com.flipchase.android.R;
import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.parcels.CatalogueChunk;
import com.flipchase.android.parcels.FilterBy;
import com.flipchase.android.parcels.SortBy;
import com.flipchase.android.util.StringUtils;
import com.flipchase.android.view.widget.CustomFontTextView;

/**
 * @author m.farhan
 *
 */
public class FilterActivity extends BaseActivity implements OnClickListener,DialogInterface.OnClickListener,DialogInterface.OnMultiChoiceClickListener{

	private int mSortBySelectedOptionIndex = 0;
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
		
		mSortByFilterDialog = createSortByDialog();
		mFilterByDialog = createFilterByDialog();
		
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

		((CustomFontTextView)findViewById(R.id.txtv_single_selection)).setText(mSortByOptions[mSortBySelectedOptionIndex]);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.sort_by));
		builder.setSingleChoiceItems(mSortByOptions, mSortBySelectedOptionIndex, this);
		builder.setPositiveButton("OK",this);
		builder.setNegativeButton("Cancel", this);
		return builder.create();
	}

	private final Dialog createFilterByDialog() {
		//To Do : Option would come from server as of now it is hard coded.
		mFilterByOptions = filterBy.getFilterByArray();

		mFilterBySelected = filterBy.getSelectedFilterBooleanArray(); 

		updateMultiselectionUI();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.sort_by));
		builder.setMultiChoiceItems(mFilterByOptions, mFilterBySelected, (OnMultiChoiceClickListener) this);
		builder.setPositiveButton("OK",this);
		builder.setNegativeButton("Cancel", this);
		return builder.create();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txtv_single_selection:
			mSortByFilterDialog.show();
			break;
		case R.id.txtv_multiselection_selection:
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
			break;

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
		String url="";
		return url;
	}
	
	
	
	
}
