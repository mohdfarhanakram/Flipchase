package com.flipchase.android.service.impl;

import java.util.List;

import com.flipchase.android.domain.Catalogue;
import com.flipchase.android.domain.Category;
import com.flipchase.android.repository.CategoryRepository;
import com.flipchase.android.repository.impl.CategoryRepositoryImpl;
import com.flipchase.android.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;
	    
	public CategoryServiceImpl() {
		categoryRepository = new CategoryRepositoryImpl();
	}
	
	@Override
	public List<Category> getAllCategory() {
		return categoryRepository.getAllDummyCategory();
	}

	@Override
	public List<Catalogue> getCatalogueForCategory(String categoryId) {
		return null;
	}

	@Override
	public List<Category> getLatestCategory() {
		return null;
	}

}
