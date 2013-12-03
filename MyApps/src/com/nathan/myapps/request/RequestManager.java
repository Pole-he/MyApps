package com.nathan.myapps.request;

import java.io.File;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * Manager for the queue
 * 
 * @author Trey Robinson
 *
 */
public class RequestManager {
	
	/**
	 * the queue :-)
	 */
	private static RequestQueue mRequestQueue;

	/**
	 * Nothing to see here.
	 */
	private RequestManager() {
	 // no instances
	} 

	/**
	 * @param context
	 * 			application context
	 */
	public static void init(Context context) {
		//mRequestQueue = Volley.newRequestQueue(context);
		
        File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        File file = new File(sdDir, "nathan");
        if(!file.exists())
        {
            file.mkdir();
        }
        DiskBasedCache cache = new DiskBasedCache(file, 20 * 1024 * 1024); //
        Network network = new BasicNetwork(new HurlStack()); //
        mRequestQueue = new RequestQueue(cache, network); // 
        mRequestQueue.start();
	}

	/**
	 * @return
	 * 		instance of the queue
	 * @throws
	 * 		IllegalStatException if init has not yet been called
	 */
	public static RequestQueue getRequestQueue() {
	    if (mRequestQueue != null) {
	        return mRequestQueue;
	    } else {
	        throw new IllegalStateException("Not initialized");
	    }
	}
}
