package com.anad.mobile.post.ReportManager.api;


import android.content.Context;
import android.support.annotation.NonNull;

import com.anad.mobile.post.API.Api;
import com.anad.mobile.post.API.ApiManager;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ReportApiCaller extends Api implements  ApiManager<ReportApiCaller> {

    private Map<String,String> params;
    private String cookies;
    private JsonRequest request;
    private Context context;
    private String bodyRequest;
    private static ReportApiCaller reportApiCaller = null;

    private ReportApiCaller(Context context) {
        this.context = context;
    }

    public static ReportApiCaller getInstance(Context context) {
        if (reportApiCaller == null) {
            reportApiCaller = new ReportApiCaller(context);
        }
        return reportApiCaller;
    }


    @Override
    public ReportApiCaller withUrl(@NonNull String url) {
        this.url = url;
        return this;
    }

    @Override
    public ReportApiCaller withMethod(int method) {
        this.method = method;
        return this;
    }

    @Override
    public ReportApiCaller setRetryPolicy(RetryPolicy retryPolicy) {
        if (retryPolicy != null) {
            this.retryPolicy = retryPolicy;
        }
        return this;
    }

    @Override
    public ReportApiCaller withCookies(String cookies) {
        this.cookies = cookies;
        return this;
    }


    @Override
    public void CallJsonObject(Response.Listener<JSONObject> response,Response.ErrorListener errorListener) {
        request = new JsonObjectRequest(method, url, null,response,errorListener){
            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return bodyRequest == null ? null : bodyRequest.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                params.put("Cookie",cookies);
                return params;
            }
        };
        AddRequestToQueue();

    }

    @Override
    public void CallJsonArray(Response.Listener<JSONArray> response,Response.ErrorListener errorListener) {
        request = new JsonArrayRequest(method, url, null, response,errorListener){

            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return bodyRequest == null ? null : bodyRequest.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                params.put("Cookie",cookies);
                return params;
            }
        };
        AddRequestToQueue();
    }

    @Override
    public ReportApiCaller withHeaders(Map<String, String> headers) {
        this.params = headers;
        return this;
    }

    @Override
    public ReportApiCaller withBody(String body) {
         bodyRequest = body;
        return this;
    }

    private void AddRequestToQueue() {
        request.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }



}
