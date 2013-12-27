package com.nathan.myapps.activity.music;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.nathan.myapps.MyApplication;
import com.nathan.myapps.R;
import com.nathan.myapps.activity.music.service.PoPoInterface;
import com.nathan.myapps.activity.music.service.ServiceToken;
import com.nathan.myapps.activity.music.xml.IFeedback;
import com.nathan.myapps.activity.music.xml.PicXml;
import com.nathan.myapps.activity.music.xml.PicXmlTask;
import com.nathan.myapps.adapter.MusicAdapter;
import com.nathan.myapps.bean.music.ListMusic;
import com.nathan.myapps.bean.music.MusicItem;
import com.nathan.myapps.request.HttpVolleyRequest;
import com.nathan.myapps.url.URLs;
import com.nathan.myapps.utils.DataHandler;
import com.nathan.myapps.utils.Logger;
import com.nathan.myapps.utils.MusicUtils;
import com.nathan.myapps.widget.AlphaImageView;
import com.nathan.myapps.widget.LoadingView;
import com.nathan.myapps.widget.SlidingLayer;
import com.nathan.myapps.widget.SlidingLayer.OnInteractListener;
import com.sds.android.lib.text.TTTextUtils;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicListActivity extends ActionBarActivity implements ServiceConnection,
        OnClickListener, IFeedback {

    private ListView lvMusic;
    private List<MusicItem> listMusic = new ArrayList<MusicItem>();
    private MusicAdapter mMusicListAdapter;
    private LoadingView mLoadingView;

    private ServiceToken mToken;

    private TextView tvTitle;
    private TextView tvSinger;
    private ImageView ivPlay, ivPause, ivNext;
    private AlphaImageView ivPic;
    private SeekBar sbMusic;
    private int mCurrentPosition;
    private int currentTime;
    private int allTime;
    private List<Integer> allTimeList;
    private SlidingLayer mSlidingLayer;
    View view;
    private boolean hasMeasured = false;
    private MusicPlayFragment mMusicPlayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(
                getResources().getDrawable(R.drawable.actionbar_music));
        findViewById();
        init();
        getData(0);
        blindServer();
    }

    private void blindServer() {

    }

    @SuppressWarnings("rawtypes")
    private void getData(int mCurrentPage) {

        HttpVolleyRequest<ListMusic> request = new HttpVolleyRequest<ListMusic>(this);
        request.HttpVolleyRequestGet(DataHandler.instance().getMusic(mCurrentPage),
                ListMusic.class, MusicItem.class, createMyReqSuccessListener(),
                createMyReqErrorListener());
        showLoadingView();
    }

    private void showLoadingView() {
        if (mLoadingView == null) {
            mLoadingView = new LoadingView(this, 2);
        }
        if (!mLoadingView.isShowing()) {
            mLoadingView.show();
        }
    }

    private void dimissLoadingView() {
        if (mLoadingView.isShowing()) {
            mLoadingView.dismiss();
        }
    }

    private void init() {
        mMusicListAdapter = new MusicAdapter(listMusic, MusicListActivity.this);
        lvMusic.setAdapter(mMusicListAdapter);
        ivPlay.setOnClickListener(this);
        ivPause.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        pauseState();
        view.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener()
        {

            @Override
            public boolean onPreDraw() {
                if (hasMeasured == false) {
                    mSlidingLayer.setOffsetTouch(view.getLeft());
                    hasMeasured = true;
                }
                return true;
            }
        });
        mSlidingLayer.setStickTo(SlidingLayer.STICK_TO_BOTTOM);
        mSlidingLayer.setOffsetWidth(getResources().getDimensionPixelOffset(
                R.dimen.play_control_bar_height));
        mSlidingLayer.setOnInteractListener(new OnInteractListener()
        {

            @Override
            public void onOpened() {
                // TODO Auto-generated method stub

                //getSupportActionBar().hide();
            }

            @Override
            public void onOpen() {

            }

            @Override
            public void onClosed() {
                // TODO Auto-generated method stub

                //getSupportActionBar().show();
            }

            @Override
            public void onClose() {

            }
        });
    }

    private void findViewById() {
        lvMusic = (ListView) findViewById(R.id.musicList);
        tvTitle = (TextView) findViewById(R.id.play_control_title);
        tvSinger = (TextView) findViewById(R.id.play_control_artist);
        ivPlay = (ImageView) findViewById(R.id.btn_play);
        ivPause = (ImageView) findViewById(R.id.btn_pause);
        ivNext = (ImageView) findViewById(R.id.btn_next);
        view = (View) findViewById(R.id.play_button_stub);
        ivPic = (AlphaImageView) findViewById(R.id.list_album);
        sbMusic = (SeekBar) findViewById(R.id.skb_progress);
        mSlidingLayer = (SlidingLayer) findViewById(R.id.slidingLayer);
        mMusicPlayFragment = (MusicPlayFragment) getSupportFragmentManager().findFragmentById(
                R.id.playFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
        case KeyEvent.KEYCODE_BACK:
            if (mSlidingLayer.isOpened()) {
                mSlidingLayer.closeLayer(true);
                return true;
            }

        default:
            return super.onKeyDown(keyCode, event);
        }
    }

    @SuppressWarnings("rawtypes")
    private Listener<ListMusic> createMyReqSuccessListener() {
        return new Listener<ListMusic>()
        {

            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(ListMusic response) {
                dimissLoadingView();

                listMusic.addAll((List<MusicItem>) response.data);
                mMusicListAdapter.setListStatus(listMusic.size());
                mMusicListAdapter.notifyDataSetChanged();

            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {
                dimissLoadingView();
            }
        };
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {

        switch (paramMenuItem.getItemId()) {
        case android.R.id.home:
            finish();
            break;
        }
        return super.onOptionsItemSelected(paramMenuItem);

    }

    public void getListSong(List<String> songUrl, List<Integer> allTimeList, int mLastPosition) {
        try {
            this.allTimeList = allTimeList;
            MusicUtils.mService.clearPlaylist();
            MusicUtils.mService.addSongAllPlaylist(songUrl);
            MusicUtils.mService.playFile(mLastPosition);
            playState();
            getSongPic();
            handler.sendEmptyMessage(1);
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void playFile(int position) {
        try {
            MusicUtils.mService.playFile(position);
            getSongPic();
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void pasueMusic() {
        try {
            MusicUtils.mService.pause();
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void nextMusic() {
        try {
            MusicUtils.mService.skipForward();
            getSongPic();
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        // unbindService(mConnection);
    }

    protected void onStart() {

        // Bind to Service
        mToken = MusicUtils.bindToService(this, this);

        super.onStart();
    }

    @Override
    protected void onStop() {
        // Unbind
        if (MusicUtils.mService != null)
            MusicUtils.unbindFromService(mToken);

        // TODO: clear image cache

        super.onStop();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder obj) {
        MusicUtils.mService = PoPoInterface.Stub.asInterface(obj);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        MusicUtils.mService = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_play:
            pasueMusic();
            break;
        case R.id.btn_pause:
            pasueMusic();
            break;
        case R.id.btn_next:
            nextMusic();
            break;
        }
        try {
            if (MusicUtils.mService.isPause()) {
                pauseState();
            }
            else {
                playState();
            }
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void pauseState() {
        ivPause.setVisibility(View.GONE);
        ivPlay.setVisibility(View.VISIBLE);
    }

    private void playState() {
        ivPause.setVisibility(View.VISIBLE);
        ivPlay.setVisibility(View.GONE);
    }

    private void getSongPic() {
        MusicItem music = null;
        try {
            music = listMusic.get(MusicUtils.mService.getPosition());
            allTime = allTimeList.get(MusicUtils.mService.getPosition());
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (music != null) {
            PicXmlTask task = new PicXmlTask(this);
            task.execute(DataHandler.instance().getSongPic(music.song.song_name,
                    music.song.singer_name, music.song.song_id, music.song.singer_id));
        }
        tvTitle.setText(music.song.song_name);
        tvSinger.setText(music.song.singer_name);
        currentTime = 0;
        sbMusic.setProgress(0);
        sbMusic.setMax(allTime);
        mMusicPlayFragment.setMaxProgress(allTime);
        mMusicPlayFragment.setTitle(music.song.song_name);
        mMusicPlayFragment.setArtist(music.song.singer_name);
    }

    @Override
    public boolean onFeedback(String key, boolean isSuccess, Object result) {
        if (isSuccess) {
            List<PicXml> listPic = (List<PicXml>) result;
            PicXml pic = listPic.get(0);
            String picKey = TTTextUtils.decryptPictureKey((int) Long.parseLong(pic.uid0, 16),
                    (int) Long.parseLong(pic.uid1, 16), (int) Long.parseLong(pic.uid2, 16),
                    pic.uid3);
            ivPic.setImageUrl(URLs.MUSIC.pic_main_url + picKey + ".jpg",
                    MyApplication.getInstance().mImageLoader);
            picBitmap.clear();
            loadPic=0;
            for (PicXml picXml : listPic) {
                String picUrl = TTTextUtils.decryptPictureKey(
                        (int) Long.parseLong(picXml.uid0, 16),
                        (int) Long.parseLong(picXml.uid1, 16),
                        (int) Long.parseLong(picXml.uid2, 16), picXml.uid3);
                loadPic(URLs.MUSIC.pic_main_url + picUrl + ".jpg", listPic.size());
            }

        }
        else {
            ivPic.setImageResource(R.drawable.img_album_background);
        }
        return false;
    }

    private Handler handler = new Handler()
    {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                if (MusicUtils.mService != null) {
                    try {
                        currentTime = MusicUtils.mService.getCurrentDuration();
                        sbMusic.setProgress(currentTime / 1000);
                        mMusicPlayFragment.setProgress(currentTime / 1000);
                    }
                    catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } // 获取当前音乐播放的位置

                    handler.sendEmptyMessageDelayed(1, 1000);
                }

            }
        };
    };

    private List<Drawable> picBitmap = new ArrayList<Drawable>();
    private int loadPic = 0;

    private void loadPic(String url, final int all) {
        MyApplication.getInstance().mImageLoader.get(url, new ImageListener()
        {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onResponse(ImageContainer imagecontainer, boolean flag) {
                loadPic++;
                Logger.e("",imagecontainer.getRequestUrl()+"");
                picBitmap.add(new BitmapDrawable(getResources(), imagecontainer.getBitmap()));
                if (picBitmap.size() > 0 && loadPic == all) {
                    mMusicPlayFragment.setPicBitmap(picBitmap);
                }
            }

        });
    }
}
