package com.anad.mobile.post.Utils.NetworkUtils;

import com.anad.mobile.post.AccountManager.model.LoginResponse;
import com.anad.mobile.post.AccountManager.model.PartyAssign;
import com.anad.mobile.post.Models.Line;
import com.anad.mobile.post.ReportManager.model.ARP.ARPMiddlePoint;
import com.anad.mobile.post.ReportManager.model.ARP.ARPReport;
import com.anad.mobile.post.ReportManager.model.Base.Report;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariMiddlePoint;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariReport;
import com.anad.mobile.post.ReportManager.model.Base.SearchReportItem;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
    Call<List<RahsepariReport>> getRahsepariReports(@Header("Cookie") String userCookie, @Body SearchReportItem searchItem);

    @POST("Main/ARPReport/MobileARPGetAll")
    Call<List<ARPReport>> getARPReports(@Header("Cookie") String userCookie,@Body SearchReportItem searchItem);

    @GET("Main/Line/MobileGetAllLine")
    Call<List<Line>> getAllLines(@Header("Cookie") String userCookie);

    @GET("Main/RahsepariMiddlePoint/MobileGetByRahsepariReportId")
    Call<List<RahsepariMiddlePoint>> getRahsepariMiddlePoint(@Header("Cookie") String userCookie,@Query("id") Long rahsepariReportId);

    @GET("Main/ARPMiddlePoint/MobileGetByARPReportId")
    Call<List<ARPMiddlePoint>> getARPMiddlePoint(@Header("Cookie") String userCookie,@Query("id") Long arpReportId);

}
