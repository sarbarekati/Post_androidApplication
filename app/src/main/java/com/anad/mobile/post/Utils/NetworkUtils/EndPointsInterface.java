package com.anad.mobile.post.Utils.NetworkUtils;

import com.anad.mobile.post.AccountManager.model.LoginResponse;
import com.anad.mobile.post.AccountManager.model.PartyAssign;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface EndPointsInterface {

    @GET("main/partyassign/getbypartyid?")
    Call<List<PartyAssign>> getRoleOfClient(@Header("Cookie") String userCookie, @Query("partyId") Integer partyId);

    @GET("visionauth/login?")
    Call<LoginResponse> login(@Query("username") String username,@Query("password") String password);

}
