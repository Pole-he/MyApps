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

import com.nathan.myapps.widget.gridview.StaggeredGridView;
import com.nathan.myapps.widget.waterfall.MultiColumnListView.OnLoadMoreListener;
import com.nathan.myapps.widget.waterfall.MultiColumnPTRListView;
import com.nathan.myapps.widget.waterfall.MultiColumnPTRListView.OnRefreshListener;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class AblumMainActivity extends ActionBarActivity {

    private MultiColumnPTRListView mMCLVEvent;

    private List<PicItem> mPicList = new ArrayList<PicItem>();
    private StaggeredAdapter adapter;
    private int mCurrentPage = 0;

    private StaggeredGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ablum_waterfall);
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
         mMCLVEvent.setOnRefreshListener(mOnRefreshListener);
         mMCLVEvent.setOnLoadMoreListener(mOnLoadMoreListener);
    }

    private void init() {
         adapter = new StaggeredAdapter(this, mPicList);
         mMCLVEvent.setAdapter(adapter);
//        int margin = getResources().getDimensionPixelSize(R.dimen.play_innser_recommend_margin_top);
//
//        gridView.setItemMargin(margin); // set the GridView margin
//
//        gridView.setPadding(margin, 0, margin, 0); // have the margin on the
//                                                   // sides as well
//
//        adapter = new StaggeredAdapter(this, mPicList);
//
//        gridView.setAdapter(adapter);
        
    }

    private void findViewById() {
         mMCLVEvent = (MultiColumnPTRListView)
         this.findViewById(R.id.mclv_theme_waterfall);
       // gridView = (StaggeredGridView) this.findViewById(R.id.staggeredGridView1);
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
