package com.anad.mobile.post.ReportManager.model.Base;

import com.anad.mobile.post.Models.Line;
import com.anad.mobile.post.ReportManager.model.ARP.ARPReport;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariMiddlePoint;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariReport;

import java.util.List;

public interface IReportResponse {

    void onSuccessRahsepari(List<RahsepariReport> response);
    void onSuccessARP(List<ARPReport> response);
    void onSuccessLine(List<Line> lines);
    void onFailed(String message);

}
