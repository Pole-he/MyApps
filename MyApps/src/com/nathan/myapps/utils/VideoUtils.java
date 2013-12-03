package com.nathan.myapps.utils;

public class VideoUtils {

    public static String getCommonVideoUrl(String paramString) {
        Long localLong = Long.valueOf((long) Math.ceil(System.currentTimeMillis() / 1000L));
        return paramString.replace("type//", "type/flv/ts/" + localLong + "/useKeyframe/0/");
    }

    public static String getHDVideoUrl(String paramString) {
        Long localLong = Long.valueOf((long) (Math.ceil(System.currentTimeMillis() / 1000L)));
        return paramString.replace("type//", "type/hd2/ts/" + localLong + "/useKeyframe/0/");
    }
}
