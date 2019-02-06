package com.anad.mobile.post.AccountManager;

import android.content.Context;
import android.util.Log;

import com.anad.mobile.post.Utils.Constants;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginApi {
    private static final String TAG = "LoginApi";
    private Context context;
    private static LoginApi loginApi = null;
    private OnLoginResponse loginResponse;

    private LoginApi(Context context) {
        this.context = context;
    }

    public static LoginApi getInstance(Context context) {
        if (loginApi == null) {
            loginApi = new LoginApi(context);
        }
        return loginApi;
    }

    public void setOnLoginResponse(OnLoginResponse onLoginResponse) {
        loginResponse = onLoginResponse;
    }


    public void login(String username, String password) {
        String url = loginUrl(username, password);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    loginResponse.setCookie(parsHeader(response.getJSONObject("headers")));
                    loginResponse.onSuccess(parseResponse(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                String jsonString;
                try {
                    jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    JSONObject jsonResponse = new JSONObject(jsonString);
                    jsonResponse.put("headers", new JSONObject(response.headers));
                    return Response.success(jsonResponse,
                            HttpHeaderParser.parseCacheHeaders(response));

                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(40000, 2, 2));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private String loginUrl(String username, String password) {
        return Constants.LOGIN_POST_FW + "username=" + username + "&password=" + password;
    }

    private String parsHeader(JSONObject header) throws JSONException {
        return header.getString("Set-Cookie");
    }

    private LoginResponse parseResponse(JSONObject response){
        Gson gson = new Gson();
        return gson.fromJson(response.toString(),LoginResponse.class);
    }


}
