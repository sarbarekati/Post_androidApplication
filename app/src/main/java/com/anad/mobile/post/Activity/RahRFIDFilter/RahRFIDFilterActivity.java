package com.anad.mobile.post.Activity.RahRFIDFilter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.anad.mobile.post.API.FilterApi;
import com.anad.mobile.post.Activity.MainActivity;
import com.anad.mobile.post.Activity.RahRFIDReportList;
import com.anad.mobile.post.Adapter.SpinnerAdapter;
import com.anad.mobile.post.MapManager.Model.SearchLastPositionItem;
import com.anad.mobile.post.Models.Cars;
import com.anad.mobile.post.Models.DriverModel;
import com.anad.mobile.post.Models.FilterModel.CarTreeItem;
import com.anad.mobile.post.Models.HandleSubTree;
import com.anad.mobile.post.Models.LastPosition;
import com.anad.mobile.post.Models.Org;
import com.anad.mobile.post.Models.OrgInfoModel;
import com.anad.mobile.post.Models.SubTree;
import com.anad.mobile.post.Models.FilterModel.TreeItem;
import com.anad.mobile.post.R;
import com.anad.mobile.post.ReportManager.Managers.ReportManager;
import com.anad.mobile.post.ReportManager.model.Base.SearchReportItem;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.DataTimeUtils.DateTimeUtils;
import com.anad.mobile.post.Utils.FontUtils.FontUtil;
import com.anad.mobile.post.Utils.JalaliCalendar;
import com.anad.mobile.post.Utils.ReportTypeConst;
import com.anad.mobile.post.Utils.Util;
import com.google.gson.Gson;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RahRFIDFilterActivity extends AppCompatActivity implements View.OnClickListener, IRahRFIDReport {

    //region Fields
    public static final String RAHSEPARI_REPORT_RESPONSE = "RahsepariReportResponse";
    private static final String TIME_START = "TIME_START";
    private static final String TIME_END = "TIME_END";
    public static final String REPORT_INFO = "REPORT_INFO";
    public static final String REPORT_ID = "REPORT_ID";

    private AppCompatSpinner spinnerIndexOne, spinnerIndexTwo, spinnerIndexThree, spinnerIndexFour, spinnerIndexFive, spinnerIndexDriver, spinnerFilters;
    private TextView txtTitle, txtOrgName;
    private RelativeLayout containerOne, containerTwo, containerThree, containerFour, containerFive, containerDriver;
    private int lastIndexSpinnerOne, lastIndexSpinnerTwo, lastIndexSpinnerThree, lastIndexSpinnerFour, lastIndexSpinnerFive, lastIndexSpinnerDriver;
    private Button acceptFilter;
    private List<String> userAccessOrgName;

    private List<SubTree> subTreeListSpinnerOne, subTreeListSpinnerTwo, subTreeListSpinnerThree, subTreeListSpinnerFour, subTreeListSpinnerFive;
    private List<OrgInfoModel> oSubTreeListSpinnerOne, oSubTreeListSpinnerTwo, oSubTreeListSpinnerThree, oSubTreeListSpinnerFour, oSubTreeListSpinnerFive;

    private List<Cars> subTreeListSpinnerDriver;
    private List<DriverModel> oSubTreeListSpinnerDriver;


    private Util util;
    private LinearLayout TimeContainer, SpeedContainer, LengthContainer;
    private RelativeLayout btnStartDate, btnEndDate, btnStartTime, btnEndTime;
    PersianCalendar persianCal;
    private TextView txtStartDate, txtStartTime, txtEndDate, txtEndTime;
    private AppCompatEditText edtSpeedStart, edtSpeedEnd, edtLengthStart, edtLengthEnd;

    int reportId;
    List<Integer> ids = new ArrayList<>();
    private RelativeLayout pathContainer, filterContainer;
    private LinearLayout dateContainer;
    private boolean isFromMap;
    private boolean isOnlineCheck;
    private EditText edtCarCode;
    private RelativeLayout carOnline;
    private AppCompatCheckBox car_online_check_box;

    private TextView filterCheckBox;
    private TextView carCount;
    private Spinner pathSpinner;
    private List<LastPosition> positionToShow;

    private List<Integer> carOrgIds;
    private static final String SEPARATOR = ",";
    StringBuilder sb;
    List<String> path;

    private int SP_ONE = -1;
    private int SP_TWO = -1;
    private int SP_THREE = -1;
    private int SP_FOUR = -1;
    private int SP_FIVE = -1;
    private int SP_DRIVER = -1;
    private boolean isSelectCar;
    private boolean isPathSelected;
    private String routeName = "";
    private boolean isEnterCode;


    private LinearLayout spinnerContainer;
    private RelativeLayout codeContainer;
    private AppCompatCheckBox chSpinners, chCarCode;
    private boolean isCarCode;
    private boolean isSpinner = true;
    private CardView card_car_number;
    private ImageView imgBackClick;
    private LinearLayout carNumberContainer;
    private PostSharedPreferences preferences;
    private ir.hamsaa.persiandatepicker.util.PersianCalendar initDate;
    private int YEAR, MONTH, DAY;
    private String persianDate;
    private FilterApi api;
    private AppCompatCheckBox onlineCars;
    private boolean isSelectAll;

    /**
     * Field For Create SearchReportItem
     */
    private List<Integer> driverIds;
    private List<Integer> deviceCodes;
    private List<Integer> lineIds;
    private List<Integer> carIds;
    private int LENGTH_TO;
    private int LENGTH_FROM;
    private int SPEED_FROM;
    private int SPEED_TO;
    private String START_DATE;
    private String END_DATE;
    private String DURATION_TO;
    private String DURATION_FROM;


    private int REPORT_TYPE;


    private ReportManager reportManager;
    private List<String> linesNames;
    private SpinnerAdapter<TreeItem> adapterLevelOne;
    private SpinnerAdapter<CarTreeItem> adapterLevelTwo;

    //endregion

    //region Activity LifeCycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new PostSharedPreferences(this);
        reportManager = new ReportManager(this, preferences, this);
        if (Util.authenticateUser(preferences)) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_rah_rfidreport);

            util = Util.getInstance();
            persianCal = new PersianCalendar();

            api = FilterApi.getInstance(this);

            initDate = new ir.hamsaa.persiandatepicker.util.PersianCalendar();
            PersianCalendar p = new PersianCalendar();
            Date c = new Date();
            JalaliCalendar jalaliCalendar = new JalaliCalendar();
            persianDate = jalaliCalendar.getJalaliDate(c);

            YEAR = Integer.parseInt(persianDate.split("/")[0]);
            MONTH = Integer.parseInt(persianDate.split("/")[1]);
            DAY = Integer.parseInt(persianDate.split("/")[2]);


            Bundle b = getIntent().getExtras();
            if (b != null) {
                reportId = b.getInt(REPORT_ID);
            }


            carIds = new ArrayList<>();


            userAccessOrgName = new ArrayList<>();
            subTreeListSpinnerOne = new ArrayList<>();
            subTreeListSpinnerTwo = new ArrayList<>();
            subTreeListSpinnerThree = new ArrayList<>();
            subTreeListSpinnerFour = new ArrayList<>();
            subTreeListSpinnerFive = new ArrayList<>();

            oSubTreeListSpinnerOne = new ArrayList<>();
            oSubTreeListSpinnerTwo = new ArrayList<>();
            oSubTreeListSpinnerThree = new ArrayList<>();
            oSubTreeListSpinnerFour = new ArrayList<>();
            oSubTreeListSpinnerFive = new ArrayList<>();
            oSubTreeListSpinnerDriver = new ArrayList<>();


            initializeView();


            onlineCars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    isOnlineCheck = isChecked;
                }
            });


            if (reportId == Constants.RAHSEPARI_REPORT) {
                txtTitle.setText(R.string.Rahsepari_Report);
            } else if (reportId == Constants.ARP_REPORT) {
                txtTitle.setText(R.string.ARP_Report);
            }

            Bundle bMap = getIntent().getExtras();

            if (bMap != null && bMap.getBoolean(MainActivity.MAP_FILTER)) {
                setFilterForShowCar();

            }


            reportManager.callGetTreeItem(-1);


//            handleSubTree = HandleSubTree.getInstance(this);


/*
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    handleSubTree.getUserAccessByUserNameOrParentIdHandler(Constants.URL_GET_USER_ACCESS_BY_USER_NAME + preferences.getPrefUserName(), new HandleSubTree.OnGetOrgInfoHandler() {
                        @Override
                        public void onGetOrg(OrgInfoModel orgInfoModel) {
                            List<Org> orgs = orgInfoModel.getOrgModel();
                            if (orgs != null && orgs.size() > 0) {
                                userAccessOrgName.add(0, getString(R.string.all_item));
                                for (int i = 0; i < orgs.size(); i++) {
                                    userAccessOrgName.add(i + 1, orgs.get(i).getOrg_name());
                                }


                                ArrayAdapter sAdapter = new ArrayAdapter(RahRFIDFilterActivity.this, R.layout.spinner_item, userAccessOrgName);
                                spinnerIndexOne.setAdapter(sAdapter);

                                OrgInfoModel allOrg = new OrgInfoModel();
                                allOrg.setAllCarCount(orgInfoModel.getAllCarCount());


                                oSubTreeListSpinnerOne.add(0, allOrg);
                                oSubTreeListSpinnerOne.add(1, orgInfoModel);

                                setUpSpinners();
                            }
                        }
                    });


                }
            }, 1000);
*/


            chSpinners.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        isSpinner = true;
                        chCarCode.setChecked(false);
                        carNumberContainer.setVisibility(View.GONE);
                        codeContainer.setVisibility(View.VISIBLE);
                        spinnerContainer.setVisibility(View.VISIBLE);
                    } else {
                        isSpinner = false;
                    }
                }
            });

            chCarCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        isCarCode = true;
                        chSpinners.setChecked(false);
                        carNumberContainer.setVisibility(View.VISIBLE);
                        codeContainer.setVisibility(View.VISIBLE);
                        spinnerContainer.setVisibility(View.GONE);
                    } else {
                        isCarCode = false;
                    }
                }
            });

            if (isFromMap)
                acceptFilter.setOnClickListener(new AcceptForMapClickListener());
            else
                acceptFilter.setOnClickListener(new AcceptClickListener());


            /*acceptFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (REPORT_TYPE) {
                        case 0:
                            if (!txtStartTime.getText().toString().equals(""))
                                MabdatakhirStart = txtStartTime.getText().toString();
                            if (!txtEndTime.getText().toString().equals(""))
                                MabdatakhirEnd = txtEndTime.getText().toString();
                            break;
                        case 1:
                            if (!txtStartTime.getText().toString().equals(""))
                                MaghsadtakhirStart = txtStartTime.getText().toString();
                            if (!txtEndTime.getText().toString().equals(""))
                                MaghsadtakhirEnd = txtEndTime.getText().toString();
                            break;
                        case 2:
                            if (!txtStartTime.getText().toString().equals(""))
                                MaghsadTajilStart = txtStartTime.getText().toString();
                            if (!txtEndTime.getText().toString().equals(""))
                                MaghsadTajilEnd = txtEndTime.getText().toString();
                            break;
                        case 3:
                            if (!txtStartTime.getText().toString().equals(""))
                                ModatBargiriStart = txtStartTime.getText().toString();
                            if (!txtEndTime.getText().toString().equals(""))
                                ModatBargiriEnd = txtEndTime.getText().toString();
                            break;
                        case 4:

                            if (!txtStartTime.getText().toString().equals(""))
                                MabdaMoghararStart = txtStartTime.getText().toString();
                            if (!txtEndTime.getText().toString().equals(""))
                                MabdaMoghararEnd = txtEndTime.getText().toString();
                            break;
                        case 5:
                            if (!txtStartTime.getText().toString().equals(""))
                                MaghsadMoghararStart = txtStartTime.getText().toString();
                            if (!txtEndTime.getText().toString().equals(""))
                                MaghsadMoghararEnd = txtEndTime.getText().toString();

                            break;
                        case 6:
                            if (!txtStartTime.getText().toString().equals(""))
                                ZamanMoghararTeyStart = txtStartTime.getText().toString();
                            if (!txtEndTime.getText().toString().equals(""))
                                ZamanMoghararTeyEnd = txtEndTime.getText().toString();
                            break;
                        case 7:
                            if (!edtSpeedStart.getText().toString().equals(""))
                                minAvgSpeed = edtSpeedStart.getText().toString();
                            if (!edtSpeedEnd.getText().toString().equals(""))
                                maxAvgSpeed = edtSpeedEnd.getText().toString();
                            break;
                        case 8:
                            if (!edtSpeedStart.getText().toString().equals(""))
                                minSpeed = edtSpeedStart.getText().toString();
                            if (!edtSpeedEnd.getText().toString().equals(""))
                                maxSpeed = edtSpeedEnd.getText().toString();
                            break;
                        case 9:
                            if (!edtLengthStart.getText().toString().equals(""))
                                minLength = edtLengthStart.getText().toString();
                            if (!edtLengthEnd.getText().toString().equals(""))
                                maxLength = edtLengthEnd.getText().toString();
                            break;


                    }

                    sb = new StringBuilder();
                    positionToShow = new ArrayList<>();
                    carIds = new ArrayList<>();
                    carOrgIds = new ArrayList<>();


                    //////////////////////

                    if (isSpinner) {
                        lastIndexSpinnerOne = SP_ONE;
                        lastIndexSpinnerTwo = SP_TWO;
                        lastIndexSpinnerThree = SP_THREE;
                        lastIndexSpinnerFour = SP_FOUR;
                        lastIndexSpinnerFive = SP_FIVE;
                        lastIndexSpinnerDriver = spinnerIndexDriver.getLastVisiblePosition();

                        if (lastIndexSpinnerOne == 0) {// all item
                            positionToShow = getPositionWithOrg(getAllCarId(oSubTreeListSpinnerOne));
                            carOrgIds = getAllCarId(oSubTreeListSpinnerOne);


                        } else if (lastIndexSpinnerTwo == 0) { // all item
                            positionToShow = getPositionWithOrg(getAllCarId(oSubTreeListSpinnerTwo));
                            carOrgIds = getAllCarId(oSubTreeListSpinnerTwo);
                            lastOrgId = oSubTreeListSpinnerOne.get(1).getOrgModel().get(SP_ONE - 1).getOrg_Id();
                        } else if (lastIndexSpinnerThree == 0) { // all item
                            positionToShow = getPositionWithOrg(getAllCarId(oSubTreeListSpinnerThree));
                            carOrgIds = getAllCarId(oSubTreeListSpinnerThree);
                            lastOrgId = oSubTreeListSpinnerTwo.get(1).getOrgModel().get(SP_TWO - 1).getOrg_Id();
                        } else if (lastIndexSpinnerFour == 0) { // all item
                            positionToShow = getPositionWithOrg(getAllCarId(oSubTreeListSpinnerFour));
                            carOrgIds = getAllCarId(oSubTreeListSpinnerFour);
                            lastOrgId = lastOrgId = oSubTreeListSpinnerThree.get(1).getOrgModel().get(SP_THREE - 1).getOrg_Id();
                        } else if (lastIndexSpinnerFive == 0) { // all item
                            positionToShow = getPositionWithOrg(getAllCarId(oSubTreeListSpinnerFive));
                            carOrgIds = getAllCarId(oSubTreeListSpinnerFive);
                            lastOrgId = oSubTreeListSpinnerFour.get(1).getOrgModel().get(SP_FOUR - 1).getOrg_Id();
                        } else if (lastIndexSpinnerDriver == 0) {


                            List<Integer> id = new ArrayList<>();

                            // DriverModel d = oSubTreeListSpinnerDriver.get(lastIndexSpinnerDriver);
                            for (int i = 1; i < oSubTreeListSpinnerDriver.size(); i++) {
                                id.add(oSubTreeListSpinnerDriver.get(i).getDrv_ID());
                            }


                            carIds = id;
                            positionToShow = getPositionWithId(carIds);

                            if (SP_FIVE != -1) {
                                lastOrgId = oSubTreeListSpinnerFive.get(1).getOrgModel().get(SP_FIVE - 1).getOrg_Id();
                            } else if (SP_FOUR != -1) {
                                lastOrgId = oSubTreeListSpinnerFour.get(1).getOrgModel().get(SP_FOUR - 1).getOrg_Id();
                            } else if (SP_THREE != -1) {
                                lastOrgId = oSubTreeListSpinnerThree.get(1).getOrgModel().get(SP_THREE - 1).getOrg_Id();
                            } else if (SP_TWO != -1) {
                                lastOrgId = oSubTreeListSpinnerTwo.get(1).getOrgModel().get(SP_TWO - 1).getOrg_Id();
                            } else if (SP_ONE != -1) {
                                lastOrgId = oSubTreeListSpinnerOne.get(1).getOrgModel().get(SP_ONE - 1).getOrg_Id();
                            }


                        } else if (lastIndexSpinnerDriver != -1) {
                            isSelectCar = true;
                            List<Integer> id = new ArrayList<>();

                            DriverModel d = oSubTreeListSpinnerDriver.get(lastIndexSpinnerDriver - 1);

                            id.add(d.getDrv_ID());
                            carIds = id;
                            positionToShow = getPositionWithId(id);
                            lastOrgId = d.getOrgId();
                        }
                    }


                    //////////////////////////////


                    if (isFromMap) {


                        if (isCarCode) {
                            if (!edtCarCode.getText().toString().equals("")) {

                                final int Id = Integer.parseInt(edtCarCode.getText().toString());

                                String URL_CARS = Constants.URL_GET_ALL_DRIVER + preferences.getPrefUserName() + "/" + Id;
                                final FilterApi f = FilterApi.getInstance(RahRFIDFilterActivity.this);


                                f.getDriver(new FilterApi.OnGetUserSubTreeObject() {
                                    @Override
                                    public void onResponseUserSubTree(final List<Cars> cars) {
                                        if (cars.size() > 0) {

                                            final String URL_LAST_POS = Constants.URL_GET_ALL_DRIVER_LAST_POS + preferences.getPrefUserName() + "/" + cars.get(0).getCarId();

                                            f.getLastOneDriverPosition(new FilterApi.OnGetLastPosition() {
                                                @Override
                                                public void OnResponse(LastPosition lastPosition) {
                                                    if (isOnlineCheck) {
                                                        String Date = DateTimeUtils.getCurrentDate();
                                                        String hour = DateTimeUtils.getCurrentHour();
                                                        carIds = new ArrayList<>();

                                                        int diff = Math.abs(Integer.parseInt(lastPosition.getLTime().split(":")[0]) - Integer.parseInt(hour));
                                                        if (lastPosition.getLDate().equals(Date) && diff <= 2) {
                                                        *//*carIds.add(lastPosition.getID());

                                                        positionToShow = getPositionWithId(carIds);*//*
                                                            positionToShow = new ArrayList<>();
                                                            positionToShow.add(lastPosition);
                                                            isEnterCode = true;
                                                            lastOrgId = cars.get(0).getOrg_ID();
                                                            List<UserAccess> userAccesses = new ArrayList<>();
                                                            for (int i = 0; i < positionToShow.size(); i++) {
                                                                UserAccess u = new UserAccess();
                                                                u.setLastPosition(positionToShow.get(i));
                                                                userAccesses.add(u);

                                                            }
                                                            Gson gson = new Gson();
                                                            String sendUserAccess = gson.toJson(userAccesses);
                                                            Intent i = new Intent(RahRFIDFilterActivity.this, MainActivity.class);
                                                            i.putExtra("MAP_FILTER", sendUserAccess);
                                                            i.putExtra("IS_ONLINE", isOnlineCheck);
                                                            i.putExtra("CAR_ID", Id + "");
                                                            startActivity(i);
                                                            finish();
                                                        } else {
                                                            Toast.makeText(RahRFIDFilterActivity.this, "خودرو انتخاب شده در حالت آنلاین نمی باشد.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                    *//*carIds.add(lastPosition.getID());
                                                    positionToShow = getPositionWithId(carIds);*//*
                                                        positionToShow = new ArrayList<>();
                                                        positionToShow.add(lastPosition);
                                                        isEnterCode = true;
                                                        lastOrgId = cars.get(0).getOrg_ID();
                                                        List<UserAccess> userAccesses = new ArrayList<>();
                                                        for (int i = 0; i < positionToShow.size(); i++) {
                                                            UserAccess u = new UserAccess();
                                                            u.setLastPosition(positionToShow.get(i));
                                                            userAccesses.add(u);

                                                        }
                                                        Gson gson = new Gson();
                                                        String sendUserAccess = gson.toJson(userAccesses);
                                                        Intent i = new Intent(RahRFIDFilterActivity.this, MainActivity.class);
                                                        i.putExtra("MAP_FILTER", sendUserAccess);
                                                        i.putExtra("IS_ONLINE", isOnlineCheck);
                                                        i.putExtra("CAR_ID", Id + "");
                                                        startActivity(i);
                                                        finish();
                                                    }

                                                }
                                            }, URL_LAST_POS);


                                        } else {
                                            Toast.makeText(RahRFIDFilterActivity.this, "کاربر به این کد ماشین دسترسی ندارد.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, URL_CARS);
                            }

                            // if select car from spinners


                        } else {

                            final String ORG_OR_CAR_ID;
                            if (isSelectCar)
                                ORG_OR_CAR_ID = String.valueOf(carIds.get(0));
                            else if (isSelectAll) {
                                StringBuilder str = new StringBuilder();
                                for (int i = 0; i < oSubTreeListSpinnerOne.get(1).getOrgModel().size(); i++) {
                                    str.append(oSubTreeListSpinnerOne.get(1).getOrgModel().get(i).getOrg_Id());
                                    str.append(",");
                                }

                                ORG_OR_CAR_ID = (str.toString()).substring(0, str.toString().lastIndexOf(","));


                            } else {
                                ORG_OR_CAR_ID = String.valueOf(lastOrgId);
                            }

                            Log.i(TAG, "last org Id that user select is: " + ORG_OR_CAR_ID);
                            final List<UserAccess> userAccesses = new ArrayList<>();
                            api.getLastPosWithId(new FilterApi.OnAllDriversBack() {
                                @Override
                                public void OnResponse(List<LastPosition> lastPositions) {
                                    int diff;

                                    if (lastPositions.size() > 0) {
                                        if (isOnlineCheck) {
                                            String Date = DateTimeUtils.changeDateFormat(DateTimeUtils.getCurrentDate());
                                            String hour = DateTimeUtils.getCurrentHour();
                                            int length = lastPositions.size();
                                            for (int i = 0; i < length; i++) {
                                                diff = Math.abs(Integer.parseInt(lastPositions.get(i).getLTime().split(":")[0]) - Integer.parseInt(hour));
                                                if (lastPositions.get(i).getLDate().equals(Date) && diff <= 2) {
                                                    UserAccess u = new UserAccess();
                                                    u.setLastPosition(lastPositions.get(i));
                                                    userAccesses.add(u);
                                                }
                                            }

                                        } else {
                                            int length = lastPositions.size();
                                            for (int i = 0; i < length; i++) {
                                                UserAccess u = new UserAccess();
                                                u.setLastPosition(lastPositions.get(i));
                                                userAccesses.add(u);
                                            }
                                        }


                                        Gson gson = new Gson();
                                        String sendUserAccess = gson.toJson(userAccesses);
                                        Intent intent = new Intent(RahRFIDFilterActivity.this, MainActivity.class);
                                        intent.putExtra("CAR_ID", ORG_OR_CAR_ID);
                                        intent.putExtra("IS_ONLINE", isOnlineCheck);
                                        intent.putExtra("MAP_FILTER", sendUserAccess);
                                        startActivity(intent);
                                        finish();
                                        isSelectAll = false;

                                    } else {
                                        Toast.makeText(RahRFIDFilterActivity.this, "متحرک مورد نظر دارای اطلاعات نمی باشد.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, Constants.URL_GET_LAST_POS_WITH_ID + ORG_OR_CAR_ID);
                        }
                    } else {


                        if (txtStartDate.getText().toString().equals("")) {
                            Toast.makeText(RahRFIDFilterActivity.this, R.string.please_enter_start_date, Toast.LENGTH_SHORT).show();
                        } else if (txtEndDate.getText().toString().equals("")) {
                            Toast.makeText(RahRFIDFilterActivity.this, R.string.please_enter_end_date, Toast.LENGTH_SHORT).show();
                        }


                        if (isCarCode) {
                            if (!edtCarCode.getText().toString().equals("")) {

                                int Id = Integer.parseInt(edtCarCode.getText().toString());
                                String URL = Constants.URL_GET_ALL_DRIVER + preferences.getPrefUserName() + "/" + Id;
                                FilterApi f = FilterApi.getInstance(RahRFIDFilterActivity.this);
                                f.getDriver(new FilterApi.OnGetUserSubTreeObject() {
                                    @Override
                                    public void onResponseUserSubTree(List<Cars> cars) {
                                        if (cars.size() > 0) {
                                            for (Cars c : cars
                                                    ) {
                                                carIds.add(c.getCarId());
                                            }
                                            positionToShow = getPositionWithId(carIds);
                                            isEnterCode = true;
                                            lastOrgId = cars.get(0).getOrg_ID();
                                            getReportAndStartActivity(lastOrgId);
                                        } else {
                                            Toast.makeText(RahRFIDFilterActivity.this, "کاربر به این کد ماشین دسترسی ندارد.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, URL);

                            }
                        } else {
                            getReportAndStartActivity(lastOrgId);
                        }


                    }
                }
            });*/


            btnStartTime.setOnClickListener(this);
            btnEndTime.setOnClickListener(this);
            btnStartDate.setOnClickListener(this);
            btnEndDate.setOnClickListener(this);
        } else {
            Util.backToLoginActivity(this);
        }
    }
    //endregion


    private void initializeView() {

        onlineCars = findViewById(R.id.online_cars_checkBox);

        dateContainer = findViewById(R.id.date_container);
        pathContainer = findViewById(R.id.path);
        filterContainer = findViewById(R.id.filter_container);

        carNumberContainer = findViewById(R.id.car_number_container);

        imgBackClick = findViewById(R.id.rah_rfid_imgView_back);
        imgBackClick.setOnClickListener(this);

        card_car_number = findViewById(R.id.card_car_number);
        chCarCode = findViewById(R.id.check_Box_code_number);
        chSpinners = findViewById(R.id.check_box_spinners);
        spinnerContainer = findViewById(R.id.spinner_container_1);
        codeContainer = findViewById(R.id.code_container);


        filterCheckBox = findViewById(R.id.rah_rfid_txt_filterTitle);

        //  car_online_check_box = findViewById(R.id.online_check_box);

        edtCarCode = findViewById(R.id.edt_car_code);
        carOnline = findViewById(R.id.car_online_container);


        containerOne = findViewById(R.id.container_one);
        containerTwo = findViewById(R.id.container_two);
        containerThree = findViewById(R.id.container_three);
        containerFour = findViewById(R.id.container_four);
        containerFive = findViewById(R.id.container_five);
        containerDriver = findViewById(R.id.container_driver);

        carCount = findViewById(R.id.carCount);
        TimeContainer = findViewById(R.id.rah_rfid_layout_Time_container);
        SpeedContainer = findViewById(R.id.rah_rfid_layout_speed_container);
        LengthContainer = findViewById(R.id.rah_rfid_layout_length_container);


        btnStartDate = findViewById(R.id.date_from);
        btnEndDate = findViewById(R.id.date_to);
        btnStartTime = findViewById(R.id.time_from);
        btnEndTime = findViewById(R.id.time_to);
        String dateWithTrueFormat = DateTimeUtils.changeDateFormat(initDate);


        txtStartDate = findViewById(R.id.rah_rfid_txt_start_date);
        txtStartDate.setText(dateWithTrueFormat);
        txtStartTime = findViewById(R.id.rah_rfid_txt_start_time);
        txtEndDate = findViewById(R.id.rah_rfid_txt_end_date);
        txtEndDate.setText(dateWithTrueFormat);
        txtEndTime = findViewById(R.id.rah_rfid_txt_end_time);

        edtSpeedEnd = findViewById(R.id.rah_rfid_edt_speed_end);
        edtSpeedStart = findViewById(R.id.rah_rfid_edt_speed_start);
        edtLengthEnd = findViewById(R.id.rah_rfid_edt_length_end);
        edtLengthStart = findViewById(R.id.rah_rfid_edt_length_start);


        userAccessOrgName = new ArrayList<>();
        acceptFilter = findViewById(R.id.Filter_Activity_fab_accept_filter);
        util.setTypeFaceButton(acceptFilter, this);
        txtTitle = findViewById(R.id.title_filter);
        util.setTypeFace(txtTitle, this);

        txtOrgName = findViewById(R.id.Filter_Activity_txt_org_name);
        spinnerIndexOne = findViewById(R.id.spinner_one);
        spinnerIndexTwo = findViewById(R.id.spinner_two);
        spinnerIndexThree = findViewById(R.id.spinner_three);
        spinnerIndexFour = findViewById(R.id.spinner_four);
        spinnerIndexFive = findViewById(R.id.spinner_five);
        spinnerIndexDriver = findViewById(R.id.spinner_driver);


        setFontForNumberFields();
        setUpSpinnerFilters();
        setUpSpinnerPath();

        spinnerFilters.setOnItemSelectedListener(new FilterFieldReport());
        pathSpinner.setOnItemSelectedListener(new PathFieldOnItemListener());
        pathSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                reportManager.callGetLineApi();
                return false;
            }
        });

        spinnerIndexOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer stateId = adapterLevelOne.getTreeItemId(position);
                if (stateId != -1) {
                    reportManager.callGetTreeItem(stateId);
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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                driverIds = new ArrayList<>();
                driverIds.add(adapterLevelTwo.getTreeItemId(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void setFontForNumberFields() {
        FontUtil.setTypeFaceNumber(this, carCount, txtStartDate, txtEndDate,
                txtStartTime, txtEndTime, edtSpeedEnd, edtSpeedStart, edtLengthEnd, edtLengthStart);
    }

    private void setUpSpinnerPath() {
        pathSpinner = findViewById(R.id.path_spinner);

    }


    private void setUpSpinnerFilters() {

        spinnerFilters = findViewById(R.id.rah_rfid_spinner_filter);
        spinnerFilters.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_item,
                this.getResources().getStringArray(R.array.Report_filters)));

    }

    @Override
    public void onClick(View v) {


        int HourStart = 0;
        int minuteStart = 0;

        switch (v.getId()) {
            case R.id.time_from:
                chooseTime(txtStartTime, TIME_START, HourStart, minuteStart);
                break;
            case R.id.time_to:
                chooseTime(txtEndTime, TIME_END, HourStart, minuteStart);
                break;
            case R.id.date_from:
                chooseDate(txtStartDate);
                break;
            case R.id.date_to:
                chooseDate(txtEndDate);
                break;
            case R.id.rah_rfid_imgView_back:
                onBackPressed();
                break;
        }

    }

    private void chooseTime(final TextView timeView, String tag, int hourStart, int minuteStart) {

        TimePickerDialog timePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

                String time = DateTimeUtils.changeTimeFormat(hourOfDay, minute);
                timeView.setText(time);
            }
        }, hourStart, minuteStart, true);
        timePicker.show(getFragmentManager(), tag);
    }

    private void chooseDate(final TextView dateView) {


        initDate.setPersianDate(YEAR, MONTH, DAY);
        PersianDatePickerDialog datePickerStart = new PersianDatePickerDialog(this)
                .setPositiveButtonString(getString(R.string.confirm))
                .setNegativeButton(getString(R.string.cancel))
                .setTodayButton(getString(R.string.today))
                .setTodayButtonVisible(true)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMinYear(1300)
                .setInitDate(initDate)
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(ir.hamsaa.persiandatepicker.util.PersianCalendar persianCalendar) {
                        String startDate = DateTimeUtils.changeDateFormat(persianCalendar);
                        dateView.setText(startDate);
                    }

                    @Override
                    public void onDismissed() {

                    }
                });
        datePickerStart.show();
    }


    private void setFilterForShowCar() {

        txtTitle.setText(R.string.Filter_desire_Car);
        dateContainer.setVisibility(View.GONE);
        filterContainer.setVisibility(View.GONE);
        pathContainer.setVisibility(View.GONE);
        carOnline.setVisibility(View.VISIBLE);
        isFromMap = true;

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private SearchReportItem setSearchItemReport() {


        if (REPORT_TYPE == ReportTypeConst.All) {
            DURATION_FROM = "00:00";
            DURATION_TO = "00:00";
            LENGTH_FROM = 0;
            LENGTH_TO = 0;
        }

        if (isCarCode) {
            driverIds = null;
            deviceCodes = new ArrayList<>();
            deviceCodes.add(Integer.parseInt(edtCarCode.getText().toString()));
            isCarCode = false;
        }

        START_DATE = DateTimeUtils.convertToGerGorian(txtStartDate.getText().toString());
        END_DATE = DateTimeUtils.convertToGerGorian(txtEndDate.getText().toString());

        return SearchReportItem.createReportFilter(driverIds,
                lineIds, START_DATE, END_DATE,
                null, REPORT_TYPE, DURATION_FROM,
                DURATION_TO, SPEED_FROM,
                SPEED_TO, LENGTH_FROM, LENGTH_TO, deviceCodes);


    }

    private void determineReportFilter() {

        switch (REPORT_TYPE) {

            case ReportTypeConst.TakhirMabada:
                if (!txtStartTime.getText().toString().equals(""))
                    DURATION_FROM = txtStartTime.getText().toString();
                if (!txtEndTime.getText().toString().equals(""))
                    DURATION_TO = txtEndTime.getText().toString();
                break;

            case ReportTypeConst.TakhirMaghsad:
                if (!txtStartTime.getText().toString().equals(""))
                    DURATION_FROM = txtStartTime.getText().toString();
                if (!txtEndTime.getText().toString().equals(""))
                    DURATION_TO = txtEndTime.getText().toString();
                break;

            case ReportTypeConst.TajilMaghsad:
                if (!txtStartTime.getText().toString().equals(""))
                    DURATION_FROM = txtStartTime.getText().toString();
                if (!txtEndTime.getText().toString().equals(""))
                    DURATION_TO = txtEndTime.getText().toString();
                break;

            case ReportTypeConst.ModatBargiri:
                if (!txtStartTime.getText().toString().equals(""))
                    DURATION_FROM = txtStartTime.getText().toString();
                if (!txtEndTime.getText().toString().equals(""))
                    DURATION_TO = txtEndTime.getText().toString();
                break;

            case ReportTypeConst.ZamanMoghararMabdaKhorooj:

                if (!txtStartTime.getText().toString().equals(""))
                    DURATION_FROM = txtStartTime.getText().toString();
                if (!txtEndTime.getText().toString().equals(""))
                    DURATION_TO = txtEndTime.getText().toString();
                break;

            case ReportTypeConst.ZamanMoghararMaghsadVorood:
                if (!txtStartTime.getText().toString().equals(""))
                    DURATION_FROM = txtStartTime.getText().toString();
                if (!txtEndTime.getText().toString().equals(""))
                    DURATION_TO = txtEndTime.getText().toString();
                break;

            case ReportTypeConst.ZamanTeyMasir:
                if (!txtStartTime.getText().toString().equals(""))
                    DURATION_FROM = txtStartTime.getText().toString();
                if (!txtEndTime.getText().toString().equals(""))
                    DURATION_TO = txtEndTime.getText().toString();
                break;
            case ReportTypeConst.AverageSpeed:
                if (!edtSpeedStart.getText().toString().equals(""))
                    SPEED_FROM = Integer.parseInt(edtSpeedStart.getText().toString());
                if (!edtSpeedEnd.getText().toString().equals(""))
                    SPEED_TO = Integer.parseInt(edtSpeedEnd.getText().toString());
                break;
            case ReportTypeConst.MaxSpeed:
                if (!edtSpeedStart.getText().toString().equals(""))
                    SPEED_FROM = Integer.parseInt(edtSpeedStart.getText().toString());
                if (!edtSpeedEnd.getText().toString().equals(""))
                    SPEED_TO = Integer.parseInt(edtSpeedEnd.getText().toString());
                break;
            case ReportTypeConst.Length:
                if (!edtLengthStart.getText().toString().equals(""))
                    LENGTH_FROM = Integer.parseInt(edtLengthStart.getText().toString());
                if (!edtLengthEnd.getText().toString().equals(""))
                    LENGTH_TO = Integer.parseInt(edtLengthEnd.getText().toString());
                break;
        }
    }

    private SearchLastPositionItem getSearchLastPositionItem() {
        return SearchLastPositionItem.createSearchLastPositionItem(driverIds, isOnlineCheck);
    }


    @Override
    public void startActivity(Bundle bundle) {
        Util.gotoActivity(this, RahRFIDReportList.class, bundle, false);
    }


    @Override
    public void fillLineData(List<String> lines) {

        this.linesNames = lines;
        if (lines != null && lines.size() > 0) {
            ArrayAdapter a = new ArrayAdapter(RahRFIDFilterActivity.this, R.layout.spinner_item, lines);
            pathSpinner.setAdapter(a);
        }

    }

    @Override
    public void fillTreeItem(List<TreeItem> treeItems) {

        if (!treeItems.isEmpty()) {
            adapterLevelOne = new SpinnerAdapter<>(RahRFIDFilterActivity.this, treeItems);
            spinnerIndexOne.setAdapter(adapterLevelOne);
        }
    }

    @Override
    public void fillCarTreeItem(List<CarTreeItem> carTreeItems) {

        if (!carTreeItems.isEmpty()) {
            containerTwo.setVisibility(View.VISIBLE);
            adapterLevelTwo = new SpinnerAdapter<>(RahRFIDFilterActivity.this, carTreeItems);
            spinnerIndexTwo.setAdapter(adapterLevelTwo);
        }


    }


    private String parseSearchLastPositionItem(SearchLastPositionItem search) {
        return new Gson().toJson(search);
    }


    //region Inner Classes
    private class AcceptClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            determineReportFilter();
            reportManager.callReportApi(reportId, setSearchItemReport());

        }
    }

    private class FilterFieldReport implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (position) {

                case 0://همه موارد
                    REPORT_TYPE = ReportTypeConst.All;
                    SpeedContainer.setVisibility(View.GONE);
                    LengthContainer.setVisibility(View.GONE);
                    TimeContainer.setVisibility(View.GONE);
                    break;

                case 1://تاخیر در خروج از میدا
                    REPORT_TYPE = ReportTypeConst.TakhirMabada;
                    SpeedContainer.setVisibility(View.GONE);
                    LengthContainer.setVisibility(View.GONE);
                    TimeContainer.setVisibility(View.VISIBLE);

                    break;
                case 2://تاخیر در ورود به مقصد
                    REPORT_TYPE = ReportTypeConst.TakhirMaghsad;
                    SpeedContainer.setVisibility(View.GONE);
                    LengthContainer.setVisibility(View.GONE);
                    TimeContainer.setVisibility(View.VISIBLE);

                    break;
                case 3://تعجیل در ورود به مقصد
                    REPORT_TYPE = ReportTypeConst.TajilMaghsad;
                    SpeedContainer.setVisibility(View.GONE);
                    LengthContainer.setVisibility(View.GONE);
                    TimeContainer.setVisibility(View.VISIBLE);


                    break;
                case 4://براساس مدت بارگیری
                    REPORT_TYPE = ReportTypeConst.ModatBargiri;
                    SpeedContainer.setVisibility(View.GONE);
                    LengthContainer.setVisibility(View.GONE);
                    TimeContainer.setVisibility(View.VISIBLE);


                    break;
                case 5://براساس زمان مقرر خروج از مبدا
                    REPORT_TYPE = ReportTypeConst.ZamanMoghararMabdaKhorooj;
                    SpeedContainer.setVisibility(View.GONE);
                    LengthContainer.setVisibility(View.GONE);
                    TimeContainer.setVisibility(View.VISIBLE);


                    break;
                case 6://براساس زمان مقرر ورود به مقصد
                    REPORT_TYPE = ReportTypeConst.ZamanMoghararMaghsadVorood;
                    SpeedContainer.setVisibility(View.GONE);
                    LengthContainer.setVisibility(View.GONE);
                    TimeContainer.setVisibility(View.VISIBLE);

                    break;
                case 7://براساس زمان طی مسیر
                    REPORT_TYPE = ReportTypeConst.ZamanTeyMasir;
                    SpeedContainer.setVisibility(View.GONE);
                    LengthContainer.setVisibility(View.GONE);
                    TimeContainer.setVisibility(View.VISIBLE);
                    break;
                case 8://براساس سرعت میانگین
                    REPORT_TYPE = ReportTypeConst.AverageSpeed;
                    SpeedContainer.setVisibility(View.VISIBLE);
                    LengthContainer.setVisibility(View.GONE);
                    TimeContainer.setVisibility(View.GONE);

                    break;
                case 9://براساس تخلف سرعت
                    REPORT_TYPE = ReportTypeConst.MaxSpeed;
                    SpeedContainer.setVisibility(View.VISIBLE);
                    LengthContainer.setVisibility(View.GONE);
                    TimeContainer.setVisibility(View.GONE);

                    break;
                case 10://براساس مسافت طی شده
                    REPORT_TYPE = ReportTypeConst.Length;
                    SpeedContainer.setVisibility(View.GONE);
                    LengthContainer.setVisibility(View.VISIBLE);
                    TimeContainer.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class PathFieldOnItemListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            lineIds = new ArrayList<>();
            if (position != 0) {
                String name = linesNames.get(position);
                lineIds.add((int) reportManager.setLineIdWithName(name));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class AcceptForMapClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString(MainActivity.MAP_FILTER, parseSearchLastPositionItem(getSearchLastPositionItem()));
            Util.gotoActivity(RahRFIDFilterActivity.this, MainActivity.class, bundle, false);
        }
    }
    //endregion
}
