package com.anad.mobile.post.MapManager.Manager;

import android.content.Context;

import com.anad.mobile.post.MapManager.Model.IMapView;
import com.anad.mobile.post.MapManager.Model.LastPosition;
import com.anad.mobile.post.MapManager.Model.MapResponse;
import com.anad.mobile.post.MapManager.Model.SearchLastPositionItem;
import com.anad.mobile.post.MapManager.api.MapApiCaller;
import com.anad.mobile.post.Storage.PostSharedPreferences;

import java.util.List;

public class MapManager implements MapResponse {


    private MapApiCaller apiCaller;
    private PostSharedPreferences preferences;
    private IMapView view;

    public MapManager(Context context,IMapView view) {
        preferences = new PostSharedPreferences(context);
        apiCaller = MapApiCaller.getInstance(context);
        apiCaller.setMapResponse(this);
        this.view = view;
    }


    public void callLastPosition(SearchLastPositionItem search){
        apiCaller.callLastPositionApi(preferences.getCookies(),search);
    }


    @Override
    public void onSuccessLastPosition(List<LastPosition> lastPositions) {
        view.fillLastPosition(lastPositions);
    }

    @Override
    public void onFailed(String message) {

    }



}
