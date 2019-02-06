package com.anad.mobile.post.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Util;

/**
 * Created by elias.mohammadi on 96.11.26
 */

public class ProfileFragment extends Fragment {
    View view;
   private PostSharedPreferences preferences;
   private TextView txtUserName;
   private TextView txtJob;
   private Util util;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,container,false);
        preferences = new PostSharedPreferences(getActivity());
        util = Util.getInstance();
        initializeView();
        setFont();
        return view;
    }

    private void initializeView() {
        txtJob = view.findViewById(R.id.txt_profile_job);
        txtUserName = view.findViewById(R.id.txt_profile_name);

        txtUserName.setText(preferences.getPrefUserName());
        txtJob.setText(preferences.getUserJob());


    }
    private void setFont(){
        util.setTypeFaceLight(txtJob,getActivity().getApplicationContext());
        util.setTypeFace(txtUserName,getActivity().getApplicationContext());
    }
}
