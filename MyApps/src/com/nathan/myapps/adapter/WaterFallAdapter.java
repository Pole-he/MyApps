package com.nathan.myapps.adapter;

import java.io.Serializable ;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.activity.ablum.AblumDetailActivity ;
import com.nathan.myapps.bean.ablum.PicItem;
import com.nathan.myapps.utils.Logger;
import com.nathan.myapps.widget.AtNetworkImageView;
import com.nathan.myapps.widget.WaterFallNetworkImageView ;
import com.nathan.myapps.widget.ScaleImageView;

import android.R.integer ;
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

public class WaterFallAdapter extends BaseAdapter {

    private List<PicItem> list;
    private Context mContext;
    private LayoutInflater mInflater = null;

    public WaterFallAdapter(Context context, List<PicItem> list) {
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
            holder.imageView = (WaterFallNetworkImageView) convertView.findViewById(R.id.imageView1);
//            holder.imageView.setDefaultImageResId ( R.drawable.placeholder_thumb ) ;
//            holder.imageView.setErrorImageResId ( R.drawable.placeholder_fail ) ;
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final PicItem pic = list.get(position);
        holder.imageView.mWidth = pic.picture_width;
        holder.imageView.mHeight = pic.picture_height;
//        ImageListener listener = ImageLoader.getImageListener(holder.imageView,
//                android.R.color.black, R.drawable.placeholder_fail);
//        MyApplication.getInstance().mImageLoader.get(pic.picture_small_url, listener);

//         holder.imageView.getViewTreeObserver().addOnPreDrawListener(new
//         OnPreDrawListener()
//         {
//        
//         @Override
//         public boolean onPreDraw() {
//         return true;
//         }
//         });
         holder.imageView.setImageUrl(pic.picture_small_url,
                         MyApplication.getInstance().mImageLoader);

        holder.imageView.setTag(list);
        holder.imageView.setTag ( R.id.water_position, position);
        holder.imageView.setOnClickListener(mClickListener);
        return convertView;
    }

    private OnClickListener mClickListener = new OnClickListener()
    {

        @Override
        public void onClick(View v) {
                Intent intent = new Intent(mContext,AblumDetailActivity.class);
                intent.putExtra("data", (Serializable)v.getTag ( ));
                intent.putExtra("intoPosition", (Integer)v.getTag ( R.id.water_position ));
                mContext.startActivity(intent);
        }
    };

    static class ViewHolder {

            WaterFallNetworkImageView imageView;
    }
}
