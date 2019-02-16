package com.anad.mobile.post.ReportManager.model.ARP;

import com.anad.mobile.post.ReportManager.model.Base.Report;
import com.google.gson.annotations.SerializedName;

public class ARPReport extends Report {

    @SerializedName("ARPReportId")
    private int ARPReportId;

    public int getARPReportId() {
        return ARPReportId;
    }

    public void setARPReportId(int ARPReportId) {
        this.ARPReportId = ARPReportId;
    }
}
