package com.anad.mobile.post.ReportManager.api;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReportApiCallerTest {

    @Test
    public void CreateReportApiCaller_NotNoll()
    {
        Context context = InstrumentationRegistry.getTargetContext();
        assertNotNull(ReportApiCaller.getInstance(context));
        assertNotNull(ReportApiCaller.getInstance(context).withUrl(""));
    }

    @Test
    public void setRetryPolicy_CreateRetryPolicy_NotNull(){

        Context context = InstrumentationRegistry.getTargetContext();
        assertNotNull(ReportApiCaller.getInstance(context).setRetryPolicy(null));
    }

    @Test
    public void setRetryPolicy_CreateRetryPolicy_defaultRetryPolicy()
    {
        Context context = InstrumentationRegistry.getTargetContext();

        ReportApiCaller apiCaller = ReportApiCaller.getInstance(context).setRetryPolicy(null);

        assertEquals(10000,apiCaller.retryPolicy.getCurrentTimeout());
    }

    @Test
    public void setRetryPolicy_CreateRetryPolicy_customRetryPolicy(){
        Context context = InstrumentationRegistry.getTargetContext();

        ReportApiCaller apiCaller = ReportApiCaller.getInstance(context);

        apiCaller.setRetryPolicy(new DefaultRetryPolicy(5000,2,2.0f));

        assertEquals(5000,apiCaller.retryPolicy.getCurrentTimeout());
    }

    @Test
    public void setUrl_defaultUrl(){
        Context context = InstrumentationRegistry.getTargetContext();

        ReportApiCaller apiCaller = ReportApiCaller.getInstance(context);

        assertNotNull(apiCaller);
        assertEquals("",apiCaller.url);
    }

    @Test
    public void setUrl_CustomUrl(){
        Context context = InstrumentationRegistry.getTargetContext();
        ReportApiCaller apiCaller = ReportApiCaller.getInstance(context).withUrl("post.anadgroup.ir");
        assertNotNull(apiCaller);
        assertEquals("post.anadgroup.ir",apiCaller.url);
    }


    @Test
    public void setUrl_defaultMethod(){
        Context context = InstrumentationRegistry.getTargetContext();

        ReportApiCaller apiCaller = ReportApiCaller.getInstance(context);

        assertNotNull(apiCaller);
        assertEquals(0,apiCaller.method);
    }



}