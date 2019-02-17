package com.anad.mobile.post.ReportManager.model.Rahsepari;


import com.anad.mobile.post.ReportManager.model.Base.Report;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RahsepariReport extends Report{



    @SerializedName("RahsepariReportId")
    @Expose
    public Integer rahsepariReportId;

    public Integer getRahsepariReportId() {
        return rahsepariReportId;
    }

    public void setRahsepariReportId(Integer rahsepariReportId) {
        this.rahsepariReportId = rahsepariReportId;
    }
}
