package com.nathan.myapps.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.nathan.myapps.url.URLs;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("DefaultLocale")
public class DataHandler {

    private final String API_KEY = "ios";
    private final String API_SECRET = "8ce32e9a0072037578899a53e155441f";
    private static DataHandler mInstance;

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
        String str = ApiUtils.getAccessToken(localTreeMap, API_SECRET);
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = "ios";
        arrayOfObject[1] = Long.valueOf(l);
        arrayOfObject[2] = str;
        String.format(URLs.ANIME_TASTE.anime_v3_feature, arrayOfObject);
    }

    @SuppressLint("DefaultLocale")
    public String getList(int paramInt) {
        long l = System.currentTimeMillis() / 1000L;
        TreeMap<String, String> localTreeMap = new TreeMap<String, String>();
        localTreeMap.put("api_key", "ios");
        localTreeMap.put("timestamp", String.valueOf(l));
        localTreeMap.put("page", String.valueOf(paramInt));
        String str = ApiUtils.getAccessToken(localTreeMap, API_SECRET);
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = Integer.valueOf(paramInt);
        arrayOfObject[1] = Long.valueOf(l);
        arrayOfObject[2] = "ios";
        arrayOfObject[3] = str;
        return String.format(URLs.ANIME_TASTE.anime_v2, arrayOfObject);
    }

    public String getRandom(int paramInt) {
        long l = System.currentTimeMillis() / 1000L;
        TreeMap<String, String> localTreeMap = new TreeMap<String, String>();
        localTreeMap.put("api_key", "ios");
        localTreeMap.put("timestamp", String.valueOf(l));
        localTreeMap.put("order", "random");
        localTreeMap.put("limit", String.valueOf(paramInt));
        String str = ApiUtils.getAccessToken(localTreeMap, API_SECRET);
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = "ios";
        arrayOfObject[1] = Long.valueOf(l);
        arrayOfObject[2] = Integer.valueOf(paramInt);
        arrayOfObject[3] = str;
        return String.format(URLs.ANIME_TASTE.anime_v3, arrayOfObject);
    }

    /**
     * 相册
     * 
     * @param tag
     *            30-美女 68-明星 165-搞笑 5-壁纸 100-影视 1-动漫
     * @param start
     * @return
     */
    public String getAblum(int tag, int start) {

        Object[] arrayOfObject = new Object[14];
        arrayOfObject[0] = "dbf0faa5-95ed-4b16-8841-44cd47132d70";
        arrayOfObject[1] = "d8:b3:77:37:27:7b";
        arrayOfObject[2] = "3.0.0";
        arrayOfObject[3] = "322480";
        arrayOfObject[4] = "vwBvT6iAcuWzB3XQ1WfE1gmLcwNMoTmd";
        arrayOfObject[5] = "h0bDq3isAqnyIHuD3Y0IeeKG";
        arrayOfObject[6] = "zh";
        arrayOfObject[7] = "3.0.0";
        arrayOfObject[8] = "d8:b3:77:37:27:7b";
        arrayOfObject[9] = "356440046688758";
        arrayOfObject[10] = Integer.valueOf(tag);
        arrayOfObject[11] = Integer.valueOf(start);
        arrayOfObject[12] = Integer.valueOf(20);
        arrayOfObject[13] = "middle";
        return String.format(URLs.ABLUM.url, arrayOfObject);
    }

    public String getMusic(int page) {
        return URLs.MUSIC.found_url;
    }

    public String getMusicPlay(List<String> song_id) {

        StringBuffer song = new StringBuffer();
        for (String songId : song_id) {
            song.append(songId);
            song.append("%2C");
        }
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = song.toString();
        return String.format(URLs.MUSIC.music_url, arrayOfObject) + URLs.MUSIC.music_url_end;
    }

    public String getSongPic(String title, String artist, String song_id, String singer_id) {
        Object[] arrayOfObject = new Object[4];
        try {
            arrayOfObject[0] = title == null ? "" : URLEncoder.encode(title, "UTF-8");
            arrayOfObject[1] = artist == null ? "" : URLEncoder.encode(artist, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ;
        arrayOfObject[2] = song_id;
        arrayOfObject[3] = singer_id;
        return String.format(URLs.MUSIC.song_pic, arrayOfObject);
    }
    
    public String getMiuiPic(int n)
    {
        return String.format(URLs.MIUI.data_url, n*40);
    }
}
