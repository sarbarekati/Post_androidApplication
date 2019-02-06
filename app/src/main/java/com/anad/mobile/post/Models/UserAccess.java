package com.anad.mobile.post.Models;

/**
 * Create ELIAS MOHAMMADY 96.10.11
 */

public class UserAccess {

    private int Org_ID;
    private int Parent_ID;
    private String Org_Name;
    private Cars cars;
    private LastPosition lastPosition;
    private double lat;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    private double lng;

    public int getOrg_ID() {
        return Org_ID;
    }

    public void setOrg_ID(int org_ID) {
        Org_ID = org_ID;
    }

    public int getParent_ID() {
        return Parent_ID;
    }

    public void setParent_ID(int parent_ID) {
        Parent_ID = parent_ID;
    }

    public String getOrg_Name() {
        return Org_Name;
    }

    public void setOrg_Name(String org_Name) {
        Org_Name = org_Name;
    }

    public Cars getCars() {
        return cars;
    }

    public void setCars(Cars cars) {
        this.cars = cars;
    }

    public LastPosition getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(LastPosition lastPosition) {
        this.lastPosition = lastPosition;
    }
}
