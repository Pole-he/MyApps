package com.nathan.myapps.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.activity.VideoViewPlayingActivity;
import com.nathan.myapps.bean.at.VideoItem;
import com.nathan.myapps.custom.FadeInNetworkImageView;
import com.nathan.myapps.utils.VideoUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VideoListAdapter extends BaseAdapter {

    private List<VideoItem> list;
    private Context mContext;
    private LayoutInflater mInflater = null;

    public VideoListAdapter(Context context, List<VideoItem> list) {
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
    public View getView(int i, View convertView, ViewGroup viewgroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.video_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_pic = (FadeInNetworkImageView) convertView.findViewById(R.id.thumb);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        VideoItem video = list.get(i);
        // ImageListener listener =
        // ImageLoader.getImageListener(viewHolder.iv_pic,
        // R.drawable.placeholder_thumb, R.drawable.placeholder_fail);
        // MyApplication.getInstance().mImageLoader.get(video.HomePic,
        // listener);
        viewHolder.iv_pic.setDefaultImageResId(R.drawable.placeholder_thumb);
        viewHolder.iv_pic.setErrorImageResId(R.drawable.placeholder_fail);
        viewHolder.iv_pic.setImageUrl(video.HomePic, MyApplication.getInstance().mImageLoader);
        viewHolder.iv_pic.setTag(video);
        
        viewHolder.tv_title.setText(video.Name);
        viewHolder.tv_content.setText(video.Brief);
        
        convertView.setOnClickListener(playVideo);
        
        return convertView;
    }

    private OnClickListener playVideo = new OnClickListener()
    {

        @Override
        public void onClick(View v) {
            String source = ((VideoItem)((ViewHolder)v.getTag()).iv_pic.getTag()).VideoUrl;
            Intent intent = new Intent(mContext, VideoViewPlayingActivity.class);
            intent.setData(Uri.parse(VideoUtils.getHDVideoUrl(source)));
            mContext.startActivity(intent);

        }
    };

    static class ViewHolder {

        FadeInNetworkImageView iv_pic;
        TextView tv_title, tv_content;
    }
}
