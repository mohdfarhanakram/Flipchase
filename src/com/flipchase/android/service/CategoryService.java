package com.flipchase.android.service;

import java.util.List;

import com.flipchase.android.domain.Catalogue;
import com.flipchase.android.domain.Category;

public interface CategoryService {

	public List<Category> getAllCategory();
	
	public List<Catalogue> getCatalogueForCategory(String categoryId);
	
	
}
