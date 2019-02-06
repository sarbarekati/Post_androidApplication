package com.anad.mobile.post.DataProvider;


import android.content.Context;
import android.graphics.Color;

import com.anad.mobile.post.API.DashboardApi;
import com.anad.mobile.post.Models.DashboardModels.BaseDashboardModel;
import com.anad.mobile.post.Models.DashboardModels.FormDashboardModel;
import com.anad.mobile.post.Models.DashboardModels.IDashboardModel;
import com.anad.mobile.post.Models.DashboardModels.PieChartStyle;
import com.anad.mobile.post.Models.DashboardModels.SpeedDashboardModel;
import com.anad.mobile.post.Models.DashboardModels.WebServiceType;
import com.anad.mobile.post.Models.DashboardModels.YearDashboardModel;
import com.anad.mobile.post.Utils.Util;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;


public class DashboardDataProvider {
    private DashboardApi api;
    private List<String> labels;
    private List<BarEntry> barEntries;
    private List<PieEntry> pieEntries;
    private List<Integer> colors;
    private Context context;



    public DashboardDataProvider(Context context) {
        this.context = context;
        api = DashboardApi.getInstance(context);
    }




    public void getBarChartData(String url,int webServiceType, final onBarDataCallBack onBarDataCallBack) {




        switch (webServiceType) {

            case WebServiceType.BASE_DASHBOARD:
                getBaseDashboardServices(url, onBarDataCallBack);
                break;

            case WebServiceType.YEAR_DASHBOARD:
                getYearDashboardServices(url, onBarDataCallBack);
                break;

            case WebServiceType.FORM_DASHBOARD:
                getFormDashboardServices(url, onBarDataCallBack);
                break;
            case WebServiceType.SPEED_DASHBOARD:
                getSpeedDashboardServices(url, onBarDataCallBack);
                break;

        }


    }

    private void getSpeedDashboardServices(String url, final onBarDataCallBack onBarDataCallBack) {
        barEntries = new ArrayList<>();
        colors = new ArrayList<>();
        api.getSpeedDashboardModelReport(url, new DashboardApi.onSpeedDashboardModelCallBack() {
            BarDataSet dataSet;


            @Override
            public void onResponse(List<SpeedDashboardModel> list) {
                if (list != null && list.size() > 0) {
                    setLabelForXAxis(list);
                    for (int i = 0; i < list.size(); i++) {
                        BarEntry barEntry = new BarEntry(i, list.get(i).getCount());
                        barEntries.add(barEntry);
                        colors.add(Util.getRandomColor());
                    }
                }
                dataSet = new BarDataSet(barEntries, "BarDataSet");
                dataSet.setColors(colors);
                BarData barDate = new BarData(dataSet);
                onBarDataCallBack.onBarDataCreate(barDate);

            }
        });
    }

    private void getSpeedDashboardServices(String url, final onPieDataCallBack onPieDataCallBack) {
        pieEntries = new ArrayList<>();
        colors = new ArrayList<>();
        api.getSpeedDashboardModelReport(url, new DashboardApi.onSpeedDashboardModelCallBack() {
            PieDataSet dataSet;

            @Override
            public void onResponse(List<SpeedDashboardModel> list) {
                if (list != null && list.size() > 0) {
                    setLabelForXAxis(list);
                    for (int i = 0; i < list.size(); i++) {
                        PieEntry pieEntry = new PieEntry(list.get(i).getCount(),"");
                        pieEntries.add(pieEntry);
                        colors.add(Util.getRandomColor());
                    }
                }
                dataSet = new PieDataSet(pieEntries, "");
                dataSet.setColors(colors);
                PieChartStyle.setXValuePositionOutSide(dataSet);
                dataSet.setValueLinePart1Length(0.5f);
                dataSet.setValueLinePart2Length(0.5f);
                dataSet.setValueLineColor(Color.WHITE);
                PieData pieData = new PieData(dataSet);
                onPieDataCallBack.onPieDataCreate(pieData);
            }
        });
    }


    private void getFormDashboardServices(String url, final onBarDataCallBack onBarDataCallBack) {
        barEntries = new ArrayList<>();
        colors = new ArrayList<>();
        api.getFormDashboardModelReport(url, new DashboardApi.onFormDashboardModelCallBack() {
            BarDataSet dataSet;


            @Override
            public void onResponse(List<FormDashboardModel> list) {
                if (list != null && list.size() > 0) {
                    setLabelForXAxis(list);
                    for (int i = 0; i < list.size(); i++) {
                        BarEntry barEntry = new BarEntry(i, list.get(i).getCount());
                        barEntries.add(barEntry);
                        colors.add(Util.getRandomColor());
                    }
                }
                dataSet = new BarDataSet(barEntries, "BarDataSet");
                dataSet.setColors(colors);
                BarData barDate = new BarData(dataSet);
                onBarDataCallBack.onBarDataCreate(barDate);

            }
        });
    }


    private void getYearDashboardServices(String url, final onBarDataCallBack onBarDataCallBack) {

        barEntries = new ArrayList<>();
        colors = new ArrayList<>();
        api.getYearDashboardModelReport(url, new DashboardApi.onYearDashboardModelCallBack() {
            BarDataSet dataSet;


            @Override
            public void onResponse(List<YearDashboardModel> list) {
                if (list != null && list.size() > 0) {
                    setLabelForXAxis(list);
                    for (int i = 0; i < list.size(); i++) {
                        BarEntry barEntry = new BarEntry(i, list.get(i).getCount());
                        barEntries.add(barEntry);
                        colors.add(Util.getRandomColor());
                    }
                }
                dataSet = new BarDataSet(barEntries, "BarDataSet");
                dataSet.setColors(colors);
                BarData barDate = new BarData(dataSet);
                onBarDataCallBack.onBarDataCreate(barDate);

            }
        });
    }


    private void getBaseDashboardServices(String url, final onBarDataCallBack onBarDataCallBack) {
        barEntries = new ArrayList<>();
        colors = new ArrayList<>();
        api.getBaseDashboardModelReport(url, new DashboardApi.onBaseDashboardModelCallBack() {
            BarDataSet dataSet;

            @Override
            public void onResponse(List<BaseDashboardModel> list) {
                if (list != null && list.size() > 0) {
                    setLabelForXAxis(list);
                    for (int i = 0; i < list.size(); i++) {
                        BarEntry barEntry = new BarEntry(i, list.get(i).getCount());
                        barEntries.add(barEntry);
                        colors.add(Util.getRandomColor());
                    }
                }
                dataSet = new BarDataSet(barEntries, "BarDataSet");
                dataSet.setColors(colors);
                BarData barDate = new BarData(dataSet);
                onBarDataCallBack.onBarDataCreate(barDate);
            }
        });
    }

    private void getBaseDashboardServices(String url, final onPieDataCallBack onPieDataCallBack) {
        pieEntries = new ArrayList<>();
        colors = new ArrayList<>();
        api.getBaseDashboardModelReport(url, new DashboardApi.onBaseDashboardModelCallBack() {
            PieDataSet dataSet;

            @Override
            public void onResponse(List<BaseDashboardModel> list) {
                if (list != null && list.size() > 0) {
                    setLabelForXAxis(list);
                    for (int i = 0; i < list.size(); i++) {
                        PieEntry pieEntry = new PieEntry(i, list.get(i).getCount());
                        pieEntries.add(pieEntry);
                        colors.add(Util.getRandomColor());
                    }
                }
                dataSet = new PieDataSet(pieEntries, "");
                dataSet.setColors(colors);
                PieChartStyle.setXValuePositionOutSide(dataSet);
                dataSet.setValueLinePart1Length(0.5f);
                dataSet.setValueLinePart2Length(0.5f);
                dataSet.setValueLineColor(Color.WHITE);
                PieData pieData = new PieData(dataSet);
                onPieDataCallBack.onPieDataCreate(pieData);
            }
        });
    }


    private void getYearDashboardServices(String url, final onPieDataCallBack onPieDataCallBack) {
        pieEntries = new ArrayList<>();
        colors = new ArrayList<>();
        api.getYearDashboardModelReport(url, new DashboardApi.onYearDashboardModelCallBack() {
            PieDataSet dataSet;

            @Override
            public void onResponse(List<YearDashboardModel> list) {
                if (list != null && list.size() > 0) {
                    setLabelForXAxis(list);
                    for (int i = 0; i < list.size(); i++) {
                        PieEntry pieEntry = new PieEntry(i, list.get(i).getCount());
                        pieEntries.add(pieEntry);
                        colors.add(Util.getRandomColor());
                    }
                }
                dataSet = new PieDataSet(pieEntries, "");
                dataSet.setColors(colors);
                PieChartStyle.setXValuePositionOutSide(dataSet);
                dataSet.setValueLinePart1Length(0.5f);
                dataSet.setValueLinePart2Length(0.5f);
                dataSet.setValueLineColor(Color.WHITE);
                PieData pieData = new PieData(dataSet);
                onPieDataCallBack.onPieDataCreate(pieData);
            }
        });
    }

    private void getFormDashboardServices(String url, final onPieDataCallBack onPieDataCallBack) {
        pieEntries = new ArrayList<>();
        colors = new ArrayList<>();
        api.getFormDashboardModelReport(url, new DashboardApi.onFormDashboardModelCallBack() {
            PieDataSet dataSet;

            @Override
            public void onResponse(List<FormDashboardModel> list) {
                if (list != null && list.size() > 0) {
                    setLabelForXAxis(list);
                    for (int i = 0; i < list.size(); i++) {
                        PieEntry pieEntry = new PieEntry(list.get(i).getCount(),list.get(i).getTitle());
                        pieEntries.add(pieEntry);
                        colors.add(Util.getRandomColor());
                    }
                }
                dataSet = new PieDataSet(pieEntries, "");
                dataSet.setColors(colors);
                dataSet.setValueTypeface(Util.getFont(context));
                dataSet.setValueTextColor(Color.WHITE);
                dataSet.setValueLinePart1Length(0.5f);
                dataSet.setValueLinePart2Length(0.5f);
                dataSet.setValueLineColor(Color.WHITE);
                PieChartStyle.setXValuePositionOutSide(dataSet);



                PieData pieData = new PieData(dataSet);

                onPieDataCallBack.onPieDataCreate(pieData);
            }
        });
    }

    public void getPieChartData(String dataUrl,int webServiceType, onPieDataCallBack onPieDataCallBack) {
        switch (webServiceType) {

            case WebServiceType.YEAR_DASHBOARD:
                getYearDashboardServices(dataUrl,onPieDataCallBack);
                break;

            case WebServiceType.BASE_DASHBOARD:
                getBaseDashboardServices(dataUrl, onPieDataCallBack);
                break;

            case WebServiceType.FORM_DASHBOARD:
                getFormDashboardServices(dataUrl,onPieDataCallBack);
                break;

            case WebServiceType.SPEED_DASHBOARD:
                getSpeedDashboardServices(dataUrl, onPieDataCallBack);
                break;

        }

    }

    public void getCountableData(String dataUrl, final onCountableDataCallBack onCountableDataCallBack)
    {
        api.getCountableDashboardReport(dataUrl, new DashboardApi.onIntegerCallBack() {
            @Override
            public void onResponse(int count) {
                onCountableDataCallBack.onResponse(count);
            }
        });
    }

    public interface onCountableDataCallBack{
        void onResponse(int count);
    }


    public interface onBarDataCallBack {
        void onBarDataCreate(BarData barData);
    }

    public interface onPieDataCallBack {
        void onPieDataCreate(PieData pieData);
    }

    private <T extends IDashboardModel> void setLabelForXAxis(List<T> list) {
        labels = new ArrayList<>();
        for (IDashboardModel dashboardModel : list) {
            labels.add(dashboardModel.getTitle());
        }

    }

    public List<String> getLabelsForXAxis() {
        return labels;
    }


}
