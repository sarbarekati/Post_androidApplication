package com.anad.mobile.post.API;

import android.content.Context;
import android.util.Log;

import com.anad.mobile.post.Models.BooleanRequest;
import com.anad.mobile.post.Models.LoginModel;
import com.anad.mobile.post.Models.UpdateModel;
import com.anad.mobile.post.Models.User;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class WebApi {
    private Context context;
    private static final String TAG = "WebApi";
    private static final int TIME_OUT = 10000;
    private static final String API_KEY = Constants.API_KEY;
    private String userPassBase64;

    public WebApi(Context context, String userPassBase64) {
        this.context = context;
        this.userPassBase64 = userPassBase64;
    }

    public WebApi(Context context) {
        this.context = context;
    }

    public void ChangeUserTokenAPI(String url,String userName,String password)
    {

        final String userPasswordBASE64 = Util.EncryptUsernamePassword(userName,password);
        RequestQueue queue = Volley.newRequestQueue(context);
       BooleanRequest request = new BooleanRequest(Request.Method.POST, url, new Response.Listener<Boolean>() {
           @Override
           public void onResponse(Boolean response) {
               Log.d(TAG, "change user token: " + response);
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Log.d(TAG, "change user token: " + error);
           }
       }){
           @Override
           public String getBodyContentType() {
               return "application/json;charset=utf-8";

           }

           @Override
           public Map<String, String> getHeaders() throws AuthFailureError {
               Map<String,String> header = new HashMap<>();
               header.put(Constants.LABEL_API_KEY,API_KEY);
               header.put(Constants.AUTHORIZATION,userPasswordBASE64);
               return header;
           }
       };

        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void GetApplicationNewVersion(final OnUpdateResponse onUpdateResponse, String url) {

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                onUpdateResponse.OnResponse(needUpdate(response));
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
                HashMap<String, String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY, API_KEY);
                //headers.put(Constants.AUTHORIZATION, userPassBase64);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public void addUser(final OnCheckRegister onCheckRegister, String url, final User user) {
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("Username", user.getUserName());
            jsonBody.put("Pass", user.getPassWord());
            jsonBody.put("FName", user.getName());
            jsonBody.put("LName", user.getFamilyName());
            jsonBody.put("Mobile", user.getPhoneNumber());
            jsonBody.put("Token", user.getToken());
            jsonBody.put("PostStr", user.getJob());

            final String requestBody = jsonBody.toString();
            RequestQueue queue = Volley.newRequestQueue(context);

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "onResponse: " + response);
                    onCheckRegister.onResponseBack(true);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    int code = error.networkResponse.statusCode;
                    onCheckRegister.onResponseBack(false);
                    Log.e(TAG, "onErrorResponse: " + code);
                }
            }) {
                @Override
                public String getBodyContentType() {

                    return "application/json;charset=uft-8";
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
                    Map<String, String> headers = new HashMap<>();
                    headers.put(Constants.LABEL_API_KEY, API_KEY);
                    return headers;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void loginUser(final OnLoginResponse onLoginResponse, final LoginModel loginModel, String url) {

        Gson gson = new Gson();
        final String requestBody = gson.toJson(loginModel);

        final String userPasswordBASE64 = Util.EncryptUsernamePassword(loginModel.getUsername(), loginModel.getPassword());

        RequestQueue queue = Volley.newRequestQueue(context);


        BooleanRequest request = new BooleanRequest(Request.Method.POST, url, new Response.Listener<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                onLoginResponse.onResponseBack(getLoginFeedBack(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
            }


        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e(TAG, "LoginWS: ", e);
                    return null;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";


            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY, API_KEY);
                headers.put(Constants.AUTHORIZATION, userPasswordBASE64);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void loginUserCheck(final OnCheckLogin onCheckLogin, final OnErrorLogin onErrorLogin, String url, LoginModel loginModel) {
        Gson gson = new Gson();
        final String requestBody = gson.toJson(loginModel);
        final String userPasswordBASE64 = Util.EncryptUsernamePassword(loginModel.getUsername(), loginModel.getPassword());
        Log.d(TAG, "base64Code: " + userPasswordBASE64);
        RequestQueue queue = Volley.newRequestQueue(context);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onCheckLogin.onResponseBack(getLoginCheck(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error);
                onErrorLogin.onResponseBack(false);
            }
        }) {
            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e(TAG, "loginUserCheck: ", e);
                    return null;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(Constants.LABEL_API_KEY, API_KEY);
                headers.put(Constants.AUTHORIZATION, userPasswordBASE64);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void sendMessageFromServer(String url) {


        try {

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "onResponse: " + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "onErrorResponse: " + error);
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
                    return headers;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(300000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetUserByUsername(final CheckUserExists checkUserExists, String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
                checkUserExists.onResponseBack(HaveAnyMember(response));
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
                return headers;
            }
        };
        RequestQueue queue = null;

        queue = Volley.newRequestQueue(context);

        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }


    private boolean getLoginFeedBack(boolean response) {
        return response;
    }

    private Map<String, String> getLoginCheck(JSONObject response) {
        Map<String, String> loginResponse = new HashMap<>();
        try {
            if (response != null) {
                boolean Active = response.getBoolean("Active");
                int State = response.getInt("State");
                String regCode = response.optString("RegCode", "");
                String RegCode = Util.DecryptRegCode(regCode);


                switch (State) {
                    case 0:// wait for accept
                        loginResponse.put("STATE", context.getString(R.string.wait_for_accept));
                        loginResponse.put("REG_CODE", RegCode);
                        break;
                    case 1:// accept wait for active
                        if (Active) {
                            loginResponse.put("STATE", "");
                            loginResponse.put("REG_CODE", RegCode);
                        } else {
                            loginResponse.put("STATE", context.getString(R.string.Account_accept_not_active));
                            loginResponse.put("REG_CODE", "-2");
                        }
                        break;

                    case 2:// cancel request
                        loginResponse.put("STATE", context.getString(R.string.Account_Cancel));
                        loginResponse.put("REG_CODE", RegCode);
                        break;

                }
            } else {
                loginResponse.put("STATE", context.getString(R.string.No_Account_with_this_Information));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getLoginCheck: ", e);
        }
        return loginResponse;
    }

    private UpdateModel needUpdate(String response) {
        Gson gson = new Gson();
        UpdateModel model = gson.fromJson(response, UpdateModel.class);
        Log.d(TAG, "model info: " + model.getVersion());
        return model;
    }

    private boolean HaveAnyMember(String response) {
        boolean haveAnyMember = false;
        if (response.equals("null"))
            haveAnyMember = true;
        Log.i(TAG, "HaveAnyMember: " + haveAnyMember);
        return haveAnyMember;
    }

    public interface CheckUserExists {
        void onResponseBack(Boolean haveAnyMember);
    }

    public interface OnLoginResponse {
        void onResponseBack(boolean loginAnswer);
    }

    public interface OnCheckRegister {
        void onResponseBack(boolean canRegister);
    }

    public interface OnCheckLogin {
        void onResponseBack(Map<String, String> loginCheck);
    }

    public interface OnErrorLogin {
        void onResponseBack(Boolean b);
    }

    public interface OnUpdateResponse {
        void OnResponse(UpdateModel updateModel);
    }






}
