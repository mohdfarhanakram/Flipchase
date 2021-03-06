package com.flipchase.android.service;

import java.util.List;

import com.flipchase.android.domain.Catalogue;
import com.flipchase.android.domain.CataloguePage;

public interface CatalogueService {

	public List<Catalogue> getLatestCatalogues();
	
	public List<CataloguePage> getCataloguePagesForCatalogue(Long catalogueId);
}
