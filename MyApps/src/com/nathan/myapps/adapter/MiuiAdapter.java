package com.nathan.myapps.adapter;

import java.io.Serializable;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.activity.ablum.AblumDetailActivity;
import com.nathan.myapps.bean.ablum.PicItem;
import com.nathan.myapps.bean.miui.MiuiPic;
import com.nathan.myapps.widget.WaterFallNetworkImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MiuiAdapter extends BaseAdapter {

    private List<MiuiPic> list;
    private Context mContext;
    private LayoutInflater mInflater = null;
    private int width;
    private int height;

    public MiuiAdapter(Context context, List<MiuiPic> list) {
        this.mContext = context;
        this.list = list;
        mInflater = LayoutInflater.from(mContext);
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels; // 屏幕宽度（像素）
        height = metric.heightPixels; // 屏幕高度（像素）
        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）

        width = (int) (width - (density * 1)) / 2;
        height = 211 * width / 236;
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
            convertView = mInflater.inflate(R.layout.miui_list_item, null);
            holder = new ViewHolder();
            holder.imageView = (WaterFallNetworkImageView) convertView
                    .findViewById(R.id.imageView1);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final MiuiPic pic = list.get(position);
        holder.imageView.mWidth = width;
        holder.imageView.mHeight = height;

        // ImageListener listener =
        // ImageLoader.getImageListener(holder.imageView,
        // android.R.color.black, R.drawable.placeholder_fail);
        // MyApplication.getInstance().mImageLoader.get(pic.picture_small_url,
        // listener);

        // holder.imageView.getViewTreeObserver().addOnPreDrawListener(new
        // OnPreDrawListener()
        // {
        //
        // @Override
        // public boolean onPreDraw() {
        // return true;
        // }
        // });
        holder.imageView.setImageUrl(pic.downloadUrlRoot+"jpeg/w"+width+"q80/"+pic.desktopLocator,
                MyApplication.getInstance().mImageLoader);
        holder.imageView.setTag(list);
        holder.imageView.setTag(R.id.water_position, position);
        holder.imageView.setOnClickListener(mClickListener);
        return convertView;
    }

    private OnClickListener mClickListener = new OnClickListener()
    {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, AblumDetailActivity.class);
            intent.putExtra("miui", (Serializable) v.getTag());
            intent.putExtra("intoPosition", (Integer) v.getTag(R.id.water_position));
            intent.putExtra("type", "");
            intent.putExtra("flag", 1);
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(android.R.anim.fade_in,
                    android.R.anim.fade_out);
        }
    };

    static class ViewHolder {

        WaterFallNetworkImageView imageView;
    }
}
