package com.flipchase.android.repository.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.flipchase.android.domain.AlertForm;
import com.flipchase.android.domain.AlertView;
import com.flipchase.android.domain.Category;
import com.flipchase.android.repository.CategoryRepository;
import com.flipchase.android.util.ServiceHandler;

public class CategoryRepositoryImpl extends BaseRepository implements CategoryRepository {

	@Override
	public List<Category> getAllCategory() {
		return null;
	}

	//NOT IN USE NOW
	public void getAllCategory1() {
		//String jsonStr = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
		AlertForm alertForm = new AlertForm();
		alertForm.setEmailId("eee");
		List<AlertView> alertViews = new ArrayList<AlertView>();
		AlertView av1 = new AlertView();
		av1.setName("abc");
		alertViews.add(av1);
		alertForm.setAlertViews(alertViews);
		String json1son;
		try {
			json1son = objectMapper.writeValueAsString(alertForm);
			String jsonStr1 = serviceHandler.makeServiceCall("http://10.0.2.2:8080/catalogue/testalerts", ServiceHandler.POST,json1son);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
