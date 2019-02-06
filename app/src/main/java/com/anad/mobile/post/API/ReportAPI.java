package com.anad.mobile.post.API;

import android.content.Context;
import android.util.Log;

import com.anad.mobile.post.Models.GeneralReport;
import com.anad.mobile.post.Models.RFID;
import com.anad.mobile.post.Models.ReportCreator;
import com.anad.mobile.post.Models.Rout;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by elias.mohammadi on 96/10/26
 */

public class ReportAPI {
    private static ReportAPI objectReportApi = null;
    private Context context;
    private static final int TIME_OUT = 100000;
    private static final int MAX_RETRIES = 5;
    private static final String TAG = "ReportAPI";
    private static final String API_KEY = Constants.API_KEY;
    private String UserPassBase64;
    private PostSharedPreferences sharedPreferences;

    private ReportAPI(Context context)
    {
        this.sharedPreferences = new PostSharedPreferences(context);
        this.context = context;
       // this.UserPassBase64 = Util.EncryptUsernamePassword(sharedPreferences.getPrefUserName(),sharedPreferences.getPrefPassword());
        this.UserPassBase64 = sharedPreferences.getEncode();

    }
    public static ReportAPI getInstance(Context context)
    {

        if(objectReportApi == null)
            objectReportApi = new ReportAPI(context);
        return objectReportApi;
    }


    public void getReport(final OnReportResponseBack onReportResponseBack,String url, GeneralReport generalReport)
    {
        Gson gson = new  GsonBuilder().serializeNulls().create();
        final String requestBody = gson.toJson(generalReport);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
                onReportResponseBack.OnResponseBack(setReportCreator(response));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ",error );
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                try{
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                    Log.e(TAG, "getBody: ",e);
                    VolleyLog.wtf("Unsupported Encoding while trying to get bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("API_KEY",API_KEY);
                headers.put("Authorization",UserPassBase64);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);


    }
    public void getReportRFID(final OnRFIDResponseBack onReportResponseBack,String url, GeneralReport generalReport)
    {
        Gson gson = new  GsonBuilder().serializeNulls().create();
        final String requestBody = gson.toJson(generalReport);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
                onReportResponseBack.OnResponseBack(setReportRFID(response));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ",error );
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                try{
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                    Log.e(TAG, "getBody: ",e);
                    VolleyLog.wtf("Unsupported Encoding while trying to get bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("API_KEY",API_KEY);
                headers.put("Authorization",UserPassBase64);
                return  headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);


    }



    public void getRoute(final OnRoutBackResponse onRoutBackResponse, String url, String startDate, String carId)
    {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("startDate",startDate);
            jsonBody.put("carId",carId);

            final String requestBody = jsonBody.toString();
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "onResponse: "+ response);
                    onRoutBackResponse.OnResponseBack(setRout(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "onErrorResponse: ",error );

                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json;charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        VolleyLog.wtf("Unsupported Encoding while trying to get bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<>();
                    headers.put("API_KEY",API_KEY);
                    headers.put("Authorization",UserPassBase64);
                    return  headers;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,
                    MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private List<ReportCreator> setReportCreator(String response)
    {

        Gson gson = new GsonBuilder().serializeNulls().create();
        Type reportCreator = new TypeToken<ArrayList<ReportCreator>>(){}.getType();
        List<ReportCreator> rp = gson.fromJson(response,reportCreator);
        return rp;

    }
    private List<RFID> setReportRFID(String response)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Type reportCreator = new TypeToken<ArrayList<RFID>>(){}.getType();
        List<RFID> rfid = gson.fromJson(response,reportCreator);
        return rfid;

    }

    private List<Rout> setRout(String response)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Type routeType = new TypeToken<ArrayList<Rout>>(){}.getType();
        return gson.fromJson(response,routeType);
    }

    public interface OnReportResponseBack
    {
        void OnResponseBack(List<ReportCreator> reportCreatorList);
    }
    public interface OnRFIDResponseBack
    {
        void OnResponseBack(List<RFID> reportCreatorList);
    }

    public interface OnRoutBackResponse
    {
        void OnResponseBack(List<Rout> routBack);
    }



}
