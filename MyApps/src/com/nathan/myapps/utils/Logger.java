package com.nathan.myapps.utils;

import android.util.Log;

public class Logger {

    /**
     * 红色log
     * 
     * @param tag
     * @param e
     */
    public static void e(String tag, String e) {
         Log.e(tag, e);
    }

    /**
     * 黑色log
     * 
     * @param tag
     * @param v
     */
    public static void v(String tag, String v) {
         Log.v(tag, v);
    }

    /**
     * 蓝色log
     * 
     * @param tag
     * @param d
     */
    public static void d(String tag, String d) {
         Log.d(tag, d);
    }

    /**
     * 绿色log
     * 
     * @param tag
     * @param i
     */
    public static void i(String tag, String i) {
         Log.i(tag, i);
    }

    /**
     * 橙色log
     * 
     * @param tag
     * @param e
     */
    public static void w(String tag, String w) {
         Log.w(tag, w);
    }
}
