package com.anad.mobile.post.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.anad.mobile.post.R;
import com.anad.mobile.post.Storage.PostSharedPreferences;
import com.anad.mobile.post.Utils.Util;


public class EnterActivity extends AppCompatActivity {
    Util util;
    PostSharedPreferences postSharedPreferences;
    Button btnRegister, btnLogin;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_enter);
        util = Util.getInstance();
        postSharedPreferences = new PostSharedPreferences(this);
        txtTitle = findViewById(R.id.Enter_txt_title);
        util.setTypeFace(txtTitle, this);
        setUpButton();
        showDialog(postSharedPreferences);
    }

    private void setUpButton() {
        btnLogin = findViewById(R.id.activity_enter_btn_login);
        btnRegister = findViewById(R.id.activity_enter_btn_register);

        util.setTypeFaceButton(btnLogin, this);
        util.setTypeFaceButton(btnRegister, this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void showDialog(PostSharedPreferences preferences) {
        if (preferences.getPrefUserName().equals("")) {
            final AlertDialog.Builder dialogBuilder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialogBuilder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                dialogBuilder = new AlertDialog.Builder(this);
            }

            if(preferences.getFirstRun()){
            /*dialogBuilder
                    .setMessage("لطفا برای استفاده از این برنامه ابتدا ثبت نام نمایید.")
                    .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();*/
            preferences.setFirstRun(false);
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
