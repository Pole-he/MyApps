package com.nathan.myapps.activity;

import com.android.volley.VolleyError;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.activity.ablum.AblumMainActivity;
import com.nathan.myapps.activity.at.AnimeTasteActivity;
import com.nathan.myapps.activity.music.MusicListActivity;
import com.nathan.myapps.bitmap.BitmapUtil;
import com.nathan.myapps.bitmap.StackBlurManager;
import com.nathan.myapps.db.UserInfoData;
import com.nathan.myapps.request.RequestManager;
import com.nathan.myapps.widget.CircleImageView;
import com.nathan.myapps.widget.MyFrameLayout;
import com.nathan.myapps.widget.SimpleImageLoadingListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class PoPoMainActivity extends Activity implements OnClickListener {

    private MyFrameLayout mfLayout;
    private TextView tvName;
    private CircleImageView cvPic;
    private ImageView ivMenu;
    private ImageView ivBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        findViewById();
        init();
    }

    private void init() {
        mfLayout.startAnimationChild();
        mfLayout.setChildClickListerner(this);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/BelshawDonutRobot.ttf");
        // 应用字体
        tvName.setTypeface(typeFace);
        ivMenu.setOnClickListener(this);
        tvName.setText(getIntent().getStringExtra("name"));
        cvPic.setImageUrl(getIntent().getStringExtra("picUrl"),
                MyApplication.getInstance().mImageLoader, new SimpleImageLoadingListener()
                {

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        StackBlurManager blur = new StackBlurManager(BitmapUtil
                                .Bytes2Bimap(RequestManager.getRequestQueue().getCache()
                                        .get(getIntent().getStringExtra("picUrl")).data));
                        ivBackground.setImageBitmap(blur.processNatively(5));
                        Animation anim = AnimationUtils.loadAnimation(PoPoMainActivity.this, R.anim.bgblur_alpha_in);
                        ivBackground.startAnimation(anim);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, VolleyError failReason) {
                    }
                });

    }

    private void findViewById() {
        mfLayout = (MyFrameLayout) this.findViewById(R.id.mf_activity_main_layout);
        tvName = (TextView) this.findViewById(R.id.userName);
        cvPic = (CircleImageView) this.findViewById(R.id.userPic);
        ivMenu = (ImageView) this.findViewById(R.id.iv_activity_main_menu);
        ivBackground = (ImageView) this.findViewById(R.id.iv_background);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {

        switch (paramMenuItem.getItemId()) {
        case R.id.action_settings:
            UserInfoData db = new UserInfoData(this);
            db.delete("1");
            startActivity(new Intent(this, WelcomeLoginActivity.class));
            finish();
            break;
        }
        return super.onOptionsItemSelected(paramMenuItem);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.iv_activity_main_menu:
            mfLayout.startAnimationChild();
            break;
        case 1:
            startActivity(new Intent(PoPoMainActivity.this, AblumMainActivity.class));
            break;
        case 2:
            startActivity(new Intent(PoPoMainActivity.this, MusicListActivity.class));
            break;
        case 3:
            startActivity(new Intent(PoPoMainActivity.this, AnimeTasteActivity.class));
            break;
        case 4:
            break;
        }

    }

}
