package com.anad.mobile.post.API;

import android.content.Context;
import android.util.Log;

import com.anad.mobile.post.Models.AlarmSetting;
import com.anad.mobile.post.Models.Alarms;
import com.anad.mobile.post.Models.User;
import com.anad.mobile.post.Models.UserAlarmDefine;
import com.anad.mobile.post.Models.UserAlarmInfo;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by elias.mohammadi on 2018/02/17
 */

public class AlarmApi {
    private int TIME_OUT = 100000;
    private int MAX_RETRIES = 10;

    private Context context;
    private static final String TAG = "AlarmApi";
    private static AlarmApi apiObject = null;
    private String userPassBase64;


    private AlarmApi(Context context) {
        PostSharedPreferences sharedPreferences;
        this.context = context;
        sharedPreferences = new PostSharedPreferences(context);
       // this.userPassBase64 = Util.EncryptUsernamePassword(sharedPreferences.getPrefUserName(),sharedPreferences.getPrefPassword());
        this.userPassBase64 = sharedPreferences.getEncode();

    }

    public static AlarmApi getInstance(Context context) {
        if (apiObject == null)
            apiObject = new AlarmApi(context);
        return apiObject;
    }

    public void getUserAlarm(final OnAlarmsCallBack onAlarmsCallBack, String url)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onAlarmsCallBack.OnResponse(setAlarms(response));
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY,Constants.API_KEY);
                headers.put(Constants.AUTHORIZATION,userPassBase64);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }



    public void getnew(OnResponseWebService onResponseWebService)
    {
        onResponseWebService.OnResponser(new User());
    }


    public void getAlarmSetting(final OnAlarmDefineBack onAlarmDefineBack, String url)
    {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: "+response);
                onAlarmDefineBack.OnResonse(setUserAlarmDefine(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public void changeAlarmSetting(String url, AlarmSetting alarmSetting)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        final String requestBody = gson.toJson(alarmSetting);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
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
                    Log.e(TAG, "getBody: ", e);
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY,Constants.API_KEY);
                headers.put(Constants.AUTHORIZATION,userPassBase64);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void sendUserAlarmInfo(String url, final UserAlarmInfo userAlarmInfo)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        final String requsetBody = gson.toJson(userAlarmInfo);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requsetBody == null ? null : requsetBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e(TAG, "getBody: ",e);
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY,Constants.API_KEY);
                headers.put(Constants.AUTHORIZATION,userPassBase64);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private List<Alarms> setAlarms(String response)
    {
        List<Alarms> list = new ArrayList<>();
        Gson gson = new Gson();
        Type t = new TypeToken<ArrayList<Alarms>>(){}.getType();
        list = gson.fromJson(response,t);
        return list;
    }


    private List<UserAlarmDefine> setUserAlarmDefine(String response)
    {
        Gson gson = new Gson();
        List<UserAlarmDefine> list = new ArrayList<>();
        Type t = new TypeToken<ArrayList<UserAlarmDefine>>(){}.getType();
        list = gson.fromJson(response,t);
        return list;
    }

    public interface OnAlarmsCallBack
    {
        void OnResponse(List<Alarms> list);
    }

    public interface  OnAlarmDefineBack
    {
        void OnResonse(List<UserAlarmDefine> list);
    }

    public interface OnResponseWebService<T>
    {
        void OnResponser(T o);
    }
}
