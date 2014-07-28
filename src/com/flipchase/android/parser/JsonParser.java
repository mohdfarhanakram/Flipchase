package com.flipchase.android.parser;

import org.json.JSONObject;

import com.flipchase.android.domain.Catalogue;
//import org.codehaus.jackson.util.TokenBuffer.Segment;

public class JsonParser {

   // private static final Gson gson = new Gson();

    public static Catalogue parseCatalogue(JSONObject jsonObjectData) {
    	Catalogue catalogue = new Catalogue();
        return catalogue;
    }


    public static Object parseOrderSummary(JSONObject jsonResponse) {
        Object summary = new Object();
        return summary;
    }

}