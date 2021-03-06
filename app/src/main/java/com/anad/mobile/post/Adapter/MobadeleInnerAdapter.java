package com.anad.mobile.post.Adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anad.mobile.post.Models.ReportRow;
import com.anad.mobile.post.R;

import java.util.List;

/**
 * Created by elias.mohammadi on 2018/01/24.
 */

public class MobadeleInnerAdapter extends RecyclerView.Adapter<MobadeleInnerAdapter.itemViewHolder> {
private Context context;
private List<ReportRow> list;

    public MobadeleInnerAdapter(Context context, List<ReportRow> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new itemViewHolder(LayoutInflater.from(context).inflate(R.layout.mobadele_inner_report,parent,false));
    }

    @Override
    public void onBindViewHolder(itemViewHolder holder, int position) {
        ReportRow row = list.get(position);
        holder.title1.setText(row.getMoVorod());
        holder.title2.setText(row.getVorod());
        holder.title3.setText(row.getMoKhoroj());
        holder.title4.setText(row.getKhoroj());
        holder.title5.setText(row.getVorodLate());
        holder.title6.setText(row.getVorodSoon());
        holder.title7.setText(row.getKhorojLate());
        holder.title8.setText(row.getKhorojSoon());

        holder.data1.setText(row.getMoVorodContent());
        holder.data2.setText(row.getVorodContent());
        holder.data3.setText(row.getMoKhorojContent());
        holder.data4.setText(row.getKhorojContent());
        holder.data5.setText(row.getVorodLateContent());
        holder.data6.setText(row.getVorodSoonCotent());
        holder.data7.setText(row.getKhorojLateContent());
        holder.data8.setText(row.getKhorojSoonContent());

        holder.row2.setBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.yellow_light,null));
        holder.row4.setBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.yellow_light,null));
        holder.row6.setBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.yellow_light,null));
        holder.row8.setBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.yellow_light,null));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class itemViewHolder extends RecyclerView.ViewHolder{
       private TextView title1,data1;
       private TextView title2,data2;
       private TextView title3,data3;
       private TextView title4,data4;
       private TextView title5,data5;
       private TextView title6,data6;
       private TextView title7,data7;
       private TextView title8,data8;
       private LinearLayout row1,row2,row3,row4,row5,row6,row7,row8;
       public itemViewHolder(View itemView) {

           super(itemView);

           title1 = itemView.findViewById(R.id.row_title);
           title2 = itemView.findViewById(R.id.row_title_2);
           title3 = itemView.findViewById(R.id.row_title_3);
           title4 = itemView.findViewById(R.id.row_title_4);
           title5 = itemView.findViewById(R.id.row_title_5);
           title6 = itemView.findViewById(R.id.row_title_6);
           title7 = itemView.findViewById(R.id.row_title_7);
           title8 = itemView.findViewById(R.id.row_title_8);

           data1 = itemView.findViewById(R.id.row_data);
           data2 = itemView.findViewById(R.id.row_data_2);
           data3 = itemView.findViewById(R.id.row_data_3);
           data4 = itemView.findViewById(R.id.row_data_4);
           data5 = itemView.findViewById(R.id.row_data_5);
           data6 = itemView.findViewById(R.id.row_data_6);
           data7 = itemView.findViewById(R.id.row_data_7);
           data8 = itemView.findViewById(R.id.row_data_8);


           row1 = itemView.findViewById(R.id.row1);
           row2 = itemView.findViewById(R.id.row2);
           row3 = itemView.findViewById(R.id.row3);
           row4 = itemView.findViewById(R.id.row4);
           row5 = itemView.findViewById(R.id.row5);
           row6 = itemView.findViewById(R.id.row6);
           row7 = itemView.findViewById(R.id.row7);
           row8 = itemView.findViewById(R.id.row8);
       }
   }
}
