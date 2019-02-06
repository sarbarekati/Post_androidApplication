package com.anad.mobile.post.Utils;

import android.app.Application;

import com.anad.mobile.post.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by elias.mohammadi on 2018/01/28
 */

public class MyApplicationSetting extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                                            .setDefaultFontPath("fonts/IRANSansMobile_Light.ttf")
                                            .setFontAttrId(R.attr.fontPath)
                                            .build());
    }
}
