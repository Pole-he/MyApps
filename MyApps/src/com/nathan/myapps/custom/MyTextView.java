package com.nathan.myapps.custom;

import com.nathan.myapps.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        setBackgroundColor(Color.TRANSPARENT);
        setTextSize(25.0f);
        setTextColor(Color.WHITE);
        setShadowLayer(1.0f, 1.0f, 1.0f, context.getResources().getColor(R.color.black));
        // 将字体文件保存在assets/fonts/目录下，www.linuxidc.com创建Typeface对象
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                "fonts/BelshawDonutRobot.ttf");
        // 应用字体
        setTypeface(typeFace);
    }

}
