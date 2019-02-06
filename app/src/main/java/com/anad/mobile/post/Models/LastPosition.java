package com.anad.mobile.post.Models;

/**
 * Created by Elias MOHAMMADI 96.10.19
 */

public class LastPosition {

    // Fields
    private String N;
    private String LastLat;
    private String LastLong;
    private String E;
    private String LDate;
    private String LTime;
    private int speed;
    private int Org_ID;
    private int ID;
    private boolean isRFID;

    //Setter and Getter
    public String getLastLat() {
        return LastLat;
    }

    public void setLastLat(String lastLat) {
        LastLat = lastLat;
    }

    public String getLastLong() {
        return LastLong;
    }

    public void setLastLong(String lastLong) {
        LastLong = lastLong;
    }


    public String getN() {
        return N;
    }

    public void setN(String n) {
        this.N = n;
    }

    public String getE() {
        return E;
    }

    public void setE(String e) {
        this.E = e;
    }

    public String getLDate() {
        return LDate;
    }

    public void setLDate(String LDate) {
        this.LDate = LDate;
    }

    public String getLTime() {
        return LTime;
    }

    public void setLTime(String LTime) {
        this.LTime = LTime;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getOrg_ID() {
        return Org_ID;
    }

    public void setOrg_ID(int org_ID) {
        Org_ID = org_ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setRFID(boolean isRFID)
    {
        this.isRFID = isRFID;
    }
    public boolean isRFID()
    {
        return this.isRFID;
    }
}
