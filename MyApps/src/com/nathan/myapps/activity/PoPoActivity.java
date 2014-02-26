package com.nathan.myapps.activity;

import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.db.UserInfoData;
import com.nathan.myapps.fragment.FragmentController;
import com.nathan.myapps.fragment.ablum.AblumFragment;
import com.nathan.myapps.fragment.at.AnimeTasteFragment;
import com.nathan.myapps.fragment.miui.MiuiFragment;
import com.nathan.myapps.fragment.music.MusicListFragment;
import com.nathan.myapps.widget.CircleCustomImageView;
import com.nathan.myapps.widget.TypefaceTextView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class PoPoActivity extends FragmentActivity implements OnClickListener {

    public final static String MENU_1 = "menu_1";
    public final static String MENU_2 = "menu_2";
    public final static String MENU_3 = "menu_3";
    public final static String MENU_4 = "menu_4";
    private TypefaceTextView ttMenu1, ttMenu2, ttMenu3, ttMenu4;
    private CircleCustomImageView ccPic;
    private FragmentController mFragmentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popo_main_activity);
        findViewById();
        init();
        setListener();
    }

    private void setListener() {
        ttMenu1.setOnClickListener(this);
        ttMenu2.setOnClickListener(this);
        ttMenu3.setOnClickListener(this);
        ttMenu4.setOnClickListener(this);
    }

    private void init() {
        ccPic.setImageUrl(getIntent().getStringExtra("picUrl"),
                MyApplication.getInstance().mImageLoader);
//        ccPic.setImageBitmap(BitmapUtil.Bytes2Bimap(RequestManager.getRequestQueue().getCache().get(getIntent().getStringExtra("picUrl")).data));
        mFragmentController = new FragmentController(this.getSupportFragmentManager(),
                R.id.fl_content);
    }

    private void findViewById() {
        ccPic = (CircleCustomImageView) findViewById(R.id.userPic);
        ttMenu1 = (TypefaceTextView) findViewById(R.id.po_menu_1);
        ttMenu2 = (TypefaceTextView) findViewById(R.id.po_menu_2);
        ttMenu3 = (TypefaceTextView) findViewById(R.id.po_menu_3);
        ttMenu4 = (TypefaceTextView) findViewById(R.id.po_menu_4);
    }

    private void reSetAllMenu() {
        ttMenu1.setSelected(false);
        ttMenu2.setSelected(false);
        ttMenu3.setSelected(false);
        ttMenu4.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.po_menu_1:
            mFragmentController.add(MiuiFragment.class, MENU_1, null);
            break;
        case R.id.po_menu_2:
            mFragmentController.add(AblumFragment.class, MENU_2, null);
            break;
        case R.id.po_menu_3:
            mFragmentController.add(AnimeTasteFragment.class, MENU_3, null);
            break;
        case R.id.po_menu_4:
            mFragmentController.add(MusicListFragment.class, MENU_4, null);
            break;
        }
        reSetAllMenu();
        v.setSelected(true);
        // getTitle(0);
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
}
