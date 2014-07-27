package com.flipchase.android.dummyData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flipchase.android.domain.Catalogue;
import com.flipchase.android.domain.City;
import com.flipchase.android.domain.Location;

public class DummyData {

	public static ArrayList<Catalogue> getDummyCatalogues() {
		ArrayList<Catalogue> catalogueList = new ArrayList<Catalogue>();
		
		/** Clothing */
		Catalogue c1 = new Catalogue();
		c1.setDisplayName("T-shirt");
		c1.setExpiryDate("12/07/2014");
		c1.setPhoto_thumb_path("http://api.androidhive.info/music/images/adele.png");
		
		Catalogue c2 = new Catalogue();
		c2.setDisplayName("Trouser");
		c2.setExpiryDate("13/07/2014");
		c2.setPhoto_thumb_path("http://ww1.hdnux.com/photos/16/23/34/3749644/3/gallery_thumb.jpg");
		

		Catalogue c3 = new Catalogue();
		c3.setDisplayName("Jeans");
		c3.setExpiryDate("14/07/2014");
		c3.setPhoto_thumb_path("http://ww1.hdnux.com/photos/16/23/34/3749644/3/gallery_thumb.jpg");
		

		Catalogue c4 = new Catalogue();
		c4.setDisplayName("Casuals");
		c4.setExpiryDate("15/07/2014");
		c4.setPhoto_thumb_path("http://srv-live-02.lazada.com.my/p/oem-7124-420974-1-gallery.jpg");
		

		Catalogue c5 = new Catalogue();
		c5.setDisplayName("Inners");
		c5.setExpiryDate("15/07/2014");
		c5.setPhoto_thumb_path("http://www.shopforbags.com/wp/wp-content/uploads/2014/02/retail-layout-64x64.jpg");
		
		
		catalogueList.add(c1);
		catalogueList.add(c2);
		catalogueList.add(c3);
		catalogueList.add(c4);
		catalogueList.add(c5);
		
		/** Electronics */
		Catalogue e1 = new Catalogue();
		e1.setDisplayName("T-shirt");
		e1.setExpiryDate("12/08/2014");
		e1.setPhoto_thumb_path("http://www.freeadsonlineuk.com/_mm/_d/_ext2/40631/ss_leading%20multichannel%20supplier%20in%20the%20electronics%20operating%20in%20over%20ten%20countries01.jpg");
		
		Catalogue e2 = new Catalogue();
		e2.setDisplayName("Trouser");
		e2.setExpiryDate("13/08/2014");
		e2.setPhoto_thumb_path("http://173-254-127-90.unifiedlayer.com/crossbestbuy/wp-content/uploads/images/LG-Electronics-42LA6200-42-Inch-LED-LCD.jpg");
		

		Catalogue e3 = new Catalogue();
		e3.setDisplayName("Jeans");
		e3.setExpiryDate("14/08/2014");
		e3.setPhoto_thumb_path("http://srv-live-03.lazada.com.ph/p/apple-3223-70919-1-gallery.jpg");
		

		Catalogue e4 = new Catalogue();
		e4.setDisplayName("Casuals");
		e4.setExpiryDate("14/08/2014");
		e4.setPhoto_thumb_path("http://www.suxingwholesale.com/bmz_cache/c/ca4ac93e7d9cdcc47866531fe896965c.image.68x68.jpg");
		

		Catalogue e5 = new Catalogue();
		e5.setDisplayName("Inners");
		e5.setExpiryDate("16/08/2014");
		e5.setPhoto_thumb_path("http://srv-live-01.lazada.com.ph/p/apple-4085-56919-1-gallery.jpg");
		

		catalogueList.add(e1);
		catalogueList.add(e2);
		catalogueList.add(e3);
		catalogueList.add(e4);
		catalogueList.add(e5);
		
/** Furniture */
		
		Catalogue f1 = new Catalogue();
		f1.setDisplayName("Sofa");
		f1.setExpiryDate("12/09/2014");
		f1.setPhoto_thumb_path("http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2011/6/8/1307530002099/Homes-New-furniture-006-thumb-8263.jpg");
		
		Catalogue f2 = new Catalogue();
		f2.setDisplayName("Chair");
		f2.setExpiryDate("13/09/2014");
		f2.setPhoto_thumb_path("http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2011/6/8/1307529997347/Homes-New-furniture-004-thumb-2326.jpg");
		

		Catalogue f3 = new Catalogue();
		f3.setDisplayName("Table");
		f3.setExpiryDate("14/09/2014");
		f3.setPhoto_thumb_path("http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2011/4/18/1303138456255/Milan-furniture-fair-004-thumb-6740.jpg");
		

		Catalogue f4 = new Catalogue();
		f4.setDisplayName("Dinning Set");
		f4.setExpiryDate("14/09/2014");
		f4.setPhoto_thumb_path("http://www.adpost.com/classifieds/graphics/furniture.gif");
		

		Catalogue f5 = new Catalogue();
		f5.setDisplayName("Bean Bags");
		f5.setExpiryDate("16/09/2014");
		f5.setPhoto_thumb_path("http://common.thesimplestores.com/img/hash/p/85903/1/68/68/0/d9d9d9/0/white.jpg");
		

		catalogueList.add(f1);
		catalogueList.add(f2);
		catalogueList.add(f3);
		catalogueList.add(f4);
		catalogueList.add(f5);
		
/** Sports */
		
		Catalogue s1 = new Catalogue();
		s1.setDisplayName("Footbal");
		s1.setExpiryDate("12/10/2014");
		s1.setPhoto_thumb_path("http://www.dropshipzone.com.au/media/catalog/product/cache/1/thumbnail/68x68/9df78eab33525d08d6e5fb8d27136e95/f/i/fit-bench-irebh40-02.jpg");
		
		Catalogue s2 = new Catalogue();
		s2.setDisplayName("Cricket Ball");
		s2.setExpiryDate("13/10/2014");
		s2.setPhoto_thumb_path("http://www.dropshipzone.com.au/media/catalog/product/cache/1/thumbnail/68x68/9df78eab33525d08d6e5fb8d27136e95/f/i/fit-e-sq-rack-02.jpg");
		

		Catalogue s3 = new Catalogue();
		s3.setDisplayName("Gym Eqipment");
		s3.setExpiryDate("14/10/2014");
		s3.setPhoto_thumb_path("http://www.dropshipzone.com.au/media/catalog/product/cache/1/thumbnail/68x68/9df78eab33525d08d6e5fb8d27136e95/f/i/fit-e-sq-rack-03.jpg");
		

		Catalogue s4 = new Catalogue();
		s4.setDisplayName("Trade Mill");
		s4.setExpiryDate("14/10/2014");
		s4.setPhoto_thumb_path("http://www.dropshipzone.com.au/media/catalog/product/cache/1/thumbnail/68x68/9df78eab33525d08d6e5fb8d27136e95/f/i/fit-f-sega-gym-st-04.jpg");
		

		Catalogue s5 = new Catalogue();
		s5.setDisplayName("Runnig Kit");
		s5.setExpiryDate("16/10/2014");
		s5.setPhoto_thumb_path("http://www.dropshipzone.com.au/media/catalog/product/cache/1/thumbnail/68x68/9df78eab33525d08d6e5fb8d27136e95/f/i/fit-f-sega-gym-st-00.jpg");
		

		catalogueList.add(s1);
		catalogueList.add(s2);
		catalogueList.add(s3);
		catalogueList.add(s4);
		catalogueList.add(s5);
		
		
/** Super Market */
		
		Catalogue m1 = new Catalogue();
		m1.setDisplayName("Sweet Corn");
		m1.setExpiryDate("12/11/2014");
		m1.setPhoto_thumb_path("https://885fa5ce61295ebf3c84-35b073afd3cf2f7bae35b2b9457774cf.ssl.cf2.rackcdn.com/cache/news/SupermarketGenericDrugDiscounts_micro.jpg");
		
		Catalogue m2 = new Catalogue();
		m2.setDisplayName("Buns");
		m2.setExpiryDate("13/11/2014");
		m2.setPhoto_thumb_path("https://www.indiangrocerybazaar.nl/media/catalog/product/cache/1/thumbnail/68x68/9df78eab33525d08d6e5fb8d27136e95/g/h/ghal0021.jpg");
		

		Catalogue m3 = new Catalogue();
		m3.setDisplayName("Cakes");
		m3.setExpiryDate("14/11/2014");
		m3.setPhoto_thumb_path("https://www.indiangrocerybazaar.nl/media/catalog/product/cache/1/thumbnail/68x68/9df78eab33525d08d6e5fb8d27136e95/4/1/414-424-large.jpg");
		

		Catalogue m4 = new Catalogue();
		m4.setDisplayName("Dairy Products");
		m4.setExpiryDate("14/11/2014");
		m4.setPhoto_thumb_path("https://www.indiangrocerybazaar.nl/media/catalog/product/cache/1/thumbnail/68x68/9df78eab33525d08d6e5fb8d27136e95/t/n/tn_20131127_114146_1.jpg");
		

		Catalogue m5 = new Catalogue();
		m5.setDisplayName("Mustard Oil");
		m5.setExpiryDate("16/11/2014");
		m5.setPhoto_thumb_path("https://www.indiangrocerybazaar.nl/media/catalog/product/cache/1/thumbnail/68x68/9df78eab33525d08d6e5fb8d27136e95/g/h/ghal0014.gif");
		

		catalogueList.add(m1);
		catalogueList.add(m2);
		catalogueList.add(m3);
		catalogueList.add(m4);
		catalogueList.add(m5);
		
		
/** Bags */
		
		Catalogue b1 = new Catalogue();
		b1.setDisplayName("School Bags");
		b1.setExpiryDate("12/12/2014");
		b1.setPhoto_thumb_path("http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2013/9/4/1378308168864/wish-list-burgandy-bags-006-thumb-5027.jpg");
		
		Catalogue b2 = new Catalogue();
		b2.setDisplayName("Travel Bags");
		b2.setExpiryDate("13/12/2014");
		b2.setPhoto_thumb_path("http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2014/6/9/1402323048215/Conversation-bags-the-wis-003-thumb-6090.jpg");
		

		Catalogue b3 = new Catalogue();
		b3.setDisplayName("Tracking Bags");
		b3.setExpiryDate("14/12/2014");
		b3.setPhoto_thumb_path("http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2013/7/30/1375195893904/Mesh-beach-bag-006-thumb-5952.jpg");
		

		Catalogue b4 = new Catalogue();
		b4.setDisplayName("Camera Bag");
		b4.setExpiryDate("14/12/2014");
		b4.setPhoto_thumb_path("http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2013/2/27/1361963854902/Best-bags-the-wish-list-005-thumb-3071.jpg");
		

		Catalogue b5 = new Catalogue();
		b5.setDisplayName("Mobile Cover");
		b5.setExpiryDate("16/12/2014");
		b5.setPhoto_thumb_path("http://srv-live-01.lazada.com.ph/p/bags-in-the-city-5680-73018-1-gallery.jpg");
		

		catalogueList.add(b1);
		catalogueList.add(b2);
		catalogueList.add(b3);
		catalogueList.add(b4);
		catalogueList.add(b5);
		
		return catalogueList;
	}
	
	public static City getCityFromId(String id) {
		City city1 = new City();
		if(id.equalsIgnoreCase("1")) {
			city1.setDisplayName("Delhi");
			city1.setId("1");
			city1.setName("Delhi");
			city1.setLatitude(28.644);
			city1.setLongitude(77.2167);
		} else {
			city1.setDisplayName("Mumbai");
			city1.setId("2");
			city1.setName("Mumbai");
			city1.setLatitude(19.075);
			city1.setLongitude(72.877);
		}
		return city1;
	}
	
	public static Location getLocationFromId(String id) {
		Location cityLocation1 = new Location();
		cityLocation1.setId("1");
		cityLocation1.setDisplayName("Kundli");
		cityLocation1.setName("Kundli");
		cityLocation1.setCity(1L);
		
		Location cityLocation2 = new Location();
		cityLocation2.setId("2");
		cityLocation2.setDisplayName("");
		cityLocation2.setName("Sector 3, Rohini");
		cityLocation2.setCity(1L);
		
		if(id.equalsIgnoreCase("1")) {
			return cityLocation1;
		} else {
			return cityLocation2;
		}
	}
	
	public static List<City> getCities() {
		List<City> cities = new ArrayList<City>();
		City city1 = new City();
		city1.setDisplayName("Delhi");
		city1.setId("1");
		city1.setName("Delhi");
		city1.setLatitude(28.644);
		city1.setLongitude(77.2167);
		
		City city2 = new City();
		city2.setDisplayName("Mumbai");
		city2.setId("2");
		city2.setName("Mumbai");
		city1.setLatitude(19.075);
		city1.setLongitude(72.877);
		
		cities.add(city1);
		cities.add(city2);
		
		return cities;
	}
	
	public static List<Location> getLocationsForCity(City city) {
		return getDummyCityWiseLocations().get(Long.parseLong(city.getId()));
	}
	
	public static Map<Long, List<Location>> getDummyCityWiseLocations() {
		
		Map<Long, List<Location>> cityWiseLocations = new HashMap<Long, List<Location>>();
		
		List<Location> city1Locs = new ArrayList<Location>();
		
		Location cityLocation1 = new Location();
		cityLocation1.setId("1");
		cityLocation1.setDisplayName("Kundli");
		cityLocation1.setName("Kundli");
		cityLocation1.setCity(1L);
		
		Location cityLocation2 = new Location();
		cityLocation2.setId("2");
		cityLocation2.setDisplayName("");
		cityLocation2.setName("Sector 3, Rohini");
		cityLocation2.setCity(1L);
		
		Location cityLocation3 = new Location();
		cityLocation3.setId("3");
		cityLocation3.setDisplayName("");
		cityLocation3.setName("Model Town");
		cityLocation3.setCity(1L);
		
		city1Locs.add(cityLocation1);
		city1Locs.add(cityLocation2);
		city1Locs.add(cityLocation3);
		cityWiseLocations.put(1L, city1Locs);
		
		List<Location> city2Locs = new ArrayList<Location>();
		Location cityLocation4 = new Location();
		cityLocation4.setId("4");
		cityLocation4.setDisplayName("Nariman Point");
		cityLocation4.setName("Nariman Point");
		cityLocation4.setCity(2L);
		
		Location cityLocation5 = new Location();
		cityLocation5.setId("5");
		cityLocation5.setDisplayName("");
		cityLocation5.setName("Colaba");
		cityLocation5.setCity(2L);
		
		Location cityLocation6 = new Location();
		cityLocation6.setId("6");
		cityLocation6.setDisplayName("");
		cityLocation6.setName("Borivali");
		cityLocation6.setCity(2L);
		
		city2Locs.add(cityLocation4);
		city2Locs.add(cityLocation5);
		city2Locs.add(cityLocation6);
		cityWiseLocations.put(2L, city2Locs);
		
		return cityWiseLocations;
	}
}
