package com.nathan.myapps.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlUtils {

    public static String removeSpace(String url) {

            return url.replaceAll(" ", "%20");

    }

    public static String codeChinese(String url) {
        return url.replaceAll("%3A", ":").replaceAll("%2F", "/");
    }
}
