package com.anad.mobile.post.ReportManager.model.Base;

import com.anad.mobile.post.ReportManager.model.ARP.ARPReport;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariReport;

import java.util.List;

public interface IReportResponse {

    void onSuccessRahsepari(List<RahsepariReport> response);
    void onSuccessARP(List<ARPReport> response);
    void onFailed(String message);

}
