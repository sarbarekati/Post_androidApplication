package com.anad.mobile.post.Activity.ShowPath;


import android.content.Context;

import com.anad.mobile.post.API.TreeItemApi.ITreeItemResponse;
import com.anad.mobile.post.API.TreeItemApi.TreeItemApi;
import com.anad.mobile.post.Models.FilterModel.CarTreeItem;
import com.anad.mobile.post.Models.FilterModel.TreeItem;
import com.anad.mobile.post.Models.ShowPathModels.IDailyRouteResponse;
import com.anad.mobile.post.Models.ShowPathModels.Route;
import com.anad.mobile.post.ReportManager.api.ReportApiCaller;
import com.anad.mobile.post.Storage.PostSharedPreferences;

import java.util.List;

public class ShowPathPresenter implements ITreeItemResponse, IDailyRouteResponse {

    private ShowPathView view;
    private TreeItemApi treeItemApi;
    private PostSharedPreferences preferences;
    private ReportApiCaller apiCaller;

    public ShowPathPresenter(ShowPathView view, PostSharedPreferences preferences, Context context) {
        this.view = view;
        treeItemApi = TreeItemApi.getInstance(context);
        apiCaller = ReportApiCaller.getInstance(context);
        this.preferences = preferences;
    }


    public void fillDataFilter(Integer stateId) {
        treeItemApi.setTreeItemResponse(this);
        if (stateId == -1) {
            treeItemApi.callTreeItemApi(preferences.getCookies(), stateId);
        } else {
            treeItemApi.callCarItemApi(preferences.getCookies(), stateId);
        }

    }


    public void getDailyRoute(Integer carId,String startDate,String endDate){
        apiCaller.setIDailyRouteResponse(this);
        apiCaller.callDailyRouteApi(preferences.getCookies(),carId,startDate,endDate);
    }


    @Override
    public void onSuccessTreeItem(List<TreeItem> treeItems) {
        if (checkNull(treeItems)) {
            view.fillFilter(treeItems);
        }
    }

    @Override
    public void onSuccessCarTreeItem(List<CarTreeItem> treeItems) {
        if (checkNull(treeItems)) {
            view.fillCar(treeItems);
        }
    }

    @Override
    public void onSuccessDailyRoute(List<Route> routes) {
        if (checkNull(routes)) {
            view.renderDailyRoute(routes);
        }
    }

    @Override
    public void onFailed(String message) {

    }


    private <T extends Object> boolean checkNull(List<T> list) {
        return (list != null && !list.isEmpty());
    }
}
