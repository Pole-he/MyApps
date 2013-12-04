package com.nathan.myapps.activity;

import java.util.List;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.nathan.myapps.R;
import com.nathan.myapps.adapter.VideoListAdapter;
import com.nathan.myapps.bean.at.ListJson;
import com.nathan.myapps.bean.at.VideoItem;
import com.nathan.myapps.request.GsonRequest;
import com.nathan.myapps.request.RequestManager;
import com.nathan.myapps.utils.DataHandler;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

public class AnimeTasteActivity extends Activity {

    private ListView lvVideo;
    private TextView tvLoading;
    private LayoutInflater mLayoutInflater;
    private ViewPager mShowPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        findViewById();
        init();
        getData();
    }

    private void getData() {
        GsonRequest<ListJson> request = new GsonRequest<ListJson>(Method.GET, DataHandler
                .instance().getList(0), ListJson.class, VideoItem.class,
                createMyReqSuccessListener(), createMyReqErrorListener());
        RequestManager.getRequestQueue().add(request);
        
        tvLoading.setVisibility(View.VISIBLE);
        Animation loading = AnimationUtils.loadAnimation(this, R.anim.rotate_loading);
        tvLoading.startAnimation(loading);
    }

    private void init() {
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/BelshawDonutRobot.ttf");
        // 应用字体
        tvLoading.setTypeface(typeFace);
    }

    
    private void findViewById() {
        lvVideo = (ListView) this.findViewById(R.id.videoList);
        tvLoading = (TextView) this.findViewById(R.id.loading);
    }

    @SuppressWarnings("rawtypes")
    private Listener<ListJson> createMyReqSuccessListener() {
        return new Listener<ListJson>()
        {

            @Override
            public void onResponse(ListJson response) {
                tvLoading.clearAnimation();
                tvLoading.setVisibility(View.GONE);
                @SuppressWarnings("unchecked")
                VideoListAdapter adapter = new VideoListAdapter(AnimeTasteActivity.this,
                        (List<VideoItem>)response.list);
                lvVideo.setAdapter(adapter);
            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", error.getMessage() + "");
                tvLoading.clearAnimation();
                tvLoading.setVisibility(View.GONE);
            }
        };
    }
}
