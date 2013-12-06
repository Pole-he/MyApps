package com.nathan.myapps.activity.ablum;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.nathan.myapps.R;
import com.nathan.myapps.adapter.StaggeredAdapter;
import com.nathan.myapps.adapter.WaterFallAdapter;
import com.nathan.myapps.bean.ablum.PicItem;
import com.nathan.myapps.bean.ablum.PicListJson;

import com.nathan.myapps.request.HttpVolleyRequest;

import com.nathan.myapps.utils.DataHandler;
import com.nathan.myapps.utils.Logger;

import com.nathan.myapps.widget.waterfall.MultiColumnListView.OnLoadMoreListener;
import com.nathan.myapps.widget.waterfall.MultiColumnPTRListView;
import com.nathan.myapps.widget.waterfall.MultiColumnPTRListView.OnRefreshListener;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

public class AblumListActivity extends ActionBarActivity {

    // private MultiColumnPTRListView mMCLVEvent;

    private List<PicItem> mPicList = new ArrayList<PicItem>();
    private StaggeredAdapter adapter;
    private int mCurrentPage = 0;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ablum_list);
        findViewById();
        init();
        setListerner();
        getData(30, mCurrentPage);
    }

    private void getData(int tag, int start) {
        HttpVolleyRequest<PicListJson> request = new HttpVolleyRequest<PicListJson>(this);
        request.HttpVolleyRequestGet(DataHandler.instance().getAblum(tag, start),
                PicListJson.class, PicItem.class, createMyReqSuccessListener(),
                createMyReqErrorListener());
    }

    private void setListerner() {
        // mMCLVEvent.setOnRefreshListener(mOnRefreshListener);
        // mMCLVEvent.setOnLoadMoreListener(mOnLoadMoreListener);
    }

    private void init() {


        adapter = new StaggeredAdapter(this, mPicList);

        listView.setAdapter(adapter);
        
    }

    private void findViewById() {
        // mMCLVEvent = (MultiColumnPTRListView)
        // this.findViewById(R.id.mclv_theme_waterfall);
        listView = (ListView) this.findViewById(R.id.list_ablum);
    }

    private OnRefreshListener mOnRefreshListener = new OnRefreshListener()
    {

        @Override
        public void onRefresh() {

        }
    };

    private OnLoadMoreListener mOnLoadMoreListener = new OnLoadMoreListener()
    {

        @Override
        public void onLoadMore() {
            mCurrentPage = mCurrentPage + 20;
            getData(30, mCurrentPage);
        }
    };

    @SuppressWarnings("rawtypes")
    private Listener<PicListJson> createMyReqSuccessListener() {
        return new Listener<PicListJson>()
        {

            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(PicListJson response) {
                if (mCurrentPage == 0) {
                   // mPicList.clear();
                }
                mPicList.addAll((List<PicItem>) response.data.pictures);
                adapter.notifyDataSetChanged();
            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
    }
}
