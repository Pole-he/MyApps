package com.nathan.myapps.activity.ablum;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private String type;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.type = getArguments().getString("type");
    }

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
                (Activity) mContext , true);
        request.HttpVolleyRequestGet(DataHandler.instance().getAblum(tag, start),
                PicListJson.class, PicItem.class, createMyReqSuccessListener(),
                createMyReqErrorListener());
    }

    // 30-美女 68-明星 168-搞笑 5-壁纸
    private int getCountFromType(String type) {
        if ("美女".equals(type)) {
            return 30;
        }
        else if ("明星".equals(type)) {
            return 68;
        }
        else if ("搞笑".equals(type)) {
            return 165;
        }
        else if ("壁纸".equals(type)) {
            return 5;
        }
        else if ("动漫".equals(type)) {
            return 1;
        }
        else if ("影视".equals(type)) {
            return 100;
        }
        return 30;
    }

    @Override
    public void init() {
        adapter = new WaterFallAdapter(mContext, mPicList, type);
        ptrstgv.setAdapter(adapter);
        ptrstgv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        // ptrstgv.getRefreshableView().setHeaderView(new Button(mContext));
        showLoadingView();
        getData(getCountFromType(type), mCurrentPage);
    }

    private OnRefreshListener<StaggeredGridView> mRefresh = new OnRefreshListener<StaggeredGridView>()
    {

        @Override
        public void onRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
            mCurrentPage = 0;
            getData(getCountFromType(type), mCurrentPage);
        }
    };

    private OnLoadmoreListener mLoadMore = new OnLoadmoreListener()
    {

        @Override
        public void onLoadmore() {
            mCurrentPage = mCurrentPage + 20;
            getData(getCountFromType(type), mCurrentPage);
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

    public static Fragment newInstance(String type) {
        AblumListFragment f = new AblumListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        f.setArguments(bundle);
        return f;
    }
}
