/**
 * 
 */
package com.flipchase.android.view.fragment;

import java.util.Stack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flipchase.android.constants.AppConstants;
import com.flipchase.android.listener.IScreenView;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.parcels.StoreCatalogue;
import com.flipchase.android.parser.IParser;
import com.flipchase.android.view.activity.BaseActivity;
import com.flipchase.android.view.activity.HomeActivity;

/**
 * @author m.farhan
 *
 */
public class BaseFragment extends Fragment implements IScreenView, SearchView.OnSuggestionListener, SearchView.OnQueryTextListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	/**
     * trigger to fetch the data from the server
     *
     * @param url
     * @param eventType
     * @param parser
     */
    public boolean fetchData(String url, int eventType, IParser parser) {
        return ((BaseActivity) getActivity()).fetchData(url, eventType, parser);
    }

    /**
     * @param bodyText shows the progress dialog to the user for any task that is under progress
     */
    public void showProgressDialog(String bodyText) {
        ((BaseActivity) getActivity()).showProgressDialog(bodyText);
    }

    /**
     * remove the progress dialog if it is showing on the screen
     */
    public void removeProgressDialog() {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).removeProgressDialog();
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * shows the common error dialog for all the exceptions occured during the application
     */
    public void showCommonErrorDialog() {
        showCommonError("Common Error Message");
    }

    /**
     * shows all the errors that are occurred during the network calls
     *
     * @param message
     */
    public void showWebErrorDialog(String message) {
        showCommonError(message);
    }

    /**
     * created the instance for the Catalog class
     *
     * @return : Object of Catalog
     */
   /* public Catalog popFragmentCatalogData() {

        return ((BaseActivity) getActivity()).getCatalogDataStack().peek();
    }*/
    public StoreCatalogue popFragmentStoreCatalogData() {
        if (this instanceof StoreFragment) {
            Stack<StoreCatalogue> stack = ((BaseActivity) getActivity()).getStoreCatalogueDataStack();
            if (stack.size() == 1) {
                stack.pop();
                return null;
            }
            if (stack.size() > 1) {
                stack.pop();//remove the top Catalog
                StoreCatalogue data = null;
                do {
                    data = stack.peek();
                    if (data != null && data.getItems().size() == 0) {
                        stack.pop();
                        data = null;
                    }
                }
                while ((data == null) && stack.size() > 0);

                return data;

            }

        }
        return null;
    }

    /**
     * adds the product that the user has visited to the stack
     *
     * @param productData : instance of Catalog
     */
    public void pushFragmentStoreCatalogueData(StoreCatalogue productData) {
        if (isStackFull()) {
            ((BaseActivity) getActivity()).getStoreCatalogueDataStack().pop();

        }
        ((BaseActivity) getActivity()).getStoreCatalogueDataStack().push(productData);
    }

    /**
     * checks if the stack to add the shorlisted products is full or not
     *
     * @return : boolean true / false
     * true : if stack has the space , false : if stack is filled
     */
    private boolean isStackFull() {
        Stack<StoreCatalogue> stack = ((BaseActivity) getActivity()).getStoreCatalogueDataStack();
        int size = stack.size();
        for (StoreCatalogue catalog : stack) {
            if (catalog.getItems().size() == 0)
                size--;
        }
        if (size == AppConstants.MAX_CATALOG_STACK_SIZE) {
            return true;
        }
        return false;
    }

    /**
     * updates the fragment stack by removing the first product and
     * adding the last product seen
     *
     * @param productData : : instance of Catalog
     */
    public void updateTopFragmentStoreCatalogueData(StoreCatalogue productData) {
        if(((HomeActivity)getActivity()).getStoreCatalogueDataStack().size()>0)
            ((HomeActivity) getActivity()).getStoreCatalogueDataStack().pop();

        ((HomeActivity) getActivity()).getStoreCatalogueDataStack().push(productData);
    }

	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
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

	@Override
	public void updateUi(ServiceResponse response) {
		// TODO Auto-generated method stub
		
	}

}
