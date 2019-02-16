package com.anad.mobile.post.AccountManager.model;


import java.util.List;
import retrofit2.Response;

public interface OnLoginResponse {
    void onSuccess(LoginResponse loginResponse,String cookie);
    void onRoleApiCallSuccess(List<PartyAssign> response);
    void onFailed(String message);
}
