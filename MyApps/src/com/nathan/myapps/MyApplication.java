package com.nathan.myapps;

import java.io.File;
import java.util.Map;

import com.android.volley.toolbox.ImageLoader;
import com.nathan.myapps.cache.BitmapCache;
import com.nathan.myapps.cache.BitmapLruCache;
import com.nathan.myapps.cache.DataCache;
import com.nathan.myapps.request.RequestManager;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

public class MyApplication extends Application {

    private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 10;
    private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
    private static int DISK_IMAGECACHE_QUALITY = 100; // PNG is lossless so
                                                      // quality is ignored but
                                                      // must be provided
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "JSESSIONID";
    private final static int RATE = 8; // 默认分配最大空间的几分之一
    private SharedPreferences _preferences;
    public ImageLoader mImageLoader;
    static MyApplication instance;
    public DataCache dataCache;

    public MyApplication() {
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    /**
     * Intialize the request manager and the image cache
     */
    private void init() {
        RequestManager.init(this);
        dataCache = DataCache
                .get(new File(Environment.getExternalStorageDirectory() + "/Popo/json"));

        // 确定在LruCache中，分配缓存空间大小,默认程序分配最大空间的 1/8
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int maxSize = manager.getMemoryClass() / RATE; // 比如 64M/8,单位为M

        // BitmapLruCache自定义缓存class，该框架本身支持二级缓存，在BitmapLruCache封装一个软引用缓存
        mImageLoader = new ImageLoader(RequestManager.getRequestQueue(), new BitmapLruCache(
                1024 * 1024 * maxSize));
        _preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public DataCache getCache() {
        return dataCache;
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        File cacheDir = new File(Environment.getExternalStorageDirectory() + "Popo/cache");
        clearCacheFolder(cacheDir, System.currentTimeMillis());
    }

    /**
     * 清除缓存目录
     * 
     * @param dir
     *            目录
     * @param numDays
     *            当前系统时间
     * @return
     */
    private int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    /**
     * Checks the response headers for session cookie and saves it if it finds
     * it.
     * 
     * @param headers
     *            Response Headers.
     */
    public final void checkSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(SET_COOKIE_KEY)
                && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                Log.e("cookie", cookie + "");
                Editor prefEditor = _preferences.edit();
                prefEditor.putString(SESSION_COOKIE, cookie);
                prefEditor.commit();
            }
        }
    }

    /**
     * Adds session cookie to headers if exists.
     * 
     * @param headers
     */
    public final void addSessionCookie(Map<String, String> headers) {
        String sessionId = _preferences.getString(SESSION_COOKIE, "");
        // String sessionId= "9504DC27C2DB11F6F1DC36ED16824515-n1";
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
    }
}