package com.anad.mobile.post.Models.DashboardModels;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({WebServiceType.YEAR_DASHBOARD,WebServiceType.BASE_DASHBOARD,
        WebServiceType.FORM_DASHBOARD,WebServiceType.SPEED_DASHBOARD})
@Retention(RetentionPolicy.SOURCE)
public @interface WebServiceType {
    int YEAR_DASHBOARD = 0;
    int BASE_DASHBOARD = 1;
    int FORM_DASHBOARD = 2;
    int SPEED_DASHBOARD = 3;
}
