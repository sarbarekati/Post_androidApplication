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
import com.anad.mobile.post.Adapter.ReportFragmentAdapter;
import com.anad.mobile.post.Models.ListCreator;
import com.anad.mobile.post.R;

/**
 * Created by ELIAS MOHAMMADY 1396.10.09
 */

public class ReportFragment extends Fragment {
    View view;
    RecyclerView rc;
    ReportFragmentAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reports, container, false);
        setUpView();
        return view;
    }

    private void setUpView() {
        adapter = new ReportFragmentAdapter(ListCreator.getReportMenu(getActivity().getApplicationContext()), getActivity().getApplicationContext());
        rc = view.findViewById(R.id.report_fragment_rc);
        rc.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rc.setAdapter(adapter);

    }


}
