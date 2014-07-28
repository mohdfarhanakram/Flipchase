/**
 * 
 */
package com.flipchase.android.response.common;


import com.flipchase.android.response.MetaData;
import com.flipchase.android.response.Session;

/**
 * @author m.farhan
 * 
 */
public class FlipChaseBaseModel {

	/**
	 * 
	 */
	private MetaData metadata;
	private boolean success;
	private Session session;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public MetaData getMetadata() {
		return metadata;
	}

	public void setMetadata(MetaData metadata) {
		this.metadata = metadata;
	}

}
