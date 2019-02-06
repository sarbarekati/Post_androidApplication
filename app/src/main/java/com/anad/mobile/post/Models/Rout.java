package com.anad.mobile.post.Models;

/**
 * Created by elias.mohammadi on 96/10/26
 */

public class Rout {

    private String Car_ID;
    private String Start_D;
    private String Start_T;
    private String End_T;
    private int Speed_Max;
    private float Route_Length;
    private String Dur;
    private String Body;
    private int Org_ID;
    private double Fuel;

    public String getCar_ID() {
        return Car_ID;
    }

    public void setCar_ID(String car_ID) {
        Car_ID = car_ID;
    }

    public String getStart_D() {
        return Start_D;
    }

    public void setStart_D(String start_D) {
        Start_D = start_D;
    }

    public String getStart_T() {
        return Start_T;
    }

    public void setStart_T(String start_T) {
        Start_T = start_T;
    }

    public String getEnd_T() {
        return End_T;
    }

    public void setEnd_T(String end_T) {
        End_T = end_T;
    }

    public int getSpeed_Max() {
        return Speed_Max;
    }

    public void setSpeed_Max(int speed_Max) {
        Speed_Max = speed_Max;
    }

    public float getRoute_Length() {
        return Route_Length;
    }

    public void setRoute_Length(float route_Length) {
        Route_Length = route_Length;
    }

    public String getDur() {
        return Dur;
    }

    public void setDur(String dur) {
        Dur = dur;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public int getOrg_ID() {
        return Org_ID;
    }

    public void setOrg_ID(int org_ID) {
        Org_ID = org_ID;
    }

    public double getFuel() {
        return Fuel;
    }

    public void setFuel(double fuel) {
        Fuel = fuel;
    }
}
