package com.anad.mobile.post.Fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anad.mobile.post.API.FilterApi;
import com.anad.mobile.post.Activity.MainActivity;
import com.anad.mobile.post.Activity.RahRFIDFilter.RahRFIDFilter;
import com.anad.mobile.post.Models.LastPosition;
import com.anad.mobile.post.Models.UserAccess;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.CustomOsmWindowInfo;
import com.anad.mobile.post.Utils.PersianCal;
import com.anad.mobile.post.Utils.Util;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.wms.WMSEndpoint;
import org.osmdroid.wms.WMSTileSource;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by elias.mohammadi on 2018/09/01
 */

public class OsmMapFragment extends Fragment implements FloatingActionMenu.OnMenuToggleListener
        , View.OnClickListener, android.location.LocationListener {

    private MapView mapView;
    private static final String TAG = "OsmMapFragment";
    private WMSEndpoint wmsEndpoint;
    private com.github.clans.fab.FloatingActionButton filterFab, updateFab, satFab;
    private FloatingActionMenu fab_menu;
    private View view;
    List<UserAccess> u;
    Context ctx;
    private Util util;
    private PostSharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ctx = getActivity().getApplicationContext();
        preferences = new PostSharedPreferences(ctx);

        view = inflater.inflate(R.layout.fragment_osm_map, container, false);
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));


        util = Util.getInstance();
        mapView = view.findViewById(R.id.mapView);

        mapView.getController().setZoom(15.0);


        initializeView();
        fab_menu.setOnMenuToggleListener(this);
        filterFab.setOnClickListener(this);
        updateFab.setOnClickListener(this);

        satFab.setOnClickListener(this);
        satFab.setVisibility(View.GONE);

        new MapAsyncTask().execute();


        return view;
    }

    public static OsmMapFragment newInstance(List<UserAccess> userAccess, String id, boolean isOnline) {

        Gson gson = new Gson();
        String position = gson.toJson(userAccess);
        Bundle args = new Bundle();
        args.putBoolean("filter", true);
        args.putString("POSITION", position);
        args.putString("CAR_ID", id);
        args.putBoolean("IS_ONLINE", isOnline);

        OsmMapFragment fragment = new OsmMapFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private void initializeView() {
        fab_menu = view.findViewById(R.id.fab_menu);
        filterFab = view.findViewById(R.id.Map_fab_filter);
        updateFab = view.findViewById(R.id.Map_fab_update);
        satFab = view.findViewById(R.id.Map_fab_sat);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Map_fab_filter:
                fab_menu.close(true);
                Intent i = new Intent(getActivity(), RahRFIDFilter.class);
                i.putExtra("MAP_FILTER", "from Map");
                startActivity(i);
                break;

            case R.id.Map_fab_update:
                mapView.getOverlays().clear();
                if (mapView != null) {
                    if (getArguments() != null) {
                        getLastPositionForUpdate(getArguments().getString("CAR_ID"), getArguments().getBoolean("IS_ONLINE"));
                        fab_menu.close(true);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "ابتدا فیلتر های موردنظر را انتخاب نمایید.", Toast.LENGTH_SHORT).show();
                        fab_menu.close(true);
                    }
                }
                break;
        }

    }

    @Override
    public void onMenuToggle(boolean opened) {

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng;
        if (location != null && mapView != null) {

            if (getArguments() != null) {
                Gson gson = new Gson();
                Type t = new TypeToken<ArrayList<UserAccess>>() {
                }.getType();
                u = gson.fromJson(getArguments().getString("POSITION"), t);
                List<Double> latList = new ArrayList<>();
                List<Double> lngList = new ArrayList<>();

                if (u != null && u.size() > 0) {
                    for (UserAccess item : u
                            ) {
                        LatLng lt = util.createLatLng(item.getLastPosition().getN(), item.getLastPosition().getE());
                        latList.add(lt.latitude);
                        lngList.add(lt.longitude);
                        LatLng latlng1;
                        double lat = lt.latitude;
                        double lng = lt.longitude;
                        latlng1 = new LatLng(lat, lng);


                        Marker marker = new Marker(mapView);
                        marker.setPosition(new GeoPoint(latlng1.latitude, latlng1.longitude));
                        if(item.getLastPosition().isRFID()){
                            marker.setIcon(ContextCompat.getDrawable(ctx,R.drawable.map_marker_red));
                        }else {
                            marker.setIcon(ContextCompat.getDrawable(ctx, R.drawable.car));
                        }
                        marker.setInfoWindow(new CustomOsmWindowInfo(ctx, R.layout.map_info_layout, mapView, util));

                        final String info = item.getLastPosition().getID() + "--" + item.getLastPosition().getSpeed() + "--" + item.getLastPosition().getLTime() + "--" + item.getLastPosition().getLDate();
                        marker.setSnippet(info);


                        mapView.getOverlays().add(marker);
                        mapView.getController().animateTo(new GeoPoint(35.68, 51.38));
                        mapView.getController().setZoom(5.0);


                    }
                    double minLat = Collections.min(latList);
                    double maxLat = Collections.max(latList);
                    double minLng = Collections.min(lngList);
                    double maxLng = Collections.max(lngList);

                }

            } else {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mapView.getController().animateTo(new GeoPoint(latLng.latitude, latLng.longitude));
                Toast.makeText(ctx, R.string.for_this_filter_have_not_any_car, Toast.LENGTH_SHORT).show();
            }


        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    class MapAsyncTask extends AsyncTask<Void, Void, Void> {

       // CustomWMSTileSource source;
        WMSTileSource source;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(Constants.URL_WMS);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                if (preferences.getCatchMap().equals("")) {
                    wmsEndpoint = Util.parseMap(in);
                    if (wmsEndpoint != null) {
                        Gson gson = new Gson();
                        String catchMap = gson.toJson(wmsEndpoint);
                        preferences.setCatchMap(catchMap);
                    }
                    in.close();
                    urlConnection.disconnect();
                } else {
                    wmsEndpoint = new Gson().fromJson(preferences.getCatchMap(), WMSEndpoint.class);
                }
                if (wmsEndpoint != null) {
//                  source = WMSTileSource.createFrom(wmsEndpoint, wmsEndpoint.getLayers().get(1));
                  source = WMSTileSource.createFrom(wmsEndpoint, wmsEndpoint.getLayers().get(0));
//                    source = CustomWMSTileSource.createFrom(wmsEndpoint, wmsEndpoint.getLayers().get(0));
                    //for post application
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: " + e.toString());
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: " + e.toString());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (source != null) {
                mapView.setTileSource(source);
                mapStyleInitializer();
                if (getArguments() != null && getArguments().getBoolean("filter")) {
                    onLocationChanged(new Location(LocationManager.NETWORK_PROVIDER));

                }
            } else {
                Toast.makeText(ctx, R.string.map_exception, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void mapStyleInitializer() {

        mapView.invalidate();
        mapView.setVerticalMapRepetitionEnabled(false);
        mapView.setHorizontalMapRepetitionEnabled(false);
        mapView.setMultiTouchControls(true);
        mapView.setBuiltInZoomControls(false);
        mapView.getController().animateTo(new GeoPoint(35.68, 51.38));
        mapView.getController().setZoom(5.0);
    }

    private void getLastPositionForUpdate(String id, final boolean isOnline) {
        final List<Double> latList = new ArrayList<>();
        final List<Double> lngList = new ArrayList<>();
        FilterApi api = FilterApi.getInstance(MainActivity.mainActivityContext);
        api.getLastPosWithId(new FilterApi.OnAllDriversBack() {
            @Override
            public void OnResponse(List<LastPosition> lastPositions) {
                if (isOnline) {
                    String Date = getCurrentTime()[1];
                    String hour = getCurrentTime()[0];
                    int diff;
                    LatLng carLatLng;
                    for (int i = 0; i < lastPositions.size(); i++) {

                        diff = Math.abs(Integer.parseInt(lastPositions.get(i).getLTime().split(":")[0]) - Integer.parseInt(hour));
                        if (lastPositions.get(i).getLDate().equals(Date) && diff <= 2) {
                            String lat = lastPositions.get(i).getN();
                            String lng = lastPositions.get(i).getE();
                            carLatLng = util.createLatLng(lat, lng);
                            latList.add(carLatLng.latitude);
                            lngList.add(carLatLng.longitude);

                            Marker marker = new Marker(mapView);
                            marker.setPosition(new GeoPoint(carLatLng.latitude, carLatLng.longitude));
                            marker.setIcon(ContextCompat.getDrawable(ctx, R.drawable.car));
                            mapView.getOverlays().add(marker);
                        }

                    }
                } else {
                    LatLng carLatLng;
                    for (LastPosition lastpos : lastPositions
                            ) {
                        String lat = lastpos.getN();
                        String lng = lastpos.getE();
                        carLatLng = util.createLatLng(lat, lng);
                        latList.add(carLatLng.latitude);
                        lngList.add(carLatLng.longitude);

                        Marker marker = new Marker(mapView);
                        marker.setPosition(new GeoPoint(carLatLng.latitude, carLatLng.longitude));
                        marker.setIcon(ContextCompat.getDrawable(ctx, R.drawable.car));
                        mapView.getOverlays().add(marker);

                    }
                }

                double minLat;
                double maxLat;
                double minLng;
                double maxLng;
/*
                if(latList.size()>0 && lngList.size()>0){


                    minLat = Collections.min(latList);
                    maxLat = Collections.max(latList);
                    minLng = Collections.min(lngList);
                    maxLng = Collections.max(lngList);

                    moveCamera(mMap, minLat, maxLat, minLng, maxLng);

                }else
                {
                    moveCamera(new LatLng(32.4274, 53.6880), 4f);
                }*/


            }
        }, Constants.URL_GET_LAST_POS_WITH_ID + id);
    }

    private String[] getCurrentTime() {

        Date c = Calendar.getInstance().getTime();
        Calendar c2 = Calendar.getInstance();
        PersianCal persian = new PersianCal(c2);
        String date = persian.getYear() + "/" + persian.getMonth() + "/" + persian.getDay();

        int hour = c.getHours();
        String dateTime = String.valueOf(hour) + "--" + date;
        return dateTime.split("--");


    }


}
