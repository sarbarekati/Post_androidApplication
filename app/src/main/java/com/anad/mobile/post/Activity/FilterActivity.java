package com.anad.mobile.post.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.anad.mobile.post.Models.Cars;
import com.anad.mobile.post.Models.HandleSubTree;
import com.anad.mobile.post.Models.LastPosition;
import com.anad.mobile.post.Models.Org;
import com.anad.mobile.post.Models.SubTree;
import com.anad.mobile.post.Models.UserAccess;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;
import java.util.List;


public class FilterActivity extends AppCompatActivity {
    private AppCompatSpinner spinnerIndexOne, spinnerIndexTwo, spinnerIndexThree, spinnerIndexFour, spinnerIndexFive, spinnerIndexDriver;
    private TextView txtTitle, txtOrgName;
    private int lastIndexSpinnerOne, lastIndexSpinnerTwo, lastIndexSpinnerThree, lastIndexSpinnerFour, lastIndexSpinnerFive, lastIndexSpinnerDriver;
    private FloatingActionButton acceptFilter;
    private HandleSubTree handleSubTree;
    private static final String TAG = "FilterActivity";
    private List<String> userAccessOrgName;
    private List<SubTree> subTreeListSpinnerOne, subTreeListSpinnerTwo, subTreeListSpinnerThree, subTreeListSpinnerFour, subTreeListSpinnerFive;
    private List<Org> oSubTreeListSpinnerOne, oSubTreeListSpinnerTwo, oSubTreeListSpinnerThree, oSubTreeListSpinnerFour, oSubTreeListSpinnerFive;
    private List<Cars> subTreeListSpinnerDriver;
    private Util util;
    private PostSharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new PostSharedPreferences(this);
        if (Util.authenticateUser(preferences)) {


            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_filter);
            util = Util.getInstance();
            initializeView();

            handleSubTree = HandleSubTree.getInstance(this);

            acceptFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastIndexSpinnerOne = spinnerIndexOne.getLastVisiblePosition();
                    lastIndexSpinnerTwo = spinnerIndexTwo.getLastVisiblePosition();
                    lastIndexSpinnerThree = spinnerIndexThree.getLastVisiblePosition();
                    lastIndexSpinnerFour = spinnerIndexFour.getLastVisiblePosition();
                    lastIndexSpinnerFive = spinnerIndexFive.getLastVisiblePosition();
                    lastIndexSpinnerDriver = spinnerIndexDriver.getLastVisiblePosition();


                    final UserAccess userAccess = new UserAccess();
                    userAccess.setOrg_Name(subTreeListSpinnerTwo.get(lastIndexSpinnerTwo).getOrg().getOrg_name());
                    final Cars car = subTreeListSpinnerDriver.get(lastIndexSpinnerDriver);
                    int org_id = car.getCarId();
                    handleSubTree.getLastPositionOfCar(new HandleSubTree.OnGetLastPositionCar() {
                        @Override
                        public void onGetLastPosition(LastPosition lastPosition) {

                            car.setLastPosition(lastPosition);
                            userAccess.setCars(car);
                            String lat = userAccess.getCars().getLastPosition().getN();
                            String lng = userAccess.getCars().getLastPosition().getE();
                            LatLng carLatLng = util.createLatLng(lat, lng);
                            userAccess.setLat(carLatLng.latitude);
                            userAccess.setLng(carLatLng.longitude);
                            Log.i(TAG, "onClick: user access is : "
                                    + lng + " -- " + lat + userAccess.getCars().getLastPosition().getSpeed()
                            );
                            Log.i(TAG, "onGetLastPosition: your position lat is :" + carLatLng.latitude + " and your position lng is :" + carLatLng.longitude);
                            Intent i = new Intent(FilterActivity.this, MainActivity.class);
                            // i.putExtra("LAT", carLatLng.latitude);
                            //  i.putExtra("LNG", carLatLng.longitude);
                            util.setUserAccess(userAccess);
                            i.putExtra("TAG", 1);
                            startActivity(i);
                            finish();


                        }
                    }, org_id);


                }
            });

            handleSubTree.getOrgHandler(Constants.URL_GET_LIST_SUB_TREE + preferences.getPrefUserName(), new HandleSubTree.OnGetOrgHandler() {
                @Override
                public void onGetOrg(List<Org> orgs) {
                    Log.i(TAG, "onGetOrg: org size : " + orgs.size());
                    for (Org org : orgs) {
                        userAccessOrgName.add(org.getOrg_name());
                    }
                    ArrayAdapter sAdapter = new ArrayAdapter(FilterActivity.this, R.layout.spinner_item, userAccessOrgName);
                    spinnerIndexOne.setAdapter(sAdapter);
                    setUpSpinners();

                }
            }, new HandleSubTree.OnGetSubHandler() {
                @Override
                public void onGetSubHandler(List<SubTree> subTrees) {
                    subTreeListSpinnerOne = subTrees;
                }
            });



       /* handleSubTree.getUserAccessByUserNameOrParentIdHandler(Constants.URL_GET_USER_ACCESS_BY_USER_NAME + preferences.getPrefUserName(), new HandleSubTree.OnGetOrgHandler() {
            @Override
            public void onGetOrg(List<Org> orgs) {
                if(orgs.size()>0)
                {
                    for (Org org:orgs
                         ) {
                        userAccessOrgName.add(org.getOrg_name());
                    }

                    ArrayAdapter sAdapter = new ArrayAdapter(FilterActivity.this, R.layout.spinner_item, userAccessOrgName);
                    spinnerIndexOne.setAdapter(sAdapter);
                    oSubTreeListSpinnerOne = orgs;
                    setUpSpinners();
                }
            }
        });*/


        }else{
            Util.backToLoginActivity(this);
        }
    }







    private void setUpSpinners() {
        spinnerIndexOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                userAccessOrgName = new ArrayList<>();

                int parentId = subTreeListSpinnerOne.get(position).getParentId();
                handleSubTree.getUserOrgSubTree(new HandleSubTree.OnGetSubHandler() {
                    @Override
                    public void onGetSubHandler(List<SubTree> subTrees) {
                        Log.i(TAG, "onGetSubHandler: " + subTrees.size());
                        if (subTrees.size() > 0) {
                            subTreeListSpinnerTwo = subTrees;
                            for (int i = 0; i < subTrees.size(); i++) {
                                userAccessOrgName.add(subTrees.get(i).getOrg().getOrg_name());
                            }
                            spinnerIndexTwo.setVisibility(View.VISIBLE);
                            ArrayAdapter seAdapter = new ArrayAdapter(FilterActivity.this, R.layout.spinner_item, userAccessOrgName);
                            spinnerIndexTwo.setAdapter(seAdapter);
                        } else {
                            spinnerIndexTwo.setVisibility(View.GONE);
                            spinnerIndexDriver.setVisibility(View.VISIBLE);
                            ArrayAdapter seAdapter = new ArrayAdapter(FilterActivity.this, R.layout.spinner_item, getDriver(subTreeListSpinnerOne, position));
                            spinnerIndexDriver.setAdapter(seAdapter);

                        }

                    }
                }, parentId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerIndexTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, final long id) {
                userAccessOrgName = new ArrayList<>();

                int parentId = subTreeListSpinnerTwo.get(position).getOrg().getOrg_Id();
                handleSubTree.getUserOrgSubTree(new HandleSubTree.OnGetSubHandler() {
                    @Override
                    public void onGetSubHandler(List<SubTree> subTrees) {
                        Log.i(TAG, "onGetSubHandler: " + subTrees.size());
                        if (subTrees.size() > 0) {
                            subTreeListSpinnerThree = subTrees;
                            for (int i = 0; i < subTrees.size(); i++) {
                                userAccessOrgName.add(subTrees.get(i).getOrg().getOrg_name());
                            }
                            spinnerIndexThree.setVisibility(View.VISIBLE);
                            ArrayAdapter seAdapter = new ArrayAdapter(FilterActivity.this, R.layout.spinner_item, userAccessOrgName);
                            spinnerIndexThree.setAdapter(seAdapter);
                        } else {
                            spinnerIndexThree.setVisibility(View.GONE);
                            spinnerIndexDriver.setVisibility(View.VISIBLE);
                            ArrayAdapter seAdapter = new ArrayAdapter(FilterActivity.this, R.layout.spinner_item, getDriver(subTreeListSpinnerTwo, position));
                            spinnerIndexDriver.setAdapter(seAdapter);

                        }

                    }
                }, parentId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerIndexThree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, final long id) {
                userAccessOrgName = new ArrayList<>();

                int parentId = subTreeListSpinnerThree.get(position).getOrg().getOrg_Id();
                handleSubTree.getUserOrgSubTree(new HandleSubTree.OnGetSubHandler() {
                    @Override
                    public void onGetSubHandler(List<SubTree> subTrees) {
                        Log.i(TAG, "onGetSubHandler: " + subTrees.size());
                        if (subTrees.size() > 0) {
                            subTreeListSpinnerFour = subTrees;
                            for (int i = 0; i < subTrees.size(); i++) {
                                userAccessOrgName.add(subTrees.get(i).getOrg().getOrg_name());
                            }
                            spinnerIndexFour.setVisibility(View.VISIBLE);
                            ArrayAdapter seAdapter = new ArrayAdapter(FilterActivity.this, R.layout.spinner_item, userAccessOrgName);
                            spinnerIndexFour.setAdapter(seAdapter);
                        } else {
                            spinnerIndexFour.setVisibility(View.GONE);
                            spinnerIndexDriver.setVisibility(View.VISIBLE);
                            ArrayAdapter seAdapter = new ArrayAdapter(FilterActivity.this, R.layout.spinner_item, getDriver(subTreeListSpinnerThree, position));
                            spinnerIndexDriver.setAdapter(seAdapter);

                        }

                    }
                }, parentId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerIndexFour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, final long id) {
                userAccessOrgName = new ArrayList<>();

                int parentId = subTreeListSpinnerFour.get(position).getOrg().getOrg_Id();
                handleSubTree.getUserOrgSubTree(new HandleSubTree.OnGetSubHandler() {
                    @Override
                    public void onGetSubHandler(List<SubTree> subTrees) {
                        Log.i(TAG, "onGetSubHandler: " + subTrees.size());
                        if (subTrees.size() > 0) {
                            subTreeListSpinnerFive = subTrees;
                            for (int i = 0; i < subTrees.size(); i++) {
                                userAccessOrgName.add(subTrees.get(i).getOrg().getOrg_name());
                            }
                            spinnerIndexFive.setVisibility(View.VISIBLE);
                            ArrayAdapter seAdapter = new ArrayAdapter(FilterActivity.this, R.layout.spinner_item, userAccessOrgName);
                            spinnerIndexFive.setAdapter(seAdapter);
                        } else {
                            spinnerIndexFive.setVisibility(View.GONE);
                            spinnerIndexDriver.setVisibility(View.VISIBLE);
                            ArrayAdapter seAdapter = new ArrayAdapter(FilterActivity.this, R.layout.spinner_item, getDriver(subTreeListSpinnerFour, position));
                            spinnerIndexDriver.setAdapter(seAdapter);

                        }

                    }
                }, parentId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerIndexFive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, final long id) {
                userAccessOrgName = new ArrayList<>();

                int parentId = subTreeListSpinnerFive.get(position).getOrg().getOrg_Id();
                handleSubTree.getUserOrgSubTree(new HandleSubTree.OnGetSubHandler() {
                    @Override
                    public void onGetSubHandler(List<SubTree> subTrees) {
                        Log.i(TAG, "onGetSubHandler: " + subTrees.size());
                        spinnerIndexDriver.setVisibility(View.VISIBLE);
                        ArrayAdapter seAdapter = new ArrayAdapter(FilterActivity.this, R.layout.spinner_item, getDriver(subTreeListSpinnerFive, position));
                        spinnerIndexDriver.setAdapter(seAdapter);

                    }
                }, parentId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initializeView() {

        userAccessOrgName = new ArrayList<>();
        acceptFilter = findViewById(R.id.Filter_Activity_fab_accept_filter);
        txtTitle = findViewById(R.id.Filter_Activity_txt_title);
        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey("MAP_FILTER")) {
            txtTitle.setText(R.string.Filter_desire_Car);
        }
        txtOrgName = findViewById(R.id.Filter_Activity_txt_org_name);
        spinnerIndexOne = findViewById(R.id.Filter_Activity_spinner_index_1);
        spinnerIndexTwo = findViewById(R.id.Filter_Activity_spinner_index_2);
        spinnerIndexThree = findViewById(R.id.Filter_Activity_spinner_index_3);
        spinnerIndexFour = findViewById(R.id.Filter_Activity_spinner_index_4);
        spinnerIndexFive = findViewById(R.id.Filter_Activity_spinner_index_5);
        spinnerIndexDriver = findViewById(R.id.Filter_Activity_spinner_driver);
    }


    private List<String> getDriver(List<SubTree> subTrees, int position) {
        List<String> driverName = new ArrayList<>();
        subTreeListSpinnerDriver = subTrees.get(position).getCars();
        for (int i = 0; i < subTreeListSpinnerDriver.size(); i++) {
            driverName.add(subTreeListSpinnerDriver.get(i).getDriverName() + " - " + subTreeListSpinnerDriver.get(i).getDriverFamilyName() + " - " + subTreeListSpinnerDriver.get(i).getCarId());
        }
        return driverName;

    }

}
