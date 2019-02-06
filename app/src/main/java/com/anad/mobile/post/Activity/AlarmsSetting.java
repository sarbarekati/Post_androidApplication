package com.anad.mobile.post.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anad.mobile.post.API.AlarmApi;
import com.anad.mobile.post.Adapter.AlarmsSettingAdapter;
import com.anad.mobile.post.Models.AlarmSetting;

import com.anad.mobile.post.Models.AlarmsSettingModel;
import com.anad.mobile.post.Models.ListCreator;
import com.anad.mobile.post.Models.UserAlarmDefine;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostDataBase;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;
import com.google.gson.Gson;

import java.util.ArrayList;

import java.util.List;

public class AlarmsSetting extends AppCompatActivity implements View.OnClickListener {
     AlarmsSettingAdapter adapter;
    private Button btnSaveSetting;
     RecyclerView rcSetting;
    private TextView txtTitle;
    private Util utils;
     Gson gson;
    private PostSharedPreferences preferences;

    private PostDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        preferences = new PostSharedPreferences(this);
        if (Util.authenticateUser(preferences)) {


            setContentView(R.layout.activity_alarms_setting);
            utils = Util.getInstance();
            db = new PostDataBase(this);
            gson = new Gson();
            initializeView();
        }else{
            Util.backToLoginActivity(this);
        }
    }

    private void initializeView() {
        rcSetting = findViewById(R.id.alarms_rc);
        rcSetting.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<UserAlarmDefine> userAlarmDefines = new ArrayList<>();
        List<AlarmsSettingModel> alarms = db.getAlarmSettings();

        for (AlarmsSettingModel a : alarms
                ) {
            UserAlarmDefine u = new UserAlarmDefine();
            u.AppAlarmID = a.getAlarmId();
            if (a.getNotificationForDb() == 1)
                u.ShowNotification = true;
            else
                u.ShowNotification = false;

            userAlarmDefines.add(u);

        }
        adapter = new AlarmsSettingAdapter(ListCreator.getAlarmsSettingMenu(this), this, userAlarmDefines);
        rcSetting.setAdapter(adapter);
        btnSaveSetting = findViewById(R.id.alarms_btn_save);
        btnSaveSetting.setOnClickListener(this);
        txtTitle = findViewById(R.id.alarms_setting_title);

        setFont();

    }

    private void setFont() {
        utils.setTypeFace(txtTitle, this);
        utils.setTypeFaceButton(btnSaveSetting, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alarms_btn_save:

                List<Integer> alarmId = new ArrayList<>();
                List<Boolean> showNotification = new ArrayList<>();


                for (AlarmsSettingModel a : db.getAlarmSettings()
                        ) {
                    alarmId.add(a.getAlarmId());
                    if (a.getNotificationForDb() == 1)
                        showNotification.add(true);
                    else
                        showNotification.add(false);

                }


                AlarmSetting alarmSetting = new AlarmSetting();
                alarmSetting.setAlarmId(alarmId);
                alarmSetting.setShowNotification(showNotification);


                alarmSetting.setUserName(preferences.getPrefUserName());
                AlarmApi api = AlarmApi.getInstance(AlarmsSetting.this);
                api.changeAlarmSetting(Constants.URL_ALARM_SETTING, alarmSetting);


                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
