package com.anad.mobile.post.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anad.mobile.post.API.DashboardApi;
import com.anad.mobile.post.Activity.BarChartActivity;
import com.anad.mobile.post.Activity.PieChartActivity;
import com.anad.mobile.post.DataProvider.DashboardDataProvider;
import com.anad.mobile.post.Models.DashboardModels.BarChartStyle;
import com.anad.mobile.post.Models.DashboardModels.PieChartStyle;
import com.anad.mobile.post.Models.DashboardModels.WebServiceType;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.HashMap;


public class DashboardFragment extends Fragment implements View.OnClickListener {
    View view;
    TextView txtRah, txtOnlineCar, txtForm, txtBarChart, txtPieChart1, txtPieChart2;
    TextView txtRahNo, txtFormNo, txtOnlineNo;
    Context ctx;
    DashboardApi api;
    private PieChart pieChartLen;
    private PieChart pieChartOpenDoor;
    private BarChart barChartOpenDoor;
    private BarChart barChart;
    private ImageView moreDetail;
    private ImageView moreDetailPieChart;

    private static final String TAG = "DashboardFragment";
    private DashboardDataProvider dataProvider;
    private LinearLayout barChartContainer;
    private String year, month, day,date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ctx = getActivity().getApplicationContext();
        api = DashboardApi.getInstance(ctx);
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dataProvider = new DashboardDataProvider(ctx);
        setDate();


        initializedView();
        fillCountableDashboardField();
        fillPieChartOpenDoor();
//        fillBarChartOpenDoor();
        fillBarChartRahForm();


        barChartContainer.setOnClickListener(this);
        moreDetail.setOnClickListener(this);
        moreDetailPieChart.setOnClickListener(this);

        return view;

    }

    private void setDate() {
        date = Util.getCurrentDate();
        year = Util.getYear(date);
        month = Util.getMonth(date);
        day = Util.getDay(date);
    }


    private void initializedView() {
        txtRah = view.findViewById(R.id.rah_text);
        txtBarChart = view.findViewById(R.id.bar_chart_text);
        txtForm = view.findViewById(R.id.form_text);
        txtOnlineCar = view.findViewById(R.id.online_text);
        txtPieChart1 = view.findViewById(R.id.chart_title_pie);
        txtPieChart2 = view.findViewById(R.id.chart_title_pie_2);

        txtRahNo = view.findViewById(R.id.rah_no_text);
        txtFormNo = view.findViewById(R.id.form_no_text);
        txtOnlineNo = view.findViewById(R.id.online_no_text);

        pieChartLen = view.findViewById(R.id.chart_pie);
        pieChartOpenDoor = view.findViewById(R.id.chart_pie_2);
        barChartOpenDoor = view.findViewById(R.id.barChart_2);
        barChart = view.findViewById(R.id.bar_chart_rah_form);
        barChartContainer = view.findViewById(R.id.bar_chart_container);

        moreDetail = view.findViewById(R.id.more_detail);
        moreDetailPieChart = view.findViewById(R.id.more_detail_pie_chart);

        setTypeFace();
    }

    private void setTypeFace() {
        Util util = Util.getInstance();
        util.setTypeFace(txtRah, ctx);
        util.setTypeFace(txtBarChart, ctx);
        util.setTypeFace(txtForm, ctx);
        util.setTypeFace(txtOnlineCar, ctx);
        util.setTypeFace(txtPieChart1, ctx);
        util.setTypeFace(txtPieChart2, ctx);
        util.setTypeFaceNumber(txtRahNo, ctx);
        util.setTypeFaceNumber(txtFormNo, ctx);
        util.setTypeFaceNumber(txtOnlineNo, ctx);
    }


    private void fillCountableDashboardField() {
        getData(Constants.URL_ALL_DEVICE_TODAY +date, txtOnlineNo);
        getData(Constants.URL_ALL_FORM_TODAY + date, txtRahNo);
    }

    private void getData(String url, final TextView txt) {
        dataProvider.getCountableData(url, new DashboardDataProvider.onCountableDataCallBack() {
            @Override
            public void onResponse(int count) {
                String text = count + "";
                txt.setText(text);
            }
        });
    }

    private void fillPieChartLen() {
        final PieChartStyle pieStyle = new PieChartStyle(ctx, pieChartLen);
        dataProvider.getPieChartData(Constants.URL_SUM_OF_ORG_LEN + year, WebServiceType.SPEED_DASHBOARD, new DashboardDataProvider.onPieDataCallBack() {
            @Override
            public void onPieDataCreate(PieData pieData) {
                pieStyle.fillPieChartData(pieData);
                pieStyle.setPieChartStyle(dataProvider.getLabelsForXAxis(),6f,"");
            }
        });
    }

    private void fillPieChartOpenDoor() {
        final PieChartStyle style = new PieChartStyle(ctx, pieChartOpenDoor);
        dataProvider.getPieChartData(Constants.URL_COUNT_OF_OPEN_DOOR + year, WebServiceType.FORM_DASHBOARD, new DashboardDataProvider.onPieDataCallBack() {
            @Override
            public void onPieDataCreate(PieData pieData) {
                style.setLegend(false);
                style.fillPieChartData(pieData);
                style.setPieChartStyle(dataProvider.getLabelsForXAxis(),6f,"");

            }
        });
    }

    private void fillBarChartOpenDoor(){
        final BarChartStyle style = new BarChartStyle(ctx,barChartOpenDoor);
        dataProvider.getBarChartData(Constants.URL_COUNT_OF_OPEN_DOOR + year, WebServiceType.FORM_DASHBOARD, new DashboardDataProvider.onBarDataCallBack() {
            @Override
            public void onBarDataCreate(BarData barData) {
                style.fillBarChart(barData);
                style.setStyleBarChart(dataProvider.getLabelsForXAxis(),false);
            }
        });
    }

    private void fillBarChartRahForm() {
        final BarChartStyle barChartStyle = new BarChartStyle(ctx, barChart);
        final DashboardDataProvider barDataProvider = new DashboardDataProvider(ctx);
        barDataProvider.getBarChartData(Constants.URL_COUNT_OF_RAH_FORM_ROUTE + year, WebServiceType.FORM_DASHBOARD, new DashboardDataProvider.onBarDataCallBack() {
            @Override
            public void onBarDataCreate(BarData barData) {
                barChartStyle.fillBarChart(barData);
                barChartStyle.setStyleBarChart(barDataProvider.getLabelsForXAxis(), false);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_detail:
                startIntent(BarChartActivity.class,
                        createDataBundle(Constants.URL_COUNT_OF_RAH_FORM_ROUTE + year,
                                WebServiceType.FORM_DASHBOARD)
                );
                break;

            case R.id.more_detail_pie_chart:
                startIntent(PieChartActivity.class,
                        createDataBundle(Constants.URL_COUNT_OF_OPEN_DOOR + year,
                                WebServiceType.FORM_DASHBOARD,ctx.getString(R.string.open_door_in_wrong_location))
                );
                break;
        }

    }

    private void startIntent(Class<?> className, Bundle parameters) {

        Intent i = new Intent(ctx, className);
        i.putExtras(parameters);
        startActivity(i);

    }

    private Bundle createDataBundle(String url, int webServiceType) {
        Bundle parameter = new Bundle();
        parameter.putString(Constants.URL_LABEL, url);
        parameter.putInt(Constants.WEBSERVICE_TYPE_LABEL, webServiceType);
        return parameter;
    }
    private Bundle createDataBundle(String url, int webServiceType,String centerText) {
        Bundle parameter = new Bundle();
        parameter.putString(Constants.URL_LABEL, url);
        parameter.putString(Constants.PIE_CHART_CENTER_TEXT,centerText);
        parameter.putInt(Constants.WEBSERVICE_TYPE_LABEL, webServiceType);
        return parameter;
    }


}
