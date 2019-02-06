package com.anad.mobile.post.Models.DashboardModels;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import com.anad.mobile.post.Utils.Util;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.List;


public class PieChartStyle {

    private Context context;
    private PieChart pieChart;


    public PieChartStyle(Context context,PieChart pieChart)
    {
        this.context = context;
        this.pieChart = pieChart;
    }


    public void fillPieChartData(PieData pieData) {
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public void setPieChartStyle(List<String> labels,float fontSize,String centerText) {
        pieChart.setEntryLabelTypeface(Util.getFont(context));
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(fontSize);
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setCenterTextTypeface(Util.getFont(context));
        pieChart.setCenterTextSize(10f);
        pieChart.setCenterText(centerText);
        setDescriptionLabelStyle();

    }

    public static void setXValuePositionOutSide(PieDataSet dataSet){
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
    }

    public static void setXValuePositionInside(PieDataSet dataSet){
        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
    }


    private void setDescriptionLabelStyle() {
        pieChart.getDescription().setEnabled(false);
    }

    public void setLegend(boolean show){
        Legend legend = pieChart.getLegend();
        legend.setEnabled(show);
        legend.setTextColor(Color.WHITE);
        legend.setTypeface(Util.getFont(context));
        legend.setTextSize(10f);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        legend.setXEntrySpace(5f);
        legend.setYEntrySpace(5f);

    }
}
