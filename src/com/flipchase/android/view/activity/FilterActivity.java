/**
 * 
 */
package com.flipchase.android.view.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;


import com.flipchase.android.R;
import com.flipchase.android.util.StringUtils;
import com.flipchase.android.util.Utils;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_layout);
		findViewById(R.id.txtv_single_selection).setOnClickListener(this);
		findViewById(R.id.txtv_multiselection_selection).setOnClickListener(this);
		findViewById(R.id.applyFilterButton).setOnClickListener(this);
		findViewById(R.id.cancelFilterButton).setOnClickListener(this);

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
		//To Do : Option would come from server as of now it is hard coded.
		mSortByOptions = (String[]) getResources().getStringArray(R.array.sort_by_options);

		//To Do : Default selected index set from server as of now it is 0 ;
		mSortBySelectedOptionIndex = 0;  //To Do : handle it from server response

		((CustomFontTextView)findViewById(R.id.txtv_single_selection)).setText(mSortByOptions[0]);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.sort_by));
		builder.setSingleChoiceItems(mSortByOptions, mSortBySelectedOptionIndex, this);
		builder.setPositiveButton("OK",this);
		builder.setNegativeButton("Cancel", this);
		return builder.create();
	}

	private final Dialog createFilterByDialog() {
		//To Do : Option would come from server as of now it is hard coded.
		mFilterByOptions = (String[]) getResources().getStringArray(R.array.filter_by_options);

		mFilterBySelected = new boolean[mFilterByOptions.length]; 

		//To Do : Default selected values set from server as of now it is hard coded ;
		for(int i = 0; i<mFilterByOptions.length; i++){
			/*if(i==0)
				mFilterBySelected[0] = true;
			else
				mFilterBySelected[i] = false;*/

			mFilterBySelected[i] = false;
		}

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
			break;
		case R.id.cancelFilterButton:
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
			updateMultiselectionUI();
			((CustomFontTextView)findViewById(R.id.txtv_single_selection)).setText(mSortByOptions[mSortBySelectedOptionIndex]);
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

	private String getSelectedString(){
		String selectedString = "";
		for(int i=0; i<mFilterBySelected.length; i++){
			if(mFilterBySelected[i]){
				selectedString = selectedString + " "+mFilterByOptions[i]+",";
			}
		}
		
		if(!StringUtils.isNullOrEmpty(selectedString))
			selectedString = selectedString.substring(0, selectedString.length()-2);
		
		return selectedString;

	}

	private void updateMultiselectionUI(){
		String selectedString = getSelectedString().trim();

		if(StringUtils.isNullOrEmpty(selectedString)){
			((CustomFontTextView)findViewById(R.id.txtv_multiselection_selection)).setText("Selecte Category");
		}else{
			((CustomFontTextView)findViewById(R.id.txtv_multiselection_selection)).setText(selectedString);
		}
	}
	
	private String composeUrl(){
		String url="";
		return url;
	}
	
	
}
