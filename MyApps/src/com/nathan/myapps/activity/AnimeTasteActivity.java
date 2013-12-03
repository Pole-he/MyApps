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
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

public class AnimeTasteActivity extends Activity {

    private ListView lvVideo;

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

    }

    private void init() {
        
    }

    
    private void findViewById() {
        lvVideo = (ListView) this.findViewById(R.id.videoList);
    }

    @SuppressWarnings("rawtypes")
    private Listener<ListJson> createMyReqSuccessListener() {
        return new Listener<ListJson>()
        {

            @Override
            public void onResponse(ListJson response) {
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
            }
        };
    }
}
