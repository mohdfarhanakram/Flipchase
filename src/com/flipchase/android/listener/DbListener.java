/**
 * 
 */
package com.flipchase.android.listener;

import com.flipchase.android.model.DbControllerResponse;

/**
 * @author m.farhan
 *
 */
public interface DbListener {
	public void onDatabaseOperationDone(DbControllerResponse response);
}
