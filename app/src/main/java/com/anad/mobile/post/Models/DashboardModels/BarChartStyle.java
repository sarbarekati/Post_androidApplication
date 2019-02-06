package com.anad.mobile.post.Models.DashboardModels;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import com.anad.mobile.post.Utils.Util;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

public class BarChartStyle {

    private Context context;
    private BarChart barChart;
    private List<String> labels;

    public BarChartStyle(Context context, BarChart barChart) {
        this.context = context;
        this.barChart = barChart;

    }




    public void setStyleBarChart(List<String> labels, boolean showLabel) {
        this.labels = labels;
        barChart.setFitBars(true);
        barChart.getLegend().setEnabled(false);

        setDescriptionLabelStyle();
        setStyleForXAxis(showLabel);
        setStyleForYAxis();

        barChart.invalidate();

    }

    public void fillBarChart(BarData data) {
        data.setBarWidth(0.5f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(Util.getFont(context));
        barChart.setData(data);
    }

    private void setDescriptionLabelStyle() {
        barChart.getDescription().setEnabled(false);
    }


    private void setStyleForXAxis(boolean showLabel) {
        XAxis xAxis = barChart.getXAxis();
        if (showLabel) {
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return labels.get((int) value);
                }
            });
            if (labels != null && labels.size() > 0) {
                xAxis.setLabelCount(labels.size());
            }
        } else {
            xAxis.setDrawLabels(false);
        }


        xAxis.setTextColor(Color.WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(Util.getFont(context));
        xAxis.setLabelRotationAngle(90f);


    }

    private void setStyleForYAxis() {
        YAxis yAxisR = barChart.getAxisRight();
        YAxis yAxisL = barChart.getAxisLeft();
        yAxisR.setEnabled(false);
        yAxisL.setTextColor(Color.WHITE);
        yAxisL.setTypeface(Util.getFont(context));
    }

}
