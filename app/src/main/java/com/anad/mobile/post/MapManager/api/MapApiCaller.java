package com.anad.mobile.post.MapManager.api;


import android.content.Context;

import com.anad.mobile.post.API.Retrofit.ApiClient;
import com.anad.mobile.post.MapManager.Model.LastPosition;
import com.anad.mobile.post.MapManager.Model.SearchLastPositionItem;
import com.anad.mobile.post.MapManager.Model.MapResponse;
import com.anad.mobile.post.Utils.NetworkUtils.EndPointsInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapApiCaller {

    private static MapApiCaller apiCaller = null;
    private Context context;
    private MapApiCaller(Context context) {
        this.context = context;
    }
    private MapResponse mapResponse;


    public void setMapResponse(MapResponse response){
        this.mapResponse = response;
    }


    public static MapApiCaller getInstance(Context context) {
        if (apiCaller == null)
            apiCaller = new MapApiCaller(context);
        return apiCaller;
    }


    public void callLastPositionApi(String cookies, SearchLastPositionItem search){

        Call<List<LastPosition>> call = ApiClient.getInstance(context).create(EndPointsInterface.class)
                                                .getLastPosition(cookies,search);
        call.enqueue(new Callback<List<LastPosition>>() {
            @Override
            public void onResponse(Call<List<LastPosition>> call, Response<List<LastPosition>> response) {
                mapResponse.onSuccessLastPosition(response.body());
            }

            @Override
            public void onFailure(Call<List<LastPosition>> call, Throwable t) {
                    mapResponse.onFailed(t.getMessage());
            }
        });
    }


}
