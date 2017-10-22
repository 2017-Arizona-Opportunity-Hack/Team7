package com.joshuatree.joshuatree;

/**
 * Created by azaldinfreidoon on 10/21/17.
 */

import android.content.Context;

import com.android.volley.*;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Singleton {

    private static Singleton sSingleton = null;
    private static RequestQueue sRequestQueue = null;
    private static Context sContext = null;

    private Singleton(Context context) {
        sContext = context;
        sRequestQueue = getRequestQueue();
    }

    public static synchronized Singleton getSingleton(Context context) {
        if (sSingleton == null) {
            sSingleton = new Singleton(context);
        }
        return sSingleton;
    }

    public RequestQueue getRequestQueue() {
        if (sRequestQueue == null) {
            sRequestQueue = Volley.newRequestQueue(sContext.getApplicationContext());
        }
        return sRequestQueue;
    }

    public void addRequest(Request req) {
        getRequestQueue().add(req);
    }
}
