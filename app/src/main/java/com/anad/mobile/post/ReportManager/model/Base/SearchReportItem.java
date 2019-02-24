package com.anad.mobile.post.ReportManager.model.Base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchReportItem {
    @SerializedName("driverId")
    private List<Integer> driverId;
    @SerializedName("lineId")
    private List<Integer> lineId ;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("carId")
    private List<Integer> carId ;
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
    @SerializedName("deviceId")
    private List<Integer> deviceCode;



    private SearchReportItem(List<Integer> driverId, List<Integer> lineId, String startDate, String endDate, List<Integer> carId,
                             long reportTypeId, String durationFrom, String durationTo, int speedFrom, int speedTo, int lengthFrom, int lengthTo,List<Integer> deviceCode){
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
        this.deviceCode = deviceCode;
    }



    public static SearchReportItem createReportFilter(List<Integer> driverId, List<Integer> lineId, String startDate, String endDate, List<Integer> carId, long reportTypeId,
                                                      String durationFrom, String durationTo, int speedFrom, int speedTo, int lengthFrom, int lengthTo,List<Integer> deviceCode) {
        return new SearchReportItem(driverId,lineId,startDate,endDate,carId,
                reportTypeId,durationFrom,durationTo,speedFrom,
                speedTo,lengthFrom,lengthTo,deviceCode);
    }

    public List<Integer> getDriverId() {
        return driverId;
    }

    public List<Integer> getLineId() {
        return lineId;
    }

    public List<Integer> getCarId() {
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
