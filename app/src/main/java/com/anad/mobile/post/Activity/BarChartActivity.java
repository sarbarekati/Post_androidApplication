package com.anad.mobile.post.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anad.mobile.post.DataProvider.DashboardDataProvider;
import com.anad.mobile.post.Models.DashboardModels.BarChartStyle;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Utils.Constants;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

public class BarChartActivity extends AppCompatActivity {
    private String dataUrl;
    private BarChart barChart;
    private DashboardDataProvider dashboardDataProvider;
    private int webServiceType;
    private BarChartStyle barChartStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        initialView();
        barChartStyle = new BarChartStyle(this,barChart);
        getIntentData();
        getChartDataFromDataProvider();

    }

    private void getIntentData() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            dataUrl = b.getString(Constants.URL_LABEL);
            webServiceType = b.getInt(Constants.WEBSERVICE_TYPE_LABEL);

        }
    }

    private void getChartDataFromDataProvider() {
        dashboardDataProvider = new DashboardDataProvider(this);
        dashboardDataProvider.getBarChartData(dataUrl, webServiceType,new DashboardDataProvider.onBarDataCallBack() {
            @Override
            public void onBarDataCreate(BarData barData) {
                barChartStyle.fillBarChart(barData);
                barChartStyle.setStyleBarChart(dashboardDataProvider.getLabelsForXAxis(),true);

            }
        });
    }

    private void initialView() {
        barChart = findViewById(R.id.bar_chart);
    }





}
