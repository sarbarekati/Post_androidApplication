package com.anad.mobile.post.Models;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;

import com.anad.mobile.post.R;
import com.anad.mobile.post.ReportManager.model.Base.Report;
import com.anad.mobile.post.Utils.Util;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elias.mohammadi  96.11.03.
 */

public class ListCreator {

    private ReportCreator reports;

    public ListCreator() {

    }

    private ListCreator(ReportCreator reports) {
        this.reports = reports;
    }


    public static List<ReportRow> getRows(Context context, Report reports) {
        List<ReportRow> rows = new ArrayList<>();
//        ReportRow r1 = new ReportRow(context.getString(R.string.bargiri_date), reports.getMabdaDate());
        ReportRow r2 = new ReportRow(context.getString(R.string.bargiri_time), reports.getModateBargiriMabda());
        ReportRow r3 = new ReportRow(context.getString(R.string.harkat_date), reports.getMabdaDate());
        ReportRow r4 = new ReportRow(context.getString(R.string.mogharar_time), reports.getMabdaMogharar());
        ReportRow r5 = new ReportRow(context.getString(R.string.harkat_time), reports.getMabdaKhoroj());
        ReportRow r6 = new ReportRow(context.getString(R.string.late), reports.getMabdaTakhir());
        ReportRow r7 = new ReportRow(context.getString(R.string.soon), reports.getMabdaTajil());


//        rows.add(r1);
        rows.add(r2);
        rows.add(r3);
        rows.add(r4);
        rows.add(r5);
        rows.add(r6);
        rows.add(r7);
        return rows;
    }

    public static List<ReportRow> getRowsDestination(Context context, Report reports) {
        List<ReportRow> rows = new ArrayList<>();
        ReportRow r1 = new ReportRow(context.getString(R.string.destination_date), reports.getMaghsadDate());
        ReportRow r2 = new ReportRow(context.getString(R.string.mogharar_time), reports.getMaghsadMogharar());
        ReportRow r3 = new ReportRow(context.getString(R.string.destination_time), reports.getMaghsadVorood());
        ReportRow r5 = new ReportRow(context.getString(R.string.late), reports.getMaghsadTakhir());
        ReportRow r6 = new ReportRow(context.getString(R.string.soon), reports.getMaghsadTajil());
        ReportRow r4 = new ReportRow(context.getString(R.string.bazgoshayi_date), reports.getMaghsadBazgoshai());
//        ReportRow r7 = new ReportRow(context.getString(R.string.bazrgoshayi_time), reports.getMaghsaB());
//        ReportRow r8 = new ReportRow(context.getString(R.string.tahvil_marsolat_time), reports.getMaghsad());

        rows.add(r1);
        rows.add(r2);
        rows.add(r3);
        rows.add(r4);
        rows.add(r5);
        rows.add(r6);
//        rows.add(r7);
//        rows.add(r8);
        return rows;
    }

    public static List<ReportRow> getRowsPath(Context context, Report reports) {
        List<ReportRow> rows = new ArrayList<>();
        ReportRow r1 = new ReportRow(context.getString(R.string.tey_moghrar_time), reports.getZamanMoghararTey());
        ReportRow r2 = new ReportRow(context.getString(R.string.tey_masir_realy), reports.getZamanTey());
        ReportRow r3 = new ReportRow(context.getString(R.string.late), reports.getTeyTakhir());
        ReportRow r5 = new ReportRow(context.getString(R.string.soon), reports.getTeyTajil());
        ReportRow r6 = new ReportRow(context.getString(R.string.tey_length), reports.getLength() + "");
        ReportRow r4 = new ReportRow(context.getString(R.string.avg_speed), reports.getAvrageSpeed() + "");
        ReportRow r7 = new ReportRow(context.getString(R.string.max_speed), reports.getMaxSpeed() + "");

        rows.add(r1);
        rows.add(r2);
        rows.add(r3);
        rows.add(r4);
        rows.add(r5);
        rows.add(r6);
        rows.add(r7);

        return rows;
    }

    public static List<ReportRow> getRowsMiddleTitle(Context context, RFID rfid) {
        List<ReportRow> rows = new ArrayList<>();

        for (int i = 0; i < rfid.getMiddle().size(); i++) {
            MiddlePoint m = rfid.getMiddle().get(i);
            ReportRow row = new ReportRow();
            row.setMobadeleName(context.getString(R.string.mobadele_name));
            row.setMobadeleNameContent(m.getPName());
            row.setMobadeleDate(context.getString(R.string.mobadele_date));
            row.setMobadeleDateContent(m.getDate());
            row.setMobadeleTime(context.getString(R.string.mobadele_time));
            row.setMobadeleTimeContent(m.getExchageT());
            row.setMoVorod(context.getString(R.string.mobadele_moghrar_vorod));
            row.setMoVorodContent(m.getPSEnT());
            row.setVorod(context.getString(R.string.mobadele_vorod));
            row.setVorodContent(m.getEnT());
            row.setMoKhoroj(context.getString(R.string.mobadele_moghrar_khoroj));
            row.setMoKhoroj(m.getPSExT());
            row.setKhoroj(context.getString(R.string.mobadele_khoroj));
            row.setKhorojContent(m.getExT());
            row.setVorodLate(context.getString(R.string.mobadele_vorod_late));
            row.setVorodLateContent(m.getDelayEnT());
            row.setVorodSoon(context.getString(R.string.mobadele_vorod_soon));
            row.setVorodSoonCotent(m.getAccEnT());
            row.setKhorojLate(context.getString(R.string.mobadele_khoroj_late));
            row.setKhorojLateContent(m.getDelayExT());
            row.setKhorojSoon(context.getString(R.string.mobadele_khoroj_soon));
            row.setKhorojSoonContent(m.getAccExT());
            rows.add(row);

        }

        return rows;
    }


    public static List<ReportRow> getRowsMobadeleTitle(Context context, List<? extends com.anad.mobile.post.ReportManager.model.Base.MiddlePoint> middlePoint) {
        List<ReportRow> rows = new ArrayList<>();
        for (com.anad.mobile.post.ReportManager.model.Base.MiddlePoint point : middlePoint) {
            ReportRow row = new ReportRow();
            row.setMobadeleName(context.getString(R.string.mobadele_name));
            row.setMobadeleNameContent(point.getMiddlePointTitle());
            row.setMobadeleDate(context.getString(R.string.mobadele_date));
            row.setMobadeleDateContent(point.getMobadeleDate());
            row.setMobadeleTime(context.getString(R.string.mobadele_time));
            row.setMobadeleTimeContent(point.getModateBargiriMobadele());
            row.setMoVorod(context.getString(R.string.mobadele_moghrar_vorod));
            row.setMoVorodContent(point.getMobadeleMoghararVorood());
            row.setVorod(context.getString(R.string.mobadele_vorod));
            row.setVorodContent(point.getMobadeleVorood());
            row.setMoKhoroj(context.getString(R.string.mobadele_moghrar_khoroj));
            row.setMoKhorojContent(point.getMobadeleMoghararKhoroj());
            row.setKhoroj(context.getString(R.string.mobadele_khoroj));
            row.setKhorojContent(point.getMobadeleKhoroj());
            row.setVorodLate(context.getString(R.string.mobadele_vorod_late));
            row.setVorodLateContent(point.getMobadeleTakhirVorood());
            row.setVorodSoon(context.getString(R.string.mobadele_vorod_soon));
            row.setVorodSoonCotent(point.getMobadeleTjilVorood());
            row.setKhorojLate(context.getString(R.string.mobadele_khoroj_late));
            row.setKhorojLateContent(point.getMobadeleTakhirKhoroj());
            row.setKhorojSoon(context.getString(R.string.mobadele_khoroj_soon));
            row.setKhorojSoonContent(point.getMobadeleTajilKhoroj());
            rows.add(row);
        }
        return rows;
    }


    public static List<ReportRow> getRowsMobadeleInner(Context context, List<com.anad.mobile.post.ReportManager.model.Base.MiddlePoint> middlePoint) {
        List<ReportRow> rows = new ArrayList<>();


        for (com.anad.mobile.post.ReportManager.model.Base.MiddlePoint point : middlePoint) {
            ReportRow row = new ReportRow();
            row.setMoVorod(context.getString(R.string.mobadele_moghrar_vorod));
            row.setMoVorodContent(point.getMobadeleMoghararVorood());
            row.setVorod(context.getString(R.string.mobadele_vorod));
            row.setVorodContent(point.getMobadeleVorood());

            row.setMoKhoroj(context.getString(R.string.mobadele_moghrar_khoroj));
            row.setMoVorodContent(point.getMobadeleMoghararKhoroj());
            row.setKhoroj(context.getString(R.string.mobadele_khoroj));
            row.setKhorojContent(point.getMobadeleKhoroj());
            row.setVorodLate(context.getString(R.string.mobadele_vorod_late));
            row.setVorodLateContent(point.getMobadeleTakhirVorood());
            row.setVorodSoon(context.getString(R.string.mobadele_vorod_soon));
            row.setVorodSoonCotent(point.getMobadeleTjilVorood());
            row.setKhorojLate(context.getString(R.string.mobadele_khoroj_late));
            row.setKhorojLateContent(point.getMobadeleTakhirKhoroj());
            row.setKhorojSoon(context.getString(R.string.mobadele_khoroj_soon));
            row.setKhorojSoonContent(point.getMobadeleTajilKhoroj());

            rows.add(row);

        }

        return rows;
    }

    public static List<AlarmsSettingModel> getAlarmsSettingMenu(Context context) {
        List<AlarmsSettingModel> list = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            AlarmsSettingModel a = new AlarmsSettingModel();
            switch (i) {
                case 1:
                    a.setAlarmName(context.getString(R.string.speed_over_100));
                    a.setAlarmId(i);
                    a.setNotificationOn(false);
                    break;

                case 2:
                    a.setAlarmName("توقف در نقاط غیر مجاز");
                    a.setAlarmId(i);
                    a.setNotificationOn(false);
                    break;

                case 3:
                    a.setAlarmName("فرم های صادر شده");
                    a.setAlarmId(i);
                    a.setNotificationOn(false);
                    break;

                case 4:
                    a.setAlarmName("تاخیر در مبدا");
                    a.setAlarmId(i);
                    a.setNotificationOn(false);
                    break;

                case 5:
                    a.setAlarmName("تعجیل در مبدا");
                    a.setAlarmId(i);
                    a.setNotificationOn(false);
                    break;

                case 6:
                    a.setAlarmName("تاخیر در ورود نقطه مبادله");
                    a.setAlarmId(i);
                    a.setNotificationOn(false);
                    break;

                case 7:
                    a.setAlarmName("تعجیل در ورود نقطه مبادله");
                    a.setAlarmId(i);
                    a.setNotificationOn(false);
                    break;

                case 10:
                    a.setAlarmName("تاخیر در مقصد");
                    a.setAlarmId(i);
                    a.setNotificationOn(false);
                    break;

                case 11:
                    a.setAlarmName("تعجیل در مقصد");
                    a.setAlarmId(i);
                    a.setNotificationOn(false);
                    break;

                case 8:
                    a.setAlarmName("تاخیر در خروج نقطه مبادله");
                    a.setAlarmId(i);
                    a.setNotificationOn(false);
                    break;

                case 9:
                    a.setAlarmName("تعجیل در خروج نقطه مبادله");
                    a.setAlarmId(i);
                    a.setNotificationOn(false);
                    break;
            }
            list.add(a);
        }
        return list;
    }

    public static List<Alarms> getAlarms(Context context) {
        List<Alarms> alarms = new ArrayList<>();
        Alarms a1 = new Alarms();
        a1.setAppAlarmID(1);
        a1.setDvcID(1234);
        a1.setAlarmDate("1396/11/23");
        a1.setAlarmTime("10:54");
        a1.setAppAlarmDesc("متن متن متن متن متن متن متن متن متن");


        Alarms a2 = new Alarms();
        a2.setAppAlarmID(1);
        a2.setDvcID(9635);
        a2.setAlarmDate("1396/11/23");
        a2.setAlarmTime("10:54");
        a2.setAppAlarmDesc("متن متن متن متن متن متن متن متن متن");


        Alarms a3 = new Alarms();
        a3.setAppAlarmID(3);
        a3.setDvcID(9268);
        a3.setAlarmDate("1396/11/23");
        a3.setAlarmTime("10:54");
        a3.setAppAlarmDesc("متن متن متن متن متن متن متن متن متن");

        Alarms a4 = new Alarms();
        a4.setAppAlarmID(1);
        a4.setDvcID(9825);
        a4.setAlarmDate("1396/11/23");
        a4.setAlarmTime("10:54");
        a4.setAppAlarmDesc("متن متن متن متن متن متن متن متن متن");
        alarms.add(a1);
        alarms.add(a2);
        alarms.add(a3);
        alarms.add(a4);

        return alarms;


    }

    public static List<AlarmsDefinition> getAlarmsDef(Context context) {
        List<AlarmsDefinition> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            AlarmsDefinition a = new AlarmsDefinition();
            switch (i) {
                case 1:
                    a.setAlarmName(context.getString(R.string.speed_over_100));
                    a.setAlarmId(i);
                    a.setAlarmCount(50);
                    break;

                case 2:
                    a.setAlarmName("توقف در نقاط غیر مجاز");
                    a.setAlarmId(i);
                    break;

                case 3:
                    a.setAlarmName("فرم های صادر شده");
                    a.setAlarmId(i);
                    a.setAlarmCount(5);
                    break;

                case 4:
                    a.setAlarmName("تاخیر در مبدا");
                    a.setAlarmId(i);
                    break;

                case 5:
                    a.setAlarmName("تعجیل در مبدا");
                    a.setAlarmId(i);
                    a.setAlarmCount(10);
                    break;
            }
            list.add(a);
        }
        return list;
    }

    public static List<ReportMenu> getReportMenu(Context context) {
        List<ReportMenu> reportMenus = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            ReportMenu r = new ReportMenu();
            switch (i) {
                case 1:
                    r.setText(context.getString(R.string.rahsepari_report));
                    r.setDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.icons_8_gps_receiving, null));
                    r.setID(0);
                    break;
                case 2:
                    r.setDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.icons_8_rfid_sensor, null));
                    r.setText(context.getString(R.string.rfid_report));
                    r.setID(1);
                    break;
                case 3:
                    r.setDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.icons_8_waypoint_map, null));
                    r.setText(context.getString(R.string.path_report));
                    r.setID(2);
                    break;
            }

            reportMenus.add(r);

        }
        return reportMenus;
    }


    private static List<ChartTestModel> getFakeDataForFillChart() {

        List<ChartTestModel> models = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            String title = (80 + i) + "سال";
            int value = (i * 15) / 10;
            models.add(new ChartTestModel(title, value));
        }
        return models;
    }

    public static BarData getBarData() {

        List<BarEntry> entries = new ArrayList<>();
        List<ChartTestModel> list = getFakeDataForFillChart();
        List<Integer> colors = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            entries.add(new BarEntry(i, list.get(i).getValue()));
            colors.add(Util.getRandomColor());
        }

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(colors);

        return new BarData(dataSet);
    }

    private static class ChartTestModel {
        private String title;
        private int value;

        ChartTestModel(String title, int value) {
            this.title = title;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getTitle() {
            return title;
        }
    }

    public static List<String> getLabelForXAxis() {
        List<ChartTestModel> list = getFakeDataForFillChart();
        List<String> labels = new ArrayList<>();
        for (ChartTestModel model : list) {
            labels.add(model.getTitle());
        }

        return labels;

    }



}
