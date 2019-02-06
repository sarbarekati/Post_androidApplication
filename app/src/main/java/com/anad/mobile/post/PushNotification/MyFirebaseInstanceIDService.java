package com.anad.mobile.post.PushNotification;

import android.app.Service;
import android.util.Log;

import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by elias.mohammadi on 96.11.25
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseInstanceIDSer";
    PostSharedPreferences preferences;
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        preferences = new PostSharedPreferences(getApplicationContext());
        preferences.setToken(refreshToken);
        Log.d(TAG, "onTokenRefresh: "+ refreshToken);
    }
}
