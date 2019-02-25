package com.anad.mobile.post.MapManager.Model;

import java.util.List;

public interface MapResponse {
    void onSuccessLastPosition(List<LastPosition> body);

    void onFailed(String message);
}
