package com.anad.mobile.post.ReportManager;


import android.os.Bundle;

import com.anad.mobile.post.Activity.RahRFIDFilter.IRahRFIDFilter;
import com.anad.mobile.post.Activity.RahRFIDFilter.RahRFIDFilter;
import com.anad.mobile.post.Base.IReportView;
import com.anad.mobile.post.Models.Line;
import com.anad.mobile.post.ReportManager.api.ReportApiCaller;
import com.anad.mobile.post.ReportManager.model.ARP.ARPReport;
import com.anad.mobile.post.ReportManager.model.Base.IReportResponse;
import com.anad.mobile.post.ReportManager.model.Base.SearchReportItem;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariReport;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportManager implements IReportResponse {


    private IRahRFIDFilter view;
    private ReportApiCaller reportCaller;
    private PostSharedPreferences preferences;
    private Gson gson;
    private int reportId;

    public ReportManager(IRahRFIDFilter view,PostSharedPreferences preferences){
        this.view = view;
        this.preferences = preferences;
        gson = new Gson();
    }




    public void callReportApi(int Mode, SearchReportItem searchReportItem){

        reportCaller.setReportResponseListener(this);

        reportId = Mode;

        switch (Mode){

            case Constants.RAHSEPARI_REPORT:

                reportCaller.callRahsepariReportApi(getCookies(),searchReportItem);
                break;
            case Constants.ARP_REPORT:
                reportCaller.callARPReportApi(getCookies(),searchReportItem);
                break;
            }

    }


    public void callGetLineApi(){
        reportCaller.setReportResponseListener(this);
        reportCaller.callAllLineApi(getCookies());
    }

    private String getCookies(){
        return preferences.getCookies();
    }

    @Override
    public void onSuccessRahsepari(List<RahsepariReport> response) {

        Type t = new TypeToken<ArrayList<RahsepariReport>>(){}.getType();
        String reportToSend = gson.toJson(response,t);
        Bundle bundle = new Bundle();
        bundle.putString(RahRFIDFilter.REPORT_INFO,reportToSend);
        bundle.putInt(RahRFIDFilter.REPORT_ID,reportId);

        view.startActivity(bundle);
    }

    @Override
    public void onSuccessARP(List<ARPReport> response) {

        Type t = new TypeToken<ArrayList<ARPReport>>(){}.getType();
        String reportToSend = gson.toJson(response,t);
        Bundle bundle = new Bundle();
        bundle.putString(RahRFIDFilter.REPORT_INFO,reportToSend);
        bundle.putInt(RahRFIDFilter.REPORT_ID,reportId);

        view.startActivity(bundle);

    }

    @Override
    public void onSuccessLine(List<Line> lines) {

        Map<String,Long> m = new HashMap<>();
        for (Line line : lines) {
            m.put(line.getLineTitle(),line.getLineId());
        }
        view.fillLineData(m);

    }

    @Override
    public void onFailed(String message) {

    }
}
