package com.anad.mobile.post.Utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.anad.mobile.post.Models.Points;
import com.anad.mobile.post.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CreateRoute {


    public static List<Points> pointsList;
    private DecodeData decodeData;
    private double MAX_LAT;
    private double MAX_LNG;
    private double MIN_LAT;
    private double MIN_LNG;
    private Util util;
    private int minSpeed;
    private int maxSpeed;
    private int stopTime;
    HashMap<Integer, Points> pMin;
    HashMap<Integer, Points> pMax;

    private String carId;
    private Context context;
    private String startDate;

    public CreateRoute(Context context,String startDate,String carId,List<Points> list,int min_speed,int max_speed,int stop_time) {
        decodeData = new DecodeData();
        this.context = context;
        this.startDate = startDate;
        util = Util.getInstance();
        pointsList = list;
        if(min_speed == 0)
            minSpeed = 20;
        else
            this.minSpeed = min_speed;
        if(max_speed == 0)
            maxSpeed = 100;
        else
            this.maxSpeed = max_speed;
        if(stop_time == 0)
            stopTime = 2;
        else
            this.stopTime = stop_time;
        this.carId = carId;
    }



    public void getPointsOnMap(GoogleMap googleMap, String Body,boolean isContinues) {
        ArrayList<String> bodyRecord = decodeData.getBodyRecords(Body);


        String route;
        String[] list;
        for (int i = 0; i < bodyRecord.size(); i++) {
            route = bodyRecord.get(i);
            list = route.split(",");
            Points points = new Points();


            LatLng latLng = util.createLatLng(list[1], list[3]);

            points.setN(latLng.latitude);
            points.setE(latLng.longitude);
            points.setSpeed(Double.valueOf(list[5]));
            pointsList.add(points);

        }


        if (pointsList.size() > 1) {

            PolylineOptions polylineOptions = new PolylineOptions();


            for (int i = 0; i < pointsList.size(); i++) {
                LatLng latLng = new LatLng(pointsList.get(i).getN(), pointsList.get(i).getE());
                polylineOptions.add(latLng);
                }


            int color = getRandomColor();


            polylineOptions.width(5).color(color);
            if(!isContinues)
                googleMap.clear();
            googleMap.addPolyline(polylineOptions);

            getMaxMinPoint(pointsList);

            //TODO pass speed limit here
                List<HashMap<Integer, Points>> pointsSorts = sortBySpeed(pointsList, maxSpeed, minSpeed);


            Marker marker;
            LatLng latLng;


            List<Points> stopPoints = new ArrayList<>();


            if(pointsSorts.size() > 0) {
                pMax = pointsSorts.get(0);
                pMin = pointsSorts.get(1);
                if (pMax.size() > 0) {
                    for (int k = 0; k < pMax.size(); k++) {

                        latLng = new LatLng(pointsSorts.get(0).get(k).getN(), pointsSorts.get(0).get(k).getE());
                        marker = googleMap.addMarker(new MarkerOptions().position(latLng));
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_blue));

                        marker.setAnchor(0.5f,0.5f);
                    }
                }
                if (pMin.size() > 0) {
                    for (int j = 0; j < pMin.size(); j++) {
                        latLng = new LatLng(pMin.get(j).getN(), pMin.get(j).getE());
                        marker = googleMap.addMarker(new MarkerOptions().position(latLng));
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_green));
                        marker.setAnchor(0.5f,0.5f);
                    }
                }
            }


            if (bodyRecord.size() > 0) {
                stopPoints =  getStopPoint(bodyRecord, stopTime);
                if(stopPoints.size() > 0){
                    for (Points p:stopPoints) {
                        latLng = new LatLng(p.getN(),p.getE());
                        marker = googleMap.addMarker(new MarkerOptions().position(latLng));
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_red));
                        marker.setAnchor(0.5f,0.5f);
                    }
                }
            }


            moveCamera(googleMap, MIN_LAT, MAX_LAT, MIN_LNG, MAX_LNG);

        }



    }


    public void getPointOnOsmMap(MapView mapView,String body,boolean isContinues){
        ArrayList<String> bodyRecord = decodeData.getBodyRecords(body);


        String route;
        String[] list;
        for (int i = 0; i < bodyRecord.size(); i++) {
            route = bodyRecord.get(i);
            list = route.split(",");
            Points points = new Points();


            LatLng latLng = util.createLatLng(list[1], list[3]);

            points.setN(latLng.latitude);
            points.setE(latLng.longitude);
            points.setSpeed(Double.valueOf(list[5]));
            pointsList.add(points);

        }


        if (pointsList.size() > 1) {


            Polyline line = new Polyline();

            List<GeoPoint> geoPoints = new ArrayList<>();
//            PolylineOptions polylineOptions = new PolylineOptions();


            for (int i = 0; i < pointsList.size(); i++) {
                LatLng latLng = new LatLng(pointsList.get(i).getN(), pointsList.get(i).getE());
                geoPoints.add(new GeoPoint(latLng.latitude,latLng.longitude));
            }


            int color = getRandomColor();
            line.setColor(color);
            line.setWidth(5f);
            line.setPoints(geoPoints);


            if(!isContinues)
                mapView.getOverlays().clear();
            mapView.getOverlayManager().add(line);

            getMaxMinPoint(pointsList);

            List<HashMap<Integer, Points>> pointsSorts = sortBySpeed(pointsList, maxSpeed, minSpeed);

           // Marker marker;
            org.osmdroid.views.overlay.Marker marker;
            LatLng latLng;


            List<Points> stopPoints = new ArrayList<>();

            if(pointsSorts.size() > 0) {
                pMax = pointsSorts.get(0);
                pMin = pointsSorts.get(1);
                if (pMax.size() > 0) {
                    for (int k = 0; k < pMax.size(); k++) {

                        latLng = new LatLng(pointsSorts.get(0).get(k).getN(), pointsSorts.get(0).get(k).getE());
                        marker = new org.osmdroid.views.overlay.Marker(mapView);

                        marker.setOnMarkerClickListener(new org.osmdroid.views.overlay.Marker.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(org.osmdroid.views.overlay.Marker marker, MapView mapView) {
                                return true;
                            }
                        });

                        marker.setPosition(new GeoPoint(latLng.latitude,latLng.longitude));
                        marker.setIcon(ContextCompat.getDrawable(context,R.drawable.map_marker_blue));
                        marker.setAnchor(0.5f,1f);
                        mapView.getOverlays().add(marker);
                    }
                }
                if (pMin.size() > 0) {
                    for (int j = 0; j < pMin.size(); j++) {
                        latLng = new LatLng(pMin.get(j).getN(), pMin.get(j).getE());
                        marker = new org.osmdroid.views.overlay.Marker(mapView);
                        marker.setOnMarkerClickListener(new org.osmdroid.views.overlay.Marker.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(org.osmdroid.views.overlay.Marker marker, MapView mapView) {
                                return true;
                            }
                        });

                        marker.setPosition(new GeoPoint(latLng.latitude,latLng.longitude));
                        marker.setIcon(ContextCompat.getDrawable(context,R.drawable.map_marker_green));
                        marker.setAnchor(0.5f,1f);
                        mapView.getOverlays().add(marker);
                    }
                }
            }


            if (bodyRecord.size() > 0) {
                stopPoints =  getStopPoint(bodyRecord, stopTime);
                if(stopPoints.size() > 0){
                    for (Points p:stopPoints) {
                        latLng = new LatLng(p.getN(),p.getE());
                        marker = new org.osmdroid.views.overlay.Marker(mapView);
                        marker.setOnMarkerClickListener(new org.osmdroid.views.overlay.Marker.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(org.osmdroid.views.overlay.Marker marker, MapView mapView) {
                                return true;
                            }
                        });

                        marker.setPosition(new GeoPoint(latLng.latitude,latLng.longitude));
                        marker.setIcon(ContextCompat.getDrawable(context,R.drawable.map_marker_red));
                        marker.setAnchor(0.5f,1f);
                        mapView.getOverlays().add(marker);
                    }
                }
            }



            mapView.zoomToBoundingBox(new BoundingBox(MAX_LAT,MAX_LNG,MIN_LAT,MIN_LNG),true);


        }
    }




    private int getRandomColor() {
        SecureRandom random = new SecureRandom();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return Color.argb(255,red,green,blue);
    }


    private void moveCamera(GoogleMap googleMap, double MinLat, double MaxLat, double MinLng, double MaxLng) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (pointsList.size() != 0) {
            builder.include(new LatLng(MaxLat, MaxLng));
            builder.include(new LatLng(MinLat, MinLng));

            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 64));

        }
    }

    private void getMaxMinPoint(List<Points> points) {

        List<Double> e = new ArrayList<>();
        List<Double> n = new ArrayList<>();
        for (Points p:points) {
            e.add(p.getE());
            n.add(p.getN());
        }
        MAX_LNG = Collections.max(e);
        MIN_LAT = Collections.min(n);
        MIN_LNG = Collections.min(e);
        MAX_LAT = Collections.max(n);


    }


    private List<HashMap<Integer, Points>> sortBySpeed(List<Points> points, double maxSpeed, double minSpeed) {
        List<HashMap<Integer, Points>> plist = new ArrayList<>();
        HashMap<Integer, Points> pMax = new HashMap<>();
        HashMap<Integer, Points> pMin = new HashMap<>();
        int i = 0;
        int j = 0;

        if (maxSpeed != 0 || minSpeed != 0) {
            for (Points p : points) {
                if (p.getSpeed() > maxSpeed) {
                    Points points1 = new Points();
                    points1.setN(p.getN());
                    points1.setE(p.getE());
                    points1.setSpeed(p.getSpeed());
                    pMax.put(i, points1);
                    i++;

                } else if (p.getSpeed() < minSpeed) {
                    Points points2 = new Points();
                    points2.setN(p.getN());
                    points2.setE(p.getE());
                    points2.setSpeed(p.getSpeed());
                    pMin.put(j, points2);
                    j++;

                }
            }

            plist.add(pMax);
            plist.add(pMin);
        }

        return plist;
    }


    private List<Integer> getStopPointDoubtId(List<Points> points) {
        List<Integer> p = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).getSpeed() <= 6) {
                p.add(i);
            }
        }
        return p;
    }


    private List<Points> getStopPoint(ArrayList<String> bodyRecord, int stopTime) {
        int fHour;
        int fMin;
        int sHour;
        int sMin;

        if(stopTime != 0) {

            List<Points> p = new ArrayList<>();

            List<Integer> Ids = getStopPointDoubtId(pointsList);
            for (int i = 0; i < Ids.size(); i++) {


                int first = Ids.get(i);
                int second = first + 1;


                fHour = Integer.parseInt(bodyRecord.get(first).split(",")[0].split(":")[0]);
                fMin = Integer.parseInt(bodyRecord.get(first).split(",")[0].split(":")[1]);

                if (second == bodyRecord.size()) {

                    sHour = Integer.parseInt(bodyRecord.get(first).split(",")[0].split(":")[0]);
                    sMin = Integer.parseInt(bodyRecord.get(first).split(",")[0].split(":")[1]);
                } else {

                    sHour = Integer.parseInt(bodyRecord.get(second).split(",")[0].split(":")[0]);
                    sMin = Integer.parseInt(bodyRecord.get(second).split(",")[0].split(":")[1]);

                }

                int compareTime = ((sHour - fHour) * 60) + (sMin - fMin);

                if (compareTime >= stopTime) {
                    Points points = pointsList.get(first);
                    p.add(points);
                }

            }

            return p;
        }

        return new ArrayList<>();
    }

}
