package com.anad.mobile.post.ReportManager.model.Rahsepari;

import com.anad.mobile.post.ReportManager.model.Base.MiddlePoint;
import com.google.gson.annotations.SerializedName;

public class RahsepariMiddlePoint extends MiddlePoint {

    @SerializedName("RahsepariMiddlePointId")
    private int RahsepariMiddlePointId;
    @SerializedName("RahsepariReportId")
    private int RahsepariReportId;

    public int getRahsepariMiddlePointId() {
        return RahsepariMiddlePointId;
    }

    public void setRahsepariMiddlePointId(int rahsepariMiddlePointId) {
        RahsepariMiddlePointId = rahsepariMiddlePointId;
    }

    public int getRahsepariReportId() {
        return RahsepariReportId;
    }

    public void setRahsepariReportId(int rahsepariReportId) {
        RahsepariReportId = rahsepariReportId;
    }
}
