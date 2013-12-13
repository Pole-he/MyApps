package com.nathan.myapps.adapter;

import java.io.Serializable;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.activity.ablum.AblumDetailActivity;
import com.nathan.myapps.bean.ablum.PicItem;
import com.nathan.myapps.widget.WaterFallNetworkImageView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

public class WaterFallAdapter extends BaseAdapter {

    private List<PicItem> list;
    private Context mContext;
    private LayoutInflater mInflater = null;
    private String type;

    public WaterFallAdapter(Context context, List<PicItem> list, String type) {
        this.mContext = context;
        this.list = list;
        this.type = type;
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
            convertView = mInflater.inflate(R.layout.ablum_item, null);
            holder = new ViewHolder();
            holder.imageView = (WaterFallNetworkImageView) convertView
                    .findViewById(R.id.imageView1);
            holder.flLike = (FrameLayout) convertView.findViewById(R.id.ablum_item_like);
            holder.tvNum = (TextView) convertView.findViewById(R.id.ablum_item_num);

            // holder.imageView.setDefaultImageResId (
            // R.drawable.placeholder_thumb ) ;
            // holder.imageView.setErrorImageResId ( R.drawable.placeholder_fail
            // ) ;
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

        // holder.imageView.getViewTreeObserver().addOnPreDrawListener(new
        // OnPreDrawListener()
        // {
        //
        // @Override
        // public boolean onPreDraw() {
        // return true;
        // }
        // });
        holder.imageView.setImageUrl(pic.picture_small_url,
                MyApplication.getInstance().mImageLoader);
        holder.imageView.setTag(list);
        holder.imageView.setTag(R.id.water_position, position);
        holder.imageView.setOnClickListener(mClickListener);
        holder.tvNum.setText(pic.like_count);
        holder.flLike.setOnClickListener(mClickLike);
        holder.flLike.setTag(holder);
        return convertView;
    }

    private OnClickListener mClickLike = new OnClickListener()
    {

        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
            holder.tvNum.setText((Integer.valueOf(holder.tvNum.getText().toString()) + 1) + "");
            PicItem pic = ((List<PicItem>) holder.imageView.getTag())
                    .get((Integer) holder.imageView.getTag(R.id.water_position));
            pic.like_count = (Integer.valueOf(pic.like_count) + 1) + "";
        }
    };
    private OnClickListener mClickListener = new OnClickListener()
    {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, AblumDetailActivity.class);
            intent.putExtra("data", (Serializable) v.getTag());
            intent.putExtra("intoPosition", (Integer) v.getTag(R.id.water_position));
            intent.putExtra("type", type);
            mContext.startActivity(intent);
        }
    };

    static class ViewHolder {

        FrameLayout flLike;
        TextView tvNum;
        WaterFallNetworkImageView imageView;
    }
}
