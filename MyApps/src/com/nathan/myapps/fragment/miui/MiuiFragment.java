package com.nathan.myapps.fragment.miui;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nathan.myapps.R;
import com.nathan.myapps.adapter.MiuiAdapter;
import com.nathan.myapps.bean.at.ListJson;
import com.nathan.myapps.bean.at.VideoItem;
import com.nathan.myapps.bean.miui.MiuiPic;
import com.nathan.myapps.fragment.BaseFragment;
import com.nathan.myapps.request.HttpVolleyRequest;
import com.nathan.myapps.request.RequestManager;
import com.nathan.myapps.utils.DataHandler;
import com.nathan.myapps.utils.DateDeserializerUtils;
import com.nathan.myapps.utils.DateSerializerUtils;

public class MiuiFragment extends BaseFragment implements OnScrollListener {

    private Context mContext;
    private Boolean mUpdating = true;// 防止多次加载
    private PullToRefreshGridView ptrGridView;
    private List<MiuiPic> listMiuiPic = new ArrayList<MiuiPic>();
    private MiuiAdapter adapter;
    private Gson mGson = new GsonBuilder()
            .registerTypeAdapter(java.util.Date.class, new DateDeserializerUtils())
            .registerTypeAdapter(java.util.Date.class, new DateSerializerUtils())
            .enableComplexMapKeySerialization().setDateFormat(DateFormat.LONG).create();
    private int mCurrentPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.miui_gridview_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        findViews(view);
        init();
        addListeners();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void addListeners() {
        ptrGridView.setOnRefreshListener(mRefresh);
        ptrGridView.setOnScrollListener(this);
    }

    @Override
    public void init() {
        adapter = new MiuiAdapter(mContext, listMiuiPic);
        ptrGridView.setAdapter(adapter);
        getData(0);
    }

    @Override
    public void findViews(View paramView) {
        ptrGridView = (PullToRefreshGridView) paramView.findViewById(R.id.pull_refresh_grid);

    }

    private OnRefreshListener<GridView> mRefresh = new OnRefreshListener<GridView>()
    {

        @Override
        public void onRefresh(PullToRefreshBase<GridView> refreshView) {
            ptrGridView.onRefreshComplete();
            mCurrentPosition = 0;
            getData(mCurrentPosition);
        }
    };

    // private void getData(int n) {
    // RequestManager.getRequestQueue().add(
    // new JsonArrayRequest(DataHandler.instance().getMiuiPic(n),
    // createMyReqSuccessListener(), createMyReqErrorListener()));
    // }

    @SuppressWarnings("rawtypes")
    private void getData(int mCurrentPage) {

        HttpVolleyRequest<List<MiuiPic>> request = new HttpVolleyRequest<List<MiuiPic>>(
                (Activity) mContext, true);
        request.HttpVolleyRequestGet(DataHandler.instance().getMiuiPic(mCurrentPage), null, null,
                createMyReqSuccessListener(), createMyReqErrorListener());
    }

    @SuppressWarnings("rawtypes")
    private Listener<List<MiuiPic>> createMyReqSuccessListener() {
        return new Listener<List<MiuiPic>>()
        {

            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(List<MiuiPic> response) {
                mUpdating = false;
                ptrGridView.onRefreshComplete();
                if (mCurrentPosition == 0) {
                    listMiuiPic.clear();
                }
//                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<MiuiPic>>()
//                {
//                }.getType();
//                listMiuiPic.addAll((List<MiuiPic>) mGson.fromJson(response.toString(), type));
                listMiuiPic.addAll((List<MiuiPic>) response);
                adapter.notifyDataSetChanged();
            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {
                mUpdating = false;
                ptrGridView.onRefreshComplete();
            }
        };
    }

    public static Fragment newInstance(String type) {
        MiuiFragment f = new MiuiFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {
        if ((!mUpdating) && (paramInt3 != 0)
                && (paramAbsListView.getLastVisiblePosition() == paramInt3 - 1)) {
            mUpdating = true;
            mCurrentPosition = mCurrentPosition + 1;
            getData(mCurrentPosition);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
        // TODO Auto-generated method stub

    }
}
