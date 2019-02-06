package com.anad.mobile.post.Models.DashboardModels;

import com.google.gson.annotations.SerializedName;


public class YearDashboardModel extends IDashboardModel {
    @SerializedName("yearTitle")
    private String title;
    @SerializedName("count")
    private int count;

    public YearDashboardModel(String title,int count){
        this.title = title;
        this.count = count;
    }

    public String getTitle(){
        return title;
    }


    public int getCount(){
        return count;
    }


}
