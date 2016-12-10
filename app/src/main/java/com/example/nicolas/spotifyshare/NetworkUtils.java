package com.example.nicolas.spotifyshare;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Nicolas on 9/25/2016.
 */
public class NetworkUtils {

    public static void GetRequest(Context context, String url, VolleyListener volleyListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, volleyListener, volleyListener);
        queue.add(stringRequest);
    }

}
