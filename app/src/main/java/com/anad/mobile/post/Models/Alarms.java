package com.anad.mobile.post.Models;

/**
 * Created by elias.mohammadi on 96.11.14
 */

public class Alarms {
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID;
    private int AppAlarmID;
    private String AppAlarmDesc;
    private String AlarmDate;
    private String AlarmTime;
    private boolean Archived;
    private boolean Deleted;
    private boolean Sended;
    private boolean Readed;
    private int DvcID;
    private int AppUserID;

    public int getAppAlarmID() {
        return AppAlarmID;
    }

    public void setAppAlarmID(int appAlarmID) {
        AppAlarmID = appAlarmID;
    }

    public String getAppAlarmDesc() {
        return AppAlarmDesc;
    }

    public void setAppAlarmDesc(String appAlarmDesc) {
        AppAlarmDesc = appAlarmDesc;
    }

    public String getAlarmDate() {
        return AlarmDate;
    }

    public void setAlarmDate(String alarmDate) {
        AlarmDate = alarmDate;
    }

    public String getAlarmTime() {
        return AlarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        AlarmTime = alarmTime;
    }

    public boolean isArchived() {
        return Archived;
    }

    public void setArchived(boolean archived) {
        Archived = archived;
    }

    public boolean isDeleted() {
        return Deleted;
    }

    public void setDeleted(boolean deleted) {
        Deleted = deleted;
    }

    public boolean isSended() {
        return Sended;
    }

    public void setSended(boolean sended) {
        Sended = sended;
    }

    public boolean isReaded() {
        return Readed;
    }

    public void setReaded(boolean readed) {
        Readed = readed;
    }

    public int getDvcID() {
        return DvcID;
    }

    public void setDvcID(int dvcID) {
        DvcID = dvcID;
    }

    public int getAppUserID() {
        return AppUserID;
    }

    public void setAppUserID(int appUserID) {
        AppUserID = appUserID;
    }
}
