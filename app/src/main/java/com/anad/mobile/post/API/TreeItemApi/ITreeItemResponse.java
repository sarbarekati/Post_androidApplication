package com.anad.mobile.post.API.TreeItemApi;

import com.anad.mobile.post.Models.FilterModel.CarTreeItem;
import com.anad.mobile.post.Models.FilterModel.TreeItem;

import java.util.List;

public interface ITreeItemResponse {
    void onSuccessTreeItem(List<TreeItem> treeItems);
    void onSuccessCarTreeItem(List<CarTreeItem> treeItems);
    void onFailed(String message);

}
