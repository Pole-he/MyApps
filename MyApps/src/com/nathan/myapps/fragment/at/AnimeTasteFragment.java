package com.nathan.myapps.fragment.at;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response;
import com.android.volley.Request.Method; 
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import com.nathan.myapps.R;
import com.nathan.myapps.activity.at.AnimeTasteDetailActivity;
import com.nathan.myapps.adapter.VideoListAdapter;
import com.nathan.myapps.adapter.ViewFlowImageAdapter;
import com.nathan.myapps.bean.at.ListJson;
import com.nathan.myapps.bean.at.VideoItem;
import com.nathan.myapps.fragment.BaseFragment;
import com.nathan.myapps.request.HttpVolleyRequest;
import com.nathan.myapps.utils.DataHandler;
import com.nathan.myapps.widget.CircleFlowIndicator;
import com.nathan.myapps.widget.LayersLayout;
import com.nathan.myapps.widget.LoadingView;
import com.nathan.myapps.widget.ScrollBackListView;
import com.nathan.myapps.widget.ViewFlow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import android.widget.ListView;


public class AnimeTasteFragment extends BaseFragment implements OnScrollListener,
        OnClickListener {

    private ScrollBackListView lvVideo;
    private LayoutInflater mLayoutInflater;
    private Boolean mUpdating = true;// 防止多次加载
    private int mCurrentPage = 0;
    private VideoListAdapter mVideoListAdapter;
    private List<VideoItem> listVideo = new ArrayList<VideoItem>();
    private LoadingView mLoadingView;
    private ViewFlow mShowViewFlow;
    private LayersLayout layersLayout;

    private Context mContext;
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.at_activity_start, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        findViews(view);
        init();
        super.onViewCreated(view, bundle);
    }


    @SuppressWarnings("rawtypes")
    private void getData(int mCurrentPage) {

        HttpVolleyRequest<ListJson> request = new HttpVolleyRequest<ListJson>((Activity)mContext , true);
        request.HttpVolleyRequestGet(DataHandler.instance().getList(mCurrentPage), ListJson.class,
                VideoItem.class, createMyReqSuccessListener(), createMyReqErrorListener());
        showLoadingView();
    }



    private void showLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = new LoadingView(mContext, 3);
        }
        if (!mLoadingView.isShowing()) {
            mLoadingView.show();
        }
    }

    private void dimissLoadingView() {
        if (mLoadingView.isShowing()) {
            mLoadingView.dismiss();
        }
    }


    @SuppressWarnings("rawtypes")
    private Listener<ListJson> createMyReqSuccessListener() {
        return new Listener<ListJson>()
        {

            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(ListJson response) {
                mUpdating = false;
                dimissLoadingView();
                if (mCurrentPage == 0) {

                    ViewFlowImageAdapter mVpAdapter = new ViewFlowImageAdapter(mContext, (List<VideoItem>) response.feature);

                    mShowViewFlow.setAdapter(mVpAdapter);
                    mShowViewFlow.setmSideBuffer(((List<VideoItem>) response.feature).size()); // 实际图片张数，
                    // 我的ImageAdapter实际图片张数为3
                    //mShowViewFlow.setOnTouchListener(new MyTouchListener());
                    mShowViewFlow.setTimeSpan(4500);
                    mShowViewFlow.setSelection(((List<VideoItem>) response.feature).size() * 1000); // 设置初始位置
                    mShowViewFlow.startAutoFlowTimer(); // 启动自动播放
                    layersLayout.setView(mShowViewFlow);
                }

                listVideo.addAll((List<VideoItem>) response.list);
                mVideoListAdapter.notifyDataSetChanged();

            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {
                mUpdating = false;
                dimissLoadingView();
            }
        };
    }

    @Override
    public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {
        if ((!mUpdating) && (paramInt3 != 0)
                && (paramAbsListView.getLastVisiblePosition() == paramInt3 - 1)) {
            mUpdating = true;
            mCurrentPage = mCurrentPage + 1;
            getData(mCurrentPage);
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
        // TODO Auto-generated method stub

    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, AnimeTasteDetailActivity.class);
        intent.putExtra("VideoItem", (VideoItem) v.getTag());
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mShowViewFlow.onConfigurationChanged(newConfig);
    }

    @Override
    public void addListeners() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void init() {
        mVideoListAdapter = new VideoListAdapter(mContext, listVideo);
        lvVideo.setAdapter(mVideoListAdapter);
        getData(mCurrentPage);
    }

    @Override
    public void findViews(View paramView) {
        lvVideo = (ScrollBackListView) paramView.findViewById(R.id.videoList);
        layersLayout = (LayersLayout) paramView.findViewById(R.id.layerslayout);// 获得自定义图层，对触屏事件进行重定向

        mLayoutInflater = LayoutInflater.from(mContext);
        this.lvVideo.setOnScrollListener(this);
        View localView = this.mLayoutInflater.inflate(R.layout.at_gallery_item, null, false);
        this.lvVideo.addHeaderView(localView);
        this.mShowViewFlow = ((ViewFlow) localView.findViewById(R.id.viewflow));
        CircleFlowIndicator indic = (CircleFlowIndicator) paramView.findViewById(R.id.viewflowindic);
        mShowViewFlow.setFlowIndicator(indic);
        
    }
}
