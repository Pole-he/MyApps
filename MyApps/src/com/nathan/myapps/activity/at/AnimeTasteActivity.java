package com.nathan.myapps.activity.at;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.baidu.cyberplayer.utils.T;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.adapter.VideoListAdapter;
import com.nathan.myapps.adapter.ViewFlowImageAdapter;
import com.nathan.myapps.adapter.ViewPagerAdapter;
import com.nathan.myapps.bean.at.ListJson;
import com.nathan.myapps.bean.at.VideoItem;
import com.nathan.myapps.request.GsonRequest;
import com.nathan.myapps.request.HttpVolleyRequest;
import com.nathan.myapps.request.RequestManager;
import com.nathan.myapps.utils.DataHandler;
import com.nathan.myapps.widget.AtNetworkImageView;
import com.nathan.myapps.widget.CircleFlowIndicator;
import com.nathan.myapps.widget.FixedSpeedScroller;
import com.nathan.myapps.widget.LayersLayout;
import com.nathan.myapps.widget.LoadingView;
import com.nathan.myapps.widget.ViewFlow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AnimeTasteActivity extends ActionBarActivity implements OnScrollListener,
        OnClickListener {

    private ListView lvVideo;
    private LayoutInflater mLayoutInflater;
    private Boolean mUpdating = true;// 防止多次加载
    private int mCurrentPage = 0;
    private VideoListAdapter mVideoListAdapter;
    private List<VideoItem> listVideo = new ArrayList<VideoItem>();
    private LoadingView mLoadingView;
    private ViewFlow mShowViewFlow;
    private LayersLayout layersLayout;

    private boolean isReversible = true;
    /**
     * 记录当前自动滑动的状态，true就滑动，false停止滑动
     */
    private boolean isContinue = true;

    private Handler mHandler;

    private Timer timer;

    private static boolean isSleep = true;
    /**
     * 设置viewpager的初始页面
     */
    private static final int initPositon = 50000;
    /**
     * viewpager的当前页面
     */
    private static int currentPosition = 0;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.at_activity_start);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(
                getResources().getDrawable(R.drawable.bbuton_info));
        findViewById();
        init();
        getData(mCurrentPage);
    }

    @SuppressWarnings("rawtypes")
    private void getData(int mCurrentPage) {

        HttpVolleyRequest<ListJson> request = new HttpVolleyRequest<ListJson>(this);
        request.HttpVolleyRequestGet(DataHandler.instance().getList(mCurrentPage), ListJson.class,
                VideoItem.class, createMyReqSuccessListener(), createMyReqErrorListener());
        showLoadingView();
    }

    private void init() {
        mVideoListAdapter = new VideoListAdapter(AnimeTasteActivity.this, listVideo);
        lvVideo.setAdapter(mVideoListAdapter);
    }

    private void showLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = new LoadingView(this, 3);
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

    private void findViewById() {
        lvVideo = (ListView) this.findViewById(R.id.videoList);
        layersLayout = (LayersLayout) findViewById(R.id.layerslayout);// 获得自定义图层，对触屏事件进行重定向

        mLayoutInflater = LayoutInflater.from(this);
        this.lvVideo.setOnScrollListener(this);
        View localView = this.mLayoutInflater.inflate(R.layout.at_gallery_item, null, false);
        this.lvVideo.addHeaderView(localView);
        this.mShowViewFlow = ((ViewFlow) localView.findViewById(R.id.viewflow));
        CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
        mShowViewFlow.setFlowIndicator(indic);
        
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

                    ViewFlowImageAdapter mVpAdapter = new ViewFlowImageAdapter(
                            AnimeTasteActivity.this, (List<VideoItem>) response.feature);

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

    // 为ViewPager添加图片
    private List<View> getViewPager(List<VideoItem> list) {
        List<View> mViews = new ArrayList<View>();

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < list.size(); i++) {
            AtNetworkImageView mImageView = new AtNetworkImageView(this);
            mImageView.setLayoutParams(mParams);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setTag(list.get(i));
            mImageView.setOnClickListener(this);
            mImageView.setImageUrl(list.get(i).DetailPic, MyApplication.getInstance().mImageLoader);
            mViews.add(mImageView);
        }
        return mViews;
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

    /**
     * viewpager页面变化的监听器
     * 
     * @author user
     * 
     */
    class MyPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {
            Log.e("", position + "");
            currentPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // TODO Auto-generated method stub

        }

    }

    /**
     * 监听手势监听器
     * 
     * @author user
     * 
     */
    class MyTouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            // PointF localPointF1 = new PointF();
            // PointF localPointF2 = new PointF();
            // int i = event.getAction();
            // if ((i == 0) || (i == 2) || (i == 1)) {
            // ((ViewGroup) v).requestDisallowInterceptTouchEvent(true);
            // if ((localPointF1.x != localPointF2.x) || (localPointF1.y !=
            // localPointF2.y))
            // ;
            // }

            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                isContinue = false;
                ((ViewGroup) v).requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                ((ViewGroup) v).requestDisallowInterceptTouchEvent(false);

            default:
                isContinue = true;
                isSleep = true;
                break;
            }
            return false;
        }

    }

    /**
     * 设置线程间隔
     */
    private void sleep(long time) {
        try {
            Thread.sleep(time);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.at_start, paramMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {

        // boolean bool = true;
        // if (paramMenuItem.getItemId() == 2131034241)
        // //startActivity(new Intent(this.mContext, SettingActivity.class));
        // while (true)
        // {
        // return bool;
        // if (paramMenuItem.getItemId() == 2131034239)
        // {
        // startActivity(new Intent(this.mContext, FavActivity.class));
        // continue;
        // }

        switch (paramMenuItem.getItemId()) {
        case android.R.id.home:
            finish();
            break;
        }
        return super.onOptionsItemSelected(paramMenuItem);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AnimeTasteDetailActivity.class);
        intent.putExtra("VideoItem", (VideoItem) v.getTag());
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mShowViewFlow.onConfigurationChanged(newConfig);
    }
}
