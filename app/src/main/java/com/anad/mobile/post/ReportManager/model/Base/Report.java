package com.anad.mobile.post.ReportManager.model.Base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class Report {

    @SerializedName("MabdaDate")
    @Expose
    private String MabdaDate;
    @SerializedName("MabdaKhoroj")
    @Expose
    private String MabdaKhoroj;
    @SerializedName("MabdaMogharar")
    @Expose
    private String MabdaMogharar;
    @SerializedName("MabdaTajil")
    @Expose
    private String MabdaTajil;
    @SerializedName("ModateBargiriMaghsad")
    @Expose
    private String ModateBargiriMaghsad;
    @SerializedName("MaghsadMogharar")
    @Expose
    private String MaghsadMogharar;
    @SerializedName("MabdaMiddlePointTitle")
    @Expose
    private String MabdaMiddlePointTitle;
    @SerializedName("MaghsadVorood")
    @Expose
    private String MaghsadVorood;
    @SerializedName("MaghsadDate")
    @Expose
    private String MaghsadDate;
    @SerializedName("MaghsadMiddlePointTitle")
    @Expose
    private String MaghsadMiddlePointTitle;
    @SerializedName("MaghsadBazgoshai")
    @Expose
    private String MaghsadBazgoshai;
    @SerializedName("MabdaTakhir")
    @Expose
    private String MabdaTakhir;
    @SerializedName("ModateBargiriMabda")
    @Expose
    private String ModateBargiriMabda;
    @SerializedName("DailyMissinId")
    @Expose
    private String DailyMissinId;
    @SerializedName("ReportDate")
    @Expose
    private String ReportDate;
    @SerializedName("MaghsadTajil")
    @Expose
    private String MaghsadTajil;
    @SerializedName("MaghsadTakhir")
    @Expose
    private String MaghsadTakhir;
    @SerializedName("TeyTajil")
    @Expose
    private String TeyTajil;
    @SerializedName("TeyTakhir")
    @Expose
    private String TeyTakhir;
    @SerializedName("AvrageSpeed")
    @Expose
    private Integer AvrageSpeed;
    @SerializedName("MaxSpeed")
    @Expose
    private Integer MaxSpeed;
    @SerializedName("ZamanTey")
    @Expose
    private String ZamanTey;
    @SerializedName("ZamanMoghararTey")
    @Expose
    private String ZamanMoghararTey;
    @SerializedName("Length")
    @Expose
    private Integer Length;
    @SerializedName("DailyMissinTitle")
    @Expose
    private String DailyMissinTitle;
    @SerializedName("ReportRouteTitle")
    @Expose
    private String ReportRouteTitle;
    @SerializedName("DeviceCode")
    @Expose
    private Integer DeviceCode;
    @SerializedName("Driver1")
    @Expose
    private String Driver1;
    @SerializedName("Driver2")
    @Expose
    private String Driver2;
    @SerializedName("LintTitle")
    @Expose
    private String LintTitle;


    public String getMabdaDate() {
        return MabdaDate;
    }

    public void setMabdaDate(String mabdaDate) {
        MabdaDate = mabdaDate;
    }

    public String getMabdaKhoroj() {
        return MabdaKhoroj;
    }

    public void setMabdaKhoroj(String mabdaKhoroj) {
        MabdaKhoroj = mabdaKhoroj;
    }

    public String getMabdaMogharar() {
        return MabdaMogharar;
    }

    public void setMabdaMogharar(String mabdaMogharar) {
        MabdaMogharar = mabdaMogharar;
    }

    public String getMabdaTajil() {
        return MabdaTajil;
    }

    public void setMabdaTajil(String mabdaTajil) {
        MabdaTajil = mabdaTajil;
    }

    public String getModateBargiriMaghsad() {
        return ModateBargiriMaghsad;
    }

    public void setModateBargiriMaghsad(String modateBargiriMaghsad) {
        ModateBargiriMaghsad = modateBargiriMaghsad;
    }

    public String getMaghsadMogharar() {
        return MaghsadMogharar;
    }

    public void setMaghsadMogharar(String maghsadMogharar) {
        MaghsadMogharar = maghsadMogharar;
    }

    public String getMabdaMiddlePointTitle() {
        return MabdaMiddlePointTitle;
    }

    public void setMabdaMiddlePointTitle(String mabdaMiddlePointTitle) {
        MabdaMiddlePointTitle = mabdaMiddlePointTitle;
    }

    public String getMaghsadVorood() {
        return MaghsadVorood;
    }

    public void setMaghsadVorood(String maghsadVorood) {
        MaghsadVorood = maghsadVorood;
    }

    public String getMaghsadDate() {
        return MaghsadDate;
    }

    public void setMaghsadDate(String maghsadDate) {
        MaghsadDate = maghsadDate;
    }

    public String getMaghsadMiddlePointTitle() {
        return MaghsadMiddlePointTitle;
    }

    public void setMaghsadMiddlePointTitle(String maghsadMiddlePointTitle) {
        MaghsadMiddlePointTitle = maghsadMiddlePointTitle;
    }

    public String getMaghsadBazgoshai() {
        return MaghsadBazgoshai;
    }

    public void setMaghsadBazgoshai(String maghsadBazgoshai) {
        MaghsadBazgoshai = maghsadBazgoshai;
    }

    public String getMabdaTakhir() {
        return MabdaTakhir;
    }

    public void setMabdaTakhir(String mabdaTakhir) {
        MabdaTakhir = mabdaTakhir;
    }

    public String getModateBargiriMabda() {
        return ModateBargiriMabda;
    }

    public void setModateBargiriMabda(String modateBargiriMabda) {
        ModateBargiriMabda = modateBargiriMabda;
    }

    public String getDailyMissinId() {
        return DailyMissinId;
    }

    public void setDailyMissinId(String dailyMissinId) {
        DailyMissinId = dailyMissinId;
    }

    public String getReportDate() {
        return ReportDate;
    }

    public void setReportDate(String reportDate) {
        ReportDate = reportDate;
    }

    public String getMagsadTajil() {
        return MaghsadTajil;
    }

    public void setMagsadTajil(String magsadTajil) {
        MaghsadTajil = magsadTajil;
    }

    public String getMagsadTakhir() {
        return MaghsadTakhir;
    }

    public void setMagsadTakhir(String magsadTakhir) {
        MaghsadTakhir = magsadTakhir;
    }

    public String getTeyTajil() {
        return TeyTajil;
    }

    public void setTeyTajil(String teyTajil) {
        TeyTajil = teyTajil;
    }

    public String getTeyTakhir() {
        return TeyTakhir;
    }

    public void setTeyTakhir(String teyTakhir) {
        TeyTakhir = teyTakhir;
    }

    public Integer getAvrageSpeed() {
        return AvrageSpeed;
    }

    public void setAvrageSpeed(Integer avrageSpeed) {
        AvrageSpeed = avrageSpeed;
    }

    public Integer getMaxSpeed() {
        return MaxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        MaxSpeed = maxSpeed;
    }

    public String getZamanTey() {
        return ZamanTey;
    }

    public void setZamanTey(String zamanTey) {
        ZamanTey = zamanTey;
    }

    public String getZamanMoghararTey() {
        return ZamanMoghararTey;
    }

    public void setZamanMoghararTey(String zamanMoghararTey) {
        ZamanMoghararTey = zamanMoghararTey;
    }

    public Integer getLength() {
        return Length;
    }

    public void setLength(Integer length) {
        Length = length;
    }

    public String getDailyMissinTitle() {
        return DailyMissinTitle;
    }

    public void setDailyMissinTitle(String dailyMissinTitle) {
        DailyMissinTitle = dailyMissinTitle;
    }

    public String getReportRouteTitle() {
        return ReportRouteTitle;
    }

    public void setReportRouteTitle(String reportRouteTitle) {
        ReportRouteTitle = reportRouteTitle;
    }

    public Integer getDeviceCode() {
        return DeviceCode;
    }

    public void setDeviceCode(Integer deviceCode) {
        DeviceCode = deviceCode;
    }

    public String getDriver1() {
        return Driver1;
    }

    public void setDriver1(String driver1) {
        Driver1 = driver1;
    }

    public String getDriver2() {
        return Driver2;
    }

    public void setDriver2(String driver2) {
        Driver2 = driver2;
    }

    public String getLintTitle() {
        return LintTitle;
    }

    public void setLintTitle(String lintTitle) {
        LintTitle = lintTitle;
    }
}
