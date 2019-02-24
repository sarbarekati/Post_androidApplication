package com.anad.mobile.post.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.anad.mobile.post.Activity.RahRFIDFilter.RahRFIDFilterActivity;
import com.anad.mobile.post.Adapter.GeneralReportAdapter;
import com.anad.mobile.post.Models.RFID;
import com.anad.mobile.post.R;
import com.anad.mobile.post.ReportManager.model.ARP.ARPReport;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariReport;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RahRFIDReportList extends AppCompatActivity {

    private List<RahsepariReport> rahsepariReports;
    private List<ARPReport> arpReports;
    private List<RFID> rfidList;
    private TextView txtNoReport;
    private Util util;
    private boolean hasPermission;
    private PostSharedPreferences preferences;
    private GeneralReportAdapter reportAdapter;

    private RecyclerView rc;

    public static final String RESULT_REPORTS = "result_reports";
    public static final String REPORT_TYPE = "report_type";
    public static final String REPORT_ID = "report_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new PostSharedPreferences(this);
        if (Util.authenticateUser(preferences)) {

            arpReports = new ArrayList<>();
            rahsepariReports = new ArrayList<>();

            setContentView(R.layout.activity_rah_rfidreport_list);

            util = Util.getInstance();
            txtNoReport = findViewById(R.id.txt_no_report);
            util.setTypeFace(txtNoReport, this);
            rfidList = new ArrayList<>();

            Bundle b = getIntent().getExtras();
            if (b != null) {
                int reportId = b.getInt(RahRFIDFilterActivity.REPORT_ID);
                String result = b.getString(RahRFIDFilterActivity.REPORT_INFO);
                Gson gson = new Gson();
                if (reportId == Constants.RAHSEPARI_REPORT) {
                    Type t = new TypeToken<ArrayList<RahsepariReport>>() {
                    }.getType();
                    rahsepariReports = gson.fromJson(result, t);
                } else if (reportId == Constants.ARP_REPORT) {
                    Type t = new TypeToken<ArrayList<ARPReport>>() {
                    }.getType();
                    arpReports = gson.fromJson(result, t);
                }
            }
            initRecyclerView();
        } else {
            Util.backToLoginActivity(this);
        }
    }


    private void initRecyclerView() {

        rc = findViewById(R.id.rc);
        rc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (rahsepariReports != null && rahsepariReports.size()>0) {
            reportAdapter = new GeneralReportAdapter(this, rahsepariReports, null, hasPermission,Constants.RAHSEPARI_REPORT);
        } else if (arpReports != null && arpReports.size()>0) {
            reportAdapter = new GeneralReportAdapter(this, null, arpReports, hasPermission,Constants.ARP_REPORT);
        }
        rc.setAdapter(reportAdapter);
    }


}
