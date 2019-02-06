package com.anad.mobile.post.Models.DashboardModels;

import com.google.gson.annotations.SerializedName;

public class SpeedDashboardModel extends IDashboardModel {

    @SerializedName("Org_Name")
    private String orgName;
    @SerializedName("Cnt")
    private int count;

    public SpeedDashboardModel(String orgName,int count){
        this.orgName = orgName;
        this.count = count;

    }

    public String getOrgName() {
        return orgName;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String getTitle() {
        return orgName;
    }

}
