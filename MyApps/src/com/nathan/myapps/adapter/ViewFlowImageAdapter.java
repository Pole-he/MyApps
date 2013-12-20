package com.nathan.myapps.adapter;

import java.util.List;

import com.android.volley.toolbox.NetworkImageView;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.activity.at.AnimeTasteDetailActivity;
import com.nathan.myapps.adapter.VideoListAdapter.ViewHolder;
import com.nathan.myapps.bean.at.VideoItem;
import com.nathan.myapps.request.RequestManager;
import com.nathan.myapps.widget.AtNetworkImageView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewFlowImageAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<VideoItem> list;

    public ViewFlowImageAdapter(Context context, List<VideoItem> feature) {
        mContext = context;
        list = feature;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // 返回很大的值使得getView中的position不断增大来实现循环
        return Integer.MAX_VALUE;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.netimage_item, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (NetworkImageView) convertView.findViewById(R.id.imgView);
            viewHolder.image.setDefaultImageResId(R.drawable.big_bg);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        VideoItem item = list.get(position % list.size());
        viewHolder.image.setImageUrl(item.DetailPic, MyApplication.getInstance().mImageLoader);
        viewHolder.image.setTag(item);
        viewHolder.image.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AnimeTasteDetailActivity.class);
                intent.putExtra("VideoItem", (VideoItem) v.getTag());
                mContext.startActivity(intent);
            }
        });
//        convertView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, AnimeTasteDetailActivity.class);
//                intent.putExtra("VideoItem", (VideoItem)((ViewHolder) v.getTag()).image.getTag());
//                mContext.startActivity(intent);
//            }
//        });
        return convertView;
    }

    static class ViewHolder {

        NetworkImageView image;
    }

}
