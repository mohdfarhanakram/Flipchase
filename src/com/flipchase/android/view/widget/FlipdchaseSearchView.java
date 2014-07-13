/**
 * 
 */
package com.flipchase.android.view.widget;

import android.content.Context;
import android.support.v7.widget.SearchView;

/**
 * @author m.farhan
 *
 */
public class FlipdchaseSearchView extends SearchView{

	private OnSearchViewCollapsedEventListener mSearchViewCollapsedEventListener;
	private OnSearchViewExpandedEventListener mOnSearchViewExpandedEventListener;

	public FlipdchaseSearchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public interface OnSearchViewCollapsedEventListener {
		public void onSearchViewCollapsed();
	}

	public interface OnSearchViewExpandedEventListener {
		public void onSearchViewExpanded();
	}

	public void setOnSearchViewCollapsedEventListener(OnSearchViewCollapsedEventListener eventListener) {
		mSearchViewCollapsedEventListener = eventListener;
	}

	public void setOnSearchViewExpandedEventListener(OnSearchViewExpandedEventListener eventListener) {
		mOnSearchViewExpandedEventListener = eventListener;
	}
	
	
	 @Override
	    public void onActionViewCollapsed() {
	        if (mSearchViewCollapsedEventListener != null)
	            mSearchViewCollapsedEventListener.onSearchViewCollapsed();
	        super.onActionViewCollapsed();



	    }

	    @Override
	    public void onActionViewExpanded() {
	        if (mOnSearchViewExpandedEventListener != null)
	            mOnSearchViewExpandedEventListener.onSearchViewExpanded();
	        super.onActionViewExpanded();
	    }

}
