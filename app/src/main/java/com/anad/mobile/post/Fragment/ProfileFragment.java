package com.anad.mobile.post.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anad.mobile.post.AccountManager.model.PartyAssign;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Util;
import com.google.gson.Gson;

/**
 * Created by elias.mohammadi on 96.11.26
 */

public class ProfileFragment extends Fragment {
    View view;
   private PostSharedPreferences preferences;
   private TextView txtUserName;
   private TextView txtJob;
   private TextView txtPartyAssignType;
   private Util util;
   private PartyAssign partyAssign;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,container,false);
        preferences = new PostSharedPreferences(getActivity());
        partyAssign = parsePartyAssignInfo(preferences.getPartyInfo());
        util = Util.getInstance();
        initializeView();
        fillViewField();
        setFont();
        return view;
    }

    private PartyAssign parsePartyAssignInfo(String partyAssignInfo){
        return new Gson().fromJson(partyAssignInfo,PartyAssign.class);
    }

    private void initializeView() {
        txtJob = view.findViewById(R.id.txt_profile_job);
        txtUserName = view.findViewById(R.id.txt_profile_name);
        txtPartyAssignType = view.findViewById(R.id.txt_profile_type);
    }

    private void fillViewField(){
        if(partyAssign!=null){
            txtUserName.setText(partyAssign.getOwnerTitle());
            txtJob.setText(partyAssign.getBusinessUnitName());
            txtPartyAssignType.setText(partyAssign.getPartyTypeTitle());
        }
    }


    private void setFont(){
        util.setTypeFaceLight(txtJob,getActivity().getApplicationContext());
        util.setTypeFaceLight(txtPartyAssignType,getActivity().getApplicationContext());
        util.setTypeFace(txtUserName,getActivity().getApplicationContext());
    }
}
