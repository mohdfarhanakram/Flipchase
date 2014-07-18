package com.flipchase.android.repository.impl;

import java.io.IOException;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.flipchase.android.util.ServiceHandler;

public abstract class BaseRepository {

	protected ServiceHandler serviceHandler = null;
	protected ObjectMapper objectMapper = null;
	protected JsonFactory jsonFactory = null;
	protected JsonParser jp = null;
	    
	public BaseRepository() {
		serviceHandler = new ServiceHandler();
		objectMapper = new ObjectMapper();
        jsonFactory = new JsonFactory();
	}
	
	protected String convertToJson(Object object) {
		String json = null;
		try {
			json = objectMapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	protected Object[] prepareParams(Object... a) {
		return null;
	}
}
