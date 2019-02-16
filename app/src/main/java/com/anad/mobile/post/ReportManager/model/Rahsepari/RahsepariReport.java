package com.anad.mobile.post.ReportManager.model.Rahsepari;


import com.anad.mobile.post.ReportManager.model.Base.Report;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RahsepariReport  {

    private Report report;


    @SerializedName("RahsepariReportId")
    @Expose
    public Integer rahsepariReportId;

    public int getRahsepariReportId() {
        return rahsepariReportId;
    }

    public void setRahsepariReportId(int rahsepariReportId) {
        this.rahsepariReportId = rahsepariReportId;
    }
}
