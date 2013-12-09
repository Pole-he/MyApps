package com.nathan.myapps.activity.ablum;

import java.util.List;

import com.nathan.myapps.R;
import com.nathan.myapps.adapter.ViewPagerAblumAdapter;
import com.nathan.myapps.bean.ablum.PicItem;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AblumDetailActivity extends ActionBarActivity implements OnClickListener {

    private ViewPager vp;
    private RelativeLayout rlTop;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvCount;

    private LinearLayout llBottom;
    private TextView tvLike;
    private TextView tvLoad;
    private TextView tvShare;

    private List<PicItem> picList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ablum_detail_view);
        findViewById();
        init();
        setListener();
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        tvLike.setOnClickListener(this);
    }

    @SuppressWarnings("unchecked")
    private void init() {
        picList = (List<PicItem>) getIntent().getSerializableExtra("data");
        int intoPostion = getIntent().getIntExtra("intoPosition", 0);
        ViewPagerAblumAdapter adapter = new ViewPagerAblumAdapter(this, picList);
        vp.setAdapter(adapter);
        vp.setCurrentItem(intoPostion);
        vp.setOnPageChangeListener(mPageChangeListener);

        tvCount.setText((intoPostion+1) + "/" + picList.size());
        tvLike.setText(getResources().getString(R.string.favorite)
                + picList.get(intoPostion).like_count);
        tvTitle.setText("美女");
    }

    private void findViewById() {
        vp = (ViewPager) findViewById(R.id.pager);
        /* top layout */
        rlTop = (RelativeLayout) this.findViewById(R.id.top_bar);
        ivBack = (ImageView) this.findViewById(R.id.btn_back);
        tvTitle = (TextView) this.findViewById(R.id.title);
        tvCount = (TextView) this.findViewById(R.id.count);
        /* bottom layout */
        llBottom = (LinearLayout) this.findViewById(R.id.bottom_bar);
        tvLike = (TextView) this.findViewById(R.id.btn_like);
        tvLoad = (TextView) this.findViewById(R.id.btn_save);
        tvShare = (TextView) this.findViewById(R.id.btn_share);
    }

    public void showControl() {
        if (rlTop.getVisibility() == View.GONE && llBottom.getVisibility() == View.GONE) {
            rlTop.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.VISIBLE);
        }
        else {
            rlTop.setVisibility(View.GONE);
            llBottom.setVisibility(View.GONE);
        }
    }

    private OnPageChangeListener mPageChangeListener = new OnPageChangeListener()
    {

        @Override
        public void onPageSelected(int position) {
            tvCount.setText((position+1) + "/" + picList.size());
            tvLike.setText(getResources().getString(R.string.favorite)
                    + picList.get(position).like_count);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (rlTop.getVisibility() == View.VISIBLE && llBottom.getVisibility() == View.VISIBLE) {
                rlTop.setVisibility(View.GONE);
                llBottom.setVisibility(View.GONE);
            }

        }
    };

    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.btn_back:
            onBackPressed();
            break;
        case R.id.title:
            onBackPressed();
            break;
        case R.id.btn_like:
            String like = tvLike.getText().toString();
            int num = Integer.valueOf(like.substring(2, like.length()))+1;
            tvLike.setText(getResources().getString(R.string.favorite)
                    + num);
            break;
        }

    }

}
