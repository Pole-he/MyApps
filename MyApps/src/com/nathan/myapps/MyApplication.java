package com.nathan.myapps;

import java.util.Map;

import com.android.volley.toolbox.ImageLoader;
import com.nathan.myapps.cache.BitmapCache;
import com.nathan.myapps.request.RequestManager;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap.CompressFormat;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Example application for adding an L1 image cache to Volley.
 * 
 * @author Trey Robinson
 * 
 */
public class MyApplication extends Application {

    private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 10;
    private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
    private static int DISK_IMAGECACHE_QUALITY = 100; // PNG is lossless so
                                                      // quality is ignored but
                                                      // must be provided
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "JSESSIONID";

    private SharedPreferences _preferences;
    public ImageLoader mImageLoader;
    static MyApplication instance;

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
        mImageLoader = new ImageLoader(RequestManager.getRequestQueue(), new BitmapCache());
        _preferences = PreferenceManager.getDefaultSharedPreferences(this);
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