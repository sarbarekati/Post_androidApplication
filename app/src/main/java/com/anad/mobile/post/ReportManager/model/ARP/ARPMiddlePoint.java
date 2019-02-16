package com.anad.mobile.post.ReportManager.model.ARP;

import com.anad.mobile.post.Models.MiddlePoint;
import com.google.gson.annotations.SerializedName;

public class ARPMiddlePoint extends MiddlePoint {

    @SerializedName("ARPMiddlePointId")
    private int ARPMiddlePointId;
    @SerializedName("ARPReportId")
    private int ARPReportId;
}
