package com.anad.mobile.post;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.anad.mobile.post.ReportManager.api.ReportApiCaller;
import com.anad.mobile.post.ReportManager.model.ARP.ARPReport;
import com.anad.mobile.post.ReportManager.model.Base.IReportResponse;
import com.anad.mobile.post.ReportManager.model.Base.SearchReportItem;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariReport;
import com.anad.mobile.post.Storage.PostSharedPreferences;

import java.util.List;

public class testActivity extends AppCompatActivity implements IReportResponse {


    private static final String TAG = "testActivity";
    PostSharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        preferences = new PostSharedPreferences(this);

        ReportApiCaller api = ReportApiCaller.getInstance(this);
        api.setReportResponseListener(this);

        api.callRahsepariReportApi(preferences.getCookies(),SearchReportItem.createReportFilter(null,
                null,
                "2018/10/30",
                "2019/01/01",
                null,
                0,
                "00:00",
                "00:00",
                0,
                0,
                0,
                0));

        api.callARPReportApi(preferences.getCookies(),SearchReportItem.createReportFilter(null,
                null,
                "2018/10/30",
                "2019/01/01",
                null,
                0,
                "00:00",
                "00:00",
                0,
                0,
                0,
                0));


    }
    @Override
    public void onSuccessRahsepari(List<RahsepariReport> response) {

        Log.d(TAG, "onSuccessRahsepari: " + response.size());


    }

    @Override
    public void onSuccessARP(List<ARPReport> response) {
        Log.d(TAG, "onSuccessARP: "+response.size());
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
