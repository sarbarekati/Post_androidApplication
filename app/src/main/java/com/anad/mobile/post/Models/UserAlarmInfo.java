package com.anad.mobile.post.Models;

/**
 * Created by elias.mohammadi on 2018/02/17.
 */

public class UserAlarmInfo {
    private String userName;

    public AlarmState getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(AlarmState alarmState) {
        this.alarmState = alarmState;
    }

    private AlarmState alarmState;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
