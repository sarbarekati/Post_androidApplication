package com.anad.mobile.post.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anad.mobile.post.R;
import com.anad.mobile.post.Utils.Util;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by elias.mohammadi on 2018/02/14
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private  View customContentView;
    private Context context;
    private Util util;

    public CustomInfoWindowAdapter(Context context)
    {
        util = Util.getInstance();
        this.context = context;
        customContentView = LayoutInflater.from(context).inflate(R.layout.map_info_layout,null);
    }

    @Override
    public View getInfoWindow(Marker marker) {


        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LinearLayout lv = customContentView.findViewById(R.id.root);
        TextView txtDeviceId = customContentView.findViewById(R.id.marker_device_id);
        TextView txtTime = customContentView.findViewById(R.id.marker_time);
        TextView txtDate = customContentView.findViewById(R.id.marker_date);
        TextView txtSpeed = customContentView.findViewById(R.id.marker_speed);


        lv.setLayoutParams(new LinearLayout.LayoutParams(240,240));

        String[] info = marker.getSnippet().split("--");


        txtDeviceId.setText(info[0]);
        txtTime.setText(info[2]);
        txtDate.setText(info[3]);
        txtSpeed.setText(info[1] + " Km/h");

        util.setTypeFaceNumber(txtDeviceId,context);
        util.setTypeFaceNumber(txtTime,context);
        util.setTypeFaceNumber(txtDate,context);
        util.setTypeFaceNumber(txtSpeed,context);

        return customContentView;
    }
}
