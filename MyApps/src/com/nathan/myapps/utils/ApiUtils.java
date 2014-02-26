package com.nathan.myapps.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import android.content.Context;
import android.util.Log;

public class ApiUtils {

    private final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");

    public static String getAccessToken(TreeMap<String, String> localTreeMap, String paramString) {
        String str1 = "";
        Iterator<String> localIterator = localTreeMap.keySet().iterator();
        while (true) {
            if (!localIterator.hasNext())
            {
<<<<<<< HEAD
                 Logger.e("///",str1.substring(0, -1 + str1.length()) + paramString+"//");
=======
>>>>>>> 18bc1fa08fcba301e298662de217eea33eacfaf7
                return MD5.digest(str1.substring(0, -1 + str1.length()) + paramString);
            }
            String str2 = (String) localIterator.next();
            str1 = str1 + str2 + "=" + (String) localTreeMap.get(str2) + "&";
        }

    }

    public static String getCreateTimeString(Date time) {
        return friendly_time(DateToString(time, "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 日期到字符串转换
     * 
     * @param date
     *            日期对象(可以是java.util.Date及其子类)
     * @param format
     *            格式字符串(注意：时间中的小时，hh表示12小时进制，HH表示24小时进制)
     * @return 格式化后的字符串
     */
    public static String DateToString(Date date, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        String strDateTime = "";
        if (date != null) {
            strDateTime = formater.format(date);
        }
        return strDateTime;
    }

    /**
     * 以友好的方式显示时间
     * 
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.format(cal.getTime());
        String paramDate = dateFormater2.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
        }
        else if (days == 1) {
            ftime = "昨天";
        }
        else if (days == 2) {
            ftime = "前天";
        }
        else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        }
        else if (days > 10) {
            ftime = dateFormater2.format(time);
        }
        return ftime;
    }

    /**
     * 将字符串转位日期类型
     * 
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        if (isEmpty(sdate))
            return null;
        try {
            return dateFormater.parse(sdate);
        }
        catch (ParseException e) {
            return null;
        }
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     * 
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static List<String> getFilePaths(Context context, String path) {

        List<String> fileNames = null;

        try {
            fileNames = Arrays.asList(context.getAssets().list(path));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileNames;

    }

    /**
     * 04:36 变成秒
     * 
     * @param time
     * @return
     */
    public static int getSongTime(String time) {
        int hour = Integer.valueOf(time.substring(0, 2));
        int seconds = Integer.valueOf(time.substring(3, 5));
        return hour * 60 + seconds;
    }

    /**
     * 15000 变成 秒
     * 
     * @param time
     * @return
     */
    public static String getStringTime(int time) {
        int hour = time / 60;
        int seconds = time % 60;
        return seconds < 10 ? hour + ":0" + seconds : hour + ":" + seconds;
    }

}