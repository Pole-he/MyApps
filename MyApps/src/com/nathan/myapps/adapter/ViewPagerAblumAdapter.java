package com.nathan.myapps.adapter;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

import com.android.volley.VolleyError;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.activity.ablum.AblumDetailActivity;
import com.nathan.myapps.bean.ablum.PicItem;
import com.nathan.myapps.bean.miui.MiuiPic;
import com.nathan.myapps.utils.Logger;
import com.nathan.myapps.widget.SimpleImageLoadingListener;
import com.nathan.myapps.widget.WaterFallNetworkImageView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ViewPagerAblumAdapter extends PagerAdapter {

    private List<PicItem> list;
    private List<MiuiPic> miuiList;
    private LayoutInflater inflater;
    private Context mContext;
    private int n = 0;
    private int height;

    public ViewPagerAblumAdapter(Context context, List<PicItem> list) {
        mContext = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public ViewPagerAblumAdapter(Context context, List<MiuiPic> list, int n) {
        mContext = context;
        this.miuiList = list;
        inflater = LayoutInflater.from(context);
        this.n = n;
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        // width = metric.widthPixels; // 屏幕宽度（像素）
        height = metric.heightPixels; // 屏幕高度（像素）
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public void finishUpdate(View container) {
    }

    @Override
    public int getCount() {
        if (n != 0) {
            return miuiList != null ? miuiList.size() : 0;
        }
        else {
            return list != null ? list.size() : 0;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        // 自定义的view
        View userLayout = inflater.inflate(R.layout.ablum_detail_item, view, false);
        PhotoView wtImageView = (PhotoView) userLayout.findViewById(R.id.imageView1);
        final FrameLayout flLoading = (FrameLayout) userLayout.findViewById(R.id.flLoading);
        if (n != 0) {
            final MiuiPic pic = miuiList.get(position);
            wtImageView.setImageUrl(pic.downloadUrlRoot + "jpeg/h" + height + "q80/"
                    + pic.desktopLocator, MyApplication.getInstance().mImageLoader,
                    new SimpleImageLoadingListener()
                    {

                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            flLoading.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            flLoading.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view,
                                VolleyError failReason) {
                            flLoading.setVisibility(View.GONE);
                        }
                    });

            wtImageView.setTag(pic);
        }
        else {
            final PicItem pic = list.get(position);

            wtImageView.setImageUrl(pic.picture_big_url, MyApplication.getInstance().mImageLoader,
                    new SimpleImageLoadingListener()
                    {

                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            flLoading.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            flLoading.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view,
                                VolleyError failReason) {
                            flLoading.setVisibility(View.GONE);
                        }
                    });

            wtImageView.setTag(pic);
        }
        // wtImageView.setOnClickListener(mOnClickListener);
        wtImageView.setOnPhotoTapListener(mOnClickListener);
        ((ViewPager) view).addView(userLayout, 0);
        return userLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View container) {
    }

    private OnPhotoTapListener mOnClickListener = new OnPhotoTapListener()
    {

        // @Override
        // public void onClick(View view) {
        // ((AblumDetailActivity) mContext).showControl();
        // }

        @Override
        public void onPhotoTap(View view, float x, float y) {
            ((AblumDetailActivity) mContext).showControl();

        }

    };
}
