package com.anad.mobile.post.Models.DashboardModels;

import com.google.gson.annotations.SerializedName;

public class FormDashboardModel extends IDashboardModel {

    @SerializedName("Route_Name")
    private String routeName;
    @SerializedName("Count")
    private int count;

    public FormDashboardModel(String routeName, int count) {
        this.routeName = routeName;
        this.count = count;
    }

    public String getRouteName() {
        return routeName;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String getTitle() {
        return routeName;
    }

}
