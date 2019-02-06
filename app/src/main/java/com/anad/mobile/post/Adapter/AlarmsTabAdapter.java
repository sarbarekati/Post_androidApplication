package com.anad.mobile.post.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.TextView;

import com.anad.mobile.post.Fragment.SaveAlarmsFragment;
import com.anad.mobile.post.Fragment.ShowAlarmsFragment;
import com.anad.mobile.post.R;

/**
 * Created by elias.mohammadi on 2018/02/06
 */

public class AlarmsTabAdapter extends FragmentPagerAdapter {
    private Context context;
    public AlarmsTabAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SaveAlarmsFragment.newInstance();
            case 1:
                return ShowAlarmsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "بایگانی شده ها";
            case 1:
                return "همه هشدارها";
            default:
                return null;
        }
    }

    public void SetOnSelectView(TabLayout tabLayout,int position){
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView txt = selected.findViewById(R.id.txt_tab);
        txt.setTextColor(Color.WHITE);
    }
    public void SetOnUnSelectView(TabLayout tabLayout,int position){
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        View selected = tab.getCustomView();
        TextView txt = selected.findViewById(R.id.txt_tab);
        txt.setTextColor(ResourcesCompat.getColor(context.getResources(),R.color.gray_title,null));
    }
}
