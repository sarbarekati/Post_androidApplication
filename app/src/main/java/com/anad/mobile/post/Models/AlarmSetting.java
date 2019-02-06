package com.anad.mobile.post.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elias.mohammadi on 2018/02/17.
 */

public class AlarmSetting {
    private List<Integer> alarmId;
    private List<Boolean> showNotification;
    private String userName;

    public List<Integer> getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(List<Integer> alarmId) {
        this.alarmId = alarmId;
    }

    public List<Boolean> getShowNotification() {
        return showNotification;
    }

    public void setShowNotification(List<Boolean> showNotification) {
        this.showNotification = showNotification;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
