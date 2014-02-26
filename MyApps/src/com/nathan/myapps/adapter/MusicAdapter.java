package com.nathan.myapps.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.NetworkImageView;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.activity.PoPoActivity;
import com.nathan.myapps.activity.music.MusicListActivity;
import com.nathan.myapps.adapter.VideoListAdapter.ViewHolder;
import com.nathan.myapps.bean.at.VideoItem;
import com.nathan.myapps.bean.music.DataSong;
import com.nathan.myapps.bean.music.ListMusic;
import com.nathan.myapps.bean.music.ListSong;
import com.nathan.myapps.bean.music.MusicItem;
import com.nathan.myapps.bean.music.Song;
import com.nathan.myapps.bean.music.SongItem;
import com.nathan.myapps.bean.music.SongList;
import com.nathan.myapps.fragment.music.MusicListFragment;
import com.nathan.myapps.request.HttpVolleyRequest;
import com.nathan.myapps.utils.ApiUtils;
import com.nathan.myapps.utils.DataHandler;
import com.nathan.myapps.utils.Logger;
import com.nathan.myapps.utils.UrlUtils;
import com.nathan.myapps.widget.AtNetworkImageView;
import com.nathan.myapps.widget.CircleImageView;
import com.nathan.myapps.widget.CircleImageViewWithoutFrame;
import com.nathan.myapps.widget.LoadListenerImageView;
import com.nathan.myapps.widget.ScaleImageView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicAdapter extends BaseAdapter {

    private List<MusicItem> list;
    private Context mContext;
    private LayoutInflater mInflater = null;
    private List<Boolean> listStatus;
    private List<String> song_id = new ArrayList<String>();

    public MusicAdapter(List<MusicItem> list, Context context) {
        this.list = list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);

        listStatus = new ArrayList<Boolean>();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.music_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.userPic = (CircleImageViewWithoutFrame) convertView
                    .findViewById(R.id.iconUserPic);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.iconUserName);
            viewHolder.userTime = (TextView) convertView.findViewById(R.id.iconUserTime);

            viewHolder.cover = (NetworkImageView) convertView.findViewById(R.id.cover);
            viewHolder.musicTitle = (TextView) convertView.findViewById(R.id.musicTitle);
            viewHolder.musicContent = (TextView) convertView.findViewById(R.id.musicContet);
            viewHolder.musicPlay = (ImageView) convertView.findViewById(R.id.musicPlay);
            viewHolder.flBottom_1 = (FrameLayout) convertView.findViewById(R.id.fl_comment);
            viewHolder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            viewHolder.flBottom_2 = (FrameLayout) convertView.findViewById(R.id.fl_forward);
            viewHolder.tv_forward = (TextView) convertView.findViewById(R.id.tv_forward);
            viewHolder.flBottom_3 = (FrameLayout) convertView.findViewById(R.id.fl_collect);
            viewHolder.tv_collection = (TextView) convertView.findViewById(R.id.tv_collect);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MusicItem music = list.get(position);
        // ImageListener listener =
        // ImageLoader.getImageListener(viewHolder.iv_pic,
        // R.drawable.placeholder_thumb, R.drawable.placeholder_fail);
        // MyApplication.getInstance().mImageLoader.get(video.HomePic,
        // listener);
        viewHolder.userPic.setImageUrl(UrlUtils.removeSpace(music.user.pic),
                MyApplication.getInstance().mImageLoader);
        viewHolder.userName.setText(music.user.nick_name);
        viewHolder.userTime.setText(ApiUtils.getCreateTimeString(new Date(Long
                .valueOf(music.create_at + "000"))));
        if (music.pics != null && music.pics[0] != null) {
            viewHolder.cover.setImageUrl(music.pics[0], MyApplication.getInstance().mImageLoader);
        }
        else {
            viewHolder.cover.setImageResource(R.drawable.social_music_circle_bkg);
        }

        // 如果是多首歌曲
        if (music.songlist != null) {
            viewHolder.musicTitle.setText(music.songlistname);
            viewHolder.musicTitle.setCompoundDrawables(
                    mContext.getResources().getDrawable(R.drawable.icon_social_songlist), null,
                    null, null);
        }
        else {
            viewHolder.musicTitle.setText(music.song.song_name + " - " + music.song.singer_name);
            viewHolder.musicTitle.setCompoundDrawables(
                    mContext.getResources().getDrawable(R.drawable.icon_social_song), null, null,
                    null);
        }

        viewHolder.musicContent.setText(music.tweet);
        viewHolder.tv_comment.setText(music.comment_count + "");
        viewHolder.tv_collection.setText(music.favorite_count + "");
        viewHolder.musicPlay.setTag(music);
        viewHolder.musicPlay.setTag(R.id.music_flag, true);

        if (listStatus.get(position)) {
            viewHolder.musicPlay.setImageResource(R.drawable.icon_music_circle_pause);
        }
        else {
            viewHolder.musicPlay.setImageResource(R.drawable.icon_music_circle_play);
        }
        viewHolder.musicPlay.setOnClickListener(mPlayClick);
        return convertView;
    }

    static class ViewHolder {

        CircleImageViewWithoutFrame userPic;
        TextView userName, userTime;
        NetworkImageView cover;
        ImageView musicPlay;
        TextView musicTitle;
        TextView musicContent;
        FrameLayout flBottom_1;
        TextView tv_comment;
        FrameLayout flBottom_2;
        TextView tv_forward;
        FrameLayout flBottom_3;
        TextView tv_collection;
    }

    private int mLastPosition = -1;
    private View mLastView;
    private OnClickListener mPlayClick = new OnClickListener()
    {

        @Override
        public void onClick(View view) {
            MusicItem music = (MusicItem) view.getTag();
            int mPosition = list.indexOf(music);
            if (mLastPosition != mPosition) {
                if (song_id.size() == 0) {
                    for (MusicItem musicItem : list) {
                        if (musicItem.songlist != null) {
                            // for (SongList song : musicItem.songlist) {
                            //
                            // }
                            song_id.add(musicItem.songlist.get(0).song_id);
                        }
                        else {
                            song_id.add(musicItem.song.song_id);
                        }
                    }
                    getData(song_id);
                }
                else {
                    playPosition(mPosition);
                }

                if (mLastPosition != -1) {
                    listStatus.set(mLastPosition, false);
                }
                if (mLastView != null) {
                    ((ImageView) mLastView).setImageResource(R.drawable.icon_music_circle_play);
                }
                ((ImageView) view).setImageResource(R.drawable.icon_music_circle_pause);

                listStatus.set(mPosition, true);

                mLastPosition = mPosition;
                mLastView = view;

            }
            else {
                if (listStatus.get(mLastPosition)) {
                    ((MusicListActivity) mContext).pasueMusic();
                    ((ImageView) view).setImageResource(R.drawable.icon_music_circle_play);
                    listStatus.set(mLastPosition, false);
                }
                else {
                    ((MusicListActivity) mContext).pasueMusic();
                    ((ImageView) view).setImageResource(R.drawable.icon_music_circle_pause);
                    listStatus.set(mLastPosition, true);
                }
            }

        }

    };

    private void getData(List<String> song_id) {
        HttpVolleyRequest<ListMusic> request = new HttpVolleyRequest<ListMusic>((Activity) mContext);
        request.HttpVolleyRequestGet(DataHandler.instance().getMusicPlay(song_id), ListMusic.class,
                DataSong.class, createMyReqSuccessListener(), createMyReqErrorListener());
    }

    @SuppressWarnings("rawtypes")
    private Listener<ListMusic> createMyReqSuccessListener() {
        return new Listener<ListMusic>()
        {

            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(ListMusic response) {
                List<String> SongUrl = new ArrayList<String>();
                List<Integer> allTimeList = new ArrayList<Integer>();
                for (DataSong data : (List<DataSong>) response.data) {
                    if (data.url_list != null) {
                        // for (SongItem song : data.url_list) {
                        // SongUrl.add(song.url);
                        // }
                        SongUrl.add(data.url_list.get(data.url_list.size() - 1).url);
                        allTimeList
                                .add(ApiUtils.getSongTime(data.url_list.get(data.url_list.size() - 1).duration));
                    }
                }
                if (((Activity) mContext) instanceof MusicListActivity) {
                    ((MusicListActivity) mContext).getListSong(SongUrl, allTimeList, mLastPosition);
                }
                else {
                    ((MusicListFragment) ((PoPoActivity) mContext).getSupportFragmentManager()
                            .findFragmentByTag(PoPoActivity.MENU_4)).getListSong(SongUrl,
                            allTimeList, mLastPosition);
                }
            }
        };
    }

    private void playPosition(int position) {
        if (((Activity) mContext) instanceof MusicListActivity) {
            ((MusicListActivity) mContext).playFile(position);
        }
        else {
            ((MusicListFragment) ((PoPoActivity) mContext).getSupportFragmentManager()
                    .findFragmentByTag(PoPoActivity.MENU_4)).playFile(position);
        }
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };
    }

    public void initListStatus(int size) {
        for (int i = 0; i < size; i++) {
            listStatus.set(i, false);
        }
    }

    public void setListStatus(int size) {
        for (int i = 0; i < size; i++) {
            listStatus.add(false);
        }
    }
}
