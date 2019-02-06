package com.anad.mobile.post.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anad.mobile.post.API.AlarmApi;
import com.anad.mobile.post.Activity.MainActivity;
import com.anad.mobile.post.Adapter.AlarmsSaveAdapter;
import com.anad.mobile.post.Models.Alarms;
import com.anad.mobile.post.Models.AlarmsDefinition;
import com.anad.mobile.post.Models.ListCreator;
import com.anad.mobile.post.Models.User;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.RecyclerOnClick;
import com.anad.mobile.post.Utils.RecyclerOnClickListener;
import com.anad.mobile.post.Utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by elias.mohammadi on 96.11.23
 */

public class ShowAlarmsFragment extends Fragment {

    private View view;
    private Util util;
    private RecyclerView rc;
    private static final String TAG = "ShowAlarmsFragment";
    private PostSharedPreferences preferences;
    private AlarmsSaveAdapter adapter;
    private int alarmId_1, alarmId_2, alarmId_3, alarmId_4, alarmId_5, alarmId_6, alarmId_7, alarmId_8, alarmId_9, alarmId_10, alarmId_11, alarmId_12;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_alarm, container, false);
        preferences = new PostSharedPreferences(MainActivity.mainActivityContext);
        initializeView();
        return view;
    }

    private void initializeView() {
        util = Util.getInstance();
        String user = preferences.getPrefUserName();
        rc = view.findViewById(R.id.alarm_show_rc);
        rc.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        AlarmApi api = AlarmApi.getInstance(getActivity().getApplicationContext());


        api.getUserAlarm(new AlarmApi.OnAlarmsCallBack() {
            @Override
            public void OnResponse(List<Alarms> list) {
                final List<AlarmsDefinition> alarmDef = new ArrayList<>();
                List<Integer> intId = new ArrayList<>();
                for (Alarms alarm : list
                        ) {


                    AlarmsDefinition a = new AlarmsDefinition();
                    switch (alarm.getAppAlarmID()) {
                        case 1:

                            alarmId_1++;
                            if (!intId.contains(alarm.getAppAlarmID())) {
                                intId.add(alarm.getAppAlarmID());
                            }
                            break;

                        case 2:
                            alarmId_2++;
                            if (!intId.contains(alarm.getAppAlarmID())) {
                                intId.add(alarm.getAppAlarmID());
                            }
                            break;

                        case 3:
                            alarmId_3++;
                            if (!intId.contains(alarm.getAppAlarmID())) {
                                intId.add(alarm.getAppAlarmID());
                            }
                            break;

                        case 4:
                            alarmId_4++;
                            if (!intId.contains(alarm.getAppAlarmID())) {
                                intId.add(alarm.getAppAlarmID());
                            }
                            break;

                        case 5:
                            alarmId_5++;
                            if (!intId.contains(alarm.getAppAlarmID())) {
                                intId.add(alarm.getAppAlarmID());
                            }
                            break;

                        case 6:
                            alarmId_6++;
                            if (!intId.contains(alarm.getAppAlarmID())) {
                                intId.add(alarm.getAppAlarmID());
                            }
                            break;

                        case 7:
                            alarmId_7++;
                            if (!intId.contains(alarm.getAppAlarmID())) {
                                intId.add(alarm.getAppAlarmID());
                            }
                            break;

                        case 10:
                            alarmId_10++;
                            if (!intId.contains(alarm.getAppAlarmID())) {
                                intId.add(alarm.getAppAlarmID());
                            }
                            break;

                        case 11:
                            alarmId_11++;
                            if (!intId.contains(alarm.getAppAlarmID())) {
                                intId.add(alarm.getAppAlarmID());
                            }
                            break;

                        case 8:
                            alarmId_8++;
                            if (!intId.contains(alarm.getAppAlarmID())) {
                                intId.add(alarm.getAppAlarmID());
                            }
                            break;

                        case 9:
                            alarmId_9++;
                            if (!intId.contains(alarm.getAppAlarmID())) {
                                intId.add(alarm.getAppAlarmID());
                            }

                            break;
                        case 12:
                            alarmId_12++;
                            if (!intId.contains(alarm.getAppAlarmID())) {
                                intId.add(alarm.getAppAlarmID());
                            }
                            break;

                    }

                }

                for (Integer i : intId
                        ) {
                    AlarmsDefinition a = new AlarmsDefinition();
                    switch (i) {
                        case 1:
                            a.setAlarmName(getActivity().getString(R.string.speed_over_100));
                            a.setAlarmId(1);
                            a.setAlarmCount(alarmId_1);
                            break;
                        case 2:
                            a.setAlarmName("توقف در نقاط غیر مجاز");
                            a.setAlarmId(2);
                            a.setAlarmCount(alarmId_2);
                            break;
                        case 3:
                            a.setAlarmName("باز شدن در نقاط غیر مجاز");
                            a.setAlarmId(3);
                            a.setAlarmCount(alarmId_3);
                            break;
                        case 4:
                            a.setAlarmName("فرم های صادر شده");
                            a.setAlarmId(4);
                            a.setAlarmCount(alarmId_4);
                            break;
                        case 5:
                            a.setAlarmName("تاخیر در مبدا");
                            a.setAlarmId(5);
                            a.setAlarmCount(alarmId_5);
                            break;
                        case 6:
                            a.setAlarmName("تعجیل در مبدا");
                            a.setAlarmId(6);
                            a.setAlarmCount(alarmId_6);
                            break;
                        case 7:
                            a.setAlarmName("تاخیر در ورود نقطه مبادله");
                            a.setAlarmId(7);
                            a.setAlarmCount(alarmId_7);
                            break;
                        case 8:
                            a.setAlarmName("تعجیل در ورود نقاط مبادله");
                            a.setAlarmId(8);
                            a.setAlarmCount(alarmId_8);
                            break;
                        case 9:
                            a.setAlarmName("تاخیر در مقصد");
                            a.setAlarmId(9);
                            a.setAlarmCount(alarmId_9);
                            break;
                        case 10:
                            a.setAlarmName("تعجیل در مقصد");
                            a.setAlarmId(10);
                            a.setAlarmCount(alarmId_10);
                            break;
                        case 11:
                            a.setAlarmName("تاخیر در خروج نقطه مبادله");
                            a.setAlarmId(11);
                            a.setAlarmCount(alarmId_11);
                            break;
                        case 12:
                            a.setAlarmName("تعجیل در خروج نقاط مبادله");
                            a.setAlarmId(12);
                            a.setAlarmCount(alarmId_12);
                            break;
                    }
                    alarmDef.add(a);
                }


                adapter = new AlarmsSaveAdapter(getActivity(), alarmDef,preferences, new RecyclerOnClick() {
                    @Override
                    public void onClick(View v, int position) {
                        Log.i(TAG, "onClick: Clicked");
                    }
                });
                rc.setAdapter(adapter);

            }
        }, Constants.URL_GET_USER_ALARMS + "?userName=" + user + "&skip=0&count=10&alarmId=0");

    }

    public static ShowAlarmsFragment newInstance() {

        Bundle args = new Bundle();
        ShowAlarmsFragment fragment = new ShowAlarmsFragment();
        fragment.setArguments(args);
        return fragment;

    }
}
