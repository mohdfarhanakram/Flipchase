package com.flipchase.android.repository;

import java.util.List;

import com.flipchase.android.domain.Category;

public interface CategoryRepository {

	public List<Category> getAllCategory(String url);
	public List<Category> getAllDummyCategory();
}
