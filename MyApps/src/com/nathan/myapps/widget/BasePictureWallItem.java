package com.nathan.myapps.widget;

import java.io.Serializable;

public abstract class BasePictureWallItem implements Serializable {

    private static final long serialVersionUID = 7675730207876736280L;
    public int imageHeight;
    public boolean isMeasure;
    public int mHeight;
    public int mIndex;
    public int mLeft;
    public int mTop;
    public String mUri;
    public String mUrl;
    public int mWidth;

    public abstract int getImageHeight();

    public abstract int getImageWidth();
}