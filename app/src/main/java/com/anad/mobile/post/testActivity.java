package com.anad.mobile.post;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.anad.mobile.post.AccountManager.api.LoginApi;
import com.anad.mobile.post.AccountManager.model.LoginResponse;
import com.anad.mobile.post.AccountManager.model.OnLoginResponse;
import com.anad.mobile.post.Storage.PostSharedPreferences;

public class testActivity extends AppCompatActivity {

    PostSharedPreferences preferences;
    private boolean isLoginSuccessFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);



    }

}
