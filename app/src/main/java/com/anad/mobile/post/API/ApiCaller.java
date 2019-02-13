package com.anad.mobile.post.API;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.anad.mobile.post.API.Retrofit.ApiClient;
import com.anad.mobile.post.AccountManager.model.PartyAssign;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class ApiCaller extends Api implements ApiManager<ApiCaller> {
    private Map<String, String> params;
    private String cookies;
    private JsonRequest request;
    private Context context;
    private String header;
    private String bodyRequest;
    private static ApiCaller object = null;

    private static final String TAG = "ApiCaller";

    private ApiCaller(Context context) {
        this.context = context;
    }

    public static ApiCaller getInstance(Context context) {
        if (object == null)
            object = new ApiCaller(context);
        return object;
    }


    @Override
    public ApiCaller withUrl(@NonNull String url) {
        this.url = url;
        return this;
    }

    @Override
    public ApiCaller withMethod(int method) {
        this.method = method;
        return this;
    }

    @Override
    public ApiCaller setRetryPolicy(RetryPolicy retryPolicy) {
        if (retryPolicy != null) {
            this.retryPolicy = retryPolicy;
        }
        return this;
    }

    @Override
    public ApiCaller withCookies(String cookies) {
        this.cookies = cookies;
        return this;
    }

    @Override
    public void CallJsonObject(Response.Listener<JSONObject> response, Response.ErrorListener errorListener) {
        request = new JsonObjectRequest(method, url, null, response, errorListener) {

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
                params.put("Cookie", cookies);
                return params;
            }
        };
        AddRequestToQueue();
    }

    @Override
    public void CallJsonArray(Response.Listener<JSONArray> response, Response.ErrorListener errorListener) {
        request = new JsonArrayRequest(method, url, null, response, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return params;
            }
        };
        AddRequestToQueue();
    }

    @Override
    public ApiCaller withHeaders(Map<String, String> headers) {
        this.params = headers;
        return this;
    }

    @Override
    public ApiCaller withBody(String body) {
        this.bodyRequest = body;
        return this;
    }

    private void AddRequestToQueue() {
        request.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }


    public ApiCaller withHeader(String header) {
        this.header = header;
        return this;
    }


    public void callWithOkhttp() {

        Map<String,String> headMap = new HashMap<>();


        Headers headers = Headers.of(headMap);


        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://post.anadgroup.ir/main/partyassign/getbypartyid?partyId=4")
                .headers(headers)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }


}
