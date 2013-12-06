package com.nathan.myapps.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.bean.ablum.PicItem;
import com.nathan.myapps.utils.Logger;
import com.nathan.myapps.widget.AtNetworkImageView;
import com.nathan.myapps.widget.ScaleImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

public class StaggeredAdapter extends BaseAdapter {

    private List<PicItem> list;
    private Context mContext;
    private LayoutInflater mInflater = null;

    public StaggeredAdapter(Context context, List<PicItem> list) {
        this.mContext = context;
        this.list = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        // TODO Auto-generated method stub
        return i;
    }

    @Override
    public long getItemId(int i) {
        // TODO Auto-generated method stub
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_staggered_demo, null);
            holder = new ViewHolder();
            holder.imageView = (ScaleImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final PicItem pic = list.get(position);

        // ImageListener listener = ImageLoader.getImageListener(
        // holder.imageView,
        // R.drawable.placeholder_thumb, R.drawable.placeholder_fail);
        // MyApplication.getInstance().mImageLoader.get(pic.picture_small_url,
        // listener);
        
//        holder.imageView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener()
//        {
//            
//            @Override
//            public boolean onPreDraw() {
//                holder.imageView.setImageUrl(pic.picture_small_url,
//                        MyApplication.getInstance().mImageLoader);
//                return true;
//            }
//        });

        holder.imageView.setTag(pic);
        holder.imageView.setOnClickListener(mClickListener);
        return convertView;
    }

    private OnClickListener mClickListener = new OnClickListener()
    {

        @Override
        public void onClick(View v) {

        }
    };

     static class ViewHolder {

         ScaleImageView imageView;
    }
}
