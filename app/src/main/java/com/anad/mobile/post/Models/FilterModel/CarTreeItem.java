package com.anad.mobile.post.Models.FilterModel;


import com.google.gson.annotations.SerializedName;

public class CarTreeItem extends TreeItem {

    @SerializedName("DeviceCode")
    private long deviceCode;

    @SerializedName("DeviceId")
    private long deviceId;

    @SerializedName("CarId")
    private Integer carId;

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public long getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(long deviceCode) {
        this.deviceCode = deviceCode;
    }

    @Override
    public String getText() {
        return super.getText() + "-" + deviceCode;
    }

    @Override
    public Integer getId() {
        return carId;
    }
}
