package com.nathan.myapps.request;

import android.app.Activity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.baidu.cyberplayer.utils.T;
import com.nathan.myapps.app.msg.AppMsg;
import com.nathan.myapps.bean.at.VideoItem;

public class HttpVolleyRequest<T> {

    private ErrorListener errorListener;
    private Activity mAct;

    public HttpVolleyRequest(Activity mAct) {
        this.mAct = mAct;
    }

    public void HttpVolleyRequestGet(String url, Class<T> parentClass, Class<?> class1,
            Listener<T> listener, ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(Method.GET, url, parentClass, class1, listener,
                createMyReqErrorListener());
        RequestManager.getRequestQueue().add(request);
        this.errorListener = errorListener;
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {

                AppMsg.makeText(mAct, VolleyErrorHelper.getMessage(error, mAct), AppMsg.STYLE_ALERT)
                        .show();
                if (errorListener != null)
                    errorListener.onErrorResponse(error);
            }
        };
    }
}
