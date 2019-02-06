package com.anad.mobile.post.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.anad.mobile.post.API.FilterApi;
import com.anad.mobile.post.API.WebApi;
import com.anad.mobile.post.Activity.FilterActivity;
import com.anad.mobile.post.Models.ActivityName;
import com.anad.mobile.post.Models.Cars;
import com.anad.mobile.post.Models.LastPosition;
import com.anad.mobile.post.Models.LoginModel;
import com.anad.mobile.post.Models.Rah_RT;
import com.anad.mobile.post.Models.User;
import com.anad.mobile.post.Models.UserAccess;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CREATE ELIAS MOHAMMADY 96.09.28
 */

public class SingletonApi {
    private WebApi webApi;
    private Context context;
    private List<LastPosition> allLastPositions;
    private List<Integer> car_Id;
    private List<String> rah_name;
    private static final String TAG = "SingletonApi";
    private PostSharedPreferences preferences;


    private SingletonApi(Context context) {
        this.context = context;
        preferences = new PostSharedPreferences(context);
       // webApi = new WebApi(context,Util.EncryptUsernamePassword(preferences.getPrefUserName(),preferences.getPrefPassword()));
        webApi = new WebApi(context,preferences.getEncode());
    }

    private static SingletonApi object = null;

    public static SingletonApi getInstance(Context mContext) {
        if (object == null)
            object = new SingletonApi(mContext);
        return object;
    }


    public void canRegister(final User user, ActivityName MODE, final OnCanRegisterBack onCanRegisterBack) {
        switch (MODE) {
            case REGISTERACTIVITY:
                String GET_USER_URL = Constants.URL_GET_USER_BY_USER_NAME + "/" + user.getUserName();
                webApi.GetUserByUsername(new WebApi.CheckUserExists() {
                    @Override
                    public void onResponseBack(Boolean haveAnyMember) {
                        Log.i(TAG, "onResponseBack-Have Any Member: " + haveAnyMember);
                        if (haveAnyMember) {
                            onCanRegisterBack.onResponseBack(true);
                        } else {
                            Toast.makeText(context, R.string.Can_not_register_with_this_user_name, Toast.LENGTH_SHORT).show();
                            onCanRegisterBack.onResponseBack(false);
                        }

                    }
                }, GET_USER_URL);
                break;
            case REGISTERACTIVITY_NEXT:
                webApi.addUser(new WebApi.OnCheckRegister() {

                    @Override
                    public void onResponseBack(boolean canRegister) {
                        Log.i(TAG, "onResponseBack-Can Register: " + canRegister);
                        if (!canRegister) {

                            Toast.makeText(context, R.string.Can_not_register_with_this_phone_number, Toast.LENGTH_SHORT).show();
                            onCanRegisterBack.onResponseBack(false);
                        } else {
                            Log.i(TAG, "onResponseBack - Add Now: " + "New User Added");
                            onCanRegisterBack.onResponseBack(true);

                        }
                    }
                }, Constants.URL_REGISTER, user);
                break;
        }


    }

    public void checkLogin(String URL, LoginModel loginModel, final CanLogin canLogin) {

        webApi.loginUser(new WebApi.OnLoginResponse() {
            @Override
            public void onResponseBack(boolean loginAnswer) {
                if (loginAnswer) {
                    Log.i(TAG, "onResponseBack: " + "YOU LOGGED IN");
                    canLogin.onResponseBack(true);
                } else {
                    Log.i(TAG, "onResponseBack: " + "YOU CAN NOT LOGGED IN");
                    canLogin.onResponseBack(false);
                }
            }
        }, loginModel,URL);
    }

    public void checkLoginWithState(String userName,String password,String registerCode, final CheckState checkState)
    {
        LoginModel loginModel = new LoginModel();
        loginModel.setUsername(userName);
        loginModel.setPassword(password);
        loginModel.setRegCode(registerCode);

        webApi.loginUserCheck(new WebApi.OnCheckLogin() {
            @Override
            public void onResponseBack(Map<String, String> loginCheck) {

                checkState.onResponseString(loginCheck);

            }
        }, new WebApi.OnErrorLogin() {
            @Override
            public void onResponseBack(Boolean b) {
                if(!b)
                {
                    Map<String,String> m = new HashMap<>();
                    m.put("STATE","نام کاربری یا رمز عبور را اشتباه وارد کرده اید.");
                    checkState.onResponseString(m);
                }

            }
        }, Constants.URL_GET_USER_CHECK_ACTIVE,loginModel);
    }



    public void getAllDriversCode(final OnCarIdBack onCarIdBack,int carCode) {
        car_Id = new ArrayList<>();
        String URL = Constants.URL_GET_ALL_DRIVER + preferences.getPrefUserName() + "/"+ carCode;
        FilterApi f = FilterApi.getInstance(context);
        f.getDriver(new FilterApi.OnGetUserSubTreeObject() {
            @Override
            public void onResponseUserSubTree(List<Cars> cars) {
                if (cars.size() > 0) {
                    for (Cars c : cars
                            ) {
                        car_Id.add(c.getCarId());
                    }
                }
                onCarIdBack.OnResponseBack(car_Id);
            }
        }, URL);
    }

    public void getRoutesName(final OnRouteNameBack onRouteNameBack) {
        rah_name = new ArrayList<>();
        String url = Constants.URL_GET_ALL_Route + preferences.getPrefUserName();
        FilterApi f = FilterApi.getInstance(context);
        f.getRoute(new FilterApi.OnRouteBack() {
            @Override
            public void OnResponse(List<Rah_RT> routs) {
                if (routs.size() > 0) {
                    for (Rah_RT r :
                            routs) {
                        rah_name.add(r.getR_Name());
                    }
                }
                onRouteNameBack.OnResponseBack(rah_name);

            }
        }, url);
    }

    public void getAllDriversLastPos(final OnAllDriverLastPosBack onAllDriverLastPosBack,int carId) {
      allLastPositions = new ArrayList<>();
        String url = Constants.URL_GET_ALL_DRIVER_LAST_POS + preferences.getPrefUserName() + "/"+carId;
        FilterApi f = FilterApi.getInstance(context);
        f.getAllDriverLastPos(new FilterApi.OnAllDriversBack() {
            @Override
            public void OnResponse(List<LastPosition> lastPositions) {
                if (lastPositions.size() > 0) {
                    allLastPositions = lastPositions;
                }
                onAllDriverLastPosBack.OnResponseBack(allLastPositions);
            }
        }, url);
    }




    public interface OnCanRegisterBack {
        void onResponseBack(boolean canRegister);
    }

    public interface CanLogin {
        void onResponseBack(boolean canLogin);
    }

    public interface CheckState
    {
        void onResponseString(Map<String,String> checkState);
    }

    public interface  BackListOfSubTree
    {
        void onResponseList(List<UserAccess> userList);
    }

    public interface OnCarIdBack
    {
        void OnResponseBack(List<Integer> carId);
    }
    public interface OnRouteNameBack
    {
        void OnResponseBack(List<String> routeName);
    }
    public interface OnAllDriverLastPosBack
    {
        void OnResponseBack(List<LastPosition> allLastPositions);
    }

}
