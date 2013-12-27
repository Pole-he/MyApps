package com.nathan.myapps.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;
import com.nathan.myapps.R;

public class AlphaImageView extends NetworkImageView {

    private static final int FADE_IN_TIME_MS = 500;

    public AlphaImageView(Context context) {
        super(context);
        init();
    }

    public AlphaImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlphaImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        TransitionDrawable td = new TransitionDrawable(new Drawable[] {
                new ColorDrawable(android.R.color.transparent),
                new BitmapDrawable(getContext().getResources(), bm) });

        setImageDrawable(td);
        td.startTransition(FADE_IN_TIME_MS);
    }
}