package com.anad.mobile.post.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anad.mobile.post.DataProvider.DashboardDataProvider;
import com.anad.mobile.post.Models.DashboardModels.PieChartStyle;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Utils.Constants;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class PieChartActivity extends AppCompatActivity {
    private PieChart pieChart;
    private String dataUrl;
    private int webServiceType;
    private PieChartStyle pieChartStyle;
    private DashboardDataProvider dashboardDataProvider;
    private String centerText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        InitialView();

        pieChartStyle = new PieChartStyle(this,pieChart);

        Bundle b =getIntent().getExtras();
        if(b!=null){
            dataUrl = b.getString(Constants.URL_LABEL);
            webServiceType = b.getInt(Constants.WEBSERVICE_TYPE_LABEL);
            centerText = b.getString(Constants.PIE_CHART_CENTER_TEXT);
        }

        dashboardDataProvider = new DashboardDataProvider(this);
        dashboardDataProvider.getPieChartData(dataUrl, webServiceType, new DashboardDataProvider.onPieDataCallBack() {
            @Override
            public void onPieDataCreate(PieData pieData) {
                pieChartStyle.setLegend(true);
                pieChartStyle.setPieChartStyle(dashboardDataProvider.getLabelsForXAxis(),10f,centerText);
                pieChartStyle.fillPieChartData(pieData);
            }
        });



    }

    private void InitialView() {
        pieChart = findViewById(R.id.pie_chart);
    }
}
