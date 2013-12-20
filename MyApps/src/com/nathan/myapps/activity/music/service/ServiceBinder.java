
package com.nathan.myapps.activity.music.service;

import com.nathan.myapps.utils.MusicUtils;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;


public class ServiceBinder implements ServiceConnection {
    private final ServiceConnection mCallback;

    public ServiceBinder(ServiceConnection callback) {
        mCallback = callback;
    }

    @Override
    public void onServiceConnected(ComponentName className, IBinder service) {
        MusicUtils.mService = PoPoInterface.Stub.asInterface((IBinder) service);
        if (mCallback != null)
            mCallback.onServiceConnected(className, service);
    }

    @Override
    public void onServiceDisconnected(ComponentName className) {
        if (mCallback != null)
            mCallback.onServiceDisconnected(className);
        MusicUtils.mService = null;
    }
}
