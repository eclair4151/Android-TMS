package com.shemeshapps.android_tms;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shemeshapps.android_tms.Helpers.PersistentCookieStore;
import com.shemeshapps.android_tms.Helpers.WebTMSRequest;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Tomer on 2/14/15.
 */
public class RequestUtil {

    public static RequestQueue queue;
    public static Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    public static void init(Context context)
    {
        //CookieHandler.setDefault(new CookieManager(PersistentCookieStore.getInstance(context), CookiePolicy.ACCEPT_ALL));


        //CookieManager manager = new CookieManager();
        //CookieHandler.setDefault( manager  );

        queue = Volley.newRequestQueue(context);
    }

    public static void getHomePage(Response.Listener listener)
    {
        String url = "https://duapp2.drexel.edu/webtms_du/app";
        WebTMSRequest r = new WebTMSRequest(Request.Method.GET,url,listener,errorListener,WebTMSRequest.requestType.HOMEPAGE);

        queue.add(r);
    }


    //NOTE: Term is from 1-x sending a 0 will end up crashing it
    public static void searchClasses(Response.Listener listener,final int termIndex,final String courseName,final String courseNumber,final String crn)
    {
        String url = "https://duapp2.drexel.edu/webtms_du/app";

        WebTMSRequest r = new WebTMSRequest(Request.Method.POST,url,listener,errorListener, WebTMSRequest.requestType.CLASSSEARCH){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError
        {
            Map<String, String> params = new LinkedHashMap<>();

            params.put("formids", "term,courseName,crseNumb,crn");
            params.put("component", "searchForm");
            params.put("page", "Home");
            params.put("service", "direct");
            params.put("term", Integer.toString(termIndex));
            params.put("courseName", courseName);
            params.put("crseNumb", courseNumber);
            params.put("crn", crn);
            return params;
        }

        };

        queue.add(r);
    }

}
