package com.nathan.myapps.utils;

import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;

import com.nathan.myapps.activity.music.service.PoPoInterface;
import com.nathan.myapps.activity.music.service.PoPoService;
import com.nathan.myapps.activity.music.service.ServiceBinder;
import com.nathan.myapps.activity.music.service.ServiceToken;

public class MusicUtils {

    public static PoPoInterface mService = null;

    private static HashMap<Context, ServiceBinder> sConnectionMap = new HashMap<Context, ServiceBinder>();

    /**
     * @param context
     * @return
     */
    public static ServiceToken bindToService(Activity context) {
        return bindToService(context, null);
    }

    /**
     * @param context
     * @param callback
     * @return
     */
    public static ServiceToken bindToService(Context context, ServiceConnection callback) {
        Activity realActivity = ((Activity) context).getParent();
        if (realActivity == null) {
            realActivity = (Activity) context;
        }

        ContextWrapper cw = new ContextWrapper(realActivity);
        cw.startService(new Intent(cw, PoPoService.class));
        ServiceBinder sb = new ServiceBinder(callback);
        if (cw.bindService((new Intent()).setClass(cw, PoPoService.class), sb, 0)) {
            sConnectionMap.put(cw, sb);
            return new ServiceToken(cw);
        }
        return null;
    }

    /**
     * @param token
     */
    public static void unbindFromService(ServiceToken token) {
        if (token == null) {
            return;
        }
        ContextWrapper cw = token.mWrappedContext;
        ServiceBinder sb = sConnectionMap.remove(cw);
        if (sb == null) {
            return;
        }
        cw.unbindService(sb);
        if (sConnectionMap.isEmpty()) {
            mService = null;
        }
    }

    /**
     * stop service
     * @param context
     */
    public static void stopService(Context context) {
        Activity realActivity = ((Activity) context).getParent();
        if (realActivity == null) {
            realActivity = (Activity) context;
        }

        ContextWrapper cw = new ContextWrapper(realActivity);
        cw.stopService(new Intent(cw, PoPoService.class));
    }

}
