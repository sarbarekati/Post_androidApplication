package com.anad.mobile.post.AccountManager.model;

import com.anad.mobile.post.AccountManager.model.LoginResponse;

public interface OnLoginResponse {
    void onSuccess(LoginResponse loginResponse,String cookie);
}
