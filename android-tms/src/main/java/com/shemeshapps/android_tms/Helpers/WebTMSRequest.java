package com.shemeshapps.android_tms.Helpers;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.shemeshapps.android_tms.Parser;


/**
 * Created by Tomer on 2/16/15.
 */
public class WebTMSRequest<T> extends Request<T> {
    public static enum requestType{
        HOMEPAGE,
        CLASSSEARCH
    };

    Response.Listener listener;
    requestType type;

    public WebTMSRequest(int method, String url, Response.Listener listener,Response.ErrorListener errorListener,requestType type)
    {
        super(method,url,errorListener);
        this.type = type;
        this.listener = listener;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response)
    {
        T parsedResponse = null;
        switch (type){
            case HOMEPAGE:
                parsedResponse = (T)Parser.ParseHomepage(new String(response.data));
                break;
            case CLASSSEARCH:
                parsedResponse = (T)Parser.ParseClassList(new String(response.data));
                break;
        }

        return Response.success(parsedResponse, HttpHeaderParser.parseCacheHeaders(response));

    }


    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
