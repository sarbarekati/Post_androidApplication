package com.anad.mobile.post.Activity.ShowReport;


import com.anad.mobile.post.ReportManager.model.ARP.ARPMiddlePoint;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariMiddlePoint;

import java.util.List;

public interface IShowReport {

    void setRahsepariMiddlePoint(List<RahsepariMiddlePoint> middlePoints);
    void setARPMiddlePoint(List<ARPMiddlePoint> middlePoints);


}
