package com.anad.mobile.post.Models;

/**
 * Created by elias.mohammadi on 2018/02/06.
 */

public class AlarmsSettingModel {
    private String alarmName;
    private int alarmId;
    private boolean isNotificationOn;

    public int getNotificationForDb() {
        return notificationForDb;
    }

    public void setNotificationForDb(int notificationForDb) {
        this.notificationForDb = notificationForDb;
    }

    private int notificationForDb;


    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public boolean isNotificationOn() {
        return isNotificationOn;
    }

    public void setNotificationOn(boolean notificationOn) {
        isNotificationOn = notificationOn;
    }
}
