package com.anad.mobile.post.Activity.ShowPath;

import com.anad.mobile.post.Models.FilterModel.CarTreeItem;
import com.anad.mobile.post.Models.FilterModel.TreeItem;
import com.anad.mobile.post.Models.ShowPathModels.Route;

import java.util.List;

public interface ShowPathView {

    void fillFilter(List<TreeItem> treeItems);
    void fillCar(List<CarTreeItem> carTreeItems);
    void renderDailyRoute(List<Route> routes);

}
