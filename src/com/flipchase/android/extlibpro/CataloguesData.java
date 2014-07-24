package com.flipchase.android.extlibpro;

import java.util.ArrayList;
import java.util.List;

public class CataloguesData {

  public static final List<Data> IMG_DESCRIPTIONS = new ArrayList<Data>();

  static {
	  CataloguesData.IMG_DESCRIPTIONS.add(new CataloguesData.Data("", "00001.jpg","", "", "",""));
	  CataloguesData.IMG_DESCRIPTIONS.add(new CataloguesData.Data("", "00002.jpg","", "", "",""));
	  CataloguesData.IMG_DESCRIPTIONS.add(new CataloguesData.Data("", "00003.jpg","", "", "",""));
	  CataloguesData.IMG_DESCRIPTIONS.add(new CataloguesData.Data("", "00004.jpg","", "", "",""));
	  CataloguesData.IMG_DESCRIPTIONS.add(new CataloguesData.Data("", "00005.jpg","", "", "",""));
  }

  public static final class Data {

    public final String title;
    public final String imageFilename;
    public final String description;
    public final String country;
    public final String city;
    public final String link;

    private Data(String title, String imageFilename, String description, String country,
                 String city, String link) {
      this.title = title;
      this.imageFilename = imageFilename;
      this.description = description;
      this.country = country;
      this.city = city;
      this.link = link;
    }
  }
}
