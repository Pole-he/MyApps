package com.nathan.myapps.activity.ablum;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridView;
import com.nathan.myapps.R;
import com.nathan.myapps.adapter.WaterFallAdapter;
import com.nathan.myapps.bean.ablum.PicItem;
import com.nathan.myapps.bean.ablum.PicListJson;
import com.nathan.myapps.fragment.BaseFragment;
import com.nathan.myapps.request.HttpVolleyRequest;
import com.nathan.myapps.utils.DataHandler;
import com.nathan.myapps.widget.LoadingView;
import com.nathan.myapps.widget.gridview.StaggeredGridView;
import com.nathan.myapps.widget.gridview.StaggeredGridView.OnLoadmoreListener;

public class AblumListFragment extends BaseFragment {

    private List<PicItem> mPicList = new ArrayList<PicItem>();
    private WaterFallAdapter adapter;
    private int mCurrentPage = 0;
    private PullToRefreshStaggeredGridView ptrstgv;
    private LoadingView mLoadingView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.ablum_list, container, false);
    }

    @Override
    public void addListeners() {
        ptrstgv.setOnRefreshListener(mRefresh);
        ptrstgv.setOnLoadmoreListener(mLoadMore);
    }

    @Override
    public void findViews(View paramView) {
        ptrstgv = (PullToRefreshStaggeredGridView) paramView.findViewById(R.id.ptrstgv);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        findViews(view);
        init();
        addListeners();
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressWarnings("rawtypes")
    private void getData(int tag, int start) {
        HttpVolleyRequest<PicListJson> request = new HttpVolleyRequest<PicListJson>(
                (Activity) mContext);
        request.HttpVolleyRequestGet(DataHandler.instance().getAblum(tag, start),
                PicListJson.class, PicItem.class, createMyReqSuccessListener(),
                createMyReqErrorListener());
    }

    @Override
    public void init() {
        adapter = new WaterFallAdapter(mContext, mPicList);
        ptrstgv.setAdapter(adapter);
        ptrstgv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        // ptrstgv.getRefreshableView().setHeaderView(new Button(mContext));
        showLoadingView();
        getData(30, mCurrentPage);
    }

    private OnRefreshListener<StaggeredGridView> mRefresh = new OnRefreshListener<StaggeredGridView>()
    {

        @Override
        public void onRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
            mCurrentPage = 0;
            getData(30, mCurrentPage);
        }
    };

    private OnLoadmoreListener mLoadMore = new OnLoadmoreListener()
    {

        @Override
        public void onLoadmore() {
            mCurrentPage = mCurrentPage + 20;
            getData(30, mCurrentPage);
            showLoadingView();

        }
    };

    private void showLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = new LoadingView(mContext, 1);
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
    private Listener<PicListJson> createMyReqSuccessListener() {
        return new Listener<PicListJson>()
        {

            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(PicListJson response) {
                dimissLoadingView();
                ptrstgv.onRefreshComplete();
                if (mCurrentPage == 0) {
                    mPicList.clear();
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
                ptrstgv.onRefreshComplete();
                dimissLoadingView();
            }
        };
    }
}
