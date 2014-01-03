package com.nathan.myapps.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.activity.ablum.AblumMainActivity;
import com.nathan.myapps.activity.at.AnimeTasteActivity;
import com.nathan.myapps.activity.music.MusicListActivity;
import com.nathan.myapps.activity.music.service.PoPoInterface;
import com.nathan.myapps.activity.music.service.ServiceToken;
import com.nathan.myapps.bitmap.BitmapUtil;
import com.nathan.myapps.bitmap.StackBlurManager;
import com.nathan.myapps.db.UserInfoData;
import com.nathan.myapps.request.RequestManager;
import com.nathan.myapps.utils.ApiUtils;
import com.nathan.myapps.utils.MusicUtils;
import com.nathan.myapps.widget.AnimationImageSwitcher;
import com.nathan.myapps.widget.CircleImageView;
import com.nathan.myapps.widget.MyFrameLayout;
import com.nathan.myapps.widget.SimpleImageLoadingListener;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
    private AnimationImageSwitcher is_bg;

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
                        // StackBlurManager blur = new
                        // StackBlurManager(BitmapUtil
                        // .Bytes2Bimap(RequestManager.getRequestQueue().getCache()
                        // .get(getIntent().getStringExtra("picUrl")).data));
                        // ivBackground.setImageBitmap(blur.processNatively(5));
                        // Animation anim =
                        // AnimationUtils.loadAnimation(PoPoMainActivity.this,
                        // R.anim.bgblur_alpha_in);
                        // ivBackground.startAnimation(anim);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, VolleyError failReason) {
                    }
                });

        List<Drawable> list = new ArrayList<Drawable>();
        try {
            AssetManager asm = this.getAssets();
            List<String> fileName = ApiUtils.getFilePaths(this, "blur");
            for (String name : fileName) {
                list.add((BitmapDrawable) Drawable.createFromStream(asm.open("blur/"+name), null));
            }

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        is_bg.setImageDrawable(list.get(0));
        is_bg.setImageList(list);

    }

    @Override
    protected void onResume() {
        //is_bg.startAutoFlowTimer();
        super.onResume();
    }
    
    @Override
    protected void onStop() {
        is_bg.stop();
        super.onStop();
    }
    private void findViewById() {
        mfLayout = (MyFrameLayout) this.findViewById(R.id.mf_activity_main_layout);
        tvName = (TextView) this.findViewById(R.id.userName);
        cvPic = (CircleImageView) this.findViewById(R.id.userPic);
        ivMenu = (ImageView) this.findViewById(R.id.iv_activity_main_menu);
        ivBackground = (ImageView) this.findViewById(R.id.iv_background);
        is_bg = (AnimationImageSwitcher) this.findViewById(R.id.bg_image);
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
