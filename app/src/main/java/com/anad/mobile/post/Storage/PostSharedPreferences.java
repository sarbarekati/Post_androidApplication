package com.anad.mobile.post.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.anad.mobile.post.BuildConfig;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;

import java.util.HashSet;
import java.util.Set;

/*
* CREATE BY ELIAS MOHAMMADI 96.9.12
* */
public class PostSharedPreferences {
    private static final String POST_SHARED_PREF_NAME = "post_shared_pref";
    private static final String USER_NAME = "user_name";
    private static final String NAME = "name";
    private static final String FAMILY_NAME = "family_name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String USER_JOB = "user_job";
    private static final String PASSWORD = "password";
    private static final String REMEMBER_ME = "remember_me";
    private static final String REGISTER_CODE = "register_code";
    private static final String FIRST_RUN = "first_run";
    private static final String TOKEN = "token";
    private static final String ENCODE ="encode" ;
    private static final String KEY ="key" ;
    private static final String CATCH_MAP ="catch_map";
    private static final String COOKIES = "cookies";
    private static final String PARTY_INFO = "partyInfo";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;


    public PostSharedPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(POST_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();

    }


    public void setPrefUserName(String userName) {
        String userEncrypt = Util.createSHA256(userName, Constants.KEY);
        editor.putString(USER_NAME, userEncrypt);
        editor.apply();
    }

    public void setBase64Encode(String encode){
        editor.putString(ENCODE,encode);
        editor.apply();
    }

    public void setPrefPassword(String password) {
        String passEncrypt = Util.createSHA256(password,Constants.KEY);
        editor.putString(PASSWORD, passEncrypt);
        editor.apply();
    }

    public void setCatchMap(String catchMap){
        editor.putString(CATCH_MAP,catchMap);
        editor.apply();
    }

    public void setPrefName(String name) {
        editor.putString(NAME, name);
        editor.apply();
    }

    public void setPrefFamilyName(String familyName) {
        editor.putString(FAMILY_NAME, familyName);
        editor.apply();
    }

    public void setPrefPhoneNumber(String phoneNumber) {
        editor.putString(PHONE_NUMBER, phoneNumber);
        editor.apply();

    }

    public void setFirstRun(boolean isFirstRun)
    {
        editor.putBoolean(FIRST_RUN,isFirstRun);
        editor.apply();
    }

    public void setPrefUserJob(String userJob) {
        editor.putString(USER_JOB, userJob);
        editor.apply();
    }


    public void setRememberMe(boolean rememberMe) {

        editor.putBoolean(REMEMBER_ME, rememberMe);
        editor.apply();
    }

    public void setRegisterCode(String registerCode) {
        String regEncrypt = Util.createSHA256(registerCode,Constants.KEY);
        editor.putString(REGISTER_CODE, regEncrypt);
        editor.apply();
    }

    public void setKeyOpenActivity(String key){
        String keyEncrypt = Util.createSHA256(key,Constants.KEY);
        editor.putString(KEY,keyEncrypt);
        editor.apply();
    }


    public void setToken(String Token) {
        editor.putString(TOKEN, Token);
        editor.apply();
    }

    public void setCookie(String cookies){
        editor.putString(COOKIES,cookies);
        editor.apply();
    }

    public String getCookies(){
         return mSharedPreferences.getString(COOKIES,null);
    }


    public boolean getRememberMe() {
        return mSharedPreferences.getBoolean(REMEMBER_ME, false);
    }

    public String getPrefUserName() {
        String value = mSharedPreferences.getString(USER_NAME, "");
        return Util.deHashSHA256(value,Constants.KEY);
    }

    public String getPrefPassword() {
        String value = mSharedPreferences.getString(PASSWORD, "");
        return Util.deHashSHA256(value,Constants.KEY);
    }


    public String getCatchMap(){
        return mSharedPreferences.getString(CATCH_MAP,"");

    }

    public String getName() {
        return mSharedPreferences.getString(NAME, "");
    }

    public String getFamilyName() {
        return mSharedPreferences.getString(FAMILY_NAME, "");
    }

    public String getPhoneNumber() {
        return mSharedPreferences.getString(PHONE_NUMBER, "");
    }

    public String getUserJob() {
        return mSharedPreferences.getString(USER_JOB, "");
    }

    public String getRegisterCode() {
        String value = mSharedPreferences.getString(REGISTER_CODE, "");
        return Util.deHashSHA256(value,Constants.KEY);
    }
    public boolean getFirstRun(){return mSharedPreferences.getBoolean(FIRST_RUN,true);}




    public String getEncode(){return mSharedPreferences.getString(ENCODE,"");}
    public String getKeyOpenActivity(){
        return mSharedPreferences.getString(KEY,"");

    }

    public String getToken() {
        return mSharedPreferences.getString(TOKEN, "");

    }

    public String getPartyInfo(){
        return mSharedPreferences.getString(PARTY_INFO,"");
    }


    public void setPartyInfo(String partyInfo) {
        editor.putString(PARTY_INFO,partyInfo);
        editor.apply();
    }
}
