package com.nathan.myapps.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import android.util.Log;

public class ApiUtils {

    public static String getAccessToken(TreeMap<String, String> localTreeMap, String paramString) {
        String str1 = "";
        Iterator<String> localIterator = localTreeMap.keySet().iterator();
        while (true) {
            if (!localIterator.hasNext())
                return MD5.digest(str1.substring(0, -1 + str1.length()) + paramString);
            String str2 = (String) localIterator.next();
            str1 = str1 + str2 + "=" + (String) localTreeMap.get(str2) + "&";
        }

    }
}