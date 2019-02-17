package com.anad.mobile.post.Activity.RahRFIDFilter;

import com.anad.mobile.post.Base.IReportView;
import com.anad.mobile.post.Models.Line;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IRahRFIDFilter extends IReportView {

    void fillLineData(Map<String,Long> lines);


}
