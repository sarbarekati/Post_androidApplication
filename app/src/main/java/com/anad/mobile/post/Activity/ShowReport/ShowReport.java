package com.anad.mobile.post.Activity.ShowReport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anad.mobile.post.Activity.RahRFIDFilter.IRahRFIDReport;
import com.anad.mobile.post.Activity.RahRFIDReportList;
import com.anad.mobile.post.Adapter.MobadeleRowReportAdapter;
import com.anad.mobile.post.Adapter.RowReportAdapter;
import com.anad.mobile.post.Models.ListCreator;
import com.anad.mobile.post.Models.RFID;
import com.anad.mobile.post.Models.ReportCreator;
import com.anad.mobile.post.Models.ReportRow;
import com.anad.mobile.post.R;
import com.anad.mobile.post.ReportManager.MiddlePointApiManager;
import com.anad.mobile.post.ReportManager.model.ARP.ARPMiddlePoint;
import com.anad.mobile.post.ReportManager.model.Base.MiddlePoint;
import com.anad.mobile.post.ReportManager.model.Base.Report;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariMiddlePoint;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;
import com.google.gson.Gson;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.ramotion.foldingcell.FoldingCell;

import java.util.List;


public class ShowReport extends AppCompatActivity implements IShowReport {
    private static final String TAG = "ShowReport";
    private List<ReportRow> rows;
    private List<ReportRow> rowsDestination;
    private List<ReportRow> rowsPath;
    private List<ReportRow> rowsMobadele;
    private List<ReportRow> rowsMobadeleInner;
    private TextView car_number, car_code, driver_name, driver_path, date_report;
    private TextView titleMabda, titleMaghsad, titlePath, titleNoghteMobadele, titleStopPoint;
    private TextView car_number_title, car_code_title, driver_name_title, driver_path_title, date_report_title;
    private TextView report_title;
    ExpandableTextView expTv1;
    private CardView cardStopPoint;
    private PostSharedPreferences preferences;
    Util util;
    RFID rfid;

    private MiddlePointApiManager apiManager;
    private long reportId;
    private Report report;
    private RecyclerView rcMobadele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new PostSharedPreferences(this);
        apiManager = new MiddlePointApiManager(this, preferences, this);
        if (Util.authenticateUser(preferences)) {


            setContentView(R.layout.activity_report);
            util = Util.getInstance();
            setUpTextView();

            Bundle b = getIntent().getExtras();
            if (b != null) {

                reportId = b.getLong(RahRFIDReportList.REPORT_ID);
                if(b.getInt(RahRFIDReportList.REPORT_TYPE) == Constants.RAHSEPARI_REPORT) {
                    apiManager.callGetRahsepariMiddlePoint(reportId);
                }else if(b.getInt(RahRFIDReportList.REPORT_TYPE) == Constants.ARP_REPORT){
                    apiManager.callGetARPMiddlePoint(reportId);
                }

                String getJson = b.getString(RahRFIDReportList.RESULT_REPORTS);
                Gson gson = new Gson();
                report = gson.fromJson(getJson, Report.class);
                cardStopPoint = findViewById(R.id.card_stop_point);

                cardStopPoint.setVisibility(View.GONE);

                rows = ListCreator.getRows(this, report);
                rowsDestination = ListCreator.getRowsDestination(this, report);
                rowsPath = ListCreator.getRowsPath(this, report);


                String carCode = report.getDeviceCode() + "";
                car_code.setText(carCode);
//                car_number.setText(report.get());
                driver_name.setText(report.getDriver1());
                driver_path.setText(report.getReportRouteTitle());
                date_report.setText(report.getReportDate());
//                expTv1.setText(report.getStopPoint());

            }


            RecyclerView rc = findViewById(R.id.inception_rc);
            RecyclerView rcDestination = findViewById(R.id.destination_rc);
            RecyclerView rcPath = findViewById(R.id.path_rc);
            rcMobadele = findViewById(R.id.mobadele_outer_rc);
            rcMobadele.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


            final FoldingCell fInception = findViewById(R.id.folding_cell);
            final FoldingCell fDestination = findViewById(R.id.folding_cell_destination);
            final FoldingCell fPath = findViewById(R.id.folding_cell_path);
            Button btnPlus = findViewById(R.id.inception_plus_btn);
            Button btnMinus = findViewById(R.id.inception_minus_btn);
            Button btnDestinationPlus = findViewById(R.id.destination_plus_btn);
            Button btnDestinationMinus = findViewById(R.id.destination_minus_btn);

            Button btnPathPlus = findViewById(R.id.path_plus_btn);
            Button btnPathMinus = findViewById(R.id.path_minus_btn);
            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fInception.toggle(false);
                }
            });
            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fInception.toggle(false);
                }
            });

            btnDestinationPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fDestination.toggle(false);
                }
            });
            btnDestinationMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fDestination.toggle(false);
                }
            });

            btnPathPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fPath.toggle(false);
                }
            });
            btnPathMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fPath.toggle(false);
                }
            });

            //===========================================================
            //===========================================================
            RowReportAdapter adapter = new RowReportAdapter(rows, this);
            RowReportAdapter adapterDestination = new RowReportAdapter(rowsDestination, this);
            RowReportAdapter adapterPath = new RowReportAdapter(rowsPath, this);

            rc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rcDestination.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rcPath.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            //===========================================================
            //===========================================================

            rc.setAdapter(adapter);
            rcDestination.setAdapter(adapterDestination);
            rcPath.setAdapter(adapterPath);
            TextView txtTitle = findViewById(R.id.report_title);
            util.setTypeFace(txtTitle, this);

        } else {
            Util.backToLoginActivity(this);
        }
    }


    private void setUpTextView() {
        car_number = findViewById(R.id.car_number_report);
        car_code = findViewById(R.id.car_code_report);
        driver_path = findViewById(R.id.path_report);
        date_report = findViewById(R.id.date_report);
        driver_name = findViewById(R.id.driver_name);


        car_number_title = findViewById(R.id.car_number_title);
        car_code_title = findViewById(R.id.car_code_title);
        driver_path_title = findViewById(R.id.path_title);
        date_report_title = findViewById(R.id.date_title);
        driver_name_title = findViewById(R.id.name_title);

        expTv1 = findViewById(R.id.expand_text_view);

        titleMabda = findViewById(R.id.title_mabda);
        titleMaghsad = findViewById(R.id.title_maghsad);
        titlePath = findViewById(R.id.title_path);
        titleNoghteMobadele = findViewById(R.id.title_noghte_mobadele);
        titleStopPoint = findViewById(R.id.title_stop_point);

        report_title = findViewById(R.id.report_title);

        setFont();


    }

    private void setFont() {
        util.setTypeFaceLight(car_number, this);
        util.setTypeFaceLight(car_code, this);
        util.setTypeFaceLight(driver_path, this);
        util.setTypeFaceLight(date_report, this);
        util.setTypeFaceLight(driver_name, this);

        util.setTypeFaceLight(titleMabda, this);
        util.setTypeFaceLight(titleMaghsad, this);
        util.setTypeFaceLight(titlePath, this);
        util.setTypeFaceLight(titleNoghteMobadele, this);
        util.setTypeFaceLight(titleStopPoint, this);

        util.setTypeFaceLight(car_number_title, this);
        util.setTypeFaceLight(car_code_title, this);
        util.setTypeFaceLight(driver_path_title, this);
        util.setTypeFaceLight(date_report_title, this);
        util.setTypeFaceLight(driver_name_title, this);
    }


    @Override
    public void setRahsepariMiddlePoint(List<RahsepariMiddlePoint> middlePoints) {
        fillRecyclerViewWithMiddlePoint(middlePoints);
    }

    @Override
    public void setARPMiddlePoint(List<ARPMiddlePoint> middlePoints) {
        fillRecyclerViewWithMiddlePoint(middlePoints);
    }


    private void fillRecyclerViewWithMiddlePoint(List<? extends MiddlePoint> middlePoints){
        rowsMobadele = ListCreator.getRowsMobadeleTitle(this,middlePoints);
        MobadeleRowReportAdapter adapterMobadele = new MobadeleRowReportAdapter(rowsMobadele, this);
        rcMobadele.setAdapter(adapterMobadele);
    }


}
