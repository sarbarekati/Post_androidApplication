package com.anad.mobile.post.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.anad.mobile.post.API.AlarmApi;
import com.anad.mobile.post.Models.AlarmState;
import com.anad.mobile.post.Models.Alarms;
import com.anad.mobile.post.Models.UserAlarmInfo;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostDataBase;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by elias.mohammadi on 2018/02/12
 */

public class AlarmChildAdapter extends RecyclerView.Adapter<AlarmChildAdapter.itemViewHolder> {
    private Context context;
    private List<Alarms> alarmList;
    private Util util;
    private PostSharedPreferences preferences;
    private PostDataBase db;
    private boolean archive;

    public AlarmChildAdapter(Context context, List<Alarms> alarmList) {
        util = Util.getInstance();
        this.context = context;
        this.alarmList = alarmList;
        preferences = new PostSharedPreferences(context);
        db = new PostDataBase(context);
        archive = false;
    }
    public AlarmChildAdapter(Context context, List<Alarms> alarmList,boolean archive) {
        util = Util.getInstance();
        this.context = context;
        this.alarmList = alarmList;
        preferences = new PostSharedPreferences(context);
        db = new PostDataBase(context);
        this.archive = archive;
    }

    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_alarm_child,parent,false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(itemViewHolder holder, int position) {
        final Alarms alarms = alarmList.get(position);
        holder.txtDesc.setText(alarms.getAppAlarmDesc());
        holder.txtDate.setText(alarms.getAlarmDate());
        holder.txtTime.setText(alarms.getAlarmTime());
        holder.txtCarCode.setText(alarms.getDvcID()+"");
        View view = LayoutInflater.from(context).inflate(R.layout.option_dialog,null);
        final Dialog mBottomSheetDialog = new Dialog(context,R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        holder.childContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.show();
            }
        });
        TextView txtSave = view.findViewById(R.id.save_txt);

            txtSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserAlarmInfo u = new UserAlarmInfo();
                    List<Integer> ids = new ArrayList<>();
                    ids.add(alarms.getID());
                    AlarmState alarmState = new AlarmState();
                    alarmState.setId(ids);
                    alarmState.setAlarmId(alarms.getAppAlarmID());
                    alarmState.setField("ARCHIVE");
                    alarmState .setFieldState(true);
                    alarmState.setAlarmDate(alarms.getAlarmDate());
                    alarmState.setAlarmTime(alarms.getAlarmTime());
                     u.setUserName(preferences.getPrefUserName());
                    u.setAlarmState(alarmState);
                    AlarmApi api = AlarmApi.getInstance(context);
                    api.sendUserAlarmInfo(Constants.URL_CHANGE_ALARM_FIELD,u);
                    db.addAlarm(alarms);
                    mBottomSheetDialog.dismiss();

                }
            });
        ImageView img = view.findViewById(R.id.save_icon);
            if(archive){
                txtSave.setVisibility(View.GONE);
                img.setVisibility(View.GONE);
            }



        TextView txtShare = view.findViewById(R.id.share_txt);

        txtShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String title ="هشدار :" + alarms.getAppAlarmDesc();
                String body  = " در تاریخ : " + alarms.getAlarmDate() + "\n" + " در ساعت : " + alarms.getAlarmTime() + "\n" + "  برای ماشین با شناسه "+alarms.getDvcID() + " اتفاق افتاده است  ";
                String txtShare = title + body;
                i.putExtra(Intent.EXTRA_TEXT,txtShare);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(i,"اشتراک گذاری از طریق..."));
                mBottomSheetDialog.dismiss();
            }
        });

        TextView txtRemove = view.findViewById(R.id.remove_txt);

        txtRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAlarmInfo u = new UserAlarmInfo();
                List<Integer> ids = new ArrayList<>();
                ids.add(alarms.getID());
                AlarmState alarmState = new AlarmState();
                alarmState.setId(ids);
                alarmState.setAlarmId(alarms.getAppAlarmID());
                alarmState.setField("DELETE");
                alarmState .setFieldState(true);
                alarmState.setAlarmDate(alarms.getAlarmDate());
                alarmState.setAlarmTime(alarms.getAlarmTime());
                u.setUserName(preferences.getPrefUserName());
                u.setAlarmState(alarmState);
                AlarmApi api = AlarmApi.getInstance(context);
                api.sendUserAlarmInfo(Constants.URL_CHANGE_ALARM_FIELD,u);
                mBottomSheetDialog.dismiss();
                alarmList.remove(alarms);
                notifyDataSetChanged();
            }
        });


        util.setTypeFaceLight(txtRemove,context);
        util.setTypeFaceLight(txtSave,context);
        util.setTypeFaceLight(txtShare,context);



    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    class itemViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDesc;
        private TextView txtCarCode;
        private TextView txtDate;
        private TextView txtTime;
        private RelativeLayout childContainer;
        public itemViewHolder(View itemView) {
            super(itemView);
            txtDesc = itemView.findViewById(R.id.alarm_child_desc);
            txtCarCode = itemView.findViewById(R.id.alarm_child_code_car);
            txtDate = itemView.findViewById(R.id.alarm_child_date);
            txtTime = itemView.findViewById(R.id.alarm_child_time);
            childContainer = itemView.findViewById(R.id.child_container);

            util.setTypeFace(txtDesc,context);
            util.setTypeFaceLight(txtTime,context);
            util.setTypeFaceLight(txtDate,context);
            util.setTypeFaceLight(txtCarCode,context);
        }
    }
}
