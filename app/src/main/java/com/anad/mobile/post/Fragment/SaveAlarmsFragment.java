package com.anad.mobile.post.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anad.mobile.post.Activity.MainActivity;
import com.anad.mobile.post.Adapter.AlarmChildAdapter;
import com.anad.mobile.post.Models.Alarms;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostDataBase;

import java.util.List;

/**
 * Created by elias.mohammadi on 2018/02/06
 */

public class SaveAlarmsFragment extends Fragment {
    private View view;
    RecyclerView rc;
    private PostDataBase db;
    AlarmChildAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_save_alarms, container, false);
        return view;
    }

    private void initializeView() {
        List<Alarms> list = db.getAlarms();
        adapter = new AlarmChildAdapter(MainActivity.mainActivityContext, list, true);
        rc = view.findViewById(R.id.rc_archive);
        rc.setLayoutManager(new LinearLayoutManager(MainActivity.mainActivityContext, LinearLayoutManager.VERTICAL, false));
        rc.setAdapter(adapter);
    }

    public static SaveAlarmsFragment newInstance() {

        Bundle args = new Bundle();
        SaveAlarmsFragment fragment = new SaveAlarmsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (isVisibleToUser) {
                db = new PostDataBase(getActivity().getApplicationContext());
                initializeView();

            }
        }
    }
}
