package com.anad.mobile.post.Models.ShowPathModels;

import java.util.List;

public interface IDailyRouteResponse {

    void onSuccessDailyRoute(List<Route> routes);
    void onFailed(String message);
}
