package com.nathan.myapps.widget;

import com.nathan.myapps.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class MyFrameLayout extends FrameLayout {

    private FrameLayout flOne;
    private FrameLayout flSecond;
    private FrameLayout flThird;
    private FrameLayout flFour;

    private LayoutParams params1;
    private LayoutParams params2;
    private LayoutParams params3;
    private LayoutParams params4;

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        flOne = new FrameLayout(context);
        flOne.setBackgroundResource(R.drawable.bbuton_success);
        flOne.setClickable(true);
        params1 = new FrameLayout.LayoutParams(context, attrs);
        addView(flOne);
        MyTextView tv1 = new MyTextView(context, attrs);
        FrameLayout.LayoutParams tvLytp1 = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        tvLytp1.gravity = Gravity.CENTER;
        tv1.setLayoutParams(tvLytp1);
        tv1.setText("ALBUM");
        flOne.addView(tv1);

        flSecond = new FrameLayout(context);
        flSecond.setBackgroundResource(R.drawable.bbuton_danger);
        flSecond.setClickable(true);
        params2 = new FrameLayout.LayoutParams(context, attrs);
        addView(flSecond);
        MyTextView tv2 = new MyTextView(context, attrs);
        FrameLayout.LayoutParams tvLytp2 = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        tvLytp2.gravity = Gravity.CENTER;
        tv2.setLayoutParams(tvLytp2);
        tv2.setText("MUSIC");
        flSecond.addView(tv2);

        flThird = new FrameLayout(context);
        flThird.setBackgroundResource(R.drawable.bbuton_info);
        flThird.setClickable(true);
        params3 = new FrameLayout.LayoutParams(context, attrs);
        addView(flThird);
        MyTextView tv3 = new MyTextView(context, attrs);
        FrameLayout.LayoutParams tvLytp3 = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        tvLytp3.gravity = Gravity.CENTER;
        tv3.setLayoutParams(tvLytp3);
        tv3.setText("ANIMATION");
        flThird.addView(tv3);

        flFour = new FrameLayout(context);
        flFour.setBackgroundResource(R.drawable.bbuton_warning);
        flFour.setClickable(true);
        params4 = new FrameLayout.LayoutParams(context, attrs);
        addView(flFour);
        MyTextView tv4 = new MyTextView(context, attrs);
        FrameLayout.LayoutParams tvLytp4 = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        tvLytp4.gravity = Gravity.CENTER;
        tv4.setLayoutParams(tvLytp4);
        tv4.setText("?");
        flFour.addView(tv4);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
        int height = (bottom - top) / 2;
        int width = (right - left) / 2;

        params1.width = width;
        params1.height = height;
        flOne.setLayoutParams(params1);

        params2.width = width;
        params2.height = height;
        params2.leftMargin = width;
        flSecond.setLayoutParams(params2);

        params3.width = width;
        params3.height = height;
        params3.topMargin = height;
        flThird.setLayoutParams(params3);

        params4.width = width;
        params4.height = height;
        params4.topMargin = height;
        params4.leftMargin = width;
        flFour.setLayoutParams(params4);

    }


    public void setChildClickListerner(OnClickListener listerner)
    {
        flOne.setOnClickListener(listerner);
        flOne.setId(1);
        flSecond.setOnClickListener(listerner);
        flSecond.setId(2);
        flThird.setOnClickListener(listerner);
        flThird.setId(3);
        flFour.setOnClickListener(listerner);
        flFour.setId(4);
    }
    
    public void startAnimationChild() {
        flOne.startAnimation(getAnimation1());
        flSecond.startAnimation(getAnimation2());
        flThird.startAnimation(getAnimation3());
        flFour.startAnimation(getAnimation4());
    }

    private TranslateAnimation translate1;
    private TranslateAnimation translate2;
    private TranslateAnimation translate3;
    private TranslateAnimation translate4;

    private TranslateAnimation getAnimation1() {
        if (translate1 == null) {
            translate1 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f);
            translate1.setDuration(1500);
        }
        return translate1;
    }

    private TranslateAnimation getAnimation2() {
        if (translate2 == null) {
            translate2 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f);
            translate2.setDuration(1500);
        }
        return translate2;
    }

    private TranslateAnimation getAnimation3() {
        if (translate3 == null) {
            translate3 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f);
            translate3.setDuration(1500);
        }
        return translate3;
    }

    private TranslateAnimation getAnimation4() {
        if (translate4 == null) {
            translate4 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f);
            translate4.setDuration(1500);
        }
        return translate4;
    }
}
