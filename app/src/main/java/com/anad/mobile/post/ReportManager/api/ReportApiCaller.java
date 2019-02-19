package com.anad.mobile.post.ReportManager.api;


import android.content.Context;
import android.util.Log;

import com.anad.mobile.post.API.Retrofit.ApiClient;
import com.anad.mobile.post.Models.Line;
import com.anad.mobile.post.ReportManager.model.ARP.ARPMiddlePoint;
import com.anad.mobile.post.ReportManager.model.ARP.ARPReport;
import com.anad.mobile.post.ReportManager.model.Base.IMiddlePointResponse;
import com.anad.mobile.post.ReportManager.model.Base.IReportResponse;
import com.anad.mobile.post.ReportManager.model.Base.Report;
import com.anad.mobile.post.ReportManager.model.Base.SearchReportItem;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariMiddlePoint;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariReport;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.NetworkUtils.EndPointsInterface;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportApiCaller {

    private Context context;
    private static ReportApiCaller reportApiCaller = null;
    private IReportResponse reportResponseListener;
    private IMiddlePointResponse iMiddlePointResponse;

    private static final String TAG = "ReportApiCaller";

    private ReportApiCaller(Context context) {
        this.context = context;
    }

    public static ReportApiCaller getInstance(Context context) {
        if (reportApiCaller == null) {
            reportApiCaller = new ReportApiCaller(context);
        }
        return reportApiCaller;
    }

    public void setMiddlePointResponseListener(IMiddlePointResponse middlePointResponseListener){
        this.iMiddlePointResponse = middlePointResponseListener;
    }

    public void setReportResponseListener(IReportResponse reportResponseListener) {
        this.reportResponseListener = reportResponseListener;
    }

    public void callRahsepariReportApi(String cookies, SearchReportItem search) {


        Call<List<RahsepariReport>> call = ApiClient.getInstance(context)
                .create(EndPointsInterface.class)
                .getRahsepariReports(cookies, search);

        call.enqueue(new Callback<List<RahsepariReport>>() {
            @Override
            public void onResponse(Call<List<RahsepariReport>> call, Response<List<RahsepariReport>> response) {
                reportResponseListener.onSuccessRahsepari(response.body());
            }

            @Override
            public void onFailure(Call<List<RahsepariReport>> call, Throwable t) {
                reportResponseListener.onFailed(t.getMessage());
            }
        });
    }

    public void callARPReportApi(String cookies, SearchReportItem searchReportItem) {

        Call<List<ARPReport>> call = ApiClient.getInstance(context)
                .create(EndPointsInterface.class)
                .getARPReports(cookies, searchReportItem);
        call.enqueue(new Callback<List<ARPReport>>() {
            @Override
            public void onResponse(Call<List<ARPReport>> call, Response<List<ARPReport>> response) {
                reportResponseListener.onSuccessARP(response.body());
            }

            @Override
            public void onFailure(Call<List<ARPReport>> call, Throwable t) {
                reportResponseListener.onFailed(t.getMessage());
            }
        });


    }

    public void callAllLineApi(String cookie) {
        Call<List<Line>> call = ApiClient.getInstance(context).create(EndPointsInterface.class).getAllLines(cookie);
        call.enqueue(new Callback<List<Line>>() {
            @Override
            public void onResponse(Call<List<Line>> call, Response<List<Line>> response) {
                reportResponseListener.onSuccessLine(response.body());
            }

            @Override
            public void onFailure(Call<List<Line>> call, Throwable t) {
                reportResponseListener.onFailed(t.toString());
            }
        });
    }


    public void callRahsepariMiddleApi(String cookie,long id) {
        Call<List<RahsepariMiddlePoint>> call = ApiClient.getInstance(context)
                .create(EndPointsInterface.class)
                .getRahsepariMiddlePoint(cookie, id);

        call.enqueue(new Callback<List<RahsepariMiddlePoint>>() {
            @Override
            public void onResponse(Call<List<RahsepariMiddlePoint>> call, Response<List<RahsepariMiddlePoint>> response) {
                iMiddlePointResponse.onSuccessRahsepariMiddlePoint(response.body());
            }

            @Override
            public void onFailure(Call<List<RahsepariMiddlePoint>> call, Throwable t) {
                reportResponseListener.onFailed(t.toString());
            }
        });
    }

    public void callARPMiddleApi(String cookie,long id) {
        Call<List<ARPMiddlePoint>> call = ApiClient.getInstance(context)
                .create(EndPointsInterface.class)
                .getARPMiddlePoint(cookie, id);

        call.enqueue(new Callback<List<ARPMiddlePoint>>() {
            @Override
            public void onResponse(Call<List<ARPMiddlePoint>> call, Response<List<ARPMiddlePoint>> response) {
                iMiddlePointResponse.onSuccessARPMiddlePoint(response.body());
            }

            @Override
            public void onFailure(Call<List<ARPMiddlePoint>> call, Throwable t) {
                reportResponseListener.onFailed(t.toString());
            }
        });
    }


}
