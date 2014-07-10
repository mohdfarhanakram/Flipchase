/**
 * 
 */
package com.flipchase.android.listener;

import com.flipchase.android.model.ServiceResponse;

/**
 * @author m.farhan
 *
 */
public interface IParser {
    public ServiceResponse parseData(int eventType,String data);

}
