package com.anad.mobile.post.MapManager.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastPosition {
    @SerializedName("DeviceCode")
    @Expose
    private long deviceCode;


    @SerializedName("E")
    @Expose
    private String E;

    @SerializedName("N")
    @Expose
    private String N;

    @SerializedName("LastDate")
    @Expose
    private String lastDate;

    @SerializedName("LastTime")
    @Expose
    private String lastTime;

    @SerializedName("Speed")
    @Expose
    private int speed;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public long getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(long deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getE() {
        return E;
    }

    public void setE(String e) {
        E = e;
    }

    public String getN() {
        return N;
    }

    public void setN(String n) {
        N = n;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
