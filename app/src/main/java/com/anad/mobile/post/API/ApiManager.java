package com.anad.mobile.post.API;


import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public interface ApiManager<T>{

    T withUrl(@NonNull String url);
    T withMethod(int method);
    T setRetryPolicy(RetryPolicy retryPolicy);
    T withCookies(String cookies);
    void CallJsonObject(Response.Listener<JSONObject> response,Response.ErrorListener errorListener);
    void CallJsonArray(Response.Listener<JSONArray> response, Response.ErrorListener errorListener);
    T withHeaders(Map<String,String> headers);
    T withBody(String body);
}
