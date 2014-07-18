package com.flipchase.android.service.impl;

import java.util.List;

import com.flipchase.android.domain.Catalogue;
import com.flipchase.android.domain.CataloguePage;
import com.flipchase.android.repository.CatalogueRepository;
import com.flipchase.android.repository.impl.CatalogueRepositoryImpl;
import com.flipchase.android.service.CatalogueService;

public class CatalogueServiceImpl implements CatalogueService {

	private CatalogueRepository catalogueRepository;
	
	public CatalogueServiceImpl() {
		catalogueRepository = new CatalogueRepositoryImpl();
	}
	
	@Override
	public List<Catalogue> getLatestCatalogues() {
		return catalogueRepository.getLatestCatalogues();
	}

	@Override
	public List<CataloguePage> getCataloguePagesForCatalogue(Long catalogueId) {
		return catalogueRepository.getCataloguePagesForCatalogue(catalogueId);
	}

	
}
