package com.anad.mobile.post.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anad.mobile.post.R;
import com.anad.mobile.post.Utils.AdapterUtils.SpinnerItems;

import java.util.List;

public class SpinnerAdapter<T extends SpinnerItems> extends BaseAdapter {

    private List<T> list;
    private Context context;

    public SpinnerAdapter(Context context,List<T> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Integer getTreeItemId(int position){
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SpinnerItems currentItem = list.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item,parent,false);
        TextView txtView = convertView.findViewById(R.id.textSpinner);
        txtView.setText(currentItem.getText());
        return convertView;

    }


}
