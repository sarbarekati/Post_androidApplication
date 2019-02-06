package com.anad.mobile.post.AccountManager;

public interface OnLoginResponse {
    void setCookie(String cookie);
    void onSuccess(LoginResponse loginResponse);
}
