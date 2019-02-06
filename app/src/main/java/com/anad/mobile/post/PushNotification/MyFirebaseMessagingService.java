package com.anad.mobile.post.PushNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.anad.mobile.post.Activity.MainActivity;
import com.anad.mobile.post.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Random;
import java.security.SecureRandom;

/**
 * Created by elias.mohammadi on 96.11.25
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFMService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Notification is from: " + remoteMessage.getFrom());
        if(remoteMessage.getNotification() != null){
            Log.i(TAG, "Notification is: " + remoteMessage.getNotification());

        }
        if(remoteMessage.getData().size()>0)
        {
            Log.d(TAG, "Data payload: " + remoteMessage.getData());

            JSONObject jsonObject = new JSONObject(remoteMessage.getData());
            Log.e("Tag",remoteMessage.getData().toString());


            sendNotification(remoteMessage.getData().toString());
            
        }
    }

    private void sendNotification(String msg) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, new SecureRandom().nextInt(100) , intent,
                PendingIntent.FLAG_ONE_SHOT);
        long when = System.currentTimeMillis();
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this);
        mNotifyBuilder.setVibrate(new long[] { 1000, 1000,1000,1000,1000,1000});
        boolean lollipop = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        if (lollipop) {

            mNotifyBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.app_name))
                    .setStyle(
                            new NotificationCompat.BigTextStyle()
                                    .bigText(msg))
                    .setSmallIcon(R.drawable.anad_logo)
                    .setContentText(msg)
                    .setColor(Color.TRANSPARENT)


                    .setWhen(when).setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

        } else {

            mNotifyBuilder = new NotificationCompat.Builder(this)
                    .setStyle(
                            new NotificationCompat.BigTextStyle()
                                    .bigText(msg))
                    .setContentTitle(getString(R.string.app_name)).setContentText(msg)
                    .setSmallIcon(R.drawable.anad_logo)
                    .setWhen(when).setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

        }


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

        notificationManager.notify(new SecureRandom().nextInt(100) /* ID of notification */, mNotifyBuilder.build());
    }


}
