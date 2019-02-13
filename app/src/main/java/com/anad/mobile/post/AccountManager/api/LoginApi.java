package com.anad.mobile.post.AccountManager.api;

import android.content.Context;
import android.util.Log;

import com.anad.mobile.post.API.Retrofit.ApiClient;
import com.anad.mobile.post.AccountManager.model.LoginResponse;
import com.anad.mobile.post.AccountManager.model.OnLoginResponse;
import com.anad.mobile.post.AccountManager.model.PartyAssign;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginApi {
    private static final String TAG = "LoginApi";
    private Context context;
    private static LoginApi loginApi = null;
    private OnLoginResponse loginResponse;
    private static OkHttpClient client = null;
    private PostSharedPreferences preferences;
    private LoginApi(Context context,PostSharedPreferences preferences) {
        this.context = context;
        this.preferences = preferences;
    }

    public static LoginApi getInstance(Context context,PostSharedPreferences preferences) {
        if (loginApi == null) {
            loginApi = new LoginApi(context,preferences);
        }
        return loginApi;
    }

    public void setOnLoginResponse(OnLoginResponse onLoginResponse) {
        loginResponse = onLoginResponse;
    }

    public void callWithOkhttp(String username, String password) {

        Request request = new Request.Builder()
                .url(loginUrl(username, password))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                loginResponse.onSuccess(parseResponse(result),buildCookie(response.headers("Set-Cookie")));
            }
        });
    }



    public void callWithRetrofit(String username,String password){




        retrofit2.Call<LoginResponse> call = ApiClient.getInstance(context)
                                                        .create(LoginEndPointInterface.class)
                                                        .login(username,password);
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {

                String cookie = preferences.getCookies();

                loginResponse.onSuccess(response.body(),cookie);
            }

            @Override
            public void onFailure(retrofit2.Call<LoginResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });




    }


    public void callRoleApi(int partyId){
        retrofit2.Call<List<PartyAssign>> call = ApiClient.getInstance(context)
                .create(LoginEndPointInterface.class).getRoleOfClient(preferences.getCookies(),partyId);

        call.enqueue(new retrofit2.Callback<List<PartyAssign>>() {
            @Override
            public void onResponse(retrofit2.Call<List<PartyAssign>> call, retrofit2.Response<List<PartyAssign>> response) {
                Log.d(TAG, "onResponse: " + response.body().size());
            }

            @Override
            public void onFailure(retrofit2.Call<List<PartyAssign>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }


    private String loginUrl(String username, String password) {
        return Constants.LOGIN_POST_FW + "username=" + username + "&password=" + password;
    }


    private LoginResponse parseResponse(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, LoginResponse.class);
    }

    private String buildCookie(List<String> cookies){
       StringBuilder builder = new StringBuilder();
        for (String cookie : cookies) {
            builder.append(cookie);
        }
      return builder.toString()+";";
    }


}
