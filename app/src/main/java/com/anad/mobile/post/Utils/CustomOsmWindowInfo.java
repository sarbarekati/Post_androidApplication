package com.anad.mobile.post.Utils;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anad.mobile.post.R;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

/**
 * Created by elias.mohammadi on 2018/09/02.
 */

public class CustomOsmWindowInfo extends MarkerInfoWindow {

    private Util util;
    private Context context;
    public CustomOsmWindowInfo(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }
    public CustomOsmWindowInfo(Context context, int layoutResId, MapView mapView, Util util){
        super(layoutResId,mapView);
        this.util = util;
        this.context = context;
    }

    @Override
    public void onOpen(Object item) {

        mMarkerRef = (Marker) item;
        if(mView!=null){
            TextView txtDeviceId = mView.findViewById(R.id.marker_device_id);
            TextView txtTime = mView.findViewById(R.id.marker_time);
            TextView txtDate = mView.findViewById(R.id.marker_date);
            TextView txtSpeed = mView.findViewById(R.id.marker_speed);

            String[] info = mMarkerRef.getSnippet().split("--");


            txtDeviceId.setText(info[0]);
            txtTime.setText(info[2]);
            txtDate.setText(info[3]);
            txtSpeed.setText(info[1] + " Km/h");

            util.setTypeFaceNumber(txtDeviceId,context);
            util.setTypeFaceNumber(txtTime,context);
            util.setTypeFaceNumber(txtDate,context);
            util.setTypeFaceNumber(txtSpeed,context);
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        mMarkerRef =  null;
    }
}
