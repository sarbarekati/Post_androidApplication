package com.anad.mobile.post.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.anad.mobile.post.Models.Alarms;
import com.anad.mobile.post.Models.AlarmsSettingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elias.mohammadi on 96.11.14
 */

public class PostDataBase  extends SQLiteOpenHelper{
    private static final String DB_NAME = "post_db";
    private static final int DB_VERSION = 1;

    private static final String TAG = "PostDataBase";

    private static final String TABLE_ALARMS_NAME="alarms_tbl";
    private static final String TABLE_ALARMS_SETTING="alarms_setting_tbl";
    private static final String COL_ID ="id";
    private static final String COL_ALARM_DATE ="AlarmDate";
    private static final String COL_ALARM_TIME ="AlarmTime";
    private static final String COL_ALARM_DESC="AlarmDesc";
    private static final String COL_DEVICE_ID="DeviceID";
    private static final String COL_ALARM_ID="AlarmID";
    private static final String COL_NOTIFICATION = "notification";


    private static final String ALARM_TABLE_CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS "+TABLE_ALARMS_NAME+" ("
            +COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COL_ALARM_ID + " INTEGER, "
            +COL_ALARM_DATE + " TEXT, "
            +COL_ALARM_TIME + " TEXT, "
            +COL_DEVICE_ID  + " TEXT, "
            +COL_ALARM_DESC + " TEXT);";

    private static final String ALARM_TABLE_SETTING_COMMAND = "CREATE TABLE IF NOT EXISTS "+ TABLE_ALARMS_SETTING + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COL_ALARM_ID + " INTEGER, "
            +COL_NOTIFICATION + " INTEGER);";


    public PostDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
            }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(ALARM_TABLE_CREATE_COMMAND);
            db.execSQL(ALARM_TABLE_SETTING_COMMAND);
            Log.i(TAG, "onCreate: data base created successfully");
        }
        catch (SQLiteException e)
        {
            Log.e(TAG, "onCreate: ",e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addAlarm(Alarms alarms)
    {
        ContentValues cv = new ContentValues();
        cv.put(COL_ALARM_ID,alarms.getAppAlarmID());
        cv.put(COL_ALARM_DATE,alarms.getAlarmDate());
        cv.put(COL_ALARM_TIME,alarms.getAlarmTime());
        cv.put(COL_ALARM_DESC,alarms.getAppAlarmDesc());
        cv.put(COL_DEVICE_ID,alarms.getDvcID());
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

       long isInsert =  sqLiteDatabase.insert(TABLE_ALARMS_NAME,null,cv);
       if(isInsert > 0)
           return true;
       else
           return false;

    }

    public boolean addAlarmSetting(AlarmsSettingModel alarmSetting)
    {
        ContentValues cv = new ContentValues();
        cv.put(COL_ALARM_ID,alarmSetting.getAlarmId());
        cv.put(COL_NOTIFICATION,alarmSetting.getNotificationForDb());
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long isInsert = sqLiteDatabase.insert(TABLE_ALARMS_SETTING,null,cv);
        if(isInsert>0)
            return true;
        else
            return false;
    }


    public void addAlarmSetting(List<AlarmsSettingModel> alarmsSettingModels)
    {

        for (int i = 0; i <alarmsSettingModels.size() ; i++) {
            if(!checkExistsAlarmSetting(alarmsSettingModels.get(i).getAlarmId()))
            {
                addAlarmSetting(alarmsSettingModels.get(i));
            }
        }
    }

    private boolean checkExistsAlarmSetting(int alarmId) {

        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_ALARMS_SETTING + " WHERE " + COL_ALARM_ID +" = " + alarmId,null);
        return cursor.moveToFirst();

    }


    public List<Alarms> getAlarms()
    {
        List<Alarms> alarms = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_ALARMS_NAME + " ORDER BY " + COL_ID +" DESC LIMIT 10",null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Alarms a = new Alarms();
                a.setAppAlarmID(Integer.parseInt(cursor.getString(1)));
                a.setAlarmDate(cursor.getString(2));
                a.setAlarmTime(cursor.getString(3));
                a.setAppAlarmDesc(cursor.getString(5));
                a.setDvcID(Integer.parseInt(cursor.getString(4)));
                alarms.add(a);
                cursor.moveToNext();
            }

        }
        cursor.close();
        sqLiteDatabase.close();
        return alarms;
    }

    public List<AlarmsSettingModel> getAlarmSettings()
    {
        List<AlarmsSettingModel> alarmsSetting = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_ALARMS_SETTING,null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                AlarmsSettingModel alarm = new AlarmsSettingModel();
                alarm.setAlarmId(cursor.getInt(cursor.getColumnIndex(COL_ALARM_ID)));
                alarm.setNotificationForDb(cursor.getInt(cursor.getColumnIndex(COL_NOTIFICATION)));
                alarmsSetting.add(alarm);
                cursor.moveToNext();
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        return alarmsSetting;
    }


    public int deleteAlarmSetting(int alarmId)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int delete = sqLiteDatabase.delete(TABLE_ALARMS_SETTING,COL_ALARM_ID +" = " + alarmId,null);
        return delete;
    }

    public int updateAlarmSetting(int alarmId,int notification)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NOTIFICATION,notification);
        int update = sqLiteDatabase.update(TABLE_ALARMS_SETTING,cv,COL_ALARM_ID +" = " + alarmId,null);
        return update;
    }



}
