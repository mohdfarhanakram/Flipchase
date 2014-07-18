package com.flipchase.android.repository;

import java.util.List;

import com.flipchase.android.domain.Catalogue;
import com.flipchase.android.domain.CataloguePage;

public interface CatalogueRepository {

	public List<Catalogue> getLatestCatalogues();
	
	public List<CataloguePage> getCataloguePagesForCatalogue(Long catalogueId);
	
}
