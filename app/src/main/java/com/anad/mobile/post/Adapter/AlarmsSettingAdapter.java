package com.anad.mobile.post.Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.anad.mobile.post.Models.AlarmsSettingModel;
import com.anad.mobile.post.Models.UserAlarmDefine;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostDataBase;
import com.anad.mobile.post.Utils.Util;

import java.util.List;

/**
 * Created by elias.mohammadi on 2018/02/06
 */

public class AlarmsSettingAdapter extends RecyclerView.Adapter<AlarmsSettingAdapter.itemViewHolder> {
    private List<AlarmsSettingModel> list ;
    private Context context;
    private Util util;
    private List<UserAlarmDefine> userAlarmDefine;
    private PostDataBase db;

    private OnListOfSettingChange onListOfSettingChange;

    public AlarmsSettingAdapter(List<AlarmsSettingModel> list, Context context,List<UserAlarmDefine> userAlarmDefine) {
        this.list = list;
        this.context = context;
        this.userAlarmDefine = userAlarmDefine;
        util = Util.getInstance();
        db = new PostDataBase(context);

    }

    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alarms_setting_item,parent,false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final itemViewHolder holder, int position) {
      final  AlarmsSettingModel a = list.get(position);
        holder.checkItem.setText(a.getAlarmName());
        holder.notificationLabel.setVisibility(View.INVISIBLE);
        holder.enableSwitch.setVisibility(View.INVISIBLE);


        for (int i = 0; i < userAlarmDefine.size() ; i++) {
                if(userAlarmDefine.get(i).AppAlarmID == a.getAlarmId()) {
                    holder.checkItem.setChecked(true);
                    holder.notificationLabel.setVisibility(View.VISIBLE);
                    holder.enableSwitch.setVisibility(View.VISIBLE);

                    if(userAlarmDefine.get(i).ShowNotification)
                    {
                        holder.notificationLabel.setVisibility(View.VISIBLE);
                        holder.enableSwitch.setVisibility(View.VISIBLE);
                        holder.enableSwitch.setChecked(true);
                    }
                }



        }




        holder.checkItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    //setting.put(a.getAlarmId(),false);
                    holder.notificationLabel.setVisibility(View.VISIBLE);
                    holder.enableSwitch.setVisibility(View.VISIBLE);
                    db.addAlarmSetting(a);


                }
                else{



                    db.deleteAlarmSetting(a.getAlarmId());

                    holder.notificationLabel.setVisibility(View.INVISIBLE);
                    holder.enableSwitch.setVisibility(View.INVISIBLE);



                    }
            }


        });

        holder.enableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    a.setNotificationForDb(1);
                    db.updateAlarmSetting(a.getAlarmId(),1);
                    // setting.put(a.getAlarmId(),true);
                }
                else
                {
                    a.setNotificationForDb(0);
                    db.updateAlarmSetting(a.getAlarmId(),0);
                    // setting.put(a.getAlarmId(),false);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class itemViewHolder extends RecyclerView.ViewHolder{
        private AppCompatCheckBox checkItem;
        private TextView notificationLabel;
        private SwitchCompat enableSwitch;
        public itemViewHolder(View itemView) {
            super(itemView);
        checkItem = itemView.findViewById(R.id.alarms_checkBox);
        notificationLabel = itemView.findViewById(R.id.notification_label);
        enableSwitch = itemView.findViewById(R.id.notification_switch);
        util.setTypeFaceLight(notificationLabel,context);
        util.setTypeFaceCheckBox(checkItem,context);

        }
    }



    public interface OnListOfSettingChange
    {
        void OnListOfSettingChange(List<AlarmsSettingModel> list);
    }
}
