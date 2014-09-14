package com.flipchase.android.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.flipchase.android.constants.FlipchaseApi;
import com.flipchase.android.constants.JsonKey;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.response.common.FlipChaseBaseModel;

public class BaseParser implements IParser {

    public ServiceResponse parseData(int eventType, String data) {
        ServiceResponse response = null;
        try {
            response = handleJsonResponse(eventType, new JSONObject(data));
        } catch (Exception e) {
            response = new ServiceResponse();
            response.setErrorCode(ServiceResponse.EXCEPTION);
        }
        return response;
    }

    protected FlipChaseBaseModel parseBaseData(JSONObject jsonObject)
            throws JSONException {
    	FlipChaseBaseModel baseModel = new FlipChaseBaseModel();
        baseModel.setSuccess(jsonObject.getBoolean(JsonKey.SUCCESS));
        return baseModel;
    }

    protected ServiceResponse handleJsonResponse(int eventType, JSONObject jsonObject) {
        ServiceResponse response = new ServiceResponse();
        try {
            FlipChaseBaseModel responseModel = parseBaseData(jsonObject);
            response.setFlipChaseBaseModel(responseModel);
            response.setEventType(eventType);
            response.setJsonResponse(jsonObject);
            //       Log.d("response is ", jsonObject.toString());
            if (responseModel.isSuccess()) {
                response.setErrorCode(ServiceResponse.SUCCESS);
                parseJsonData(response);
            } else {
                handleError(response);
            }
        } catch (Exception e) {
            response.setErrorCode(ServiceResponse.EXCEPTION);
            e.printStackTrace();
        }
        return response;
    }

    protected void handleError(ServiceResponse response) throws JSONException {
        JSONObject jsonObject = response.getJsonResponse();
        //DK: For now we will not handle any error response
        /*
        if (Utils.isJsonObject(jsonObject, JsonKey.MESSAGE)) {
            if (jsonObject.getJSONObject(JsonKey.MESSAGE).has(JsonKey.VALIDATION)) {
                response.setErrorCode(ServiceResponse.VALIDATION_ERROR);
            } else if (jsonObject.getJSONObject(JsonKey.MESSAGE).has(JsonKey.ERROR)) {
                response.setErrorCode(ServiceResponse.MESSAGE_ERROR);
                ArrayList<String> errorMessages = new ArrayList<String>();
                if (Utils.isJsonObject(jsonObject.getJSONObject(JsonKey.MESSAGE), "error")) {
                    Iterator<String> keys = jsonObject.getJSONObject(JsonKey.MESSAGE).getJSONObject("error").keys();
                    for (int i = 0; keys.hasNext(); i++) {
                        Object json = jsonObject.getJSONObject(JsonKey.MESSAGE).getJSONObject("error").get(keys.next());
                        if (json instanceof JSONArray)
                            errorMessages.add(i, ((JSONArray) json).get(0).toString());
                        else
                            errorMessages.add(i, json.toString());

                    }
                } else {
                    for (int i = 0; i < jsonObject.getJSONObject(JsonKey.MESSAGE).getJSONArray("error").length(); i++) {
                        errorMessages.add(i, jsonObject.getJSONObject(JsonKey.MESSAGE).getJSONArray("error").getString(i));
                    }
                }
                response.setErrorMessages(errorMessages);
            }
        }
        if (response.getErrorCode() == ServiceResponse.VALIDATION_ERROR) {


        } else if (response.getErrorCode() == ServiceResponse.MESSAGE_ERROR) {

            switch (response.getEventType()) {
                case ApiType.API_APPLY_VOUCHER:
                case ApiType.API_CHECK_PIN_CODE:
                case ApiType.API_LOGIN:
                case ApiType.API_SIGN_UP:

                    String error = response.getJsonResponse().getJSONObject(JsonKey.MESSAGE).getJSONArray(JsonKey.ERROR).getString(0);
                    response.setErrorText(error);
                    break;

                default:
                    break;
            }

        }
        */
    }

    protected void parseJsonData(ServiceResponse response) throws JSONException {
    	JSONObject jsonObjectData = response.getJsonResponse();
        switch (response.getEventType()) {
            case FlipchaseApi.INIT_REQUEST:
                break;
            case FlipchaseApi.GET_ALL_CITIES:
                response.setResponseObject(FlipChaseJsonParser.parseCities(jsonObjectData));
                break;
            case FlipchaseApi.GET_ALL_LOCATIONS:
                response.setResponseObject(FlipChaseJsonParser.parseLocations(jsonObjectData));
                break;
            case FlipchaseApi.GET_ALL_CITIES_AND_LOCATIONS:
                response.setResponseObject(FlipChaseJsonParser.parseCityAndLocations(jsonObjectData));
                break;
            case FlipchaseApi.GET_CITIES_FOR_LOCATIONS:
                //response.setResponseObject(FlipChaseJsonParser.parseCities(jsonObjectData));
                break;
            case FlipchaseApi.GET_ALL_RETAILERS:
                response.setResponseObject(FlipChaseJsonParser.parseRetailers(jsonObjectData));
                break;
            case FlipchaseApi.GET_STORES_FOR_RETAILER:
                response.setResponseObject(FlipChaseJsonParser.parseStoresForRetailers(jsonObjectData));
                break;
            case FlipchaseApi.GET_LATEST_CATALOGUES:
                response.setResponseObject(FlipChaseJsonParser.parseLatestCatalogues(jsonObjectData));
                break;
            case FlipchaseApi.GET_CATALOGUE_PAGES_FOR_CATALOGUE:
                response.setResponseObject(FlipChaseJsonParser.parseCataloguePagesForCatalogue(jsonObjectData));
                break;
            case FlipchaseApi.SAVE_USER_CITY_AND_LOCATION:
            	// Nothing to do for now, but the service sends the updated user location object
            	break;
            case FlipchaseApi.GET_MOBILE_ALERTS:
            	response.setResponseObject(FlipChaseJsonParser.parseAlerts(jsonObjectData));
            	break;
            default:
                break;
        }

    }


}
