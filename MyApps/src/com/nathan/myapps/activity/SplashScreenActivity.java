package com.nathan.myapps.activity;

import com.nathan.myapps.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends Activity {

    private ImageView ivScreen;
    private TextView tvLogo, tvAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        findViewByid();
        init();
    }

    private void init() {
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/CooperBlackStd.otf");
        // 应用字体
        tvLogo.setTypeface(typeFace);
        Animation blurAlpha = AnimationUtils.loadAnimation(this, R.anim.bgblur_alpha_in);

        ivScreen.startAnimation(blurAlpha);
        blurAlpha.setAnimationListener(new AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation) {
                ivScreen.setBackgroundResource(R.drawable.splash_screen_blur);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation logoAlpha1 = AnimationUtils.loadAnimation(SplashScreenActivity.this,
                        R.anim.logoanimin);
                Animation logoAlpha2 = AnimationUtils.loadAnimation(SplashScreenActivity.this,
                        R.anim.logoanimin);
                logoAlpha1.setStartOffset(400L);
                logoAlpha2.setStartOffset(600L);
                tvLogo.startAnimation(logoAlpha1);
                tvAuthor.startAnimation(logoAlpha2);
                tvLogo.setVisibility(View.VISIBLE);
                tvAuthor.setVisibility(View.VISIBLE);
                logoAlpha2.setAnimationListener(new AnimationListener()
                {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        new Thread(new Runnable()
                        {

                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                }
                                catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(SplashScreenActivity.this, WelcomeLoginActivity.class));
                                finish();
                            }
                        }).start();

                    }
                });
            }
        });
    }

    private void findViewByid() {
        ivScreen = (ImageView) this.findViewById(R.id.iv_splash);
        tvLogo = (TextView) this.findViewById(R.id.splash_logo);
        tvAuthor = (TextView) this.findViewById(R.id.splash_author);
    }
}
