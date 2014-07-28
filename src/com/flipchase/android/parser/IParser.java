package com.flipchase.android.parser;

import com.flipchase.android.model.ServiceResponse;

public interface IParser {
    public ServiceResponse parseData(int eventType,String data);

}
