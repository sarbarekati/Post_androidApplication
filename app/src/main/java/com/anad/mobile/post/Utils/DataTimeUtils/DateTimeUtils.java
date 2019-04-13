package com.anad.mobile.post.Utils.DataTimeUtils;

import com.anad.mobile.post.Utils.JalaliCalendar;
import com.anad.mobile.post.Utils.PersianCal;

import java.util.Calendar;
import java.util.Date;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;

/**
 *
 * Contain Date and Time Utility Methods
 *
 *
 * */


public class DateTimeUtils {

    /**
     *
     * Get Current Date like 17:00 - 1397/12/22
     *
     * @return Array of Strings with two value hour and date
     * @auther Elias Mohammadi
     *
     *
     * */
    public static String[] getPersianCurrentDateTime(){
        Date c = Calendar.getInstance().getTime();
        Calendar c2 = Calendar.getInstance();
        PersianCal persian = new PersianCal(c2);
        String date = persian.getYear() + "/" + persian.getMonth() + "/" + persian.getDay();

        int hour = c.getHours();
        String dateTime = String.valueOf(hour) + "--" + date;
        return dateTime.split("--");
    }

    /**
     *
     * Get Current Hour
     *
     * @return return string as an current hour
     *
     * */
    public static String getCurrentHour(){
        return getPersianCurrentDateTime()[0];
    }
    public static String getCurrentDate(){
        return getPersianCurrentDateTime()[1];
    }


    public static String changeDateFormat(PersianCalendar persianCalendar){
        String Month;
        String Day;
        if ((persianCalendar.getPersianMonth()) < 10) {
            Month = "0" + (persianCalendar.getPersianMonth());
        } else {
            Month = (persianCalendar.getPersianMonth()) + "";
        }
        if ((persianCalendar.getPersianDay()) < 10) {
            Day = "0" + (persianCalendar.getPersianDay());
        } else {
            Day = (persianCalendar.getPersianDay()) + "";
        }
        return persianCalendar.getPersianYear() + "/" + Month + "/" + Day;
    }

    public static String changeDateFormat(String date){
        String[] dates = date.split("/");
        String year = dates[0];
        String month = dates[1];
        String day = dates[2];

        if(Integer.parseInt(month)<10)
        {
            month = "0"+ month;
        }
        if(Integer.parseInt(day)<10)
        {
            day = "0"+day;
        }

        return year+"/"+month+"/"+day;

    }


    public static String changeTimeFormat(int hourOfDay,int minute){
        String Hour;
        String Minute;
        if (hourOfDay < 10) {
            Hour = "0" + hourOfDay;
        } else {
            Hour = hourOfDay + "";
        }
        if (minute < 10) {
            Minute = "0" + minute;
        } else {
            Minute = minute + "";
        }
        return Hour + ":" + Minute;
    }

    public static String convertToGregorian(String persianDate){
        JalaliCalendar jalaliCalendar = new JalaliCalendar();
        return jalaliCalendar.getGregorian(persianDate);

    }

}
