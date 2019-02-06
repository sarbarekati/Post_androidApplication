package com.anad.mobile.post.Models.DashboardModels;

import com.google.gson.annotations.SerializedName;

public class BaseDashboardModel extends IDashboardModel {

    @SerializedName("Name")
    private String name;
    @SerializedName("Org_ID")
    private int orgId;
    @SerializedName("Cnt")
    private int count;

    public BaseDashboardModel(String name, int orgId, int count) {
        this.name = name;
        this.orgId = orgId;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getOrgId() {
        return orgId;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String getTitle() {
        return name;
    }

}
