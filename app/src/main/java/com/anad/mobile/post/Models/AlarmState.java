package com.anad.mobile.post.Models;

import java.util.List;

/**
 * Created by elias.mohammadi on 2018/02/17.
 */

public class AlarmState {
    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    private List<Integer> id;


    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    private int alarmId;
    private String field;
    private boolean fieldState;
    private String AlarmDate;

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

    private String AlarmTime;


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isFieldState() {
        return fieldState;
    }

    public void setFieldState(boolean fieldState) {
        this.fieldState = fieldState;
    }
}
