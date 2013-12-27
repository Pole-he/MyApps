package com.nathan.myapps.widget;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageSwitcher;
import android.widget.ViewAnimator;

public class CloudImageView extends View {

    private static int mLeft = 0;
    private static int mTop = 0;
    private static int width = 0;
    private static int height = 0;
    private static int fullWidth = 0;
    private static int fullHeigh = 0;
    private BitmapDrawable drawable;
    Context mContext;
    private CloudHandler handler;
    private Handler handlerTime;
    private boolean directionLeft = true;
    private boolean directionTop = true;

    private int n = 0;
    private int m = 0;

    private BitmapDrawable[] drawableArray = new BitmapDrawable[3];

    public CloudImageView(Context paramContext) {
        super(paramContext);
        mContext = paramContext;
        init();
    }

    public CloudImageView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        mContext = paramContext;
        init();
    }

    public CloudImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet);
        mContext = paramContext;
        init();
    }

    @SuppressLint("NewApi")
    public void init() {
        // drawable =
        // (BitmapDrawable)mContext.getResources().getDrawable(R.drawable.cloudimage);
        AssetManager asm = mContext.getAssets();
        try {
            drawableArray[0] = (BitmapDrawable) Drawable.createFromStream(asm.open("main1.jpg"),
                    null);
            drawableArray[1] = (BitmapDrawable) Drawable.createFromStream(asm.open("main2.jpg"),
                    null);
            drawableArray[2] = (BitmapDrawable) Drawable.createFromStream(asm.open("main3.jpg"),
                    null);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        handler = new CloudHandler();
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessageDelayed(1, 300L);
        drawable = drawableArray[0];
    }

    public void stop() {
        handler.removeCallbacksAndMessages(null);
    }

    protected void onDraw(Canvas paramCanvas) {
        paramCanvas.translate(mLeft, mTop);
        n++;
        m = n / 200;
        drawableArray[m % 3].draw(paramCanvas);
        super.onDraw(paramCanvas);
    }

    private BitmapDrawable getDrawable() {
        return drawable;
    }

    protected void onLayout(boolean paramBoolean, int left, int top, int right, int bottom) {
        if (paramBoolean) {
            width = right - left;
            height = bottom - top;
            fullWidth = 5 * width >> 2;
            fullHeigh = 5 * height >> 2;
            for (BitmapDrawable drawable : drawableArray) {
                drawable.setBounds(0, 0, fullWidth, fullHeigh);
            }
            this.getLayoutParams().height = drawableArray[0].getBitmap().getHeight() * right
                    / drawableArray[0].getBitmap().getWidth();
        }
    }

    public class CloudHandler extends Handler {

        public void handleMessage(Message paramMessage) {
            if (directionLeft) {
                if (mLeft == width - fullWidth) {
                    mLeft++;
                    directionLeft = false;
                }
                else {
                    mLeft--;
                }

            }
            else {
                if (mLeft == 0) {
                    mLeft--;
                    directionLeft = true;
                }
                else {
                    mLeft++;
                }
            }
            if (directionTop) {
                if (mTop == height - fullHeigh) {
                    mTop++;
                    directionTop = false;
                }
                else {
                    mTop--;
                }

            }
            else {
                if (mTop == 0) {
                    mTop--;
                    directionTop = true;
                }
                else {
                    mTop++;
                }
            }
            CloudImageView.this.invalidate();
            handler.sendEmptyMessageDelayed(1, 50);
        }
    }
}
