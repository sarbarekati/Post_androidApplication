package com.anad.mobile.post.Activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anad.mobile.post.Models.LoginModel;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.SingletonApi;
import com.anad.mobile.post.Utils.Util;

public class RegisterCode extends AppCompatActivity {

    private EditText edtRegisterCode;
    Button btnAcceptRegisterCode;
    private TextView txtWrongRegisterCode;
    private PostSharedPreferences postSharedPreferences;
    TextView txtTitle;
    Util util;
    SingletonApi api;
    private String URL = Constants.URL_LOGIN;
    private String USER_NAME;
    private String PASSWORD;
    private String REGISTER_CODE;
    private static final String TAG = "RegisterCode";
    private com.victor.loading.rotate.RotateLoading rotateLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_code);
        util = Util.getInstance();
        postSharedPreferences = new PostSharedPreferences(this);
        api = SingletonApi.getInstance(this);
        setUpViews();
    }

    private void setUpViews() {
        rotateLoading = findViewById(R.id.Register_Code_rotate_loading);
        edtRegisterCode = findViewById(R.id.Register_Code_edt_registerCode);
        btnAcceptRegisterCode = findViewById(R.id.RegisterCode_btn_accept);
        txtWrongRegisterCode = findViewById(R.id.Register_txt_wrong_registerCode);
        txtTitle = findViewById(R.id.Register_Code_txt_title);

        setUpFont();

        btnAcceptRegisterCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                REGISTER_CODE = edtRegisterCode.getText().toString();
                if (REGISTER_CODE.equals(""))
                    util.showToast(RegisterCode.this, "لطفا کد چهار رقمی را وارد نمایید.");
                else {
                    LoginModel loginModel = new LoginModel();
                    rotateLoading.start();
                    Bundle bundle = getIntent().getExtras();
                    if (bundle != null) {
                        USER_NAME = bundle.getString("USER_NAME");
                        PASSWORD = bundle.getString("PASSWORD");
                        loginModel.setUsername(USER_NAME);
                        loginModel.setPassword(PASSWORD);
                        loginModel.setRegCode(REGISTER_CODE);


                    }

                    api.checkLogin(URL, loginModel, new SingletonApi.CanLogin() {
                        @Override
                        public void onResponseBack(boolean canLogin) {
                            if (canLogin) {
                                rotateLoading.stop();
                                postSharedPreferences.setRegisterCode(REGISTER_CODE);
                                postSharedPreferences.setPrefUserName(USER_NAME);
                                postSharedPreferences.setPrefPassword(PASSWORD);
                                postSharedPreferences.setKeyOpenActivity(Constants.KEY);
                                postSharedPreferences.setBase64Encode(Util.EncryptUsernamePassword(USER_NAME,PASSWORD));

                                startActivity(new Intent(RegisterCode.this, MainActivity.class));
                                finish();

                            } else {
                                rotateLoading.stop();
                                util.showToast(RegisterCode.this, getString(R.string.Log_in_failed));
                            }
                        }
                    });
                }
            }
        });
    }

    private void setUpFont() {
        util.setTypeFaceLight(txtWrongRegisterCode, this);
        util.setTypeFaceEdt(edtRegisterCode, this);
        util.setTypeFaceButton(btnAcceptRegisterCode, this);
        util.setTypeFace(txtTitle, this);
    }


}
