package com.anad.mobile.post.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anad.mobile.post.Activity.AlarmsSetting;
import com.anad.mobile.post.Adapter.AlarmsTabAdapter;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Utils.Util;

/**
 * Created by public on 96.11.17
 */

public class AlarmFragment extends Fragment implements TabLayout.OnTabSelectedListener,View.OnClickListener {
    View view;
    private static final String TAG = "AlarmFragment";
    private ViewPager alarmsViewPager;
    private TabLayout alarmsTabLayout;
    private AlarmsTabAdapter tabAdapter;
    private Util util;
    private TabLayout.Tab tab;
    private TextView txt;
    private Button btnAlarmsSetting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_alarms, container, false);
        util = Util.getInstance();
        initializeView();
        return view;
    }

    private void initializeView() {
        btnAlarmsSetting = view.findViewById(R.id.alarms_btn_setting);
        btnAlarmsSetting.setOnClickListener(this);
        tabAdapter = new AlarmsTabAdapter(getChildFragmentManager(), getActivity().getApplicationContext());
        alarmsTabLayout = view.findViewById(R.id.alarm_tabLayout);
        alarmsViewPager = view.findViewById(R.id.alarms_viewPager);
        alarmsViewPager.setAdapter(tabAdapter);
        alarmsTabLayout.setupWithViewPager(alarmsViewPager);
        alarmsTabLayout.addOnTabSelectedListener(this);


        for (int i = 0; i < alarmsTabLayout.getTabCount(); i++) {
            tab = alarmsTabLayout.getTabAt(i);
            View v = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.custom_tab, null);
            txt = v.findViewById(R.id.txt_tab);
            txt.setText(tab.getText().toString());
            util.setTypeFace(txt, getActivity().getApplicationContext());
            tab.setCustomView(txt);
        }
        alarmsViewPager.setCurrentItem(1);
        tabAdapter.SetOnSelectView(alarmsTabLayout, 1);
        setFont();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        tabAdapter.SetOnSelectView(alarmsTabLayout, position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

        int position = tab.getPosition();
        tabAdapter.SetOnUnSelectView(alarmsTabLayout, position);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void setFont() {
        util.setTypeFaceButton(btnAlarmsSetting, getActivity().getApplicationContext());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.alarms_btn_setting:
                Intent i = new Intent(getActivity().getApplicationContext(), AlarmsSetting.class);
                startActivity(i);
                break;
        }
    }
}
