package com.anad.mobile.post.Activity;

import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.anad.mobile.post.Models.ActivityName;
import com.anad.mobile.post.Models.User;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.SingletonApi;
import com.anad.mobile.post.Utils.Util;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private Util util;
    private SingletonApi api;

    EditText edtUserName, edtPassword, edtPasswordVerify;
    TextView wrongPassword, wrongPasswordVerify, txtGotoLogin, txtRegisterTitle;
    Button btnRegister;


    PostSharedPreferences preferences;
    private static final String TAG = "RegisterActivity";

    private com.victor.loading.rotate.RotateLoading rotateLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        preferences = new PostSharedPreferences(this);
        util = Util.getInstance();
        api = SingletonApi.getInstance(this);
        setUpInput();
    }


    private void setUpInput() {
        txtGotoLogin = (TextView) findViewById(R.id.Register_txt_back_to_login);
        txtRegisterTitle = (TextView) findViewById(R.id.Register_txtView_register_title);
        rotateLoading = (RotateLoading) findViewById(R.id.Register_rotate_loading);
        edtPassword = (EditText) findViewById(R.id.Register_edtText_password);
        edtPasswordVerify = (EditText) findViewById(R.id.Register_edtText_password_verify);
        edtUserName = (EditText) findViewById(R.id.Register_edtText_userName);


        wrongPassword = (TextView) findViewById(R.id.Register_txtView_passwordValidation);
        wrongPasswordVerify = (TextView) findViewById(R.id.Register_txtView_password_verify);


        btnRegister = (Button) findViewById(R.id.Register_btn_next_step);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtUserName.getText().toString().equals("")) {
                    util.showToast(RegisterActivity.this, RegisterActivity.this.getString(R.string.please_enter_user_name));
                } else if (!edtPassword.getText().toString().equals("")) {
                    if (util.checkPasswordValidity(edtPassword.getText().toString())) {
                        wrongPassword.setVisibility(View.GONE);
                        if (util.checkVerifyPassWord(edtPassword.getText().toString(), edtPasswordVerify.getText().toString())) {
                            User user = new User();
                            wrongPasswordVerify.setVisibility(View.GONE);
                            rotateLoading.start();

                            preferences.setPrefPassword(edtPassword.getText().toString());
                            preferences.setPrefUserName(edtUserName.getText().toString());
                            preferences.setKeyOpenActivity(Constants.KEY);

                            user.setUserName(preferences.getPrefUserName());
                            user.setPassWord(preferences.getPrefPassword());

                            api.canRegister(user, ActivityName.REGISTERACTIVITY, new SingletonApi.OnCanRegisterBack() {
                                @Override
                                public void onResponseBack(boolean canRegister) {
                                    if (canRegister) {
                                        rotateLoading.stop();
                                        Intent i = new Intent(RegisterActivity.this, RegisterActivity_next.class);
                                        startActivity(i);
                                        finish();
                                    }
                                    rotateLoading.stop();
                                }
                            });



                        } else {
                            wrongPasswordVerify.setVisibility(View.VISIBLE);
                            edtPasswordVerify.setText("");
                        }
                    } else {
                        wrongPassword.setVisibility(View.VISIBLE);
                        edtPassword.setText("");
                    }
                } else {
                    util.showToast(RegisterActivity.this, RegisterActivity.this.getString(R.string.please_enter_password));
                }

            }
        });

        txtGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });


        edtUserName.setFilters(new InputFilter[]{util.getInputFilter()});


        util.setTypeFaceButton(btnRegister, this);
        util.setTypeFaceLight(txtGotoLogin, this);
        util.setTypeFace(txtRegisterTitle, this);
        util.setTypeFaceEdt(edtUserName, this);
        util.setTypeFaceEdt(edtPassword, this);
        util.setTypeFaceEdt(edtPasswordVerify, this);
        util.setTypeFaceLight(wrongPassword, this);
        util.setTypeFaceLight(wrongPasswordVerify, this);


    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, EnterActivity.class));
        finish();
    }
}
