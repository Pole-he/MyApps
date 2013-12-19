package com.nathan.myapps.widget;

import com.android.volley.toolbox.NetworkImageView;
import com.nathan.myapps.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class CircleImageViewWithoutFrame extends NetworkImageView {

    private Context mContext;

    public CircleImageViewWithoutFrame(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public CircleImageViewWithoutFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public CircleImageViewWithoutFrame(Context context) {
        super(context);
        mContext = context;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(getCircleDrawable(getResources(), background));
    }

    @Override
    public void setBackgroundResource(int resid) {
        // Don't worry, we don't need to override it,because it will be call
        // setBackgroundDrawable(Drawable background)
        super.setBackgroundResource(resid);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        // Don't worry, we don't need to override it,because it will be call
        // setImageDrawable(Drawable drawable)
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(getCircleDrawable(getResources(), drawable));
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.scale_out_center);
        startAnimation(anim);
    }

    @Override
    public void setImageURI(Uri uri) {
        // cheat it, let's change the way to implement
        super.setImageURI(uri);
        Drawable img = getCircleDrawable(getResources(), getDrawable());
        super.setImageDrawable(img);
    }

    @Override
    public void setImageResource(int resId) {
        // cheat it, let's change the way to implement
        Drawable img = getCircleDrawable(getResources(), resId);
        super.setImageDrawable(img);
    }

    private static final int SPACING_LINE = 3;

    private static Paint mCirclePaint = null;
    private static Paint mLinePaint = null;

    private static Paint getCirclePaint() {
        if (mCirclePaint == null) {
            mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mCirclePaint.setColor(Color.WHITE);
            // mCirclePaint.setStrokeWidth(8);
        }
        return mCirclePaint;
    }

    private Paint getLinePaint() {
        if (mLinePaint == null) {
            mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mLinePaint.setStyle(Style.STROKE);
            // You can use it to change the width of the line
            mLinePaint.setStrokeWidth(5);
            // You can use it to change the color of the line
            mLinePaint.setColor(Color.WHITE);
        }
        return mLinePaint;
    }

    /**
     * You can call this method to generate the circular bitmap, even if you
     * don't use this class
     */
    public Bitmap getCircleBitmap(Bitmap src) {

        if (src == null) {
            return null;
        }

        return toRoundBitmap(src);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPX = bitmap.getWidth() / 2;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return outBitmap;
    }

    /**
     * 转换图片成圆形
     * 
     * @param bitmap
     *            传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        }
        else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    public Bitmap getCircleBitmap(Drawable src) {
        if (src instanceof BitmapDrawable) {
            return getCircleBitmap(((BitmapDrawable) src).getBitmap());
        }
        else {
            // now, i don't know how to do...
            throw new UnsupportedException("Unsupported");
        }
    }

    public Bitmap getRoundCornerBitmap(Bitmap bitmap, float roundPX) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap bitmap2 = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(getPaddingLeft(), getPaddingTop(), width - getPaddingRight(),
                height - getPaddingBottom());
        // final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        paint.setColor(color);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmap2;
    }

    public Bitmap getCircleBitmap(Resources res, int id) {
        return getCircleBitmap(BitmapFactory.decodeResource(res, id));
    }

    public Drawable getCircleDrawable(Resources res, Bitmap src) {
        return new BitmapDrawable(res, getCircleBitmap(src));
    }

    public Drawable getCircleDrawable(Resources res, Drawable src) {
        return new BitmapDrawable(res, getCircleBitmap(src));
    }

    public Drawable getCircleDrawable(Resources res, int id) {
        return new BitmapDrawable(res, getCircleBitmap(res, id));
    }

    static class UnsupportedException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public UnsupportedException(String str) {
            super(str);
        }
    }
}