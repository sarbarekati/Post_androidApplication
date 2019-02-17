package com.anad.mobile.post.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anad.mobile.post.Activity.RahRFIDFilter.RahRFIDFilter;
import com.anad.mobile.post.Activity.ShowPathActivity;
import com.anad.mobile.post.Models.ReportMenu;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Utils.Util;

import java.util.List;

/**
 * Created by elias.mohammadi on 2018/01/24
 */

public class ReportFragmentAdapter extends RecyclerView.Adapter<ReportFragmentAdapter.itemViewHolder> {
    private List<ReportMenu> list;

    public ReportFragmentAdapter(List<ReportMenu> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private Context context;
    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new itemViewHolder(LayoutInflater.from(context).inflate(R.layout.report_fragment_item,parent,false));
    }

    @Override
    public void onBindViewHolder(itemViewHolder holder, int position) {
       final ReportMenu rp = list.get(position);
        holder.title.setText(rp.getText());
        holder.img.setImageDrawable(rp.getDrawable());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                if(rp.getID() == 2){
                    i = new Intent(context, ShowPathActivity.class);

                }
                else{
                    i = new Intent(context, RahRFIDFilter.class);
                }

                i.putExtra("REPORT_ID",rp.getID());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class itemViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView img;
        private TextView title;
        private CardView container;
        public itemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.report_fragment_text);
            img = itemView.findViewById(R.id.report_fragment_img);
            container = itemView.findViewById(R.id.report_fragment_menu);

            Util util = Util.getInstance();
            util.setTypeFace(title,context);
        }
    }
}
