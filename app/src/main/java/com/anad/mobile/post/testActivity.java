package com.anad.mobile.post;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.anad.mobile.post.AccountManager.api.LoginApi;
import com.anad.mobile.post.AccountManager.model.LoginResponse;
import com.anad.mobile.post.AccountManager.model.OnLoginResponse;
import com.anad.mobile.post.AccountManager.model.PartyAssign;
import com.anad.mobile.post.Models.Line;
import com.anad.mobile.post.ReportManager.api.ReportApiCaller;
import com.anad.mobile.post.ReportManager.model.ARP.ARPReport;
import com.anad.mobile.post.ReportManager.model.Base.IReportResponse;
import com.anad.mobile.post.ReportManager.model.Base.SearchReportItem;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariReport;
import com.anad.mobile.post.Storage.PostSharedPreferences;

import java.util.List;

public class testActivity extends AppCompatActivity implements IReportResponse,OnLoginResponse {


    private static final String TAG = "testActivity";
    PostSharedPreferences preferences;
    ReportApiCaller api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        preferences = new PostSharedPreferences(this);

        api = ReportApiCaller.getInstance(this);
        api.setReportResponseListener(this);

        LoginApi loginApi = LoginApi.getInstance(this,preferences);
        loginApi.setOnLoginResponse(this);
        loginApi.callLoginApi("admin","908070");


    }

    @Override
    public void onSuccessRahsepari(List<RahsepariReport> response) {

        if (response != null) {
            Log.d(TAG, "onSuccessRahsepari: " + response.size());
        }


    }

    @Override
    public void onSuccessARP(List<ARPReport> response) {
        if (response != null) {
            Log.d(TAG, "onSuccessARP: " + response.size());
        }
    }

    @Override
    public void onSuccessLine(List<Line> lines) {

    }

    @Override
    public void onSuccess(LoginResponse loginResponse, String cookie) {

        if(loginResponse.isSuccessful()){


        api.callRahsepariReportApi(preferences.getCookies(), SearchReportItem.createReportFilter(null,
                null,
                "2018/01/01",
                "2020/01/01",
                null,
                0,
                "00:00",
                "00:00",
                0,
                0,
                0,
                0));

        api.callARPReportApi(preferences.getCookies(), SearchReportItem.createReportFilter(null,
                null,
                "2018/01/01",
                "2020/01/01",
                null,
                0,
                "00:00",
                "00:00",
                0,
                0,
                0,
                0));
        }
    }

    @Override
    public void onRoleApiCallSuccess(List<PartyAssign> response) {

    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
