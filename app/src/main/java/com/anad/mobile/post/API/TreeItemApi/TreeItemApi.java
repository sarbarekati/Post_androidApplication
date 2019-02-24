package com.anad.mobile.post.API.TreeItemApi;


import android.content.Context;

import com.anad.mobile.post.API.Retrofit.ApiClient;
import com.anad.mobile.post.Models.FilterModel.CarTreeItem;
import com.anad.mobile.post.Models.FilterModel.TreeItem;
import com.anad.mobile.post.Utils.NetworkUtils.EndPointsInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TreeItemApi {

    private static TreeItemApi object = null;
    private Context context;
    private ITreeItemResponse treeItemResponse;
    private TreeItemApi(Context context) {
        this.context = context;
    }


    public static TreeItemApi getInstance(Context context) {
        if (object == null)
            object = new TreeItemApi(context);
        return object;
    }

    public void callTreeItemApi(String cookies, Integer stateId) {
        Call<List<TreeItem>> call = ApiClient.getInstance(context).create(EndPointsInterface.class)
                .getTreeItem(cookies,stateId);
        call.enqueue(new Callback<List<TreeItem>>() {
            @Override
            public void onResponse(Call<List<TreeItem>> call, Response<List<TreeItem>> response) {
                treeItemResponse.onSuccessTreeItem(response.body());
            }

            @Override
            public void onFailure(Call<List<TreeItem>> call, Throwable t) {
                treeItemResponse.onFailed(t.getMessage());
            }
        });
    }

    public void callCarItemApi(String cookies, Integer stateId) {
        Call<List<CarTreeItem>> call = ApiClient.getInstance(context).create(EndPointsInterface.class)
                .getCarItem(cookies,stateId);
        call.enqueue(new Callback<List<CarTreeItem>>() {
            @Override
            public void onResponse(Call<List<CarTreeItem>> call, Response<List<CarTreeItem>> response) {
                treeItemResponse.onSuccessCarTreeItem(response.body());
            }

            @Override
            public void onFailure(Call<List<CarTreeItem>> call, Throwable t) {
                treeItemResponse.onFailed(t.getMessage());
            }
        });
    }

    public void setTreeItemResponse(ITreeItemResponse treeItemResponse) {
        this.treeItemResponse = treeItemResponse;
    }


}
