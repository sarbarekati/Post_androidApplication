package com.anad.mobile.post.Activity.ShowPath;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anad.mobile.post.API.FilterApi;
import com.anad.mobile.post.API.ReportAPI;
import com.anad.mobile.post.Activity.MainActivity;
import com.anad.mobile.post.Adapter.CustomInfoWindowAdapter;
import com.anad.mobile.post.Adapter.SpinnerAdapter;
import com.anad.mobile.post.Models.Cars;
import com.anad.mobile.post.Models.DriverModel;
import com.anad.mobile.post.Models.FilterModel.CarTreeItem;
import com.anad.mobile.post.Models.FilterModel.TreeItem;
import com.anad.mobile.post.Models.HandleSubTree;
import com.anad.mobile.post.Models.Org;
import com.anad.mobile.post.Models.OrgInfoModel;
import com.anad.mobile.post.Models.Points;
import com.anad.mobile.post.Models.Rout;
import com.anad.mobile.post.Models.ShowPathModels.Route;
import com.anad.mobile.post.Models.SubTree;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.CreateRoute;
import com.anad.mobile.post.Utils.JalaliCalendar;
import com.anad.mobile.post.Utils.SingletonApi;
import com.anad.mobile.post.Utils.Util;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.wms.WMSEndpoint;
import org.osmdroid.wms.WMSTileSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class ShowPathActivity extends AppCompatActivity implements LocationListener,
        View.OnClickListener, android.location.LocationListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.InfoWindowAdapter, ShowPathView {

    //region Fields
    String currentDate;
    private Spinner spinnerIndexOne,
            spinnerIndexTwo,
            spinnerIndexThree,
            spinnerIndexFour,
            spinnerIndexFive,
            spinnerIndexDriver;
    int car_Id;
    private LinearLayout containerOne,
            containerTwo,
            containerThree,
            containerFour,
            containerFive,
            containerDriver,
            containerSpinner;

    String s;
    private FilterApi api;
    private boolean isPlay;
    private LinearLayout card_car_number;
    private PostSharedPreferences preferences;
    private int mCurrentIndex;

    private AppCompatCheckBox check_choice_car, check_enter_number;
    private int parentId;
    private List<Cars> subTreeListSpinnerDriver;


    private List<DriverModel> oSubTreeListSpinnerDriver;

    private static int lastOrgId;

    private ImageView playAnimation, backToStart, goToEnd, closeContainer;
    private AppCompatCheckBox check_continues;
    private boolean isContinues;

    private AppCompatEditText edtMin, edtMax, edtStopPoint, code_Car;
    private TextView showPathTitle;
    private Marker markerForAnimation;

    private org.osmdroid.views.overlay.Marker osmMarkerForAnimation;


    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted = false;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private GoogleMap mMap;
    private static final float DEFAULT_ZOOM = 15f;
    private Marker mMarker;

    private static final String TAG = "ShowPathActivity";

    private FloatingActionButton fab;
    private Button fab_chek;
    private TextView btn_select_date;
    private RelativeLayout date_picker_layout;
    private FABRevealLayout fabRevealLayout;
    private TextView txtDate;
    private ImageView next_date, pre_date;
    private List<String> userAccessOrgName;
    static int stop_time = 0;
    static int max_speed = 0;
    static int min_speed = 0;
    private boolean isCarCode;
    private TextView nextDayLabel, perDayLabel;
    private Util util;
    private ImageView imgBack;
    private List<Points> startAndEndPoints;
    private CardView videoRoot;
    private FloatingActionButton videoFab;
    private MapView mapView;


    private ShowPathPresenter presenter;
    private SpinnerAdapter<TreeItem> adapterLevelOne;
    private SpinnerAdapter<CarTreeItem> adapterLevelTwo;
    private Integer driverIds;


    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new PostSharedPreferences(this);
        presenter = new ShowPathPresenter(this, preferences, this);

        if (Util.authenticateUser(preferences)) {


            setContentView(R.layout.activity_show_path);

            util = Util.getInstance();
            getLocationPermission();
            initMap();


            api = FilterApi.getInstance(this);
            fab = findViewById(R.id.click_fab);


            fabRevealLayout = findViewById(R.id.fab_layout);
            fab_chek = findViewById(R.id.check_filter);
            util.setTypeFaceButton(fab_chek, this);
            btn_select_date = findViewById(R.id.select_date);
            btn_select_date.setOnClickListener(this);
            txtDate = findViewById(R.id.date_text);

            util.setTypeFaceNumber(txtDate, this);

            next_date = findViewById(R.id.next_day);
            next_date.setVisibility(View.GONE);
            pre_date = findViewById(R.id.pre_day);
            pre_date.setVisibility(View.GONE);

            nextDayLabel = findViewById(R.id.txt_next_day);
            perDayLabel = findViewById(R.id.txt_pre_day);
            nextDayLabel.setVisibility(View.GONE);
            perDayLabel.setVisibility(View.GONE);

            next_date.setOnClickListener(this);
            pre_date.setOnClickListener(this);

            userAccessOrgName = new ArrayList<>();


            initialize();

            videoFab = findViewById(R.id.video);
            videoFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (CreateRoute.pointsList != null && CreateRoute.pointsList.size() > 0) {
                        videoFab.setVisibility(View.GONE);
                        Animation animation = AnimationUtils.loadAnimation(ShowPathActivity.this, R.anim.slide_to_left);
                        videoRoot.setVisibility(View.VISIBLE);
                        videoRoot.setAnimation(animation);
                    } else {
                        Toast.makeText(ShowPathActivity.this, R.string.No_Car_for_this_Date, Toast.LENGTH_SHORT).show();
                    }

                }
            });


            check_continues.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        isContinues = true;
                    } else {
                        isContinues = false;
                    }

                }
            });

            check_choice_car.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        containerSpinner.setVisibility(View.VISIBLE);
                        check_enter_number.setChecked(false);
                    } else {
                        containerSpinner.setVisibility(View.GONE);
                    }
                }
            });

            check_enter_number.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        check_choice_car.setChecked(false);
                        card_car_number.setVisibility(View.VISIBLE);
                        isCarCode = true;

                    } else {
                        card_car_number.setVisibility(View.GONE);
                        isCarCode = false;

                    }
                }
            });


            spinnerIndexDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    car_Id = oSubTreeListSpinnerDriver.get(0).getDrv_ID();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fabRevealLayout.revealSecondaryView();
                }
            });

            fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
                @Override
                public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {

                    txtDate.setText(btn_select_date.getText().toString());
                }

                @Override
                public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
                    setUpFilter();


                    fab_chek.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (edtMin.getText().toString().equals(""))
                                min_speed = 0;
                            else {
                                min_speed = Integer.parseInt(edtMin.getText().toString());
                            }
                            if (edtMax.getText().toString().equals(""))
                                max_speed = 0;
                            else {
                                max_speed = Integer.parseInt(edtMax.getText().toString());
                            }
                            if (edtStopPoint.getText().toString().equals(""))
                                stop_time = 0;
                            else {
                                stop_time = Integer.parseInt(edtStopPoint.getText().toString());
                            }

                            next_date.setVisibility(View.VISIBLE);
                            pre_date.setVisibility(View.VISIBLE);
                            nextDayLabel.setVisibility(View.VISIBLE);
                            perDayLabel.setVisibility(View.VISIBLE);
                            s = "";
                            if (btn_select_date.getText().toString().equals(getString(R.string.choose_date))) {
                                s = "";
                            } else {
                                s = btn_select_date.getText().toString();
                            }
                            if (s.equals("")) {
                                Toast.makeText(ShowPathActivity.this, R.string.please_select_a_date, Toast.LENGTH_SHORT).show();
                            } else {
                                if (isCarCode)
                                    car_Id = Integer.parseInt(code_Car.getText().toString());

                                /*SingletonApi api = SingletonApi.getInstance(ShowPathActivity.this);

                                api.getAllDriversCode(new SingletonApi.OnCarIdBack() {
                                    @Override
                                    public void OnResponseBack(List<Integer> carId) {

                                        if (carId.size() > 0) {
                                            getRoutInformation(s, car_Id + "", new ArrayList<Points>(), min_speed, max_speed, stop_time);

                                            fabRevealLayout.revealMainView();
                                        } else {
                                            Toast.makeText(ShowPathActivity.this, "کاربر به این خودرو دسترسی ندارد.", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }, car_Id);*/


                                //TODO call route

                                presenter.getDailyRoute(driverIds, s, s);
                                fabRevealLayout.revealMainView();


                            }


                        }
                    });
                }
            });
        } else {
            Util.backToLoginActivity(this);
        }

    }


    private void getLocationPermission() {
        String[] permission = {FINE_LOCATION, COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this
                , FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this
                    , COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void initMap() {
        mapView = findViewById(R.id.osm_map);
        if (preferences.getCatchMap().equals("")) {

        } else {
            WMSEndpoint wmsEndpoint = new Gson().fromJson(preferences.getCatchMap(), WMSEndpoint.class);
            WMSTileSource source = WMSTileSource.createFrom(wmsEndpoint, wmsEndpoint.getLayers().get(0));
            mapView.setTileSource(source);
            mapView.setVerticalMapRepetitionEnabled(false);
            mapView.setHorizontalMapRepetitionEnabled(false);
            mapView.setMultiTouchControls(true);
            mapView.setMaxZoomLevel(18.00);
            mapView.setBuiltInZoomControls(false);
            mapView.getController().animateTo(new GeoPoint(35.68, 51.38));
            mapView.getController().setZoom(5.0);


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


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.play_animation:

                isPlay = !isPlay;

                if (!isPlay) {
                    playAnimation.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play_white_24dp));
                    isPlay = false;
                    LatLng start = new LatLng(0, 0);

                    animateOsmMarker(osmMarkerForAnimation, start, start);


                } else {


                    playAnimation.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pause_white_24dp));
                    if (CreateRoute.pointsList != null && CreateRoute.pointsList.size() > 0) {
                        startAndEndPoints = CreateRoute.pointsList;

                        LatLng startMarker = new LatLng(startAndEndPoints.get(0).getN(), startAndEndPoints.get(0).getE());
                        LatLng start;
                        LatLng end;


                        if (mCurrentIndex == 0) {
                            if (osmMarkerForAnimation == null) {
                                osmMarkerForAnimation = new org.osmdroid.views.overlay.Marker(mapView);
                                osmMarkerForAnimation.setPosition(new GeoPoint(startMarker.latitude, startMarker.longitude));
                                mapView.getOverlays().add(osmMarkerForAnimation);
                            }
                            start = new LatLng(startAndEndPoints.get(0).getN(), startAndEndPoints.get(0).getE());
                            end = new LatLng(startAndEndPoints.get(1).getN(), startAndEndPoints.get(1).getE());

                            animateOsmMarker(osmMarkerForAnimation, start, end);
                        } else {
                            start = new LatLng(startAndEndPoints.get(mCurrentIndex).getN(), startAndEndPoints.get(mCurrentIndex).getE());
                            end = new LatLng(startAndEndPoints.get(mCurrentIndex + 1).getN(), startAndEndPoints.get(mCurrentIndex + 1).getE());
                            animateOsmMarker(osmMarkerForAnimation, start, end);
                        }
                    }


                }

                break;

            case R.id.close_control_container:
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_to_right);
                videoRoot.setAnimation(animation);
                videoRoot.setVisibility(View.GONE);
                videoFab.setVisibility(View.VISIBLE);
                break;

            case R.id.back_to_start:
                mCurrentIndex = 0;
                playAnimation.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play_white_24dp));
                LatLng start = new LatLng(startAndEndPoints.get(0).getN(), startAndEndPoints.get(0).getE());
                osmMarkerForAnimation.remove(mapView);
                osmMarkerForAnimation = new org.osmdroid.views.overlay.Marker(mapView);
                osmMarkerForAnimation.setPosition(new GeoPoint(start.latitude, start.longitude));
                mapView.getOverlays().add(osmMarkerForAnimation);

                isPlay = false;
                break;
            case R.id.go_to_end:

                mCurrentIndex = 0;

                playAnimation.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play_white_24dp));
                LatLng end = new LatLng(startAndEndPoints.get(startAndEndPoints.size() - 1).getN(), startAndEndPoints.get(startAndEndPoints.size() - 1).getE());
                osmMarkerForAnimation.remove(mapView);
                osmMarkerForAnimation = new org.osmdroid.views.overlay.Marker(mapView);
                osmMarkerForAnimation.setPosition(new GeoPoint(end.latitude, end.longitude));
                mapView.getOverlays().add(osmMarkerForAnimation);

                isPlay = false;
                break;

            case R.id.back_click:
                onBackPressed();
                break;


            case R.id.date_picker_container:

                ir.hamsaa.persiandatepicker.util.PersianCalendar initDate = new ir.hamsaa.persiandatepicker.util.PersianCalendar();

                Date date = new Date();
                JalaliCalendar p = new JalaliCalendar();
                String persianDate = p.getJalaliDate(date);
                int yearFilter = Integer.parseInt(persianDate.split("/")[0]);
                int monthFilter = Integer.parseInt(persianDate.split("/")[1]);
                int dayFilter = Integer.parseInt(persianDate.split("/")[2]);

                initDate.setPersianDate(yearFilter, monthFilter, dayFilter);


                PersianDatePickerDialog datePickerStart = new PersianDatePickerDialog(this)
                        .setPositiveButtonString("تائید")
                        .setNegativeButton("انصراف")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                        .setMinYear(1300)
                        .setInitDate(initDate)
                        .setActionTextColor(Color.GRAY)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
                                String Month;
                                String Day;

                                if ((persianCalendar.getPersianMonth()) < 10) {
                                    Month = "0" + (persianCalendar.getPersianMonth());
                                } else {
                                    Month = (persianCalendar.getPersianMonth()) + "";
                                }
                                if ((persianCalendar.getPersianDay()) < 10) {
                                    Day = "0" + (persianCalendar.getPersianDay());
                                } else {
                                    Day = (persianCalendar.getPersianDay()) + "";
                                }

                                String dataStart = persianCalendar.getPersianYear() + "/" + Month + "/" + Day;
                                btn_select_date.setText(dataStart);
                            }

                            @Override
                            public void onDismissed() {
                            }
                        });
                datePickerStart.show();


                break;

            case R.id.next_day:
                mCurrentIndex = 0;
                isPlay = false;
                osmMarkerForAnimation = null;
                playAnimation.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play_white_24dp));
                currentDate = txtDate.getText().toString();
                String sDay, sMonth, sYear;
                String[] s = currentDate.split("/");
                int day = Integer.parseInt(s[2]);
                int Month = Integer.parseInt(s[1]);
                int year = Integer.parseInt(s[0]);

                if (Month > 6 && Month < 12) {
                    if (day < 30) {
                        day += 1;
                    } else if (day == 30) {
                        day = 1;
                        Month += 1;
                    }
                } else if (Month >= 1 && Month <= 6) {
                    if (day < 31) {
                        day += 1;
                    } else if (day == 31) {
                        day = 1;
                        Month += 1;
                    }
                } else if (Month == 12) {
                    if (day < 29) {
                        day += 1;
                    } else if (day == 29) {
                        day = 1;
                        Month = 1;
                        year += 1;
                    }
                }

                if (day < 10) {
                    sDay = "0" + day;
                } else {
                    sDay = day + "";
                }
                if (Month < 10) {
                    sMonth = "0" + Month;

                } else {
                    sMonth = Month + "";
                }
                currentDate = year + "/" + sMonth + "/" + sDay;

                txtDate.setText(currentDate);

                presenter.getDailyRoute(driverIds,txtDate.getText().toString(),txtDate.getText().toString());

                break;

            case R.id.pre_day:
                mCurrentIndex = 0;
                isPlay = false;
                osmMarkerForAnimation = null;
                playAnimation.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play_white_24dp));
                currentDate = txtDate.getText().toString();
                String sPDay, sPMonth, sPYear;
                String[] sP = currentDate.split("/");

                int dayP = Integer.parseInt(sP[2]);
                int MonthP = Integer.parseInt(sP[1]);
                int yearP = Integer.parseInt(sP[0]);

                if (MonthP > 6 && MonthP <= 12) {
                    if (dayP > 1) {
                        dayP -= 1;
                    } else if (dayP == 1) {
                        dayP = 30;
                        MonthP -= 1;
                    }
                } else if (MonthP >= 1 && MonthP <= 6 && dayP != 1) {
                    if (dayP > 1) {
                        dayP -= 1;
                    } else {
                        dayP = 31;
                        MonthP -= 1;

                    }
                } else if (MonthP == 1 && dayP == 1) {
                    MonthP = 12;
                    dayP = 29;
                    yearP -= 1;
                }

                if (dayP < 10) {
                    sPDay = "0" + dayP;
                } else {
                    sPDay = dayP + "";
                }
                if (MonthP < 10) {
                    sPMonth = "0" + MonthP;

                } else {
                    sPMonth = MonthP + "";
                }
                currentDate = yearP + "/" + sPMonth + "/" + sPDay;

                txtDate.setText(currentDate);

               // getRoutInformation(txtDate.getText().toString(), car_Id + "", new ArrayList<Points>(), min_speed, max_speed, stop_time);

                presenter.getDailyRoute(driverIds,txtDate.getText().toString(),txtDate.getText().toString());


                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng latLng;
        if (location != null && mMap != null) {

            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.setOnMarkerClickListener(this);
            mMap.setInfoWindowAdapter(this);
            mMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                    .title(getString(R.string.you_current_position)));
            mMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.car));

            mMarker.showInfoWindow();
            String info = 1299 + "--" + 120 + "--" + 15484 + "--" + 58486;
            mMarker.setSnippet(info);
            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MainActivity.mainActivityContext));
            mMap.getUiSettings().setMapToolbarEnabled(false);
            mMap.getUiSettings().setCompassEnabled(false);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
        }


    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {


        return false;
    }





    private void initialize() {

        videoRoot = findViewById(R.id.video_root_container);
        closeContainer = findViewById(R.id.close_control_container);
        playAnimation = findViewById(R.id.play_animation);
        backToStart = findViewById(R.id.back_to_start);
        goToEnd = findViewById(R.id.go_to_end);


        closeContainer.setOnClickListener(this);
        playAnimation.setOnClickListener(this);
        backToStart.setOnClickListener(this);
        goToEnd.setOnClickListener(this);


        imgBack = findViewById(R.id.back_click);
        imgBack.setOnClickListener(this);
        date_picker_layout = findViewById(R.id.date_picker_container);
        date_picker_layout.setOnClickListener(this);

        showPathTitle = findViewById(R.id.show_path_title);
        check_continues = findViewById(R.id.ch_show_continues);
        check_choice_car = findViewById(R.id.title_choice);
        check_enter_number = findViewById(R.id.code_choice);

        edtMax = findViewById(R.id.edt_max_speed);
        edtMin = findViewById(R.id.edt_min_speed);

        edtStopPoint = findViewById(R.id.edt_stop_time);
        code_Car = findViewById(R.id.edt_code_Car);


        containerOne = findViewById(R.id.container_one);
        containerTwo = findViewById(R.id.container_two);

        containerThree = findViewById(R.id.container_three);
        containerFour = findViewById(R.id.container_four);
        containerFive = findViewById(R.id.container_five);
        containerDriver = findViewById(R.id.container_driver);
        card_car_number = findViewById(R.id.car_number_container);
        containerSpinner = findViewById(R.id.spinner_container);

        spinnerIndexOne = findViewById(R.id.spinner_one);
        spinnerIndexTwo = findViewById(R.id.spinner_two);

        spinnerIndexThree = findViewById(R.id.spinner_three);
        spinnerIndexFour = findViewById(R.id.spinner_four);
        spinnerIndexFive = findViewById(R.id.spinner_five);
        spinnerIndexDriver = findViewById(R.id.spinner_driver);

        setFont();
    }

    private void setUpFilter() {

        presenter.fillDataFilter(-1);

        spinnerIndexOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                Integer stateId = adapterLevelOne.getTreeItemId(position);
                if (stateId != -1) {
                    presenter.fillDataFilter(stateId);
                } else {
                    containerTwo.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerIndexTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, final long id) {

                driverIds = adapterLevelTwo.getTreeItemId(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setFont() {
        util.setTypeFace(showPathTitle, this);
        util.setTypeFace(showPathTitle, this);
        util.setTypeFaceLight(nextDayLabel, this);
        util.setTypeFaceLight(perDayLabel, this);
        util.setTypeFaceNumber(btn_select_date, this);
        util.setTypeFaceNumberEditText(edtMax, this);
        util.setTypeFaceNumberEditText(edtMin, this);
        util.setTypeFaceNumberEditText(edtStopPoint, this);
        util.setTypeFaceNumberEditText(code_Car, this);
    }

    private void animateOsmMarker(final org.osmdroid.views.overlay.Marker marker, final LatLng fromPosition, final LatLng toPosition) {


        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();


        final long duration = 100;

        final android.view.animation.Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);

                if (isPlay) {
                    double lat = (toPosition.latitude - fromPosition.latitude) * t + fromPosition.latitude;
                    double lngDelta = toPosition.longitude - fromPosition.longitude;

                    if (Math.abs(lngDelta) > 180) {
                        lngDelta -= Math.signum(lngDelta) * 360;
                    }
                    double lng = lngDelta * t + fromPosition.longitude;

                    marker.setPosition(new GeoPoint(lat, lng));
                    mapView.getOverlays().add(marker);


                    if (t < 1.0) {
                        handler.postDelayed(this, 16);
                    } else {

                        nextMoveAnimation();
                    }


                }
            }
        });


    }

    private void nextMoveAnimation() {
        mCurrentIndex++;
        if (mCurrentIndex < startAndEndPoints.size() - 1) {
            LatLng start = new LatLng(startAndEndPoints.get(mCurrentIndex).getN(), startAndEndPoints.get(mCurrentIndex).getE());
            LatLng end = new LatLng(startAndEndPoints.get(mCurrentIndex + 1).getN(), startAndEndPoints.get(mCurrentIndex + 1).getE());
            animateOsmMarker(osmMarkerForAnimation, start, end);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void fillFilter(List<TreeItem> treeItems) {
        if (treeItems != null) {
            adapterLevelOne = new SpinnerAdapter<>(ShowPathActivity.this, treeItems);
            spinnerIndexOne.setAdapter(adapterLevelOne);
        }

    }

    @Override
    public void fillCar(List<CarTreeItem> carTreeItems) {
        if (carTreeItems != null) {
            containerTwo.setVisibility(View.VISIBLE);
            adapterLevelTwo = new SpinnerAdapter<>(ShowPathActivity.this, carTreeItems);
            spinnerIndexTwo.setAdapter(adapterLevelTwo);
        }
    }

    @Override
    public void renderDailyRoute(List<Route> routes) {

        String body =  routes.get(0).getBody();
        CreateRoute.Builder builder = new CreateRoute.Builder(ShowPathActivity.this)
                .withMaxSpeed(max_speed)
                .withMinSpeed(min_speed)
                .withStopTime(stop_time);
        CreateRoute cr = builder.build();
        cr.getPointOnOsmMap(mapView,body,isContinues);


    }
}
