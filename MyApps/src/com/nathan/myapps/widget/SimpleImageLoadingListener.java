package com.nathan.myapps.widget;

import com.android.volley.VolleyError;

import android.graphics.Bitmap;
import android.view.View;

public class SimpleImageLoadingListener implements ImageLoadingListener {

    @Override
    public void onLoadingStarted(String imageUri, View view) {
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, VolleyError failReason) {
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
    }
}
