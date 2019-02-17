package com.anad.mobile.post.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anad.mobile.post.API.FilterApi;
import com.anad.mobile.post.Activity.MainActivity;
import com.anad.mobile.post.Activity.RahRFIDFilter.RahRFIDFilter;
import com.anad.mobile.post.Adapter.CustomInfoWindowAdapter;
import com.anad.mobile.post.Models.HandleSubTree;
import com.anad.mobile.post.Models.LastPosition;
import com.anad.mobile.post.Models.UserAccess;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.PersianCal;
import com.anad.mobile.post.Utils.Util;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Created by Elias MOHAMMADY 2017/12/30.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        View.OnClickListener, android.location.LocationListener,
        GoogleMap.OnMarkerClickListener, FloatingActionMenu.OnMenuToggleListener {
    private static final String TAG = "MapFragment";
    View view;
    private com.github.clans.fab.FloatingActionButton filterFab, updateFab, satFab;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private GoogleMap mMap;
    private static final float DEFAULT_ZOOM = 15f;
    private Marker mMarker;
    List<UserAccess> u;
    private boolean isClicked;
    private boolean isSat;
    private Util util;
    private HandleSubTree handleSubTree;
    private FloatingActionMenu fab_menu;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        util = Util.getInstance();
        handleSubTree = HandleSubTree.getInstance(getActivity().getApplicationContext());
        getLocationPermission();
        initMap();
        //  moveCamera(new LatLng(35.6892,51.3890),15f);
        //  getDeviceLocation();


        initializeView();
        fab_menu.setOnMenuToggleListener(this);
        filterFab.setOnClickListener(this);
        updateFab.setOnClickListener(this);

        satFab.setOnClickListener(this);

        return view;

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    private void initializeView() {
        fab_menu = view.findViewById(R.id.fab_menu);
        filterFab = view.findViewById(R.id.Map_fab_filter);
        updateFab = view.findViewById(R.id.Map_fab_update);
        satFab = view.findViewById(R.id.Map_fab_sat);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "onMapReady: " + "map ready");
        mMap = googleMap;
        moveCamera(new LatLng(32.4274, 53.6880), 4f);
        if (getArguments() != null && getArguments().getBoolean("filter")) {
            onLocationChanged(new Location(LocationManager.NETWORK_PROVIDER));

        }


        // onLocationChanged(new Location(LocationManager.NETWORK_PROVIDER));

        if (mLocationPermissionGranted) {
            //  getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(false);

        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.Map_fab_sat:
                if (mMap != null) {
                    if (!isSat) {
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        satFab.setLabelText(getString(R.string.map_type_street));
                        satFab.setImageDrawable(ResourcesCompat.getDrawable(getActivity().getApplicationContext().getResources(), R.drawable.icons_8_road, null));
                        isSat = !isSat;
                        fab_menu.close(true);
                    } else {

                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        satFab.setLabelText(getString(R.string.map_type_satelite));
                        satFab.setImageDrawable(ResourcesCompat.getDrawable(getActivity().getApplicationContext().getResources(), R.drawable.icons_8_satellite_sending_signal, null));
                        isSat = !isSat;
                        fab_menu.close(true);
                    }
                }

                break;


            case R.id.Map_fab_filter:
                fab_menu.close(true);
                Intent i = new Intent(getActivity(), RahRFIDFilter.class);
                i.putExtra("MAP_FILTER", "from Map");
                startActivity(i);
                break;


            case R.id.Map_fab_update:
                mMap.clear();
                if (mMap != null) {
                    if(getArguments()!=null) {
                        getLastPositionForUpdate(getArguments().getString("CAR_ID"), getArguments().getBoolean("IS_ONLINE"));
                        fab_menu.close(true);
                    }
                    else
                        {
                            Toast.makeText(getActivity().getApplicationContext(), "ابتدا فیلتر های موردنظر را انتخاب نمایید.", Toast.LENGTH_SHORT).show();
                            fab_menu.close(true);
                        }
                }
                break;

        }

    }

    private void getLocationPermission() {
        String[] permission = {FINE_LOCATION, COARSE_LOCATION};

        int b = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), FINE_LOCATION);


        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext()
                , FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext()
                    , COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(getActivity(), permission, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    initMap();
                }
                break;
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void onLocationChanged(Location location) {
        //pass new location
        LatLng latLng;
        if (location != null && mMap != null) {

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
                        Marker m = mMap.addMarker(new MarkerOptions().position(latlng1));

                        m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.car));
                        m.setTitle("test");
                        UserAccess user = item;
                        String info = user.getLastPosition().getID() + "--" + user.getLastPosition().getSpeed() + "--" + user.getLastPosition().getLTime() + "--" + user.getLastPosition().getLDate();
                        m.setSnippet(info);
                        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getActivity()));
                        mMap.getUiSettings().setMapToolbarEnabled(false);
                        mMap.getUiSettings().setCompassEnabled(false);


                    }
                    double minLat = Collections.min(latList);
                    double maxLat = Collections.max(latList);
                    double minLng = Collections.min(lngList);
                    double maxLng = Collections.max(lngList);
                    moveCamera(mMap, minLat, maxLat, minLng, maxLng);

                }

            } else {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                Toast.makeText(getActivity().getApplicationContext(), R.string.for_this_filter_have_not_any_car, Toast.LENGTH_SHORT).show();
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


    public static MapFragment newInstance(double lat, double lng) {

        Bundle args = new Bundle();
        args.putDouble("LAT", lat);
        args.putDouble("LNG", lng);
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static MapFragment newInstance(List<UserAccess> userAccess,String id,boolean isOnline) {
        Gson gson = new Gson();
        String position = gson.toJson(userAccess);
        Bundle args = new Bundle();
        args.putBoolean("filter", true);
        args.putString("POSITION", position);
        args.putString("CAR_ID",id);
        args.putBoolean("IS_ONLINE",isOnline);
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getActivity().getApplicationContext()));
        mMap.getUiSettings().setMapToolbarEnabled(false);
        return false;
    }


    private void moveCamera(final GoogleMap googleMap, double MinLat, double MaxLat, double MinLng, double MaxLng) {
        final LatLngBounds.Builder builder = new LatLngBounds.Builder();

        builder.include(new LatLng(MaxLat, MaxLng));
        builder.include(new LatLng(MinLat, MinLng));


        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 64));
            }
        });


    }


    @Override
    public void onMenuToggle(boolean opened) {

    }

    private void getLastPositionForUpdate(String id, final boolean isOnline)
    {
        final List<Double> latList = new ArrayList<>();
        final List<Double> lngList = new ArrayList<>();
        FilterApi api = FilterApi.getInstance(MainActivity.mainActivityContext);
        api.getLastPosWithId(new FilterApi.OnAllDriversBack() {
            @Override
            public void OnResponse(List<LastPosition> lastPositions) {
                if(isOnline)
                {
                    String Date = getCurrentTime()[1];
                    String hour = getCurrentTime()[0];
                    int diff;
                    LatLng carLatLng;
                    for (int i=0;i<lastPositions.size();i++)
                          {

                        diff =  Math.abs(Integer.parseInt(lastPositions.get(i).getLTime().split(":")[0]) - Integer.parseInt(hour));
                        if(lastPositions.get(i).getLDate().equals(Date)&& diff<=2)
                        {
                            String lat = lastPositions.get(i).getN();
                            String lng = lastPositions.get(i).getE();
                            carLatLng = util.createLatLng(lat,lng);
                            latList.add(carLatLng.latitude);
                            lngList.add(carLatLng.longitude);
                            mMap.addMarker(new MarkerOptions().position(carLatLng))
                                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.car));
                        }

                    }
                }
                else
                    {
                        LatLng carLatLng;
                        for (LastPosition lastpos:lastPositions
                             ) {
                            String lat = lastpos.getN();
                            String lng = lastpos.getE();
                            carLatLng = util.createLatLng(lat,lng);
                            latList.add(carLatLng.latitude);
                            lngList.add(carLatLng.longitude);
                            mMap.addMarker(new MarkerOptions().position(carLatLng))
                                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.car));


                        }
                    }

                double minLat ;
                double maxLat ;
                double minLng ;
                double maxLng ;

                if(latList.size()>0 && lngList.size()>0){


                    minLat = Collections.min(latList);
                    maxLat = Collections.max(latList);
                    minLng = Collections.min(lngList);
                    maxLng = Collections.max(lngList);

                    moveCamera(mMap, minLat, maxLat, minLng, maxLng);

                }else
                    {
                        moveCamera(new LatLng(32.4274, 53.6880), 4f);
                    }


            }
        }, Constants.URL_GET_LAST_POS_WITH_ID+id);
    }

    private String[] getCurrentTime() {

        Date c = Calendar.getInstance().getTime();
        Calendar c2 = Calendar.getInstance();
        PersianCal persian = new PersianCal(c2);
        String date = persian.getYear()+"/"+persian.getMonth()+"/"+persian.getDay();

        int hour = c.getHours();
        String dateTime = String.valueOf(hour) + "--" + date;
        String[] currentDateTime = dateTime.split("--");

        return  currentDateTime;

    }



}
