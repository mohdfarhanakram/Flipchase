/**
 * 
 */
package com.flipchase.android.view.adapter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.view.activity.BaseActivity;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

/**
 * @author FARHAN
 *
 */


public class SearchAdapter extends ArrayAdapter<String> implements Filterable {
    private ArrayList<String> resultList;
    public static Context context;
    //public Object lockOnResultList=new Object();
    public SearchAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context=context;
    }
    public SearchAdapter(Context context, int textViewResourceId,ArrayList<String> list) {
        super(context, textViewResourceId,list);
        this.context=context;
        resultList=list;
    }
    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
    	//synchronized (lockOnResultList) {
    		int count = getCount();
        	if(count > index){
        		return resultList.get(index);
        	}else{
        		return "";
        	}
		//}
    }
    
    public void setResultList(ArrayList<String> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
			@Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                	//synchronized (lockOnResultList) {
                		
                		String query = "";
                        try {
                            query = URLEncoder.encode(constraint.toString(),"utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        
                        String url = FlipchaseApi.GET_SUGGESTION_API + query + FlipchaseApi.GET_SUGGESTION_SUFFIX_API;
                        
                        ((BaseActivity)context).fetchData(url, FlipchaseApi.API_SEARCH_SUGGESTIONS, null);
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
       
					//}
                    
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }
}
