package com.anad.mobile.post.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.anad.mobile.post.API.WebApi;
import com.anad.mobile.post.Models.UpdateModel;
import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Constants;
import com.anad.mobile.post.Utils.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.N;

public class Splash extends AppCompatActivity {



    private Util util;
    boolean isConnected;
    private FloatingActionButton retryButton;
    private static final String TAG = "Splash";
    PostSharedPreferences postSharedPreferences;
    TextView txtTitle;
    private ProgressDialog progressDialog;
    private static final int progress_bar_type = 0;
    File appPathFile;
    private static final int REQUEST_CODE = 200;
    private static final String WRITE_EXTERNAL_STORAGE_PERMISSION = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String READ_EXTERNAL_STORAGE_PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE;
    String[] permissions = {WRITE_EXTERNAL_STORAGE_PERMISSION, READ_EXTERNAL_STORAGE_PERMISSION};
    private String URL_UPDATE_APP;


    private com.victor.loading.rotate.RotateLoading rotateLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.activity_splash);


        postSharedPreferences = new PostSharedPreferences(this);


        retryButton = findViewById(R.id.splash_fbtn_retry);
        rotateLoading = findViewById(R.id.Splash_rotate_loading);

        rotateLoading.start();
        util = Util.getInstance();
        isConnected = util.checkConnectivity(this);

        txtTitle = findViewById(R.id.Splash_txt_title);
        util.setTypeFace(txtTitle, this);


        if (Util.isRooted()) {
            showDialogToDecision();
        } else {
            UpdateApplication();
        }
    }

    private void retryToConnect() {
        retryButton.setVisibility(View.VISIBLE);
        rotateLoading.stop();
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isConnected = util.checkConnectivity(Splash.this);
                if (isConnected) {
                    retryButton.setVisibility(View.INVISIBLE);
                    activityDestination();
                } else {
                    util.showToast(Splash.this, Splash.this.getString(R.string.no_internet_connection));
                }
            }
        });
    }


    private void UpdateApplication() {
        try {
            final PackageInfo packageManager = this.getPackageManager().getPackageInfo(getPackageName(), 0);


            WebApi webApi = new WebApi(this);
            webApi.GetApplicationNewVersion(new WebApi.OnUpdateResponse() {
                @Override
                public void OnResponse(final UpdateModel updateModel) {
                    if (updateModel != null) {
                        Log.d(TAG, "OnResponse: " + updateModel.getVersion());
                        Log.d(TAG, "UpdateApplication: " + packageManager.versionName);
                        if (!updateModel.getVersion().equals(packageManager.versionName)) {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Splash.this);
                            dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    URL_UPDATE_APP = updateModel.getUrl();
                                    getPermissions();
                                }
                            });
                            dialogBuilder.setNegativeButton(getString(R.string.Deny), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    changeActivity();
                                }
                            });
                            dialogBuilder.setMessage(R.string.Do_you_want_to_update);
                            dialogBuilder.show();


                        } else {
                            changeActivity();
                        }
                    }
                }
            }, Constants.URL_UPDATE_APPLICATION);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getNewApplication(String url) {



        downLoadAndInstallUpdate(Splash.this, getApplicationContext(), url);


    }


    private class downloadApplication extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);

        }

        @Override
        protected String doInBackground(String... urlAddress) {

            int count = 0;
            //create url connection

            try {
                URL url = new URL(urlAddress[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                int length = connection.getContentLength();

                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                File appDirectory = new File(Environment.getExternalStorageDirectory() + "/POST_Application");
                if (!appDirectory.exists()) {
                    appDirectory.mkdirs();
                }
                String appPath = appDirectory + "/" + "POST.apk";
                appPathFile = new File(appPath);
                if (appPathFile.createNewFile()) {
                    Log.d(TAG, "appPathFile: created ");
                }
                OutputStream output = new FileOutputStream(appPathFile);
                byte data[] = new byte[1024];
                long total = 0;
                while (((count = input.read(data)) != -1)) {
                    total += count;

                    publishProgress("" + (int) ((total * 100) / length));

                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();


            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            progressDialog.setProgress(Integer.parseInt(values[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            dismissDialog(progress_bar_type);

            Uri uri;
            if (Build.VERSION.SDK_INT > M) {
                uri = FileProvider.getUriForFile(getApplicationContext(), "com.anad.mobile.post.provider", appPathFile);
            } else {
                uri = Uri.fromFile(appPathFile);
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage(getString(R.string.Please_wait_until_update_complete));
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(100);
                progressDialog.setCancelable(false);
                progressDialog.show();
                return progressDialog;
            default:
                return null;
        }
    }

    private void activityDestination() {
        if (postSharedPreferences.getRememberMe()) {
            Intent i = new Intent(Splash.this, LoginActivity.class);
            startActivity(i);
            finish();

        } else {
            rotateLoading.stop();
            startActivity(new Intent(Splash.this, EnterActivity.class));
            finish();
        }
    }


    private void changeActivity() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (isConnected) {
                    activityDestination();

                } else {
                    retryToConnect();
                    util.showToast(Splash.this, Splash.this.getString(R.string.no_internet_connection));
                }
            }
        }, 2000);
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= M) {
            if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE_PERMISSION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE_PERMISSION) == PackageManager.PERMISSION_GRANTED) {

                getNewApplication(URL_UPDATE_APP);

            } else {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }
    }

    private void downLoadAndInstallUpdate(final Activity activity, final Context context, String url) {


        final Uri uri, uriM;
        String destination;
        String fileName = "POST.apk";


        Toast.makeText(activity, R.string.Please_wait_until_update_complete, Toast.LENGTH_LONG).show();

        destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        destination += fileName;
        uri = Uri.parse("file://" + destination);

        File file = new File(destination);
        if (file.exists())
            file.delete();

        //set downloadmanager
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        //set destination
        request.setDestinationUri(uri);

        // get download service and enqueue file
        final DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        final long downloadId = manager.enqueue(request);

        uriM = FileProvider.getUriForFile(context, "com.anad.mobile.post.provider", file);


        //set BroadcastReceiver to install app when .apk is downloaded
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {

                Intent install = new Intent(Intent.ACTION_VIEW);

                if ((Build.VERSION.SDK_INT >= N)) {
                    install.setDataAndType(uriM, "application/vnd.android.package-archive");
                } else {
                    install.setDataAndType(uri, "application/vnd.android.package-archive");
                }


                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(install);
                context.unregisterReceiver(this);
                activity.finish();
            }
        };
        //register receiver for when .apk download is compete
        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getNewApplication(URL_UPDATE_APP);
            }
        }
    }

    private void showDialogToDecision() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setMessage("دستگاه شما روت شده است.لذا تمامی عواقب استفاده از برنامه در این دستگاه بر عهده شما می باشد.")
                .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        UpdateApplication();
                    }
                }).setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        }).show();

    }
}
