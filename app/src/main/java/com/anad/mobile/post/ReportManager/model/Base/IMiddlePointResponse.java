package com.anad.mobile.post.ReportManager.model.Base;


import com.anad.mobile.post.ReportManager.model.ARP.ARPMiddlePoint;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariMiddlePoint;

import java.util.List;

public interface IMiddlePointResponse {

    void onSuccessRahsepariMiddlePoint(List<RahsepariMiddlePoint> middlePoints);
    void onSuccessARPMiddlePoint(List<ARPMiddlePoint> middlePoints);



}
