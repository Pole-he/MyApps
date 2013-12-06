package com.nathan.myapps.adapter;

import java.util.List;

import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.bean.ablum.PicItem;

import com.nathan.myapps.utils.Logger;
import com.nathan.myapps.widget.FadeInNetworkImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class WaterFallAdapter extends BaseAdapter {

    private List<PicItem> list;
    private Context mContext;
    private LayoutInflater mInflater = null;

    public WaterFallAdapter(Context context, List<PicItem> list) {
        this.mContext = context;
        this.list = list;
        Logger.e("", list.size()+"///");
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
            convertView = mInflater.inflate(R.layout.ablum_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_pic = (FadeInNetworkImageView) convertView.findViewById(R.id.fade_pic);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PicItem pic = list.get(i);
        viewHolder.iv_pic.setImageUrl(pic.picture_small_url,
                MyApplication.getInstance().mImageLoader);
        viewHolder.iv_pic.setTag(pic);
        viewHolder.iv_pic.setOnClickListener(mOnClickListener);
        return convertView;
    }

    private OnClickListener mOnClickListener = new OnClickListener()
    {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

        }
    };

    static class ViewHolder {

        FadeInNetworkImageView iv_pic;
    }
}
