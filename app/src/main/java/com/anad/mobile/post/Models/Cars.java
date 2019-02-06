package com.anad.mobile.post.Models;

/**
 * Created by public on 2017/12/27.
 */

public class Cars {
    private String driverName;
    private String driverFamilyName;
    private int carId;
    private String carNumber;
    private int carSpeed;
    private String carState;
    private String lastOnlineDate;
    private String lastOnlineTime;
    private String wayWork;
    private int Org_ID;
    private LastPosition lastPosition;

    public int getOrg_ID() {
        return Org_ID;
    }

    public void setOrg_ID(int org_ID) {
        Org_ID = org_ID;
    }

    public String getWayWork() {
        return wayWork;
    }

    public void setWayWork(String wayWork) {
        this.wayWork = wayWork;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverFamilyName() {
        return driverFamilyName;
    }

    public void setDriverFamilyName(String driverFamilyName) {
        this.driverFamilyName = driverFamilyName;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public int getCarSpeed() {
        return carSpeed;
    }

    public void setCarSpeed(int carSpeed) {
        this.carSpeed = carSpeed;
    }

    public String getCarState() {
        return carState;
    }

    public void setCarState(String carState) {
        this.carState = carState;
    }

    public String getLastOnlineDate() {
        return lastOnlineDate;
    }

    public void setLastOnlineDate(String lastOnlineDate) {
        this.lastOnlineDate = lastOnlineDate;
    }

    public String getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(String lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public LastPosition getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(LastPosition lastPosition) {
        this.lastPosition = lastPosition;
    }
}
