package com.anad.mobile.post.AccountManager.api;

import android.content.Context;

import com.anad.mobile.post.API.Retrofit.ApiClient;
import com.anad.mobile.post.AccountManager.model.LoginResponse;
import com.anad.mobile.post.AccountManager.model.OnLoginResponse;
import com.anad.mobile.post.AccountManager.model.PartyAssign;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.NetworkUtils.EndPointsInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginApi {
    private Context context;
    private static LoginApi loginApi = null;
    private OnLoginResponse loginResponse;
    private PostSharedPreferences preferences;


    private LoginApi(Context context, PostSharedPreferences preferences) {
        this.context = context;
        this.preferences = preferences;
    }

    public static LoginApi getInstance(Context context, PostSharedPreferences preferences) {
        if (loginApi == null) {
            loginApi = new LoginApi(context, preferences);
        }
        return loginApi;
    }

    public void setOnLoginResponse(OnLoginResponse onLoginResponse) {
        loginResponse = onLoginResponse;
    }

    public void callLoginApi(String username, String password) {
        Call<LoginResponse> call = ApiClient.getInstance(context)
                .create(EndPointsInterface.class)
                .login(username, password);
        call.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                String cookie = preferences.getCookies();
                if (response.body() != null) {
                    loginResponse.onSuccess(response.body(), cookie);
                }
                else{
                    loginResponse.onFailed(context.getString(R.string.Error_To_Receive_Data));
                }
            }

            @Override
            public void onFailure(retrofit2.Call<LoginResponse> call, Throwable t) {
                loginResponse.onFailed(context.getString(R.string.Error_To_Receive_Data));
            }
        });
    }

    public void callRoleApi(int partyId) {
        Call<List<PartyAssign>> call = ApiClient.getInstance(context)
                .create(EndPointsInterface.class).getRoleOfClient(preferences.getCookies(), partyId);

        call.enqueue(new Callback<List<PartyAssign>>() {
            @Override
            public void onResponse(Call<List<PartyAssign>> call, Response<List<PartyAssign>> response) {
                loginResponse.onRoleApiCallSuccess(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<List<PartyAssign>> call, Throwable t) {
                loginResponse.onFailed(context.getString(R.string.Error_To_Receive_Data));
            }
        });
    }

}
