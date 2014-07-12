package com.flipchase.android.repository.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import com.flipchase.android.domain.Category;
import com.flipchase.android.repository.CategoryRepository;
import com.flipchase.android.util.ServiceHandler;

public class CategoryRepositoryImpl implements CategoryRepository {

	private ServiceHandler serviceHandler;
	private ObjectMapper objectMapper = null;
	private JsonFactory jsonFactory = null;
	private JsonParser jp = null;
	    
	public CategoryRepositoryImpl() {
		serviceHandler = new ServiceHandler();
		objectMapper = new ObjectMapper();
        jsonFactory = new JsonFactory();
	}
	
	@Override
	public List<Category> getAllCategory(String url) {
		String jsonStr = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
        Category[] categories = null;
        try {
			jp = jsonFactory.createJsonParser(jsonStr);
			categories = objectMapper.readValue(jp, Category[].class);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        return new ArrayList<Category>(Arrays.asList(categories));
	}

	@Override
	public List<Category> getAllDummyCategory() {
		return null;
	}

}
