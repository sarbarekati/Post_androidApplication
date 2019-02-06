package com.anad.mobile.post.Models;

/**
 * Created by elias.mohammadi on 2018/03/03
 */

public class DriverModel {

    private int Drv_ID;
    private String FName;
    private String LName;
    private String Car_Pelak;
    private String SimCard;
    private int orgId;
    private int DeviceId;

    public int getDrv_ID() {
        return Drv_ID;
    }

    public void setDrv_ID(int drv_ID) {
        Drv_ID = drv_ID;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getCar_Pelak() {
        return Car_Pelak;
    }

    public void setCar_Pelak(String car_Pelak) {
        Car_Pelak = car_Pelak;
    }

    public String getSimCard() {
        return SimCard;
    }

    public void setSimCard(String simCard) {
        SimCard = simCard;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(int deviceId) {
        DeviceId = deviceId;
    }
}
