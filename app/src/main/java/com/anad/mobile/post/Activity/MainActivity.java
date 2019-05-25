package com.anad.mobile.post.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.widget.TextView;

import com.anad.mobile.post.Fragment.AlarmFragment;
import com.anad.mobile.post.Fragment.DashboardFragment;
import com.anad.mobile.post.Fragment.OsmMapFragment;
import com.anad.mobile.post.Fragment.ProfileFragment;
import com.anad.mobile.post.Fragment.ReportFragment;
import com.anad.mobile.post.MapManager.Manager.MapManager;
import com.anad.mobile.post.MapManager.Model.IMapView;
import com.anad.mobile.post.MapManager.Model.LastPosition;
import com.anad.mobile.post.MapManager.Model.SearchLastPositionItem;
import com.anad.mobile.post.Models.UserAccess;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostDataBase;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.SingletonApi;
import com.anad.mobile.post.Utils.Util;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements IMapView {

    public static final String MAP_FILTER = "MAP_FILTER";
    private int exitEnter = 0;
    private Util util;
    BottomNavigationView bottomNavigationView;
    Toolbar mainToolbar;
    DrawerLayout mainDrawer;
    private SingletonApi singletonApi;

    public static List<String> rah_name;
    public static List<Integer> car_Id;
    private PostDataBase postDataBase;
    private TextView appTitle;

    AHBottomNavigationItem itemMap, itemReport, itemMore, itemProfile, itemAlarms;
    public static Context mainActivityContext;
    private PostSharedPreferences preferences;


    private MapManager mapManager;
    private List<LastPosition> carPositions;
    private SearchLastPositionItem search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new PostSharedPreferences(this);
        mapManager = new MapManager(this,this);
        if (Util.authenticateUser(preferences)) {

            setContentView(R.layout.activity_main);
            mainActivityContext = MainActivity.this;
            setUpToolBar();
            util = Util.getInstance();




            bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.Main_bottom_nav_map:
                            Bundle b = getIntent().getExtras();
                            if (b != null && b.containsKey(MAP_FILTER)) {
                                search = getSearchLastPositionItem(b.getString(MAP_FILTER));
                                mapManager.callLastPosition(search);



                            } else {
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.Main_frameLayout_fragment_container, new OsmMapFragment())
                                        .commit();
                                }
                            break;
                        case R.id.Main_bottom_nav_report:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.Main_frameLayout_fragment_container, new ReportFragment())
                                    .commit();
                            break;
//                        case R.id.Main_bottom_nav_alarms:
//                            getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.Main_frameLayout_fragment_container, new AlarmFragment())
//                                    .commit();
//                            break;
                    /*case R.id.Main_bottom_nav_more:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.Main_frameLayout_fragment_container, new MoreFragment())
                                .commit();
                        break;*/

                        case R.id.Main_bottom_nav_Dashboard:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.Main_frameLayout_fragment_container,new DashboardFragment())
                                    .commit();
                        break;

                        case R.id.Main_bottom_nav_profile:
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.Main_frameLayout_fragment_container, new ProfileFragment())
                                    .commit();
                            break;


                    }
                    return true;
                }
            });

            bottomNavigationView.setSelectedItemId(R.id.Main_bottom_nav_map);
            appTitle = findViewById(R.id.app_title);
            util.setTypeFace(appTitle, this);
        }
        else
        {
            Util.backToLoginActivity(this);
        }
    }

    private SearchLastPositionItem getSearchLastPositionItem(String str) {
        return new Gson().fromJson(str,SearchLastPositionItem.class);
    }

    private void setUpToolBar() {
        mainToolbar = findViewById(R.id.MainActivity_toolbar_main);
        mainDrawer = findViewById(R.id.Main_drawer_layout);
    }


    @Override
    public void onBackPressed() {
        exitEnter++;
        if (exitEnter > 1)
            super.onBackPressed();
        else util.showToast(this, getString(R.string.please_enter_back_again));

    }


    @Override
    public void fillLastPosition(List<LastPosition> lastPositions) {
        carPositions = new ArrayList<>();
        if(lastPositions!=null && !lastPositions.isEmpty()){
            carPositions = lastPositions;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Main_frameLayout_fragment_container,
                        OsmMapFragment.newInstance(carPositions,search.isActive(),search))
                .commit();
    }
}
