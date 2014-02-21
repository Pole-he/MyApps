package com.nathan.myapps.widget;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class PlayImageSwitcher extends ImageSwitcher {

    private Context mContext;
    private int index = 0;
    private List<Drawable> listDrawable;

    private Animation alphaIn, alphaOut;
    private Handler handler;
    private long timeSpan = 5000;

    public PlayImageSwitcher(Context paramContext) {
        super(paramContext);
        mContext = paramContext;
        init();
    }

    public PlayImageSwitcher(Context paramContext, AttributeSet paramAttributeSet) {
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
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
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
        if (handler == null) {
            handler = new Handler()
            {

                @Override
                public void handleMessage(Message msg) {
                    index++;
                    setImageDrawable(listDrawable.get(index % listDrawable.size()));
                    Message message = handler.obtainMessage(0);
                    sendMessageDelayed(message, timeSpan);
                }
            };
        }

        Message message = handler.obtainMessage(0);
        handler.sendMessageDelayed(message, timeSpan);
    }

    private void initAnimation() {

        alphaIn = new AlphaAnimation(0.0f, 1.0f);
        alphaOut = new AlphaAnimation(1.0f, 0.0f);

        alphaIn.setDuration(500);
        alphaOut.setDuration(500);
        // 设置切入动画
        setInAnimation(alphaIn);
        // 设置切出动画
        setOutAnimation(alphaOut);
    }
}
