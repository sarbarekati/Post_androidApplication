package com.anad.mobile.post.Models;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 *
 * CREATE BY ELIAS MOHAMMADY 96.09.28
 * */

public class BooleanRequest extends Request<Boolean> {
    private final Response.Listener<Boolean> listener;
    private final Response.ErrorListener errorListener;
    private final String PROTOCOL_CHARSET = "utf-8";
    private final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    public BooleanRequest(int method, String url,Response.Listener<Boolean> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
        this.errorListener = errorListener;
    }



    @Override
    protected Response<Boolean> parseNetworkResponse(NetworkResponse response) {
        Boolean parsed;
        try {
            parsed = Boolean.valueOf(new String(response.data,HttpHeaderParser.parseCharset(response.headers)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            parsed = Boolean.valueOf(new String(response.data));
        }
        return Response.success(parsed,HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(Boolean response) {
        listener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        errorListener.onErrorResponse(error);
    }

}
