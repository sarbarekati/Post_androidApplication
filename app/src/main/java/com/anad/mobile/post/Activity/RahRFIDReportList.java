package com.anad.mobile.post.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.anad.mobile.post.API.ReportAPI;
import com.anad.mobile.post.Adapter.GeneralReportAdapter;
import com.anad.mobile.post.Models.GeneralReport;
import com.anad.mobile.post.Models.RFID;
import com.anad.mobile.post.Models.ReportCreator;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RahRFIDReportList extends AppCompatActivity {
    private String ReportTitle;
    private GeneralReport gr;
    private List<GeneralReport> generalList;
    private List<RFID> rfidList;
    private TextView txtNoReport;
    private Util util;



    private boolean hasPermission;
    private PostSharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new PostSharedPreferences(this);
        if (Util.authenticateUser(preferences)) {


            setContentView(R.layout.activity_rah_rfidreport_list);

            util = Util.getInstance();
            txtNoReport = findViewById(R.id.txt_no_report);
            util.setTypeFace(txtNoReport, this);
            rfidList = new ArrayList<>();
            Bundle b = getIntent().getExtras();
            if (b != null) {
                Gson gson = new Gson();
                gr = gson.fromJson(b.getString("REPORT_INFO"), GeneralReport.class);

            }


            final RecyclerView rc = findViewById(R.id.rc);
            rc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            ReportAPI api = ReportAPI.getInstance(this);


            if (b.getInt("REPORT_ID") == 0) {
                api.getReport(new ReportAPI.OnReportResponseBack() {
                    @Override
                    public void OnResponseBack(List<ReportCreator> reportCreatorList) {

                        if (reportCreatorList.size() > 0) {

                            rc.setVisibility(View.VISIBLE);
                            txtNoReport.setVisibility(View.GONE);
                            GeneralReportAdapter adapter = new GeneralReportAdapter(RahRFIDReportList.this
                                    , reportCreatorList, rfidList, hasPermission);

                            rc.setAdapter(adapter);
                        } else {
                            rc.setVisibility(View.GONE);
                            txtNoReport.setVisibility(View.VISIBLE);
                        }

                    }
                }, Constants.URL_GET_RAH_REPORT, gr);
            }


            if (b.getInt("REPORT_ID") == 1) {
                api.getReportRFID(new ReportAPI.OnRFIDResponseBack() {
                    @Override
                    public void OnResponseBack(List<RFID> reportCreatorList) {
                        if (reportCreatorList.size() > 0) {

                            rc.setVisibility(View.VISIBLE);
                            txtNoReport.setVisibility(View.GONE);
                            List<ReportCreator> list = new ArrayList<>();
                            for (int i = 0; i < reportCreatorList.size(); i++) {

                                list.add(reportCreatorList.get(i).getRfid());
                            }
                            GeneralReportAdapter adapter = new GeneralReportAdapter(RahRFIDReportList.this
                                    , list,
                                    reportCreatorList, hasPermission);

                            rc.setAdapter(adapter);

                        } else {
                            rc.setVisibility(View.GONE);
                            txtNoReport.setVisibility(View.VISIBLE);
                        }
                    }
                }, Constants.URL_GET_RFID_REPORT, gr);
            }
        }else{
            Util.backToLoginActivity(this);
        }
    }

}
