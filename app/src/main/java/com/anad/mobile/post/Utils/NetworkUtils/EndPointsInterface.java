package com.anad.mobile.post.Utils.NetworkUtils;

import com.anad.mobile.post.AccountManager.model.LoginResponse;
import com.anad.mobile.post.AccountManager.model.PartyAssign;
import com.anad.mobile.post.ReportManager.model.ARP.ARPReport;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariReport;
import com.anad.mobile.post.ReportManager.model.Base.SearchReportItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EndPointsInterface {

    @GET("main/partyassign/getbypartyid?")
    Call<List<PartyAssign>> getRoleOfClient(@Header("Cookie") String userCookie, @Query("partyId") Integer partyId);

    @GET("visionauth/login?")
    Call<LoginResponse> login(@Query("username") String username,@Query("password") String password);

    @POST("Main/RahsepariReport/MobileGetAll")
    Call<List<RahsepariReport>> getRahsepariReports(@Header("Cookie") String userCookie,@Body SearchReportItem searchItem);

    @POST("Main/ARPReport/MobileARPGetAll")
    Call<List<ARPReport>> getARPReports(@Header("Cookie") String userCookie,@Body SearchReportItem searchItem);


}
