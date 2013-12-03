package com.nathan.myapps.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("DefaultLocale")
public class DataHandler {

    private static final String API_KEY = "ios";
    private static final String API_SECRET = "8ce32e9a0072037578899a53e155441f";
    private static DataHandler mInstance;
    private static final String sFeaturesUrl = "http://i.animetaste.net/api/animelist_v3/?api_key=%s&timestamp=%d&feature=1&access_token=%s";
    private static final String sRandomeRequestUrl = "http://i.animetaste.net/api/animelist_v3/?api_key=%s&timestamp=%d&order=random&limit=%d&access_token=%s";
    private static final String sRequestListUrl = "http://i.animetaste.net/api/animelist_v2/?api_key=%s&timestamp=%d&page=%d&access_token=%s";

    public static DataHandler instance() {
        if (mInstance == null)
            mInstance = new DataHandler();
        return mInstance;
    }

    public void getFetures() {
        long l = System.currentTimeMillis() / 1000L;
        TreeMap<String, String> localTreeMap = new TreeMap<String, String>();
        localTreeMap.put("api_key", "ios");
        localTreeMap.put("timestamp", String.valueOf(l));
        localTreeMap.put("feature", "1");
        String str = ApiUtils.getAccessToken(localTreeMap, "8ce32e9a0072037578899a53e155441f");
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = "ios";
        arrayOfObject[1] = Long.valueOf(l);
        arrayOfObject[2] = str;
        String.format(
                "http://i.animetaste.net/api/animelist_v3/?api_key=%s&timestamp=%d&feature=1&access_token=%s",
                arrayOfObject);
    }

    @SuppressLint("DefaultLocale")
    public String getList(int paramInt) {
        long l = System.currentTimeMillis() / 1000L;
        TreeMap<String, String> localTreeMap = new TreeMap<String, String>();
        localTreeMap.put("api_key", "ios");
        localTreeMap.put("timestamp", String.valueOf(l));
        localTreeMap.put("page", String.valueOf(paramInt));
        String str = ApiUtils.getAccessToken(localTreeMap, "8ce32e9a0072037578899a53e155441f");
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = "ios";
        arrayOfObject[1] = Long.valueOf(l);
        arrayOfObject[2] = Integer.valueOf(paramInt);
        arrayOfObject[3] = str;
        return String
                .format("http://i.animetaste.net/api/animelist_v2/?api_key=%s&timestamp=%d&page=%d&access_token=%s",
                        arrayOfObject);
    }
}
