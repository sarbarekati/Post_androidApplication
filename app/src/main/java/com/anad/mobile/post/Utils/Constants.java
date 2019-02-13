package com.anad.mobile.post.Utils;

/*
 * CREATE BY ELIAS MOHAMMADI 96.9.12
 **/


import com.anad.mobile.post.BuildConfig;

/**
 * for create constants such as url
 **/
public class Constants {



    static {
        System.loadLibrary("keys");
    }

    public static native String getNativeApiKey();

    public static native String getNativeKey();

    public static native String getMapServerAddress();

    //---------------------------- Constants ---------------------------------------\\
    public static final String API_KEY = getNativeApiKey();
    public static final String KEY = getNativeKey();
    public static final String LABEL_API_KEY = "API_KEY";
    public static final String AUTHORIZATION = "Authorization";
    private static final String USER_LOGIN_END_POINT = "api/userlogin/";
    private static final String DASHBOARD_END_POINT = "api/dashboard/";
    public static final String URL_LABEL = "URL";
    public static final String WEBSERVICE_TYPE_LABEL = "WebServiceType";
    public static final java.lang.String PIE_CHART_CENTER_TEXT = "pie_chart_center_text";
    public static final int MOBILE_USER_CODE = 40126;


    // ----------------------------------------- URLS -------------------------------- \\


    public static final String URL_REGISTER = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "AddNewUser";

    public static final String URL_LOGIN = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "Login";
//    public static final String URL_LOGIN = "http://130.185.78.110:8090/" + USER_LOGIN_END_POINT + "Login";
    public static final String URL_GET_USER_BY_USER_NAME = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "GetByUserName";
    public static final String URL_GET_USER_CHECK_ACTIVE = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "LoginByUserNamePassword";
//    public static final String URL_GET_USER_CHECK_ACTIVE = "http://130.185.78.110:8090/" + USER_LOGIN_END_POINT + "LoginByUserNamePassword";
    public static final String URL_SEND_FORGET_PASSWORD = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "SendMessage";
    public static final String URL_SEND_FORGET_PASSWORD_USERNAME = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "SendPasswordByUserName";
    public static final String URL_SEND_FORGET_PASSWORD_MOBILE = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "SendPasswordByMobile";


    //TO GET FIRST USER ACCESS WITH USERNAME
    public static final String URL_GET_USER_ACCESS_BY_USER_NAME = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "UserAccessByUserName/"; // User_name

    //TO GET ORGs LEVELS 2 ~ 5
    public static final String URL_GET_SUB_TREE = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "UserSubTree/"; //Parent_ID


    //TO GET DRIVER WITH ONLY CAR ID
    public static final String URL_GET_USER_ALL_DRIVER = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "getuserdrivers?orgId=";

    // TO GET LAST POSITION WITH ORG ID OR CAR ID

    public static final String URL_GET_LAST_POS_WITH_ID = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "GetLastPosWithId?idFromAndroid=";//orgId or carId


    public static final String URL_GET_LAST_POSITION = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "CarInformation/"; //Drv_ID


    public static final String URL_GET_LIST_SUB_TREE = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "GetSubTree/";//userName
    public static final String URL_GET_USER_ORG_BY_PARENT = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "GetUserOrgSubTree/";// parent id


    public static final String URL_GET_RAH_REPORT = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "GetRahReport";
    public static final String URL_GET_RFID_REPORT = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "GetRahRFIDReport";
    public static final String URL_GET_ROUT = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "GetCarRoute";

    public static final String URL_GET_ALL_DRIVER_LAST_POS = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "GetAllDriversLastPos/";

    // TO GET DRIVERS WITH USERNAME AND ORG ID
    public static final String URL_GET_ALL_DRIVER = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "GetAllDrivers/";

    public static final String URL_GET_ALL_Route = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "GetAllRoute/";
    public static final String URL_GET_USER_ALARMS = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "UserAllAlarm";//?userName=user100&skip=10&count=10
    public static final String URL_ALARM_SETTING = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "SettingSetup";// AlarmSetting
    public static final String URL_CHANGE_ALARM_FIELD = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "ChangeUserAlarmField";//UserAlarmInfo

    public static final String URL_UPDATE_APPLICATION = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "GetNewApplicationVersion";

    public static final String URL_CHANGE_TOKEN = BuildConfig.BASE_URL + USER_LOGIN_END_POINT + "ChangeUserToken";

    //Dashboard

    public static final String URL_ALL_DEVICE_TODAY = BuildConfig.BASE_URL + DASHBOARD_END_POINT + "GetAllDeviceToday?date=";//date=1397/08/15
    public static final String URL_ALL_FORM_TODAY = BuildConfig.BASE_URL + DASHBOARD_END_POINT + "GetAllFormToday?date=";//date=1397/08/15
    public static final String URL_ALL_ROUTE_IN_DAY = BuildConfig.BASE_URL + DASHBOARD_END_POINT + "GetAllRouteInDay?date=";//date=1397/08/15
    public static final String URL_COUNT_OF_ORG_DEVICE = BuildConfig.BASE_URL + DASHBOARD_END_POINT + "GetCountOfOrgDevice";
    public static final String URL_COUNT_OF_ONLINE_DEVICE = BuildConfig.BASE_URL + DASHBOARD_END_POINT + "GetCountOfOnlineDevice?";//date=1397/08/15&time=00:00
    public static final String URL_COUNT_OF_OPEN_DOOR = BuildConfig.BASE_URL + DASHBOARD_END_POINT + "GetCountOfOpenDoor?year=";//year=1396
    public static final String URL_COUNT_OF_RAH_FORM = BuildConfig.BASE_URL + DASHBOARD_END_POINT + "GetCountOfRahForm?year=";//year=1396
    public static final String URL_COUNT_OF_RAH_FORM_ROUTE = BuildConfig.BASE_URL + DASHBOARD_END_POINT + "GetCountOfRahFormRoute?year=";//year=1396
    public static final String URL_COUNT_OF_RFID_FORM = BuildConfig.BASE_URL + DASHBOARD_END_POINT + "GetCountOfRFIDFrom?year=";//year=1396
    public static final String URL_HIGH_SPEED_ORG = BuildConfig.BASE_URL + DASHBOARD_END_POINT + "GetHighSpeedOfOrg?year=";//year=1396
    public static final String URL_SUM_OF_ORG_LEN = BuildConfig.BASE_URL + DASHBOARD_END_POINT + "GetSumOfOrgLen?year=";//year=1396
    public static final String URL_HIGH_SPEED_ROUTE = BuildConfig.BASE_URL + DASHBOARD_END_POINT + "GetHighSpeedOfRoute?year=";//year=1396


    //---- MAP URL ---
    public static final String URL_WMS = getMapServerAddress();

    //----- New Login EndPoint ----\\
    public static final String LOGIN_POST_FW = BuildConfig.BASE_URL + "visionauth/login?";
    public static final String RAHSEPARI_REPORT_URL = BuildConfig.BASE_URL + "Main/RahsepariReport/MobileApiGetAll";
    public static final String GET_ROLE_URL = BuildConfig.BASE_URL + "main/partyAssign/getbypartyId?partyId=";

}
