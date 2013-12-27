package com.nathan.myapps.widget;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ViewSwitcher.ViewFactory;

public class AnimationImageSwitcher extends ImageSwitcher {

    private Context mContext;
    private int index = 0;
    private List<Drawable> listDrawable;

    private Animation translateFromLeft, translateFromRight, translateToLeft, translateToRight,
            translateFromTop, translateFromBottom, translateToTop, translateToBottom, alphaIn,
            alphaOut;
    private List<Animation[]> listAnimation = new ArrayList<Animation[]>();
    private Handler handler;
    private long timeSpan = 3000;

    public AnimationImageSwitcher(Context paramContext) {
        super(paramContext);
        mContext = paramContext;
        init();
    }

    public AnimationImageSwitcher(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        mContext = paramContext;
        init();
    }

    private void init() {
        initAnimation();
        setFactory(new ImageViewFactory(mContext));
    }

    public void setImageList(List<Drawable> list) {
        listDrawable = list;
    }

    public void stop() {
        handler.removeCallbacksAndMessages(null);
    }

    // 重写了的viewFactory
    class ImageViewFactory implements ViewFactory {

        private Context context;

        public ImageViewFactory(Context context) {
            this.context = context;
        }

        @SuppressLint("InlinedApi")
        @Override
        public View makeView() {
            // TODO Auto-generated method stub

            // 定义每个图像的显示大小
            ImageView iv = new ImageView(this.context);
            iv.setScaleType(ScaleType.FIT_XY);
            iv.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            return iv;
        }

    }

    public void startAutoFlowTimer() {
        handler = new Handler()
        {

            @Override
            public void handleMessage(Message msg) {
                index++;
                Animation anima[] = listAnimation.get((int) (Math.random() * 4));
                // 设置切入动画
                setInAnimation(anima[0]);
                // 设置切出动画
                setOutAnimation(anima[1]);
                setImageDrawable(listDrawable.get(index % listDrawable.size()));
                Message message = handler.obtainMessage(0);
                sendMessageDelayed(message, timeSpan);
            }
        };

        Message message = handler.obtainMessage(0);
        handler.sendMessageDelayed(message, timeSpan);
    }

    private void initAnimation() {
        translateFromLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        translateFromRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        translateToLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        translateToRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        translateFromTop = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        translateFromBottom = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        translateToTop = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f);
        translateToBottom = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f);
        alphaIn = new AlphaAnimation(0.0f, 1.0f);
        alphaOut = new AlphaAnimation(1.0f, 0.0f);

        translateFromLeft.setDuration(500);
        translateFromRight.setDuration(500);
        translateToLeft.setDuration(500);
        translateToRight.setDuration(500);
        translateFromTop.setDuration(500);
        translateFromBottom.setDuration(500);
        translateToTop.setDuration(500);
        translateToBottom.setDuration(500);
        alphaIn.setDuration(500);
        alphaOut.setDuration(500);

        Animation[] anim1 = { translateFromLeft, translateToRight };
        Animation[] anim2 = { translateFromRight, translateToLeft };
        Animation[] anim3 = { translateFromBottom, translateToTop };
        Animation[] anim4 = { translateFromTop, translateToBottom };
        Animation[] anim5 = { alphaIn, alphaOut };
        listAnimation.add(anim1);
        listAnimation.add(anim2);
        listAnimation.add(anim3);
        listAnimation.add(anim4);
        listAnimation.add(anim5);
    }
}
