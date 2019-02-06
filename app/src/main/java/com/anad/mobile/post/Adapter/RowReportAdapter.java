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
import com.anad.mobile.post.Utils.Util;

import java.util.List;

/**
 * Created by elias.mohammadi  96.11.03.
 */

public class RowReportAdapter extends RecyclerView.Adapter<RowReportAdapter.itemViewHolder> {
    private List<ReportRow> list;
    private Context context;
    private Util util;

    public RowReportAdapter(List<ReportRow> list, Context context) {
        this.list = list;
        this.context = context;
        util = Util.getInstance();
    }


    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_row_item,parent,false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(itemViewHolder holder, int position) {
        ReportRow row = list.get(position);
        if(position%2==0)
            holder.container.setBackgroundColor(ResourcesCompat.getColor(context.getResources(),R.color.yellow_light,null));
        holder.title.setText(row.getTitle());
        holder.content.setText(row.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class itemViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout container;
        private TextView title;
        private TextView content;
        private itemViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.row_container);
            title = itemView.findViewById(R.id.row_title);
            content = itemView.findViewById(R.id.row_data);

            util.setTypeFaceLight(title,context);
            util.setTypeFaceLight(content,context);


        }
    }
}
