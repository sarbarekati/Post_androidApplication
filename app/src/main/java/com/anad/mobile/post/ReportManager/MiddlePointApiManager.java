package com.anad.mobile.post.ReportManager;


import android.content.Context;

import com.anad.mobile.post.Activity.ShowReport.IShowReport;
import com.anad.mobile.post.ReportManager.api.ReportApiCaller;
import com.anad.mobile.post.ReportManager.model.ARP.ARPMiddlePoint;
import com.anad.mobile.post.ReportManager.model.Base.IMiddlePointResponse;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariMiddlePoint;
import com.anad.mobile.post.Storage.PostSharedPreferences;

import java.util.List;

public class MiddlePointApiManager implements IMiddlePointResponse {

    private IShowReport view;
    private ReportApiCaller reportApiCaller;
    private Context context;
    private PostSharedPreferences preferences;

    public MiddlePointApiManager(IShowReport view, PostSharedPreferences preferences, Context context) {
        this.view = view;
        this.preferences = preferences;
        this.context = context;
        reportApiCaller = ReportApiCaller.getInstance(context);
        reportApiCaller.setMiddlePointResponseListener(this);
    }

    public void callGetRahsepariMiddlePoint(long rahsepariReportId) {

        reportApiCaller.callRahsepariMiddleApi(getCookies(), rahsepariReportId);
    }

    public void callGetARPMiddlePoint(long arpReportId){
        reportApiCaller.callARPMiddleApi(getCookies(),arpReportId);

    }



    private String getCookies() {
        return preferences.getCookies();
    }

    @Override
    public void onSuccessRahsepariMiddlePoint(List<RahsepariMiddlePoint> middlePoints) {
        if (middlePoints != null) {

            view.setRahsepariMiddlePoint(middlePoints);
        }
    }

    @Override
    public void onSuccessARPMiddlePoint(List<ARPMiddlePoint> middlePoints) {
        if (middlePoints != null) {
            view.setARPMiddlePoint(middlePoints);
        }
    }


}
