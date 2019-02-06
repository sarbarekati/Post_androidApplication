package com.anad.mobile.post.API;

import android.content.Context;
import android.util.Log;

import com.anad.mobile.post.Models.Cars;
import com.anad.mobile.post.Models.DriverModel;
import com.anad.mobile.post.Models.LastPosition;
import com.anad.mobile.post.Models.Org;
import com.anad.mobile.post.Models.OrgInfoModel;
import com.anad.mobile.post.Models.Rah_RT;
import com.anad.mobile.post.Models.SubTree;
import com.anad.mobile.post.Models.UserAccess;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create By ELIAS MOHAMMADY 96.10.11
 */

public class FilterApi {

    private int TIME_OUT = 100000;
    private int MAX_RETRIES = 10;

    private Context context;
    private static final String TAG = "FilterApi";
    private static FilterApi apiObject = null;
    private String userPassBase64;
    private PostSharedPreferences sharedPreferences;

    private FilterApi(Context context) {
        this.context = context;
        this.sharedPreferences = new PostSharedPreferences(context);
        this.userPassBase64 = sharedPreferences.getEncode();

    }

    public static FilterApi getInstance(Context context) {
        if (apiObject == null)
            apiObject = new FilterApi(context);
        return apiObject;
    }
/////////////////////NEW WEB SERVICE ACCESS //////////////////////////////////////////

    public void getUserAccessByUserNameOrOrgId(final OnUserAccessOrgBack OnUserAccessOrgBack, String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
                OnUserAccessOrgBack.OnResponse(getUserOrgJson(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY, Constants.API_KEY);
                headers.put(Constants.AUTHORIZATION, userPassBase64);
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void getUserAllDriver(final OnUserAllDriversBack onUserAllDriversBack, String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
                onUserAllDriversBack.OnResponse(setAllDriver(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY, Constants.API_KEY);
                headers.put(Constants.AUTHORIZATION, userPassBase64);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }


    public void getLastPosWithId(final OnAllDriversBack onAllDriversBack, String URL) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "onResponse: have response");
                onAllDriversBack.OnResponse(setLastPosWithId(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY, Constants.API_KEY);
                headers.put(Constants.AUTHORIZATION, userPassBase64);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private List<LastPosition> setLastPosWithId(JSONArray response) {
        List<LastPosition> lastPositions = new ArrayList<>();
        int length = response.length();
        for (int i = 0; i < length; i++) {
            LastPosition lastPosition = new LastPosition();
            try {
                JSONObject obj = response.getJSONObject(i);
                lastPosition.setID(obj.optInt("ID"));
                lastPosition.setOrg_ID(obj.optInt("Org_ID"));
                lastPosition.setN(obj.optString("N"));
                lastPosition.setE(obj.optString("E"));
                lastPosition.setLDate(obj.optString("LDate"));
                lastPosition.setLTime(obj.optString("LTime"));
                lastPosition.setSpeed(obj.optInt("Speed"));
                lastPosition.setRFID(obj.getBoolean("isRFID"));
                lastPositions.add(lastPosition);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "setLastPosWithId: " + e.getMessage());
            }
        }
        return lastPositions;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////

    public void getUserSubTreeFromWebService(final OnUserSubTree onUserSubTree, String url) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "onResponse: " + response.toString());
                onUserSubTree.OnResponse(getUserSubTree(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void getUserSubTrees(final OnGetUserSubTree onGetUserSubTree, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "onResponse: " + response);
                onGetUserSubTree.onResponseUserSubTree(getUserSubTreeList(response));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY, Constants.API_KEY);
                headers.put(Constants.AUTHORIZATION, userPassBase64);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    public void getUserOrgSubTree(final OnGetUserSubTree onGetUserSubTree, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                onGetUserSubTree.onResponseUserSubTree(setUserByParent(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY, Constants.API_KEY);
                headers.put(Constants.AUTHORIZATION, userPassBase64);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }


    public void getRoute(final OnRouteBack onRouteBack, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onRouteBack.OnResponse(setRoute(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY, Constants.API_KEY);
                headers.put(Constants.AUTHORIZATION, userPassBase64);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }


    public void getDriver(final OnGetUserSubTreeObject onGetUserSubTree, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "onResponse: " + response);
                onGetUserSubTree.onResponseUserSubTree(setDriver(response));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY, Constants.API_KEY);
                headers.put(Constants.AUTHORIZATION, userPassBase64);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }


    public void getLastOneDriverPosition(final OnGetLastPosition onGetLastPosition, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                onGetLastPosition.OnResponse(setLastOneDriverPosition(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.getMessage());
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY, Constants.API_KEY);
                headers.put(Constants.AUTHORIZATION, userPassBase64);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void getLastPosition(final OnGetLastPosition onGetLastPosition, String url) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: " + response);
                onGetLastPosition.OnResponse(getLastCarPosition(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY, Constants.API_KEY);
                headers.put(Constants.AUTHORIZATION, userPassBase64);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    public void getAllDriverLastPos(final OnAllDriversBack onAllDriversBack, String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
                onAllDriversBack.OnResponse(setAllDriverLastPos(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY, Constants.API_KEY);
                headers.put(Constants.AUTHORIZATION, userPassBase64);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private List<LastPosition> setAllDriverLastPos(String response) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Type t = new TypeToken<ArrayList<LastPosition>>() {
        }.getType();
        return gson.fromJson(response, t);

    }


    private List<Cars> setDriver(JSONArray response) {
        List<Cars> cars = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                Cars car = new Cars();
                car.setCarId(obj.getInt("Drv_ID"));
                car.setDriverName(obj.getString("FName"));
                car.setDriverFamilyName(obj.getString("LName"));
                car.setOrg_ID(obj.getInt("Org_ID"));
               /* car.setLastOnlineDate(obj.getString("LDate"));
                car.setLastOnlineTime(obj.getString("LTime"));*/
                cars.add(car);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "setDriver: ", e);
        }
        return cars;
    }

    private List<Rah_RT> setRoute(String response) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Type rah = new TypeToken<ArrayList<Rah_RT>>() {
        }.getType();
        List<Rah_RT> rahList = gson.fromJson(response, rah);

        return rahList;
    }


    private List<DriverModel> setAllDriver(String response) {
        Gson gson = new Gson();
        Type t = new TypeToken<ArrayList<DriverModel>>() {
        }.getType();
        return gson.fromJson(response, t);
    }

    private List<SubTree> setUserByParent(JSONArray response) {
        List<Org> OrgList = new ArrayList<>();

        List<SubTree> subList = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                List<Cars> cars = new ArrayList<>();
                SubTree subTree = new SubTree();
                JSONObject obj = response.getJSONObject(i);
                JSONArray driver = obj.getJSONArray("driver");
                JSONObject userOrg = obj.getJSONObject("UserOrg");
                int parent_Id = obj.getInt("parentId");

                Org orgUser = new Org();
                orgUser.setOrg_Id(userOrg.getInt("Org_ID"));
                orgUser.setOrg_name(userOrg.getString("Org_Name"));
                orgUser.setOrg_from(userOrg.getInt("Org_From"));
                orgUser.setOrg_to(userOrg.getInt("Org_To"));
                orgUser.setParent_Id(userOrg.getInt("Parent_ID"));


                for (int k = 0; k < driver.length(); k++) {

                    JSONObject objectDriver = driver.getJSONObject(k);
                    Cars car = new Cars();
                    car.setCarId(objectDriver.getInt("Drv_ID"));
                    car.setDriverName(objectDriver.getString("FName"));
                    car.setDriverFamilyName(objectDriver.getString("LName"));
                    car.setOrg_ID(objectDriver.getInt("Org_ID"));
                    cars.add(car);
                }

                subTree.setOrg(orgUser);
                subTree.setCars(cars);
                subTree.setParentId(parent_Id);

                subList.add(subTree);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "setUserByParent: ", e);
        }
        return subList;
    }

    private List<SubTree> getUserSubTreeList(JSONArray response) {

        List<SubTree> subTreeList = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                List<Cars> carsList = new ArrayList<>();
                List<Org> orgList = new ArrayList<>();
                SubTree subTree = new SubTree();
                JSONObject obj = response.getJSONObject(i);
                JSONArray org = obj.optJSONArray("org");
                JSONArray driver = obj.optJSONArray("driver");
                int UserOrg = obj.optInt("UserOrg");
                int parentId = obj.optInt("parentId");


                subTree.setParentId(parentId);
                subTree.setUserOrgId(UserOrg);

                for (int j = 0; j < org.length(); j++) {
                    JSONObject objOrg = org.getJSONObject(j);
                    Org orgUser = new Org();
                    orgUser.setOrg_Id(objOrg.getInt("Org_ID"));
                    orgUser.setOrg_name(objOrg.getString("Org_Name"));
                    orgUser.setOrg_from(objOrg.getInt("Org_From"));
                    orgUser.setOrg_to(objOrg.getInt("Org_To"));
                    orgUser.setParent_Id(objOrg.getInt("Parent_ID"));
                    int car_count = objOrg.optInt("Car_Count");
                    orgUser.setCarCount(car_count);
                    orgList.add(orgUser);
                }

                if (parentId != -1) {
                    for (int k = 0; k < driver.length(); k++) {
                        JSONObject objectDriver = driver.getJSONObject(k);
                        Cars car = new Cars();
                        car.setCarId(objectDriver.getInt("Drv_ID"));
                        car.setDriverName(objectDriver.getString("FName"));
                        car.setDriverFamilyName(objectDriver.getString("LName"));
                        car.setOrg_ID(objectDriver.getInt("Org_ID"));
                        carsList.add(car);
                    }
                }
                if (orgList.size() > 0) {
                    subTree.setOrgs(orgList);
                }
                if (carsList.size() > 0) {
                    subTree.setCars(carsList);
                }

                subTreeList.add(subTree);


            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getUserSubTreeList: ", e);
        }


        return subTreeList;
    }

    private SubTree getUserSubTree(JSONObject response) {
        SubTree subTree = new SubTree();

        try {
            JSONArray org = response.getJSONArray("org");
            JSONArray driver = response.getJSONArray("driver");
            List<Org> orgList = new ArrayList<>();
            List<Cars> carsList = new ArrayList<>();
            for (int i = 0; i < org.length(); i++) {
                Org orgUser = new Org();
                JSONObject objOrg = org.getJSONObject(i);
                orgUser.setOrg_Id(objOrg.getInt("Org_ID"));
                orgUser.setOrg_name(objOrg.getString("Org_Name"));
                orgUser.setOrg_from(objOrg.getInt("Org_From"));
                orgUser.setOrg_to(objOrg.getInt("Org_To"));
                orgUser.setParent_Id(objOrg.getInt("Parent_ID"));
                orgList.add(orgUser);
            }
            for (int k = 0; k < driver.length(); k++) {
                JSONObject objectDriver = driver.getJSONObject(k);
                Cars car = new Cars();
                car.setCarId(objectDriver.getInt("Drv_ID"));
                car.setDriverName(objectDriver.getString("FName"));
                car.setDriverFamilyName(objectDriver.getString("LName"));
                car.setOrg_ID(objectDriver.getInt("Org_ID"));
                carsList.add(car);
            }

            subTree.setOrgs(orgList);
            subTree.setCars(carsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return subTree;
    }


    private OrgInfoModel getUserOrgJson(String response) {

        OrgInfoModel o = new OrgInfoModel();
        Gson gson = new Gson();

        o = gson.fromJson(response, OrgInfoModel.class);
        return o;
    }

    private List<UserAccess> getUserSubTree(JSONArray response) {
        List<UserAccess> userAccessList = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                UserAccess userAccess = new UserAccess();
                JSONObject userObject = response.getJSONObject(i);
                userAccess.setOrg_ID(userObject.getInt("Org_ID"));
                userAccess.setOrg_Name(userObject.getString("Org_Name"));
                userAccess.setParent_ID(userObject.getInt("Parent_ID"));
                userAccessList.add(userAccess);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userAccessList;
    }

    private List<Cars> getDriver(JSONArray response) {
        List<Cars> carsList = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                Cars cars = new Cars();
                JSONObject carObject = response.getJSONObject(i);
                cars.setCarId(carObject.getInt("Drv_ID"));
                cars.setDriverName(carObject.getString("FName"));
                cars.setDriverFamilyName(carObject.getString("LName"));
                cars.setCarNumber(carObject.getString("Car_Pelak"));
                carsList.add(cars);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return carsList;
    }

    private LastPosition getLastCarPosition(JSONObject reponse) {
        LastPosition lastPosition = new LastPosition();
        try {
            lastPosition.setID(reponse.optInt("ID"));
            lastPosition.setOrg_ID(reponse.optInt("Org_ID"));
            lastPosition.setN(reponse.optString("N"));
            lastPosition.setE(reponse.optString("E"));
            lastPosition.setLDate(reponse.optString("LDate"));
            lastPosition.setLTime(reponse.optString("LTime"));
            lastPosition.setSpeed(reponse.optInt("Speed"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastPosition;
    }

    public LastPosition setLastOneDriverPosition(JSONArray lastOnDriverPosition) {
        LastPosition lastPosition = new LastPosition();
        try {
            JSONObject reponse = lastOnDriverPosition.getJSONObject(0);
            lastPosition.setID(reponse.optInt("ID"));
            lastPosition.setOrg_ID(reponse.optInt("Org_ID"));
            lastPosition.setN(reponse.optString("N"));
            lastPosition.setE(reponse.optString("E"));
            lastPosition.setLDate(reponse.optString("LDate"));
            lastPosition.setLTime(reponse.optString("LTime"));
            lastPosition.setSpeed(reponse.optInt("Speed"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lastPosition;
    }


    public interface OnUserAccessOrgBack {
        void OnResponse(OrgInfoModel orgInfoModel);

    }

    public interface OnUserSubTree {
        void OnResponse(List<UserAccess> userAccessList);
    }

    public interface OnGetUserSubTree {
        void onResponseUserSubTree(List<SubTree> subTrees);
    }

    public interface OnGetUserSubTreeObject {
        void onResponseUserSubTree(List<Cars> cars);
    }

    public interface OnGetLastPosition {
        void OnResponse(LastPosition lastPosition);
    }

    public interface OnRouteBack {
        void OnResponse(List<Rah_RT> routs);
    }

    public interface OnAllDriversBack {
        void OnResponse(List<LastPosition> lastPositions);
    }

    public interface OnUserAllDriversBack {
        void OnResponse(List<DriverModel> driverModels);
    }

}
