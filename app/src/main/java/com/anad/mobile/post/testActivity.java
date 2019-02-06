package com.anad.mobile.post;


import android.app.VoiceInteractor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.anad.mobile.post.AccountManager.LoginApi;
import com.anad.mobile.post.AccountManager.LoginResponse;
import com.anad.mobile.post.AccountManager.OnLoginResponse;
import com.anad.mobile.post.ReportManager.api.ReportApiCaller;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class testActivity extends AppCompatActivity implements OnLoginResponse {

    PostSharedPreferences preferences;
    private boolean isLoginSuccessFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        preferences = new PostSharedPreferences(this);
        callLoginApi();


    }

    @Override
    public void setCookie(String cookie) {
        if (isLoginSuccessFull)
            preferences.setCookie(cookie);

    }

    @Override
    public void onSuccess(LoginResponse loginResponse) {
        isLoginSuccessFull = loginResponse.isSuccessfull();
        if (!isLoginSuccessFull)
            Toast.makeText(this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void callLoginApi() {
        LoginApi api = LoginApi.getInstance(this);
        api.login("admin", "90807");
        api.setOnLoginResponse(this);
    }
}
