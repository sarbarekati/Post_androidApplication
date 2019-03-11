package com.anad.mobile.post.Models.ShowPathModels;

import com.google.gson.annotations.SerializedName;

public class Route {

    //region Fields

    @SerializedName("RouteId")
    private Long RouteId;
    @SerializedName("DeviceId")
    private Long DeviceId;
    @SerializedName("RouteDate")
    private String RouteDate;
    @SerializedName("StartTime")
    private String StartTime;
    @SerializedName("EndTime")
    private String EndTime;
    @SerializedName("DateTime")
    private String DateTime;
    @SerializedName("MaxSpeed")
    private Integer MaxSpeed;
    @SerializedName("RouteLength")
    private Double RouteLength;
    @SerializedName("RouteDuration")
    private String RouteDuration;
    @SerializedName("Fuel")
    private Double Fuel;
    @SerializedName("Body")
    private String Body;
    //endregion

    //region Getter/Setter

    public Long getRouteId() {
        return RouteId;
    }

    public Long getDeviceId() {
        return DeviceId;
    }

    public String getRouteDate() {
        return RouteDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public String getDateTime() {
        return DateTime;
    }

    public Integer getMaxSpeed() {
        return MaxSpeed;
    }

    public Double getRouteLength() {
        return RouteLength;
    }

    public String getRouteDuration() {
        return RouteDuration;
    }

    public Double getFuel() {
        return Fuel;
    }

    public String getBody() {
        return Body;
    }


    //endregion



}
