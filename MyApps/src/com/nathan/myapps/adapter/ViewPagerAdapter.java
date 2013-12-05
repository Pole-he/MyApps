package com.nathan.myapps.adapter;

import java.util.List;

import android.R.string;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewPager适配器
 *      
 * @author user
 *
 */
public class ViewPagerAdapter extends PagerAdapter
{
    
    private static String TAG = "ViewPagerAdapter";
    /**
     * 界面列表
     */
    private List<View> mViews;
    
    public ViewPagerAdapter(List<View> mViews)
    {
        this.mViews = mViews;
    }
    
    public int getRealCount()
    {
        return mViews.size();
    }
    /**
     * 获取当前页面数
     */
    @Override
    public int getCount()
    {
       // Log.v(TAG, "getCount" + mViews.size());
        //return Integer.MAX_VALUE;
        return mViews.size();
    }
    
    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        
       // Log.v(TAG, "isViewFromObject" + (view == object));
        return view == object;
    }
    /**
     * 适配器给container容器添加视图
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        Log.e(TAG, "instantiateItem" + position);
        position = position % mViews.size();

        if(mViews.get(position).getParent() ==null)
        container.addView(mViews.get(position), 0);
        
        return mViews.get(position);
        
    }
    /**
     * 适配器移除container容器中的视图
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        Log.e(TAG, "destroyItem" + position);
//        position = position % mViews.size();
//        container.removeView(mViews.get(position));        
    }  

}
