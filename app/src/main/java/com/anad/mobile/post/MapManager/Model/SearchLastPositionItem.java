package com.anad.mobile.post.MapManager.Model;


import com.anad.mobile.post.Utils.DataTimeUtils.DateTimeUtils;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchLastPositionItem {

    @SerializedName("DeviceIds")
    private List<Integer> DeviceIds;
    @SerializedName("IsActive")
    private boolean IsActive;
    @SerializedName("LastDate")
    private String LastDate;
    @SerializedName("LastTime")
    private String LastTime;


    private SearchLastPositionItem(List<Integer> deviceIds, boolean isActive, String lastDate, String lastTime) {
        this.DeviceIds = deviceIds;
        this.IsActive = isActive;
        this.LastDate = lastDate;
        this.LastTime = lastTime;
    }

    public static SearchLastPositionItem createSearchLastPositionItem(List<Integer> deviceIds, boolean isActive) {
        return new SearchLastPositionItem(deviceIds, isActive, DateTimeUtils.getCurrentDate(), DateTimeUtils.getCurrentHour());
    }


    public List<Integer> getDeviceIds() {
        return DeviceIds;
    }

    public void setDeviceIds(List<Integer> deviceIds) {
        this.DeviceIds = deviceIds;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public String getLastDate() {
        return LastDate;
    }

    public void setLastDate(String lastDate) {
        this.LastDate = lastDate;
    }

    public String getLastTime() {
        return LastTime;
    }

    public void setLastTime(String lastTime) {
        this.LastTime = lastTime;
    }
}
