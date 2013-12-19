package com.nathan.myapps.activity.music;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.nathan.myapps.R;
import com.nathan.myapps.activity.at.AnimeTasteActivity;
import com.nathan.myapps.adapter.MusicAdapter;
import com.nathan.myapps.adapter.VideoListAdapter;
import com.nathan.myapps.adapter.ViewPagerAdapter;
import com.nathan.myapps.bean.at.ListJson;
import com.nathan.myapps.bean.at.VideoItem;
import com.nathan.myapps.bean.music.ListMusic;
import com.nathan.myapps.bean.music.MusicItem;
import com.nathan.myapps.request.HttpVolleyRequest;
import com.nathan.myapps.utils.ApiUtils;
import com.nathan.myapps.utils.DataHandler;
import com.nathan.myapps.utils.Logger;
import com.nathan.myapps.widget.LoadingView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;

public class MusicListActivity extends ActionBarActivity {

    private ListView lvMusic;
    private List<MusicItem> listMusic = new ArrayList<MusicItem>();
    private MusicAdapter mMusicListAdapter;
    private LoadingView mLoadingView;

    private PoPoInterface mPoPoInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(
                getResources().getDrawable(R.drawable.bbuton_danger));
        findViewById();
        init();
        getData(0);
        blindServer();
    }

    private void blindServer() {
        this.bindService(new Intent(MusicListActivity.this, PoPoService.class), mConnection,
                Context.BIND_AUTO_CREATE);
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

    }

    private void findViewById() {
        lvMusic = (ListView) findViewById(R.id.musicList);

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

    private ServiceConnection mConnection = new ServiceConnection()
    {

        public void onServiceConnected(ComponentName className, IBinder service) {
            mPoPoInterface = PoPoInterface.Stub.asInterface((IBinder) service);

        }

        public void onServiceDisconnected(ComponentName className) {
            mPoPoInterface = null;
        }
    };

    public void getListSong(List<String> songUrl) {
        try {
            mPoPoInterface.clearPlaylist();
            mPoPoInterface.addSongAllPlaylist(songUrl);
            
            mPoPoInterface.playFile((int) (Math.random()*songUrl.size()));
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void stopMusic() {
        try {
            mPoPoInterface.pause();
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void nextMusic()
    {
        try {
            mPoPoInterface.skipForward();
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

}
