package com.nathan.myapps.request;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.baidu.cyberplayer.utils.T;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.app.msg.AppMsg;
import com.nathan.myapps.bean.BaseData;
import com.nathan.myapps.bean.miui.MiuiPic;
import com.nathan.myapps.cache.DataCache;
import com.nathan.myapps.utils.Logger;

@SuppressWarnings("hiding")
public class HttpVolleyRequest<T> {

    private ErrorListener errorListener;
    private Listener<T> successListener;
    private Class<T> baseClass;
    private boolean isCache = false; // 是否开启缓存
    private boolean isExit = true; // 判断缓存文件是否存在
    private String urlKey;
    private Activity mAct;

    public HttpVolleyRequest(Activity mAct) {
        this.mAct = mAct;
    }

    public HttpVolleyRequest(Activity mAct, boolean isCache) {
        this.isCache = isCache;
        this.mAct = mAct;
    }

    public HttpVolleyRequest(Activity mAct, Fragment fragment, boolean isCache) {
        this.isCache = isCache;
        this.mAct = mAct;
    }

    private Handler handler = new Handler()
    {

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            successListener.onResponse((T) msg.obj);
        }
    };

    /**
     * Volley Get方法
     * 
     * @param url
     *            请求地址
     * @param parentClass
     *            父类数据结构
     * @param class1
     *            子类数据结构
     * @param listener
     *            成功监听
     * @param errorListener
     *            错误监听
     */
    @SuppressWarnings("unchecked")
    public void HttpVolleyRequestGet(String url, Class<T> parentClass, Class<?> class1,
            Listener<T> listener, ErrorListener errorListener) {

        GsonRequest<T> request = new GsonRequest<T>(Method.GET, url, parentClass, class1,
                SuccessListener(), ErrorListener());
        RequestManager.getRequestQueue().add(request);
        this.errorListener = errorListener;
        this.successListener = listener;
        this.baseClass = parentClass;
        this.urlKey = url;

        // 把缓存带出去
        if (isCache && MyApplication.getInstance().getCache().file(getCacheKey(url)) != null) {
            // successListener.onResponse((T)
            // UpairsApplication.getInstance().getCache()
            // .getAsObject(getCacheKey(url)));
            T t = (T) MyApplication.getInstance().getCache().getAsObject(getCacheKey(url));
            if (t != null) {
                handler.sendMessageDelayed(handler.obtainMessage(0, t), 1000);
            }
            else {
                isExit = false;
            }
        }
        else {
            isExit = false;
        }
    }

    /**
     * Volley Post方法
     * 
     * @param url
     *            请求地址
     * @param params
     *            上传的Map数据结构
     * @param parentClass
     *            父类数据结构
     * @param class1
     *            子类数据结构
     * @param listener
     *            成功监听
     * @param errorListener
     *            错误监听
     */
    @SuppressWarnings("unchecked")
    public void HttpVolleyRequestPost(String url, Map<String, String> params, Class<T> parentClass,
            Class<?> class1, Listener<T> listener, ErrorListener errorListener) {

        GsonRequest<T> request = new GsonRequest<T>(Method.POST, url, params, parentClass, class1,
                SuccessListener(), ErrorListener());
        RequestManager.getRequestQueue().add(request);
        this.errorListener = errorListener;
        this.successListener = listener;
        this.baseClass = parentClass;
        this.urlKey = url;
        // 把缓存带出去
        if (isCache && MyApplication.getInstance().getCache().file(getCacheKey(url)) != null) {
            // successListener.onResponse((T)
            // UpairsApplication.getInstance().getCache()
            // .getAsObject(getCacheKey(url)));
            T t = (T) MyApplication.getInstance().getCache().getAsObject(getCacheKey(url));
            if (t != null) {
                handler.sendMessageDelayed(handler.obtainMessage(0, t), 1000);
            }
            else {
                isExit = false;
            }
        }
        else {
            isExit = false;
        }
    }

    /**
     * 缓存成功处理
     * 
     * @return
     */
    private Listener<T> SuccessListener() {
        return new Listener<T>()
        {

            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(T response) {

                if (isCache) {
                    if (!isExit) {
                        if (successListener != null) {
                            successListener.onResponse(response);
                        }
                    }
                    if (response instanceof BaseData) {
                        Logger.e("",response.toString()+"");
                        MyApplication.getInstance().getCache()
                                .put(getCacheKey(urlKey), (BaseData) response, DataCache.TIME_DAY);
                    }
                    else {
                        MyApplication.getInstance().getCache()
                                .put(getCacheKey(urlKey), (ArrayList<MiuiPic>) response, DataCache.TIME_DAY);
                    }
                }
                else {
                    if (successListener != null) {
                        successListener.onResponse(response);
                    }
                }

            }
        };
    }

    /**
     * 返回错误Response 集中处理错误提示消息APPMsg
     * 
     * @return
     */
    private Response.ErrorListener ErrorListener() {
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

    private String getCacheKey(String url) {
        if(url.contains("access_token"))
        return url.substring(0, url.indexOf("&"));
        else
        return url;
    }
}
