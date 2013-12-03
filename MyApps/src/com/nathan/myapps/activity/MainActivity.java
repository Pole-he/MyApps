package com.nathan.myapps.activity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.nathan.myapps.R;
import com.nathan.myapps.bean.at.ListJson;
import com.nathan.myapps.bean.at.VideoItem;
import com.nathan.myapps.custom.MyFrameLayout;
import com.nathan.myapps.request.GsonRequest;
import com.nathan.myapps.request.RequestManager;
import com.nathan.myapps.utils.DataHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    private MyFrameLayout mfLayout;
    private TextView tvLogo;
    private ImageView ivMenu;

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
        tvLogo.setTypeface(typeFace);
        ivMenu.setOnClickListener(this);
    }

    private void findViewById() {
        mfLayout = (MyFrameLayout) this.findViewById(R.id.mf_activity_main_layout);
        tvLogo = (TextView) this.findViewById(R.id.tv_activity_main_logo);
        ivMenu = (ImageView) this.findViewById(R.id.iv_activity_main_menu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    
    
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.iv_activity_main_menu:
            mfLayout.startAnimationChild();
            break;
        case 1:
            break;
        case 2:
            break;
        case 3:
            startActivity(new Intent(MainActivity.this,AnimeTasteActivity.class));
            break;
        case 4:
            break;
        }

    }

}
