package com.anad.mobile.post.API;


import android.content.Context;
import android.util.Log;

import com.anad.mobile.post.Models.DashboardModels.BaseDashboardModel;
import com.anad.mobile.post.Models.DashboardModels.FormDashboardModel;
import com.anad.mobile.post.Models.DashboardModels.IDashboardModel;
import com.anad.mobile.post.Models.DashboardModels.SpeedDashboardModel;
import com.anad.mobile.post.Models.DashboardModels.YearDashboardModel;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardApi {


    private Context context;
    private String userPassBase64;


    private static final  int TIME_OUT = 100000;
    private static final int MAX_RETRIES = 10;

    private static DashboardApi dashboardApiObject = null;
    private Gson gson = new Gson();

    private static final String TAG = "DashboardApi";
    private DashboardApi(Context context) {
        this.context = context;
        PostSharedPreferences preferences = new PostSharedPreferences(context);
        this.userPassBase64 = preferences.getEncode();
    }
    public static DashboardApi getInstance(Context context) {
        if (dashboardApiObject == null) {
            dashboardApiObject = new DashboardApi(context);
        }
        return dashboardApiObject;
    }


    /**
     * this method use for 3 type of dashboard report now:
     * 1- getAllDeviceToday
     * 2-getAllFormToday
     * 3-getAllRouteInDay
     */
    public void getCountableDashboardReport(String url, final onIntegerCallBack onIntegerCallBack) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                onIntegerCallBack.onResponse(Integer.parseInt(response));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: " + error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return getContentType();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeader();
            }
        };
        request.setRetryPolicy(getRetryPolicy());
        setQueue(request);
    }

    /**
     * use for get all org devices:offline and online
     * 1-getCountOfOrgDevice
     * 2-getCountOfOnlineDevice
     */
    public void getBaseDashboardModelReport(String url, final onBaseDashboardModelCallBack onBaseDashboardModelCallBack) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onBaseDashboardModelCallBack.onResponse(getBaseDashboardModelFromResponse(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return getContentType();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeader();
            }
        };
        request.setRetryPolicy(getRetryPolicy());
        setQueue(request);

    }

    /**
     * for webservice with urls:
     * 1-getCountOfOpenDoor
     * 2-getCountOfRahFormRoute
     * 3-getHighSpeedOfRoute
     */

    public void getFormDashboardModelReport(String url, final onFormDashboardModelCallBack onFormDashboardModelCallBack) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onFormDashboardModelCallBack.onResponse(getFormDashboardModelFromResponse(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return getContentType();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeader();
            }
        };
        request.setRetryPolicy(getRetryPolicy());
        setQueue(request);
    }

    /**
     *
     * for webService with these urls:
     * 1-getHighSpeedOfOrg
     * 2-getSumOfOrgLen
     *
     * */

    public void getSpeedDashboardModelReport(String url, final onSpeedDashboardModelCallBack onSpeedDashboardModelCallBack) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onSpeedDashboardModelCallBack.onResponse(getSpeedDashboardModelFromResponse(response));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return getContentType();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeader();
            }
        };

        request.setRetryPolicy(getRetryPolicy());
        setQueue(request);
    }

/***
 *
 * for webServices with these urls:
 * 1- getCountOfRahForm
 * 2- getCountOfRFIDForm
 *
 */

    public void getYearDashboardModelReport(String url, final onYearDashboardModelCallBack onYearDashboardModelCallBack) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                onYearDashboardModelCallBack.onResponse(getYearDashboardModelFromResponse(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.toString());
            }

        }) {
            @Override
            public String getBodyContentType() {
                return getContentType();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeader();
            }
        };
        request.setRetryPolicy(getRetryPolicy());
        setQueue(request);
    }


    private String getContentType() {
        return "application/json;charset:utf-8";
    }

    private void setQueue(StringRequest request) {
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private Map<String, String> setHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.LABEL_API_KEY, Constants.API_KEY);
        headers.put(Constants.AUTHORIZATION, userPassBase64);
        return headers;
    }

    private DefaultRetryPolicy getRetryPolicy() {
        return new DefaultRetryPolicy(TIME_OUT, MAX_RETRIES, 2f);
    }


    private List<YearDashboardModel> getYearDashboardModelFromResponse(String response) {
        Type t = new TypeToken<ArrayList<YearDashboardModel>>() {
        }.getType();
        return gson.fromJson(response, t);
    }

    private List<BaseDashboardModel> getBaseDashboardModelFromResponse(String response) {
        Type t = new TypeToken<ArrayList<BaseDashboardModel>>() {
        }.getType();
        return new Gson().fromJson(response, t);
    }

    private List<FormDashboardModel> getFormDashboardModelFromResponse(String response) {
        Type t = new TypeToken<ArrayList<FormDashboardModel>>() {
        }.getType();
        return new Gson().fromJson(response, t);
    }

    private List<SpeedDashboardModel> getSpeedDashboardModelFromResponse(String response) {
        Type t = new TypeToken<ArrayList<SpeedDashboardModel>>() {
        }.getType();
        return new Gson().fromJson(response, t);
    }




    public interface onIntegerCallBack {
        void onResponse(int count);
    }
    public interface onYearDashboardModelCallBack {
        void onResponse(List<YearDashboardModel> list);
    }

    public interface onBaseDashboardModelCallBack {
        void onResponse(List<BaseDashboardModel> list);
    }

    public interface onFormDashboardModelCallBack {
        void onResponse(List<FormDashboardModel> list);
    }

    public interface onSpeedDashboardModelCallBack {
        void onResponse(List<SpeedDashboardModel> list);
    }


    public<v extends IDashboardModel> void getDashboardData(String url,final onResponseBack<v> responseBack){
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<v> list = parser(response);
                responseBack.onResponse(list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error);
            }
        }){
            @Override
            public String getBodyContentType() {
                return getContentType();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeader();
            }
        };

        request.setRetryPolicy(getRetryPolicy());
        setQueue(request);

    }
    public interface onResponseBack<v extends IDashboardModel>{
        void onResponse(List<v> list);

    }

    public <v extends IDashboardModel> List<v> parser(String response){
        Type t = new TypeToken<ArrayList<v>>(){}.getType();
        return new Gson().fromJson(response,t);
    }


}
