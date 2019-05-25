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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anad.mobile.post.API.FilterApi;
import com.anad.mobile.post.Activity.MainActivity;
import com.anad.mobile.post.Activity.RahRFIDFilter.RahRFIDFilterActivity;
import com.anad.mobile.post.MapManager.Manager.MapManager;
import com.anad.mobile.post.MapManager.Model.IMapView;
import com.anad.mobile.post.MapManager.Model.SearchLastPositionItem;
import com.anad.mobile.post.Models.LastPosition;
import com.anad.mobile.post.Models.UserAccess;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.CustomOsmWindowInfo;
import com.anad.mobile.post.Utils.DataTimeUtils.DateTimeUtils;
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

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by elias.mohammadi on 2018/09/01
 */

public class OsmMapFragment extends Fragment implements FloatingActionMenu.OnMenuToggleListener
        , View.OnClickListener, android.location.LocationListener, IMapView {

    public static final String POSITIONS = "POSITION";
    public static final String FILTER = "filter";
    public static final String IS_ONLINE = "IS_ONLINE";
    public static final String SEARCH_FILTER = "search_filter";
    private MapView mapView;
    private WMSEndpoint wmsEndpoint;
    private com.github.clans.fab.FloatingActionButton filterFab, updateFab, satFab;
    private FloatingActionMenu fab_menu;
    private View view;
    private Context ctx;
    private Util util;
    private PostSharedPreferences preferences;
    List<com.anad.mobile.post.MapManager.Model.LastPosition> positions;
    private MapManager mapManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //TODO CHANGE MAP LOADING
        ctx = getActivity().getApplicationContext();
        preferences = new PostSharedPreferences(ctx);


        mapManager = new MapManager(ctx, this);


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
        args.putBoolean(FILTER, true);
        args.putString("POSITION", position);
        args.putString("CAR_ID", id);
        args.putBoolean(IS_ONLINE, isOnline);

        OsmMapFragment fragment = new OsmMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static OsmMapFragment newInstance(List<com.anad.mobile.post.MapManager.Model.LastPosition> lastPositions, boolean isOnline,SearchLastPositionItem searchLastPositionItem) {

        Bundle args = new Bundle();
        args.putString(POSITIONS, new Gson().toJson(lastPositions));
        args.putBoolean(FILTER, true);
        args.putBoolean(IS_ONLINE,isOnline);
        args.putString(SEARCH_FILTER,new Gson().toJson(searchLastPositionItem));
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
                Intent i = new Intent(getActivity(), RahRFIDFilterActivity.class);
                i.putExtra(MainActivity.MAP_FILTER, true);
                startActivity(i);
                break;

            case R.id.Map_fab_update:
                mapView.getOverlays().clear();
                if (mapView != null) {
                    if (getArguments() != null) {
                        updateMap(new Gson().fromJson(getArguments().getString(SEARCH_FILTER),SearchLastPositionItem.class));
                        fab_menu.close(true);
                    } else {
                        Toast.makeText(ctx, R.string.please_select_your_filter_first, Toast.LENGTH_SHORT).show();
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


                positions = getLastPositions(getArguments().getString(POSITIONS));

                List<Double> latList = new ArrayList<>();
                List<Double> lngList = new ArrayList<>();


                if (positions != null && !positions.isEmpty()) {

                    for (com.anad.mobile.post.MapManager.Model.LastPosition lastPosition : positions) {
                        LatLng lt = util.createLatLng(lastPosition.getN(), lastPosition.getE());
                        latList.add(lt.latitude);
                        lngList.add(lt.longitude);

                        LatLng latlng1;
                        double lat = lt.latitude;
                        double lng = lt.longitude;
                        latlng1 = new LatLng(lat, lng);


                        Marker marker = new Marker(mapView);
                        marker.setPosition(new GeoPoint(latlng1.latitude, latlng1.longitude));

                        /*if(item.getLastPosition().isRFID()){
                            marker.setIcon(ContextCompat.getDrawable(ctx,R.drawable.map_marker_red));
                        }else {
                            marker.setIcon(ContextCompat.getDrawable(ctx, R.drawable.car));
                        }*/

                        marker.setIcon(ContextCompat.getDrawable(ctx, R.drawable.car));
                        marker.setInfoWindow(new CustomOsmWindowInfo(ctx, R.layout.map_info_layout, mapView, util));

                        final String info = lastPosition.getDeviceCode() + "--" + lastPosition.getSpeed() + "--" + lastPosition.getLastTime() + "--" + lastPosition.getLastDate();
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

    private List<com.anad.mobile.post.MapManager.Model.LastPosition> getLastPositions(String str) {

        Gson gson = new Gson();
        Type t = new TypeToken<ArrayList<com.anad.mobile.post.MapManager.Model.LastPosition>>() {
        }.getType();
        return gson.fromJson(str, t);
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
            } catch (Exception e) {
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
                if (getArguments() != null && getArguments().getBoolean(FILTER)) {
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


    private void updateMap(SearchLastPositionItem search) {
        mapManager.callLastPosition(search);
    }

    @Override
    public void fillLastPosition(List<com.anad.mobile.post.MapManager.Model.LastPosition> lastPositions) {
        boolean isOnline = getArguments().getBoolean(IS_ONLINE);
        List<com.anad.mobile.post.MapManager.Model.LastPosition> positions;
        if (lastPositions != null && !lastPositions.isEmpty()) {
            positions = lastPositions;

            if (isOnline) {
                positions = findOnlinePosition(lastPositions);
            }
            addMarkerToMap(positions);

        }
    }


    private void addMarkerToMap(List<com.anad.mobile.post.MapManager.Model.LastPosition> lastPositions) {
        LatLng carLatLng;
        for (com.anad.mobile.post.MapManager.Model.LastPosition position : lastPositions
                ) {
            String lat = position.getN();
            String lng = position.getE();
            carLatLng = util.createLatLng(lat, lng);
            Marker marker = new Marker(mapView);
            marker.setPosition(new GeoPoint(carLatLng.latitude, carLatLng.longitude));
            marker.setIcon(ContextCompat.getDrawable(ctx, R.drawable.car));
            mapView.getOverlays().add(marker);

        }
    }


    private List<com.anad.mobile.post.MapManager.Model.LastPosition> findOnlinePosition(List<com.anad.mobile.post.MapManager.Model.LastPosition> positions) {

        List<com.anad.mobile.post.MapManager.Model.LastPosition> lastPositions = new ArrayList<>();
        String Date = DateTimeUtils.getCurrentDate();
        String hour = DateTimeUtils.getCurrentHour();
        int diff;

        for (com.anad.mobile.post.MapManager.Model.LastPosition position : positions) {

            diff = Math.abs(Integer.parseInt(position.getLastTime().split(":")[0]) - Integer.parseInt(hour));
            if (position.getLastDate().equals(Date) && diff <= 2) {
                lastPositions.add(position);
            }
        }

        return lastPositions;

    }


}
