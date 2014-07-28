/**
 * 
 */
package com.flipchase.android.network;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.flipchase.android.listener.IRequest;
import com.flipchase.android.listener.IRequestCompletionListener;
import com.flipchase.android.model.ServiceResponse;
import com.flipchase.android.network.volley.AuthFailureError;
import com.flipchase.android.network.volley.NetworkResponse;
import com.flipchase.android.network.volley.ParseError;
import com.flipchase.android.network.volley.Request;
import com.flipchase.android.network.volley.Response;
import com.flipchase.android.network.volley.VolleyError;
import com.flipchase.android.network.volley.VolleyLog;
import com.flipchase.android.network.volley.toolbox.HttpHeaderParser;
import com.flipchase.android.parser.IParser;

/**
 * @author m.farhan
 *
 */
public class VolleyGenericRequest extends Request<Object> implements IRequest {

    public interface ContentType {

        int PLAIN_TEXT = 0;
        int FORM_ENCODED_DATA = 1;
        int JSON = 2;
    }

    /**
     * Charset for request.
     */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private Map<String, String> mParams;
    private int mEventType;
    protected Response.Listener mListener;
    protected Response.ErrorListener mErrorListener;
    private String mPostData;
    protected IRequestCompletionListener mRequestCompletionListener;
    protected int mContentType;
    protected Context mContext;
    protected IParser mParser;
   
    private Object mRequestData = null;

    /**
     * Creates a new request with the given method.
     *
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public VolleyGenericRequest(int contentType, String url, String postData, Response.Listener<String> listener,
                                Response.ErrorListener errorListener, Context ctx) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mContentType = contentType;
        mPostData = postData;
        mContext = ctx.getApplicationContext();

    }

    public VolleyGenericRequest(int contentType, String url, HashMap<String, String> paramsMap, Response.Listener<String> listener,
                                Response.ErrorListener errorListener, Context ctx) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mContentType = contentType;
        mParams = paramsMap;
        mContext = ctx.getApplicationContext();

    }

    /**
     * Creates a new request with the given method.
     *
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public VolleyGenericRequest(String url, Response.Listener<String> listener,
                                Response.ErrorListener errorListener, Context ctx) {
        super(Method.GET, url, errorListener);
        mListener = listener;
        mContext = ctx.getApplicationContext();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
       // headers.put("device", "android");
        
        return headers;
    }

    /**
     * @deprecated Use {@link #getBodyContentType()}.
     */
    @Override
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    /**
     * @deprecated Use {@link #getBody()}.
     */
    @Override
    public byte[] getPostBody() throws AuthFailureError {
        return getBody();
    }

    @Override
    public String getBodyContentType() {
        switch (mContentType) {
            case ContentType.JSON:
                return PROTOCOL_CONTENT_TYPE;
            default:
                return super.getBodyContentType();
        }

    }

    @Override
    public byte[] getBody() throws AuthFailureError {


        try {
            switch (mContentType) {
                case ContentType.JSON: {
                    return null == mPostData ? null : mPostData.getBytes(PROTOCOL_CHARSET);
                }
                case ContentType.PLAIN_TEXT:
                    return null == mPostData ? null : mPostData.getBytes();
                case ContentType.FORM_ENCODED_DATA:
                    return super.getBody();
            }
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mPostData, PROTOCOL_CHARSET);

        }
        return null;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<Object> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.d("VolleyGenericRequest", jsonString);
            ServiceResponse serviceResponse = getParser().parseData(mEventType, jsonString);
           // serviceResponse.setRequestData(getRequestData());
            serviceResponse.setEventType(mEventType);
            Response<Object> resp = Response.success((Object) (serviceResponse), HttpHeaderParser.parseCacheHeaders(response));
            return resp;
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override
    protected void deliverResponse(Object response) {
        if (mRequestCompletionListener != null)
            mRequestCompletionListener.onRequestProcessed(true, this);
        mListener.onResponse(response);
       
    }

    @Override
    public void deliverError(VolleyError error) {
        if (mRequestCompletionListener != null)
            mRequestCompletionListener.onRequestProcessed(false, this);

        JVolleyError jVolleyError = new JVolleyError(error);
        jVolleyError.setEventType(mEventType);
        super.deliverError(jVolleyError);

    }

    @Override
    public void cancel() {
        if (mRequestCompletionListener != null)
            mRequestCompletionListener.onRequestProcessed(false, this);
        super.cancel();
    }

    @Override
    public IParser getParser() {
        return mParser;
    }

    @Override
    public void setParser(IParser parser) {
        mParser = parser;
    }

    public boolean updateListeners(Object newListener) {
        boolean isUpdated = false;
        if (mErrorListener != null && mErrorListener.getClass().getName().equals(newListener.getClass().getName())) {
            mErrorListener = (Response.ErrorListener) newListener;
            isUpdated = true;

        }

        if (mListener != null && mListener.getClass().getName().equals(newListener.getClass().getName())) {
            mListener = (Response.Listener) newListener;
            isUpdated = true;


        }
        return isUpdated;
    }

    public void setOnCompletedListener(IRequestCompletionListener listener) {
        mRequestCompletionListener = listener;
    }


    public int getEventType() {
        return mEventType;
    }

    public void setEventType(int eventType) {
        this.mEventType = eventType;
    }

    public Object getRequestData() {
        return mRequestData;
    }

    public void setRequestData(Object requestData) {
        this.mRequestData = requestData;
    }
    
}

