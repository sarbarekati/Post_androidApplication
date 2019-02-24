package com.anad.mobile.post.Activity.RahRFIDFilter;

import com.anad.mobile.post.Base.IReportView;
import com.anad.mobile.post.Models.FilterModel.CarTreeItem;
import com.anad.mobile.post.Models.FilterModel.TreeItem;

import java.util.List;
public interface IRahRFIDReport extends IReportView {

    void fillLineData(List<String> lines);
    void fillTreeItem(List<TreeItem> treeItems);
    void fillCarTreeItem(List<CarTreeItem> treeItems);

}
