package com.anad.mobile.post.ReportManager.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchReportItem {
    @SerializedName("driverId")
    private long[] driverId;
    @SerializedName("lineId")
    private long[] lineId ;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("carId")
    private long[] carId ;
    @SerializedName("reportTypeId")
    private long reportTypeId;
    @SerializedName("durationFrom")
    private String durationFrom;
    @SerializedName("durationTo")
    private String durationTo;
    @SerializedName("speedFrom")
    private int speedFrom;
    @SerializedName("speedTo")
    private int speedTo;
    @SerializedName("lengthFrom")
    private int lengthFrom;
    @SerializedName("lengthTo")
    private int lengthTo;


    private SearchReportItem(long[] driverId, long[] lineId, String startDate, String endDate, long[] carId, long reportTypeId, String durationFrom, String durationTo, int speedFrom, int speedTo, int lengthFrom, int lengthTo){
        this.driverId = driverId;
        this.lineId = lineId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.carId = carId;
        this.reportTypeId = reportTypeId;
        this.durationFrom = durationFrom;
        this.durationTo = durationTo;
        this.speedFrom = speedFrom;
        this.speedTo = speedTo;
        this.lengthFrom = lengthFrom;
        this.lengthTo = lengthTo;
    }


    public static SearchReportItem createReportFilter(long[] driverId, long[] lineId, String startDate, String endDate, long[] carId, long reportTypeId, String durationFrom, String durationTo, int speedFrom, int speedTo, int lengthFrom, int lengthTo) {
        return new SearchReportItem(driverId,lineId,startDate,endDate,carId,
                reportTypeId,durationFrom,durationTo,speedFrom,
                speedTo,lengthFrom,lengthTo);
    }

    public long[] getDriverId() {
        return driverId;
    }

    public long[] getLineId() {
        return lineId;
    }

    public long[] getCarId() {
        return carId;
    }


    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public long getReportTypeId() {
        return reportTypeId;
    }

    public String getDurationFrom() {
        return durationFrom;
    }

    public String getDurationTo() {
        return durationTo;
    }

    public int getSpeedFrom() {
        return speedFrom;
    }

    public int getSpeedTo() {
        return speedTo;
    }

    public int getLengthFrom() {
        return lengthFrom;
    }

    public int getLengthTo() {
        return lengthTo;
    }

}
