package com.anad.mobile.post.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anad.mobile.post.Models.MiddlePoint;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Utils.Util;

import java.util.List;

/**
 * Created by elias.mohammadi on 2018/02/26
 */

public class PdfPointAdapter extends RecyclerView.Adapter<PdfPointAdapter.itemViewHolder> {

    public PdfPointAdapter(Context context, List<MiddlePoint> list) {
        this.context = context;
        this.list = list;
        utils = Util.getInstance();
    }

    private Context context;
    private Util utils;
    private List<MiddlePoint> list;

    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mobadele_point,parent,false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(itemViewHolder holder, int position) {
        MiddlePoint m = list.get(position);

        holder.pointName.setText(m.getPName());
        holder.pointDate.setText(m.getDate());
        holder.pointTime.setText(m.getExchageT());

        holder.mogharar_in_data.setText(m.getPSEnT());
        holder.point_in_data.setText(m.getEnT());

        holder.mogharar_out_data.setText(m.getPSExT());
        holder.point_out_data.setText(m.getExT());

        holder.takhir_in_data.setText(m.getDelayEnT());
        holder.tajil_in_data.setText(m.getAccEnT());

        holder.takhir_out_data.setText(m.getDelayExT());
        holder.tajil_out_data.setText(m.getAccExT());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class itemViewHolder extends RecyclerView.ViewHolder{
        private TextView mogharar_in_data;
        private TextView mogharar_out_data;
        private TextView point_in_data;
        private TextView point_out_data;
        private TextView takhir_in_data;
        private TextView tajil_in_data;
        private TextView takhir_out_data;
        private TextView tajil_out_data;
        private TextView pointName;
        private TextView pointTime;
        private TextView pointDate;
        public itemViewHolder(View itemView) {
            super(itemView);

            mogharar_in_data = itemView.findViewById(R.id.point_mogharar_in_data);
            mogharar_out_data = itemView.findViewById(R.id.point_mogharar_out_data);
            point_in_data = itemView.findViewById(R.id.point_in_data);
            point_out_data = itemView.findViewById(R.id.point_out_data);
            takhir_in_data = itemView.findViewById(R.id.point_takhir_in_data);
            tajil_in_data = itemView.findViewById(R.id.point_tajil_in_data);
            takhir_out_data = itemView.findViewById(R.id.point_takhir_out_data);
            tajil_out_data = itemView.findViewById(R.id.point_tajil_out_data);

            pointName = itemView.findViewById(R.id.point_name_data);
            pointTime = itemView.findViewById(R.id.point_mobadele_time_data);
            pointDate = itemView.findViewById(R.id.point_date_date);



            utils.setTypeFaceLight(pointName,context);

            utils.setTypeFaceLight(pointTime,context);
            utils.setTypeFaceLight(pointDate,context);

            utils.setTypeFaceLight(mogharar_in_data,context);
            utils.setTypeFaceLight(mogharar_out_data,context);
            utils.setTypeFaceLight(point_in_data,context);
            utils.setTypeFaceLight(point_out_data,context);
            utils.setTypeFaceLight(takhir_in_data,context);
            utils.setTypeFaceLight(tajil_in_data,context);
            utils.setTypeFaceLight(takhir_out_data,context);
            utils.setTypeFaceLight(tajil_out_data,context);
        }
    }
}
