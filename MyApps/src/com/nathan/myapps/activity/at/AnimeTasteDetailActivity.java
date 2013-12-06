package com.nathan.myapps.activity.at;

import java.util.List;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.activity.at.AnimeTasteActivity.MyPageChangeListener;
import com.nathan.myapps.activity.at.AnimeTasteActivity.MyTouchListener;
import com.nathan.myapps.adapter.ViewPagerAdapter;
import com.nathan.myapps.bean.at.ListJson;
import com.nathan.myapps.bean.at.VideoItem;
import com.nathan.myapps.request.GsonRequest;
import com.nathan.myapps.request.HttpVolleyRequest;
import com.nathan.myapps.request.RequestManager;
import com.nathan.myapps.utils.DataHandler;
import com.nathan.myapps.widget.AtNetworkImageView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnimeTasteDetailActivity extends ActionBarActivity implements OnClickListener {

    private AtNetworkImageView mDetailImageView;
    private ImageButton mPrePlayButton;
    private TextView mAutherTextView;
    private TextView mContentTextView;
    private TextView mTitleTextView;
    private LinearLayout mRecomendList;
    private View mRecommandView;
    private LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.at_activity_play);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(
                getResources().getDrawable(R.drawable.bbuton_info));
        findViewById();
        getData(5);
        init();
        setListener();

    }

    private void getData(int mCurrentPage) {

        HttpVolleyRequest<ListJson> request = new HttpVolleyRequest<ListJson>(this);
        request.HttpVolleyRequestGet(DataHandler.instance().getRandom(mCurrentPage),
                ListJson.class, VideoItem.class, createMyReqSuccessListener(),
                createMyReqErrorListener());
    }

    private void setListener() {
        mPrePlayButton.setOnClickListener(this);

    }

    private void init() {
        VideoItem video = (VideoItem) getIntent().getSerializableExtra("VideoItem");
        mDetailImageView.setImageUrl(video.DetailPic, MyApplication.getInstance().mImageLoader);
        mTitleTextView.setText(video.Name);
        mAutherTextView.setText(video.Author);
        mContentTextView.setText(video.Brief);
        mPrePlayButton.setTag(video.VideoUrl);
        mPrePlayButton.setVisibility(View.VISIBLE);
    }

    private void findViewById() {
        mDetailImageView = (AtNetworkImageView) this.findViewById(R.id.detailPic);
        mPrePlayButton = (ImageButton) this.findViewById(R.id.pre_play_button);
        mTitleTextView = (TextView) this.findViewById(R.id.title);
        mAutherTextView = (TextView) this.findViewById(R.id.author);
        mContentTextView = (TextView) this.findViewById(R.id.content);
        mRecomendList = (LinearLayout) this.findViewById(R.id.recommend_list);
        mRecommandView = (View) this.findViewById(R.id.recommand_view);
        this.mLayoutInflater = ((LayoutInflater) this.getSystemService("layout_inflater"));
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.at_start, paramMenu);
        return true;
    }

    private boolean isFav = false;

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {

        switch (paramMenuItem.getItemId()) {
        case android.R.id.home:
            finish();
            break;
        case R.id.action_fav:
            if (!isFav) {
                paramMenuItem.setIcon(getResources().getDrawable(R.drawable.ab_fav_active));
                isFav = true;
            }
            else {
                paramMenuItem.setIcon(getResources().getDrawable(R.drawable.ab_fav_normal));
                isFav = false;
            }
            break;
        }
        return super.onOptionsItemSelected(paramMenuItem);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
        case R.id.pre_play_button:
            intent.setClass(this, VideoViewPlayingActivity.class);
            intent.setData(Uri.parse((String) v.getTag()));
            startActivity(intent);
            break;
        default:
            intent.setClass(this, AnimeTasteDetailActivity.class);
            intent.putExtra("VideoItem", (VideoItem) v.getTag());
            startActivity(intent);
            finish();
            break;
        }

    }

    @SuppressWarnings("rawtypes")
    private Listener<ListJson> createMyReqSuccessListener() {
        return new Listener<ListJson>()
        {

            @Override
            public void onResponse(ListJson response) {
                @SuppressWarnings("unchecked")
                List<VideoItem> list = (List<VideoItem>) response.list;
                for (int i = 0; i < list.size(); i++) {
                    {
                        LinearLayout localLinearLayout = (LinearLayout) AnimeTasteDetailActivity.this.mLayoutInflater
                                .inflate(R.layout.at_recommend_item, null);
                        AtNetworkImageView localImageView = (AtNetworkImageView) localLinearLayout
                                .findViewById(R.id.thumb);
                        TextView localTextView1 = (TextView) localLinearLayout
                                .findViewById(R.id.recommand_title);
                        TextView localTextView2 = (TextView) localLinearLayout
                                .findViewById(R.id.recommand_content);

                        localImageView.setImageUrl(list.get(i).HomePic,
                                MyApplication.getInstance().mImageLoader);
                        localTextView1.setText(list.get(i).Name);
                        localTextView2.setText(list.get(i).Brief);
                        localLinearLayout.setTag(list.get(i));
                        localLinearLayout.setOnClickListener(AnimeTasteDetailActivity.this);
                        View localView = (View) localLinearLayout.findViewById(R.id.divide_line);
                        if ((i == -1 + list.size()) && (localView != null))
                            localLinearLayout.removeView(localView);
                        AnimeTasteDetailActivity.this.mRecomendList.addView(localLinearLayout);
                    }
                }
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
