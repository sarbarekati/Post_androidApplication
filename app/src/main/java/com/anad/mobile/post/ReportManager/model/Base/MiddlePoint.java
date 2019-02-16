package com.anad.mobile.post.ReportManager.model.Base;


import com.google.gson.annotations.SerializedName;

public abstract class MiddlePoint {

    @SerializedName("MiddlePointTitle")
    private String MiddlePointTitle;
    @SerializedName("MiddlePointId")
    private int MiddlePointId;
    @SerializedName("ModateBargiriMobadele")
    private String ModateBargiriMobadele;
    @SerializedName("MobadeleKhoroj")
    private String MobadeleKhoroj;
    @SerializedName("MobadeleVorood")
    private String MobadeleVorood;
    @SerializedName("MobadeleMoghararKhoroj")
    private String MobadeleMoghararKhoroj;
    @SerializedName("MobadeleMoghararVorood")
    private String MobadeleMoghararVorood;
    @SerializedName("MobadeleTjilVorood")
    private String MobadeleTjilVorood;
    @SerializedName("MobadeleTajilKhoroj")
    private String MobadeleTajilKhoroj;
    @SerializedName("MobadeleTakhirKhoroj")
    private String MobadeleTakhirKhoroj;
    @SerializedName("MobadeleTakhirVorood")
    private String MobadeleTakhirVorood;
    @SerializedName("MobadeleDate")
    private String MobadeleDate;

    public String getMiddlePointTitle() {
        return MiddlePointTitle;
    }

    public void setMiddlePointTitle(String middlePointTitle) {
        MiddlePointTitle = middlePointTitle;
    }

    public int getMiddlePointId() {
        return MiddlePointId;
    }

    public void setMiddlePointId(int middlePointId) {
        MiddlePointId = middlePointId;
    }

    public String getModateBargiriMobadele() {
        return ModateBargiriMobadele;
    }

    public void setModateBargiriMobadele(String modateBargiriMobadele) {
        ModateBargiriMobadele = modateBargiriMobadele;
    }

    public String getMobadeleKhoroj() {
        return MobadeleKhoroj;
    }

    public void setMobadeleKhoroj(String mobadeleKhoroj) {
        MobadeleKhoroj = mobadeleKhoroj;
    }

    public String getMobadeleVorood() {
        return MobadeleVorood;
    }

    public void setMobadeleVorood(String mobadeleVorood) {
        MobadeleVorood = mobadeleVorood;
    }

    public String getMobadeleMoghararKhoroj() {
        return MobadeleMoghararKhoroj;
    }

    public void setMobadeleMoghararKhoroj(String mobadeleMoghararKhoroj) {
        MobadeleMoghararKhoroj = mobadeleMoghararKhoroj;
    }

    public String getMobadeleMoghararVorood() {
        return MobadeleMoghararVorood;
    }

    public void setMobadeleMoghararVorood(String mobadeleMoghararVorood) {
        MobadeleMoghararVorood = mobadeleMoghararVorood;
    }

    public String getMobadeleTjilVorood() {
        return MobadeleTjilVorood;
    }

    public void setMobadeleTjilVorood(String mobadeleTjilVorood) {
        MobadeleTjilVorood = mobadeleTjilVorood;
    }

    public String getMobadeleTajilKhoroj() {
        return MobadeleTajilKhoroj;
    }

    public void setMobadeleTajilKhoroj(String mobadeleTajilKhoroj) {
        MobadeleTajilKhoroj = mobadeleTajilKhoroj;
    }

    public String getMobadeleTakhirKhoroj() {
        return MobadeleTakhirKhoroj;
    }

    public void setMobadeleTakhirKhoroj(String mobadeleTakhirKhoroj) {
        MobadeleTakhirKhoroj = mobadeleTakhirKhoroj;
    }

    public String getMobadeleTakhirVorood() {
        return MobadeleTakhirVorood;
    }

    public void setMobadeleTakhirVorood(String mobadeleTakhirVorood) {
        MobadeleTakhirVorood = mobadeleTakhirVorood;
    }

    public String getMobadeleDate() {
        return MobadeleDate;
    }

    public void setMobadeleDate(String mobadeleDate) {
        MobadeleDate = mobadeleDate;
    }
}
