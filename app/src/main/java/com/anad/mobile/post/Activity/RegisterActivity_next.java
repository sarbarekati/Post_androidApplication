package com.anad.mobile.post.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anad.mobile.post.Models.ActivityName;
import com.anad.mobile.post.Models.User;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;

import com.anad.mobile.post.Utils.SingletonApi;
import com.anad.mobile.post.Utils.Util;
import com.victor.loading.rotate.RotateLoading;


public class RegisterActivity_next extends AppCompatActivity {
    TextView txtBack, txtTitle;
    EditText edtName, edtFamilyName, edtPhoneNumber, edtJob;
    Button btnSendDataToServer;
    PostSharedPreferences preferences;
    private static final String TAG = "RegisterActivity_next";
    Util util;
    SingletonApi api;
    private com.victor.loading.rotate.RotateLoading rotateLoading;
    Button btnRegisterComplete;
    TextView txtViewTitleRegisterComplete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_next);
        preferences = new PostSharedPreferences(this);
        util = Util.getInstance();
        api = SingletonApi.getInstance(this);
        setUpViews();
    }

    private void setUpViews() {
        rotateLoading = (RotateLoading) findViewById(R.id.Register_next_rotate_loading);
        edtName = (EditText) findViewById(R.id.Register_next_edt_name);
        edtFamilyName = (EditText) findViewById(R.id.Register_next_edt_family_name);
        edtPhoneNumber = (EditText) findViewById(R.id.Register_next_edt_phone_number);
        edtJob = (EditText) findViewById(R.id.Register_next_edt_job);

        edtFamilyName.setFilters(new InputFilter[]{util.getInputFilter()});
        edtName.setFilters(new InputFilter[]{util.getInputFilter()});


        btnSendDataToServer = (Button) findViewById(R.id.Register_next_btn_send_data);

        txtBack = (TextView) findViewById(R.id.Register_next_txt_back);
        txtTitle = (TextView) findViewById(R.id.Register_next_txt_title);
        setUpFont();


        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSendDataToServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtName.getText().toString().equals("")) {
                    util.showToast(RegisterActivity_next.this, getString(R.string.Must_enter_name));
                } else if (edtFamilyName.getText().toString().equals("")) {

                    util.showToast(RegisterActivity_next.this, getString(R.string.Must_enter_last_name));
                } else if (edtPhoneNumber.getText().toString().equals("")) {

                    util.showToast(RegisterActivity_next.this, getString(R.string.Must_enter_phone_number));
                } else if (edtPhoneNumber.getText().length() < 11) {
                    util.showToast(RegisterActivity_next.this, getString(R.string.Phone_number_is_less_than_11_number));
                } else {
                    rotateLoading.start();
                    preferences.setPrefName(edtName.getText().toString());
                    preferences.setPrefFamilyName(edtFamilyName.getText().toString());

                    String phoneNumber = edtPhoneNumber.getText().toString();
                    phoneNumber = phoneNumber.replaceFirst("0", "98");
                    Log.i(TAG, "Phone Number: " + phoneNumber);

                    preferences.setPrefPhoneNumber(phoneNumber);


                    preferences.setPrefUserJob(edtJob.getText().toString());

                    Log.i(TAG, "onClick: " + preferences.getName() + " -- " +
                            preferences.getFamilyName() + " -- " +
                            preferences.getPrefUserName() + " -- " +
                            preferences.getPrefPassword() + " -- " +
                            preferences.getPhoneNumber() + " -- " +
                            preferences.getUserJob());


                    User userInformation = new User();
                    userInformation.setUserName(preferences.getPrefUserName());
                    userInformation.setPassWord(preferences.getPrefPassword());
                    userInformation.setName(preferences.getName());
                    userInformation.setFamilyName(preferences.getFamilyName());
                    userInformation.setPhoneNumber(preferences.getPhoneNumber());
                    userInformation.setJob(preferences.getUserJob());
                    userInformation.setToken(preferences.getToken());

                    api.canRegister(userInformation, ActivityName.REGISTERACTIVITY_NEXT, new SingletonApi.OnCanRegisterBack() {
                        @Override
                        public void onResponseBack(boolean canRegister) {
                            if (canRegister) {
                                rotateLoading.stop();
                                View view = LayoutInflater.from(RegisterActivity_next.this).inflate(R.layout.register_finish_dialog, null);
                                Dialog dialog = new Dialog(RegisterActivity_next.this);
                                dialog.setCancelable(false);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setContentView(view);
                                btnRegisterComplete = view.findViewById(R.id.Register_finish_btn_back);
                                txtViewTitleRegisterComplete = view.findViewById(R.id.Register_finish_txtView_title);
                                btnRegisterComplete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(RegisterActivity_next.this, EnterActivity.class));
                                        finish();
                                    }
                                });
                                dialog.show();
                                util.setTypeFaceButton(btnRegisterComplete, RegisterActivity_next.this);
                                util.setTypeFace(txtViewTitleRegisterComplete, RegisterActivity_next.this);
                            }
                            rotateLoading.stop();

                        }
                    });


                }
            }
        });


    }

    private void setUpFont() {
        util.setTypeFaceEdt(edtName, this);
        util.setTypeFaceEdt(edtFamilyName, this);
        util.setTypeFaceEdt(edtJob, this);
        util.setTypeFaceEdt(edtPhoneNumber, this);
        util.setTypeFaceButton(btnSendDataToServer, this);
        util.setTypeFaceLight(txtBack, this);
        util.setTypeFace(txtTitle, this);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterActivity_next.this, RegisterActivity.class));
        finish();
    }
}
