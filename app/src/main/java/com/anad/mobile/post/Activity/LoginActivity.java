package com.anad.mobile.post.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anad.mobile.post.API.ApiCaller;
import com.anad.mobile.post.API.WebApi;
import com.anad.mobile.post.AccountManager.api.LoginApi;
import com.anad.mobile.post.AccountManager.model.LoginResponse;
import com.anad.mobile.post.AccountManager.model.OnLoginResponse;
import com.anad.mobile.post.AccountManager.model.PartyAssign;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements OnLoginResponse {
    private static final String TAG = "LoginActivity";
    private Util util;
    private PostSharedPreferences postSharedPreferences;
    AppCompatCheckBox chRememberMe;
    TextView txtForgetPassword, txtResendMessage, txtTitle;
    EditText edtUserName, edtPassword;
    Button btnLogin;
    private RadioGroup optionRadioGroupForgivePass;
    private AppCompatRadioButton optionRadioButtonForgivePass;
    private String URL;
    // private SingletonApi api;

    private LoginApi api;
    private RadioGroup optionRadioGroupResendMessage;
    String REGISTER_CODE = "";
    String PASSWORD;
    String USER_NAME;
    com.victor.loading.rotate.RotateLoading rotateLoading;
    boolean isMobileRequest = false;
    String userPassBase64;
    private boolean isLoginSuccessFull;
    private boolean isRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        util = Util.getInstance();
        postSharedPreferences = new PostSharedPreferences(this);

        userPassBase64 = postSharedPreferences.getEncode();
        api = LoginApi.getInstance(this,postSharedPreferences);
        api.setOnLoginResponse(this);
        setUpInput();

    }

    private void setUpInput() {

        txtForgetPassword = findViewById(R.id.Login_txt_forget_password);
        txtForgetPassword.setVisibility(View.GONE);
        txtResendMessage = findViewById(R.id.Login_txt_resend_message);
        txtTitle = findViewById(R.id.Login_txt_interTitle);
        edtPassword = findViewById(R.id.Login_edt_password);
        edtUserName = findViewById(R.id.Login_edt_user_name);
        chRememberMe = findViewById(R.id.Login_chBox_rememberMe);
        btnLogin = findViewById(R.id.Login_btn_login);
        rotateLoading = findViewById(R.id.Login_rotate_loading);

        REGISTER_CODE = postSharedPreferences.getRegisterCode();

        isRememberMe = postSharedPreferences.getRememberMe();

        if (postSharedPreferences.getRememberMe()) {
            chRememberMe.setChecked(true);
            USER_NAME = postSharedPreferences.getPrefUserName();
            PASSWORD = postSharedPreferences.getPrefPassword();
            REGISTER_CODE = postSharedPreferences.getRegisterCode();
            edtUserName.setText(USER_NAME);
            edtPassword.setText(PASSWORD);
        }


        setUpFont();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtUserName.getText().toString().equals("")) {
                    if (!edtPassword.getText().toString().equals("")) {
                        rotateLoading.start();
                        final String userName = edtUserName.getText().toString();
                        final String passWord = edtPassword.getText().toString();
                        api.callWithRetrofit(userName, passWord);


//                        api.checkLoginWithState(userName, passWord,REGISTER_CODE, new SingletonApi.CheckState() {
//                            @Override
//                            public void onResponseString(Map<String,String> checkState) {
//                                if (checkState.get("STATE").equals("")) {
//                                    if (!checkState.get("REG_CODE").equals(REGISTER_CODE)) {
//                                        rotateLoading.stop();
//                                        Intent intent = new Intent(LoginActivity.this, RegisterCode.class);
//                                        intent.putExtra("USER_NAME", userName);
//                                        intent.putExtra("PASSWORD", passWord);
//
//                                        changeToken(userName,passWord);
//
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                    else if(checkState.get("REG_CODE").equals(REGISTER_CODE)) {
//                                        rotateLoading.stop();
//                                        postSharedPreferences.setPrefUserName(edtUserName.getText().toString());
//                                        postSharedPreferences.setKeyOpenActivity(Constants.KEY);
//                                        postSharedPreferences.setPrefPassword(edtPassword.getText().toString());
//                                        postSharedPreferences.setBase64Encode(Util.EncryptUsernamePassword(edtUserName.getText().toString()
//                                                ,edtPassword.getText().toString()));
//
//                                        changeToken(edtUserName.getText().toString(),edtPassword.getText().toString());
//
//                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                } else
//                                {
//                                    rotateLoading.stop();
//                                    util.showToast(LoginActivity.this, checkState.get("STATE"));
//                                }
//                            }
//                        });


                    } else {
                        util.showToast(LoginActivity.this, LoginActivity.this.getString(R.string.please_enter_password));


                    }
                } else {
                    util.showToast(LoginActivity.this, LoginActivity.this.getString(R.string.please_enter_user_name));
                }


            }
        });


        chRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRememberMe = isChecked;
                saveUserAuthenticateInfo(isRememberMe);
            }
        });

        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                @SuppressLint("InflateParams")
                View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.forget_pass_dialog, null);
                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(view);

                final EditText edtUserNameForGiveDate = view.findViewById(R.id.forget_pass_edt_user_name);
                edtUserNameForGiveDate.setFilters(new InputFilter[]{util.getInputFilter()});
                Button btnSendDate = view.findViewById(R.id.forget_pass_send);
                optionRadioGroupForgivePass = view.findViewById(R.id.forget_pass_rd_group);
                int selectOption = optionRadioGroupForgivePass.getCheckedRadioButtonId();
                optionRadioButtonForgivePass = (AppCompatRadioButton) findViewById(selectOption);
                optionRadioGroupForgivePass.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            //TODO CALL SMS WEBSERVICE HERE
                            case R.id.forget_pass_rd_username:
                                edtUserNameForGiveDate.setInputType(InputType.TYPE_CLASS_TEXT);
                                edtUserNameForGiveDate.setFilters(new InputFilter[]{util.getInputFilter()});
                                edtUserNameForGiveDate.setText("");
                                edtUserNameForGiveDate.setHint(R.string.enter_your_user_name);
                                isMobileRequest = false;
                                break;
                            case R.id.forget_pass_rd_mobile:
                                edtUserNameForGiveDate.setInputType(InputType.TYPE_CLASS_NUMBER);
                                edtUserNameForGiveDate.setFilters(util.getInputFilterForLength(11));
                                edtUserNameForGiveDate.setText("");
                                edtUserNameForGiveDate.setHint(R.string.enter_phone_number_resend);
                                isMobileRequest = true;
                                break;

                        }
                    }
                });

                btnSendDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String input = edtUserNameForGiveDate.getText().toString();
                        if (input.equals("")) {
                            if (isMobileRequest) {
                                util.showToast(LoginActivity.this, getString(R.string.please_enter_phone_number));
                            } else {

                                util.showToast(LoginActivity.this, getString(R.string.enter_your_user_name));
                            }
                        } else {
                            Log.i(TAG, "onClick: " + input);
                            WebApi web = new WebApi(LoginActivity.this, userPassBase64);
                            if (isMobileRequest) {
                                String mobile = input.replaceFirst("0", "98");
                                String url = Constants.URL_SEND_FORGET_PASSWORD_MOBILE + "/" + mobile;
                                web.sendMessageFromServer(url);
                                isMobileRequest = false;
                            } else {
                                web.sendMessageFromServer(Constants.URL_SEND_FORGET_PASSWORD_USERNAME + "/" + input);
                            }
                            dialog.dismiss();
                        }


                    }

                });

                util.setTypeFaceButton(btnSendDate, LoginActivity.this);
                util.setTypeFaceEdt(edtUserNameForGiveDate, LoginActivity.this);

                dialog.show();
            }
        });

        txtResendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("InflateParams")
                View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.resend_dialog, null);
                Dialog dialog = new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(view);
                Button btnResendMessage = view.findViewById(R.id.resend_message_btn);
                final EditText edtPhoneNumberForGiveMessage = view.findViewById(R.id.resend_edt_phone_number);
                edtPhoneNumberForGiveMessage.setInputType(InputType.TYPE_CLASS_NUMBER);

                edtPhoneNumberForGiveMessage.setFilters(util.getInputFilterForLength(11));
                optionRadioGroupResendMessage = view.findViewById(R.id.resend_rd_group);
                optionRadioGroupResendMessage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            //TODO CALL SMS WEBSERVICE HERE
                            case R.id.resend_rd_username:
                                edtPhoneNumberForGiveMessage.setFilters(util.getInputFilterForLength(50));
                                edtPhoneNumberForGiveMessage.setInputType(InputType.TYPE_CLASS_TEXT);
                                edtPhoneNumberForGiveMessage.setFilters(new InputFilter[]{util.getInputFilter()});
                                edtPhoneNumberForGiveMessage.setText("");
                                edtPhoneNumberForGiveMessage.setHint(R.string.enter_your_user_name);
                                break;
                            case R.id.resend_rd_mobile:
                                edtPhoneNumberForGiveMessage.setInputType(InputType.TYPE_CLASS_NUMBER);
                                edtPhoneNumberForGiveMessage.setText("");
                                edtPhoneNumberForGiveMessage.setHint(R.string.enter_phone_number_resend);
                                edtPhoneNumberForGiveMessage.setFilters(util.getInputFilterForLength(11));
                                break;
                        }
                    }
                });
                btnResendMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phoneNumber = edtPhoneNumberForGiveMessage.getText().toString();
                        if (phoneNumber.equals(""))
                            util.showToast(LoginActivity.this, getString(R.string.please_enter_phone_number));
                        else {
                            Log.d(TAG, "onClick: " + phoneNumber);
                            WebApi web = new WebApi(LoginActivity.this, userPassBase64);
                            web.sendMessageFromServer(Constants.URL_SEND_FORGET_PASSWORD + "/" + phoneNumber);
                        }
                    }
                });

                util.setTypeFaceEdt(edtPhoneNumberForGiveMessage, LoginActivity.this);
                util.setTypeFaceButton(btnResendMessage, LoginActivity.this);
                dialog.show();
            }
        });


    }

    private void changeToken(String userName, String password) {

        String token = postSharedPreferences.getToken();
        String URL = Constants.URL_CHANGE_TOKEN + "?userName=" + userName + "&token=" + token;

        WebApi api = new WebApi(LoginActivity.this);
        api.ChangeUserTokenAPI(URL, userName, password);
    }


    private void setUpFont() {
        util.setTypeFaceButton(btnLogin, this);
        util.setTypeFaceEdt(edtPassword, this);
        util.setTypeFaceEdt(edtUserName, this);
        util.setTypeFaceLight(txtResendMessage, this);
        util.setTypeFace(txtForgetPassword, this);
        util.setTypeFace(txtTitle, this);
        util.setTypeFaceCheckBox(chRememberMe, this);

        edtUserName.setFilters(new InputFilter[]{util.getInputFilter()});

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this, EnterActivity.class));
        finish();
    }
    @Override
    public void onSuccess(LoginResponse loginResponse,String cookie) {
        isLoginSuccessFull = loginResponse.isSuccessful();
        if (!isLoginSuccessFull) {
            Toast.makeText(this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
            rotateLoading.stop();
            return;
        } else {
            postSharedPreferences.setCookie(cookie);
            long partyId = loginResponse.getReturnValue().getPartyId();


            api.callRoleApi((int)partyId);


        }
    }


    private void saveUserAuthenticateInfo(boolean isRememberMeChecked) {

        postSharedPreferences.setRememberMe(isRememberMeChecked);
        postSharedPreferences.setPrefUserName(edtUserName.getText().toString());
        postSharedPreferences.setKeyOpenActivity(Constants.KEY);
        postSharedPreferences.setPrefPassword(edtPassword.getText().toString());
        postSharedPreferences.setBase64Encode(Util.EncryptUsernamePassword(edtUserName.getText().toString()
                , edtPassword.getText().toString()));

    }


    private class OnRoleApiResponse implements Response.Listener<JSONArray> {
        @Override
        public void onResponse(JSONArray response) {

            if (isMobileUser(response)) {
                saveUserAuthenticateInfo(isRememberMe);
                Util.gotoActivity(LoginActivity.this, MainActivity.class, null, true);
            }else{
                Toast.makeText(LoginActivity.this, R.string.Your_not_mobile_user, Toast.LENGTH_LONG).show();
                rotateLoading.stop();
            }

        }
    }


    private class OnRoleApiError implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, "onErrorResponse: "+error);
        }
    }

    private boolean isMobileUser(JSONArray response) {
        List<PartyAssign> partyAssigns = parsResponse(response);
        for (PartyAssign partyAssign : partyAssigns) {
            if (partyAssign.getPartyTypeId() == Constants.MOBILE_USER_CODE)
                return true;
        }
        return false;
    }


    private List<PartyAssign> parsResponse(JSONArray response) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<PartyAssign>>() {
        }.getType();
        return gson.fromJson(response.toString(), type);
    }


}
