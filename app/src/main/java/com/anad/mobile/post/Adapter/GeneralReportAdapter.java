package com.anad.mobile.post.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anad.mobile.post.Activity.RahRFIDReportList;
import com.anad.mobile.post.Activity.ShowReport.ShowReport;
import com.anad.mobile.post.Models.RFID;
import com.anad.mobile.post.Models.ReportCreator;
import com.anad.mobile.post.R;
import com.anad.mobile.post.ReportManager.model.ARP.ARPReport;
import com.anad.mobile.post.ReportManager.model.Base.Report;
import com.anad.mobile.post.ReportManager.model.Rahsepari.RahsepariReport;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.PdfBuilder;
import com.anad.mobile.post.Utils.Util;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by elias.mohammadi on 96/10/26
 */

public class GeneralReportAdapter extends RecyclerView.Adapter<GeneralReportAdapter.itemViewHolder> {
    private List<ReportCreator> list;
    private List<RFID> rfidLits;
    private Context context;
    private String Title;
    private Util utils;
    private RFID rfid;
    private String rfidObj;
    private boolean hasPermission;
    private List<RahsepariReport> rahsepariReports;
    private List<ARPReport> arpReports;
    private int reportType;
    private int reportIdForMiddlePoint = 0;
    private Report report ;

    public GeneralReportAdapter(Context context, List<ReportCreator> list, List<RFID> rfidList, boolean hasPermission) {
        this.list = list;
        this.context = context;
        this.rfidLits = rfidList;
        this.hasPermission = hasPermission;
        utils = Util.getInstance();
    }

    public GeneralReportAdapter(Context context, List<RahsepariReport> rahsepariReports,List<ARPReport> arpReports, boolean hasPermission, int reportType) {

        this.rahsepariReports = rahsepariReports;
        this.arpReports = arpReports;
        this.context = context;
        this.hasPermission = hasPermission;
        this.reportType = reportType;
        utils = Util.getInstance();
    }


    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_recycler_item, parent, false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(itemViewHolder holder, final int position) {


        report = new Report();
        if(reportType == Constants.RAHSEPARI_REPORT){
            report = rahsepariReports.get(position);
        }else if(reportType == Constants.ARP_REPORT){
            report = arpReports.get(position);
        }


        holder.ReportTitle.setText(Title);
        holder.ReportDiverName.setText(report.getDriver1());
        String vehicleId = report.getDeviceCode() + "";
        holder.ReportDiverId.setText(vehicleId);
        holder.ReportDate.setText(report.getReportDate());
        holder.ReportPath.setText(report.getReportRouteTitle());


        holder.mainRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reportType == Constants.RAHSEPARI_REPORT){
                    reportIdForMiddlePoint = rahsepariReports.get(position).getRahsepariReportId();
                }else if(reportType == Constants.ARP_REPORT){
                    reportIdForMiddlePoint = arpReports.get(position).getARPReportId();
                }



                Gson gson = new Gson();
                String result = gson.toJson(report);

                Bundle bundle = new Bundle();
                bundle.putString(RahRFIDReportList.RESULT_REPORTS,result);
                bundle.putInt(RahRFIDReportList.REPORT_TYPE,reportType);
                bundle.putLong(RahRFIDReportList.REPORT_ID,reportIdForMiddlePoint);

                Util.gotoActivity(context,ShowReport.class,bundle,false);

            }
        });

        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String reportToPdf = gson.toJson(report);
                if (rfidLits.size() > 0) {

                    rfidObj = gson.toJson(rfid);
                } else {
                    rfidObj = "";
                }

                Intent i = new Intent(context, PdfBuilder.class);
                i.putExtra("PERMISSION", hasPermission);
                i.putExtra("ID", position);
                i.putExtra("PDF_INFO", reportToPdf);
                i.putExtra("RFID_DETAIL", rfidObj);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });


    }


    @Override
    public int getItemCount() {
        if(rahsepariReports!=null && rahsepariReports.size()>0){
            return  rahsepariReports.size();
        }else if(arpReports!=null && arpReports.size()>0){
            return arpReports.size();
        }
        return 0;
    }

    class itemViewHolder extends RecyclerView.ViewHolder {
        private TextView ReportTitle;
        private TextView ReportDiverName;
        private TextView ReportDiverId;
        private TextView ReportDate;
        private TextView ReportPath;
        private RelativeLayout mainRoot;
        private ImageButton imgShare;

        itemViewHolder(View itemView) {
            super(itemView);

            ReportDiverId = itemView.findViewById(R.id.report_item_txt_driver_code);
            ReportDate = itemView.findViewById(R.id.report_item_txt_date);
            ReportDiverName = itemView.findViewById(R.id.report_item_txt_driver_name);
            ReportTitle = itemView.findViewById(R.id.report_item_txt_title);
            ReportPath = itemView.findViewById(R.id.report_item_txt_path);
            mainRoot = itemView.findViewById(R.id.report_item_main_root);
            imgShare = itemView.findViewById(R.id.img_share_report);


            utils.setTypeFace(ReportDate, context);
            utils.setTypeFace(ReportDiverId, context);
            utils.setTypeFace(ReportDiverName, context);
            utils.setTypeFace(ReportTitle, context);
            utils.setTypeFace(ReportPath, context);

        }
    }





}
