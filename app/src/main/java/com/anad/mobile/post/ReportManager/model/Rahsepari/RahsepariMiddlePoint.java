package com.anad.mobile.post.ReportManager.model.Rahsepari;

import com.anad.mobile.post.ReportManager.model.Base.MiddlePoint;
import com.google.gson.annotations.SerializedName;

public class RahsepariMiddlePoint extends MiddlePoint {

    @SerializedName("RahsepariMiddlePointId")
    private Long RahsepariMiddlePointId;
    @SerializedName("RahsepariReportId")
    private Long RahsepariReportId;

    public Long getRahsepariMiddlePointId() {
        return RahsepariMiddlePointId;
    }

    public void setRahsepariMiddlePointId(Long rahsepariMiddlePointId) {
        RahsepariMiddlePointId = rahsepariMiddlePointId;
    }

    public Long getRahsepariReportId() {
        return RahsepariReportId;
    }

    public void setRahsepariReportId(Long rahsepariReportId) {
        RahsepariReportId = rahsepariReportId;
    }
}
