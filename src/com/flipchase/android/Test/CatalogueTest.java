package com.flipchase.android.Test;

import java.util.List;

import com.flipchase.android.domain.Catalogue;
import com.flipchase.android.dummyData.DummyData;

public class CatalogueTest {
	
	public static void main()
	{
		DummyData d1=new DummyData();
		List<Catalogue> catalogues = d1.getDummyCatalogues();
		for(Catalogue cat : catalogues) {
			System.out.println(cat.getPhoto_thumb_path());
		}
	}
}
