package com.anad.mobile.post.API;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;

public abstract class Api {

    public String url="";
    public int method = Request.Method.GET;
    public RetryPolicy retryPolicy = new DefaultRetryPolicy(10000,2,2.0f);
}
