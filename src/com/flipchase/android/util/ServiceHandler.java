package com.flipchase.android.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ServiceHandler {

	/** static will make a single session **/
	private static DefaultHttpClient httpClient = new DefaultHttpClient();
	
	static String response = null;
	public final static int GET = 1;
	public final static int POST = 2;

	public ServiceHandler() {
	}

	/**
	 * Making service call
	 * 
	 * @url - url to make request
	 * @method - http request method
	 * */
	public String makeServiceCall(String url, int method) {
		return this.makeServiceCall(url, method, null, null);
	}

	public String makeServiceCall(String url, int method, String json) {
		return this.makeServiceCall(url, method, json, null);
	}
	
	//http://lukencode.com/2010/04/27/calling-web-services-in-android-using-httpclient/
	//TODO: ONLY PATH PARAMS HANDLELED
	private String buildURI(String url, Object... params) {
		UriTemplate uriTemplate = new UriTemplate(url);
		String uri = uriTemplate.getUriTemplate();
		List<String> varNames =  uriTemplate.getVariableNames();
		if(varNames != null && varNames.size() > 0) {
			for(int i=0; i<varNames.size();i++) {
				url = url.replace("{" + varNames.get(i) + "}", params[i].toString());
			}
		}
		return url;
	}
	
	/**
	 * Making service call
	 * 
	 * @url - url to make request
	 * @method - http request method
	 * @params - http request params
	 * */
	public String makeServiceCall(String url, int method,
			String json, Object... params) {
		try {
			if(params != null && params.length > 0) {
				url = buildURI(url, params);
			}
			HttpEntity httpEntity = null;
			HttpResponse httpResponse = null;

			// Checking http request method type
			if (method == POST) {
				HttpPost httpPost = new HttpPost(url);
				StringEntity se = new StringEntity(json);
				httpPost.setEntity(se);
				httpPost.setHeader("Accept", "application/json");
	            httpPost.setHeader("Content-type", "application/json");
				// adding post params
				//if (params != null) {
				//	httpPost.setEntity(new UrlEncodedFormEntity(params));
				//}

				httpResponse = httpClient.execute(httpPost);

			} else if (method == GET) {
				// appending params to url
				//if (params != null) {
				//	String paramString = URLEncodedUtils
				//			.format(params, "utf-8");
				//	url += "?" + paramString;
				//}
				HttpGet httpGet = new HttpGet(url);
				httpResponse = httpClient.execute(httpGet);
			}
			httpEntity = httpResponse.getEntity();
			response = EntityUtils.toString(httpEntity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;

	}
}