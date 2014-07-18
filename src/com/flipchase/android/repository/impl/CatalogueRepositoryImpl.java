package com.flipchase.android.repository.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonParseException;

import com.flipchase.android.constants.URLConstants;
import com.flipchase.android.domain.Catalogue;
import com.flipchase.android.domain.CataloguePage;
import com.flipchase.android.repository.CatalogueRepository;
import com.flipchase.android.util.ServiceHandler;

public class CatalogueRepositoryImpl extends BaseRepository implements CatalogueRepository {

	@Override
	public List<Catalogue> getLatestCatalogues() {
		String jsonStr = serviceHandler.makeServiceCall(URLConstants.GET_LATEST_CATEGORY_URL, ServiceHandler.GET);
		Catalogue[] catalogues = null;
        try {
			jp = jsonFactory.createJsonParser(jsonStr);
			catalogues = objectMapper.readValue(jp, Catalogue[].class);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        return new ArrayList<Catalogue>(Arrays.asList(catalogues));
	}

	@Override
	public List<CataloguePage> getCataloguePagesForCatalogue(Long catalogueId) {
		String jsonStr = serviceHandler.makeServiceCall(URLConstants.GET_CATALOGUE_PAGES_FOR_CATEGORY_URL, ServiceHandler.GET, null, catalogueId);
		CataloguePage[] cataloguePages = null;
        try {
			jp = jsonFactory.createJsonParser(jsonStr);
			cataloguePages = objectMapper.readValue(jp, CataloguePage[].class);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        return new ArrayList<CataloguePage>(Arrays.asList(cataloguePages));
	}
}
