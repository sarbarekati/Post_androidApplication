package com.anad.mobile.post.ReportManager.api;

import java.util.List;

public interface OnReportResponse<T> {

    void onSuccess(T entity);
    void onFailed();
    void onSuccess(List<T> entities);

}
