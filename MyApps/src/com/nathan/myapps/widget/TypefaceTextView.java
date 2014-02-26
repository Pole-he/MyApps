package com.nathan.myapps.widget;

import com.nathan.myapps.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TypefaceTextView extends TextView {

    public TypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Style
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TypefaceText);
        String type = ta.getString(R.styleable.TypefaceText_typefacetext);

        Typeface typeFace = null;
        if (type.equals("0")) {
            typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/BrightDiamond.ttf");
        }
        else if (type.equals("1")) {
            typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/BelshawDonutRobot.ttf");
        }
        else if (type.equals("2")) {
            typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/CooperBlackStd.otf");
        }
        // 应用字体
        setTypeface(typeFace);
        ta.recycle();
    }

}
