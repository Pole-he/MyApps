package com.nathan.myapps.activity;

import java.io.IOException;

import com.nathan.myapps.R;
import com.nathan.myapps.utils.Logger;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
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
                startActivity(new Intent(WelcomeLoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        initAnim();
        initPicture();
        Typeface typeFace = Typeface.createFromAsset(getAssets(),
                "fonts/BelshawDonutRobot.ttf");
        // 应用字体
        mShowText.setTypeface(typeFace);
        /**
         * 界面刚开始显示的内容
         */
        mShowPicture.setImageDrawable(mPicture_1);
        mShowText.setText("儿时友,莫相忘");
        mShowPicture.startAnimation(mFadeIn);
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
}
