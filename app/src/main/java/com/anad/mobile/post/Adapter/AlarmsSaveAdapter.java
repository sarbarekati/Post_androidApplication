package com.anad.mobile.post.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.anad.mobile.post.API.AlarmApi;
import com.anad.mobile.post.Models.AlarmState;
import com.anad.mobile.post.Models.Alarms;
import com.anad.mobile.post.Models.AlarmsDefinition;
import com.anad.mobile.post.Models.UserAlarmInfo;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.RecyclerOnClick;
import com.anad.mobile.post.Utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elias.mohammadi on 96.11.23
 */

public class AlarmsSaveAdapter extends RecyclerView.Adapter<AlarmsSaveAdapter.itemViewHolder> {
    private Util util;
    private Context context;
    private AlarmChildAdapter childAdapter;
    private List<AlarmsDefinition> alarmName;
    private PostSharedPreferences preferences;
    private static final String TAG = "AlarmsSaveAdapter";
    private boolean isExpand;
    private AlarmApi api;
    private List<Alarms> alarmsList;
    private RecyclerOnClick listener;

   private AlarmsDefinition alarm;


    public AlarmsSaveAdapter(Context context, List<AlarmsDefinition> list,PostSharedPreferences preferences, RecyclerOnClick listener) {
        this.context = context;
        alarmName = list;
        api = AlarmApi.getInstance(context);
        util = Util.getInstance();
        this.listener = listener;
        this.preferences = preferences;


    }

    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.save_alarm_parent_layout, parent, false);
        final itemViewHolder holder = new itemViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, holder.getPosition());
                alarm = alarmName.get(holder.getPosition());

                if (isExpand) {

                    holder.parentContainer.setVisibility(View.VISIBLE);
                    holder.badgeContainer.setVisibility(View.VISIBLE);
                    holder.txtBadge.setVisibility(View.VISIBLE);
                    holder.chevronUpDown.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chevron_down_white_24dp));
                    holder.rcChildAlarm.setVisibility(View.GONE);
                    holder.more.setVisibility(View.GONE);
                    isExpand = !isExpand;
                } else {

                    holder.parentContainer.setVisibility(View.VISIBLE);
                    holder.chevronUpDown.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chevron_up_white_24dp));
                    holder.rcChildAlarm.setVisibility(View.VISIBLE);
                    holder.more.setVisibility(View.VISIBLE);
                    getAlarms(alarm, holder.rcChildAlarm, holder.parentContainer, alarm.getAlarmId(), 0, 10, new getListForUpdate() {
                        @Override
                        public void onListBack(List<Alarms> alarmsList) {
                            Log.i(TAG, "onListBack: " + alarmsList.size());
                        }
                    });




                    isExpand = !isExpand;

                }
            }
        });


        return holder;

    }

    @Override
    public void onBindViewHolder(final itemViewHolder holder, int position) {

        final AlarmsDefinition alarm = alarmName.get(position);
        holder.txtAlarmTitle.setText(alarm.getAlarmName());
        if (alarm.getAlarmCount() > 0) {
            holder.badgeContainer.setVisibility(View.VISIBLE);
            holder.txtBadge.setText(alarm.getAlarmCount() + "");
        }

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.more.setVisibility(View.GONE);
                getAlarms(alarm, holder.rcChildAlarm, holder.parentContainer, alarm.getAlarmId(), 0, 10, new getListForUpdate() {
                    @Override
                    public void onListBack(List<Alarms> alarmsList) {
                        updateList(alarmsList);
                        if(alarmsList.size()>10)
                        {
                            holder.txtBadge.setText((alarm.getAlarmCount() - 10) + "");
                        }
                        holder.more.setVisibility(View.VISIBLE);
                    }
                });
            }
        });



    }


    @Override
    public int getItemCount() {
        return alarmName.size();
    }

    class itemViewHolder extends RecyclerView.ViewHolder {
        private TextView txtAlarmTitle;
        private TextView txtBadge;
        private RelativeLayout badgeContainer;
        private ImageView chevronUpDown;
        private RelativeLayout parentContainer;
        private RecyclerView rcChildAlarm;
        private Button more;


         itemViewHolder(View itemView) {
            super(itemView);
            txtAlarmTitle = itemView.findViewById(R.id.alarm_title);
            txtBadge = itemView.findViewById(R.id.alarm_txt_badge);
            badgeContainer = itemView.findViewById(R.id.badge_container);
            chevronUpDown = itemView.findViewById(R.id.alarm_chevron);
            parentContainer = itemView.findViewById(R.id.parent_expandable);
            rcChildAlarm = itemView.findViewById(R.id.alarm_child_rc);
            rcChildAlarm.canScrollVertically(View.FOCUS_DOWN);
            more = itemView.findViewById(R.id.more_btn);

            badgeContainer.setVisibility(View.GONE);

            util.setTypeFace(txtAlarmTitle, context);
            util.setTypeFaceButton(more,context);
            util.setTypeFaceNumber(txtBadge, context);

        }
    }

    private void getAlarms(final AlarmsDefinition alarm, final RecyclerView rcChildAlarm, final RelativeLayout parent, final int alarmId, int skip, int count, final getListForUpdate onGetList) {

        alarmsList = new ArrayList<>();

        api.getUserAlarm(new AlarmApi.OnAlarmsCallBack() {
            @Override
            public void OnResponse(List<Alarms> list) {
                if (list.size() > 0) {
                    for (Alarms a : list
                            ) {
                        if (alarm.getAlarmId() == a.getAppAlarmID())
                            alarmsList.add(a);
                    }

                    if (alarmsList.size() > 0) {
                        onGetList.onListBack(alarmsList);

                        List<Integer> ids = new ArrayList<>();
                        for (Alarms a : alarmsList
                                ) {
                            ids.add(a.getID());
                            AlarmState alarmState = new AlarmState();
                            alarmState.setId(ids);
                            alarmState.setAlarmId(alarmId);
                            alarmState.setField("READ");
                            alarmState.setFieldState(true);
                            alarmState.setAlarmDate(a.getAlarmDate());
                            alarmState.setAlarmTime(a.getAlarmTime());
                            changeAlarmField(alarmState);

                        }
                        childAdapter = new AlarmChildAdapter(context, alarmsList);

                    } else {
                        Toast.makeText(context, "هیچ هشداری با توجه به تنظیمات مورد نظر وجود ندارد.", Toast.LENGTH_SHORT).show();
                    }

                    rcChildAlarm.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    ScrollView.LayoutParams layoutParams = new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, (alarmsList.size()) * 120);
                    rcChildAlarm.setLayoutParams(layoutParams);
                    rcChildAlarm.setAdapter(childAdapter);

                } else {
                    Toast.makeText(context, "هیچ هشداری وجود ندارد.", Toast.LENGTH_SHORT).show();

                }
            }
        }, Constants.URL_GET_USER_ALARMS + "?userName=" + preferences.getPrefUserName() + "&skip=" + skip + "&count=" + count + "&alarmId=" + alarmId);
    }

    private void changeAlarmField(AlarmState alarmState) {
        UserAlarmInfo userAlarmInfo = new UserAlarmInfo();
        userAlarmInfo.setUserName(preferences.getPrefUserName());
        userAlarmInfo.setAlarmState(alarmState);

        api.sendUserAlarmInfo(Constants.URL_CHANGE_ALARM_FIELD, userAlarmInfo);


    }



    private void updateList(List<Alarms> alarms) {
        this.alarmsList.addAll(alarmsList.size(), alarms);
        notifyDataSetChanged();

    }

    public interface getListForUpdate {
        void onListBack(List<Alarms> alarmsList);
    }

}
