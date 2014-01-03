package com.nathan.myapps.activity.music;

import java.io.IOException;
import java.util.List;

import com.nathan.myapps.R;
import com.nathan.myapps.fragment.BaseFragment;
import com.nathan.myapps.utils.ApiUtils;
import com.nathan.myapps.widget.PlayImageSwitcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicPlayFragment extends BaseFragment implements OnClickListener {

    private Context mContext;
    private PlayImageSwitcher isCover;
    private FrameLayout flBack;
    private TextView tvTitle, tvArtist;
    private SeekBar sbProgress;
    private TextView tvBegin, tvEnd;
    private ImageView ivPre, ivPlay, ivNext;
    private List<Drawable> picBitmap;
    private RelativeLayout rlBackground;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.music_play_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        findViews(view);
        init();
        addListeners();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void addListeners() {
        flBack.setOnClickListener(this);
        ivPre.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivNext.setOnClickListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void init() {
        try {
            rlBackground.setBackgroundDrawable(Drawable.createFromStream(mContext.getAssets().open("blur/blur5.jpg"), null));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setArtist(String artist) {
        tvArtist.setText(artist);
    }
    
    public void setPicBitmap(List<Drawable> picBitmap)
    {
        this.picBitmap = picBitmap;
        isCover.setImageList(picBitmap);
        isCover.stop();
        isCover.startAutoFlowTimer();
    }

    public void setMaxProgress(int max) {
        tvBegin.setText("0:00");
        sbProgress.setProgress(0);
        sbProgress.setMax(max);
        tvEnd.setText(ApiUtils.getStringTime(max));
    }

    public void setProgress(int progress) {
        sbProgress.setProgress(progress);
        tvBegin.setText(ApiUtils.getStringTime(progress));
    }

    public void setCurrentTime() {

    }

    public void setEndTime() {

    }

    @Override
    public void findViews(View paramView) {
        rlBackground = (RelativeLayout) paramView.findViewById(R.id.rl_background);
        isCover = (PlayImageSwitcher) paramView.findViewById(R.id.is_cover);
        flBack = (FrameLayout) paramView.findViewById(R.id.fl_play_back);
        tvTitle = (TextView) paramView.findViewById(R.id.play_title);
        tvArtist = (TextView) paramView.findViewById(R.id.play_artist);
        sbProgress = (SeekBar) paramView.findViewById(R.id.skb_progress_play);
        tvBegin = (TextView) paramView.findViewById(R.id.tv_begin_time);
        tvEnd = (TextView) paramView.findViewById(R.id.tv_end_time);
        ivPre = (ImageView) paramView.findViewById(R.id.iv_music_prev);
        ivPlay = (ImageView) paramView.findViewById(R.id.iv_music_play);
        ivNext = (ImageView) paramView.findViewById(R.id.iv_music_next);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub

    }
}
