package com.anad.mobile.post.ReportManager.Managers;


import android.content.Context;
import android.os.Bundle;

import com.anad.mobile.post.API.TreeItemApi.ITreeItemResponse;
import com.anad.mobile.post.API.TreeItemApi.TreeItemApi;
import com.anad.mobile.post.AccountManager.api.LoginApi;
import com.anad.mobile.post.AccountManager.model.LoginResponse;
import com.anad.mobile.post.AccountManager.model.OnLoginResponse;
import com.anad.mobile.post.AccountManager.model.PartyAssign;
import com.anad.mobile.post.Activity.RahRFIDFilter.IRahRFIDReport;
import com.anad.mobile.post.Activity.RahRFIDFilter.RahRFIDFilterActivity;
import com.anad.mobile.post.Models.FilterModel.CarTreeItem;
import com.anad.mobile.post.Models.Line;
import com.anad.mobile.post.Models.FilterModel.TreeItem;
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

public class ReportManager implements IReportResponse, OnLoginResponse, ITreeItemResponse {


    private IRahRFIDReport view;
    private ReportApiCaller reportCaller;
    private PostSharedPreferences preferences;
    private Gson gson;
    private int reportId;
    private Map<String, Long> linesInfo;
    private Context context;
    private TreeItemApi treeItemApi;

    public ReportManager(IRahRFIDReport view, PostSharedPreferences preferences, Context context) {
        this.view = view;
        this.preferences = preferences;
        gson = new Gson();
        reportCaller = ReportApiCaller.getInstance(context);
        treeItemApi = TreeItemApi.getInstance(context);
    }

    public void callReportApi(int Mode, SearchReportItem searchReportItem) {

        reportCaller.setReportResponseListener(this);

        reportId = Mode;

        switch (Mode) {

            case Constants.RAHSEPARI_REPORT:

                reportCaller.callRahsepariReportApi(getCookies(), searchReportItem);
                break;
            case Constants.ARP_REPORT:
                reportCaller.callARPReportApi(getCookies(), searchReportItem);
                break;
        }

    }

    public void callGetLineApi() {

        LoginApi api = LoginApi.getInstance(context, preferences);
        api.setOnLoginResponse(this);
        api.callLoginApi("admin", "908070");


    }


    public void callGetTreeItem(Integer stateId) {
        treeItemApi.setTreeItemResponse(this);
        //for call state and country tree item data
        if (stateId == -1) {
            treeItemApi.callTreeItemApi(getCookies(), stateId);
        }else{
            treeItemApi.callCarItemApi(getCookies(),stateId);
        }
    }


    private String getCookies() {
        return preferences.getCookies();
    }

    @Override
    public void onSuccessRahsepari(List<RahsepariReport> response) {

        Type t = new TypeToken<ArrayList<RahsepariReport>>() {
        }.getType();
        String reportToSend = gson.toJson(response, t);
        Bundle bundle = new Bundle();
        bundle.putString(RahRFIDFilterActivity.REPORT_INFO, reportToSend);
        bundle.putInt(RahRFIDFilterActivity.REPORT_ID, reportId);

        view.startActivity(bundle);
    }

    @Override
    public void onSuccessARP(List<ARPReport> response) {

        Type t = new TypeToken<ArrayList<ARPReport>>() {
        }.getType();
        String reportToSend = gson.toJson(response, t);
        Bundle bundle = new Bundle();
        bundle.putString(RahRFIDFilterActivity.REPORT_INFO, reportToSend);
        bundle.putInt(RahRFIDFilterActivity.REPORT_ID, reportId);

        view.startActivity(bundle);

    }

    @Override
    public void onSuccessLine(List<Line> lines) {

        linesInfo = new HashMap<>();

        List<String> linesName = new ArrayList<>();

        for (Line line : lines) {
            linesName.add(line.getLineTitle());
            linesInfo.put(line.getLineTitle(), line.getLineId());
        }

        view.fillLineData(linesName);

    }

    @Override
    public void onSuccess(LoginResponse loginResponse, String cookie) {
        if (loginResponse.isSuccessful()) {
            reportCaller.setReportResponseListener(this);
            reportCaller.callAllLineApi(getCookies());
        }
    }

    @Override
    public void onRoleApiCallSuccess(List<PartyAssign> response) {

    }

    @Override
    public void onSuccessTreeItem(List<TreeItem> treeItems) {
        List<TreeItem> treeItemList = new ArrayList<>();
        TreeItem all = new TreeItem();
        all.setText("همه موارد");
        all.setId(-1);
        treeItemList.add(all);
        if (treeItems != null && !treeItems.isEmpty()) {
            treeItemList.addAll(1,treeItems);
            view.fillTreeItem(treeItemList);
        }
    }

    @Override
    public void onSuccessCarTreeItem(List<CarTreeItem> treeItems) {
        if (treeItems != null && !treeItems.isEmpty()) {
            view.fillCarTreeItem(treeItems);
        }
    }

    @Override
    public void onFailed(String message) {

    }


    public long setLineIdWithName(String name) {

        if (!name.equals("")) {
            return linesInfo.get(name);
        }
        return -1;
    }
}
