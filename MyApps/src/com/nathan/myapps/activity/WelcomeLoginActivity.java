package com.nathan.myapps.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import com.nathan.myapps.R;
import com.nathan.myapps.utils.Logger;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;

import com.tencent.tauth.Constants;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeLoginActivity extends Activity {

    private ImageView mShowPicture;
    private TextView mShowText;
    private TextView mRegister;
    private TextView mLogin;
    /**
     * 三个切换的动画
     */
    private Animation mFadeIn;
    private Animation mFadeInScale;
    private Animation mFadeOut;
    /**
     * 三个图片
     */
    private Drawable mPicture_1;
    private Drawable mPicture_2;
    private Drawable mPicture_3;

    public Tencent mTencent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcom_login);
        findViewById();
        init();
        setListener();
    }

    /**
     * 绑定UI
     */
    private void findViewById() {
        mShowPicture = (ImageView) findViewById(R.id.welcome_bg);
        mShowText = (TextView) findViewById(R.id.guide_content);
        mRegister = (TextView) findViewById(R.id.fast_register);
        mLogin = (TextView) findViewById(R.id.loginButton);
    }

    /**
     * 监听事件
     */
    private void setListener() {
        /**
         * 动画切换原理:开始时是用第一个渐现动画,当第一个动画结束时开始第二个放大动画,当第二个动画结束时调用第三个渐隐动画,
         * 第三个动画结束时修改显示的内容并且重新调用第一个动画,从而达到循环效果
         */
        mFadeIn.setAnimationListener(new AnimationListener()
        {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                mShowPicture.startAnimation(mFadeInScale);
            }
        });
        mFadeInScale.setAnimationListener(new AnimationListener()
        {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                mShowPicture.startAnimation(mFadeOut);
            }
        });
        mFadeOut.setAnimationListener(new AnimationListener()
        {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                /**
                 * 这里其实有些写的不好,还可以采用更多的方式来判断当前显示的是第几个,从而修改数据,
                 * 我这里只是简单的采用获取当前显示的图片来进行判断。
                 */
                if (mShowPicture.getDrawable().equals(mPicture_1)) {
                    mShowText.setText("同学情,请珍藏");
                    mShowPicture.setImageDrawable(mPicture_2);
                }
                else if (mShowPicture.getDrawable().equals(mPicture_2)) {
                    mShowText.setText("共奋斗,同分享");
                    mShowPicture.setImageDrawable(mPicture_3);
                }
                else if (mShowPicture.getDrawable().equals(mPicture_3)) {
                    mShowText.setText("儿时友,莫相忘");
                    mShowPicture.setImageDrawable(mPicture_1);
                }
                mShowPicture.startAnimation(mFadeIn);
            }
        });
        mRegister.setOnClickListener(new OnClickListener()
        {

            public void onClick(View v) {
                Toast.makeText(WelcomeLoginActivity.this, "暂时无法提供此功能", Toast.LENGTH_SHORT).show();
            }
        });

        mLogin.setOnClickListener(new OnClickListener()
        {

            public void onClick(View v) {
                onClickLogin();
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        initAnim();
        initPicture();
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/BelshawDonutRobot.ttf");
        // 应用字体
        mShowText.setTypeface(typeFace);
        /**
         * 界面刚开始显示的内容
         */
        mShowPicture.setImageDrawable(mPicture_1);
        mShowText.setText("儿时友,莫相忘");
        mShowPicture.startAnimation(mFadeIn);

        mTencent = Tencent.createInstance("100577807", this.getApplicationContext());
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        mFadeIn = AnimationUtils.loadAnimation(WelcomeLoginActivity.this,
                R.anim.v5_0_1_guide_welcome_fade_in);
        mFadeIn.setDuration(1000);
        mFadeInScale = AnimationUtils.loadAnimation(WelcomeLoginActivity.this,
                R.anim.v5_0_1_guide_welcome_fade_in_scale);
        mFadeInScale.setDuration(6000);

        mFadeOut = AnimationUtils.loadAnimation(WelcomeLoginActivity.this,
                R.anim.v5_0_1_guide_welcome_fade_out);
        mFadeOut.setDuration(1000);

    }

    /**
     * 初始化图片
     */
    private void initPicture() {
        try {
            AssetManager asm = this.getAssets();
            mPicture_1 = Drawable.createFromStream(asm.open("login_1.jpg"), null);
            mPicture_2 = Drawable.createFromStream(asm.open("login_3.jpg"), null);
            mPicture_3 = Drawable.createFromStream(asm.open("login_4.jpg"), null);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void onClickLogin() {
        startActivity(new Intent(WelcomeLoginActivity.this, MainActivity.class));
        finish();
        if (!mTencent.isSessionValid()) {
            IUiListener listener = new BaseUiListener()
            {

                @Override
                protected void doComplete(JSONObject values) {
                    updateUserInfo();
                }
            };
            mTencent.login(this, "all", listener);
        }
        else {
            mTencent.logout(this);
            updateUserInfo();
        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(JSONObject response) {
            doComplete(response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            Logger.e("MainActivity.this", "onError: " + e.errorDetail);

        }

        @Override
        public void onCancel() {

        }
    }

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IRequestListener requestListener = new IRequestListener()
            {

                @Override
                public void onUnknowException(Exception e, Object state) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onSocketTimeoutException(SocketTimeoutException e, Object state) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onNetworkUnavailableException(NetworkUnavailableException e,
                        Object state) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onMalformedURLException(MalformedURLException e, Object state) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onJSONException(JSONException e, Object state) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onIOException(IOException e, Object state) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onHttpStatusException(HttpStatusException e, Object state) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onConnectTimeoutException(ConnectTimeoutException e, Object state) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onComplete(final JSONObject response, Object state) {
                    // TODO Auto-generated method stub
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    new Thread()
                    {

                        @Override
                        public void run() {
                            if (response.has("figureurl")) {
                                Bitmap bitmap = null;
                                // bitmap =
                                // Util.getbitmap(response.getString("figureurl_qq_2"));
                            }
                        }

                    }.start();
                }
            };
            mTencent.requestAsync(Constants.GRAPH_SIMPLE_USER_INFO, null, Constants.HTTP_GET,
                    requestListener, null);
        }
    }

}
