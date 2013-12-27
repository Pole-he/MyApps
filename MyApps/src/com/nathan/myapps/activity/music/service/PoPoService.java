package com.nathan.myapps.activity.music.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nathan.myapps.R;
import com.nathan.myapps.utils.Logger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class PoPoService extends Service {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private List<String> songs = new ArrayList<String>();
    private int currentPosition;

    private NotificationManager nm;
    private static final int NOTIFY_ID = R.layout.playlist;
    private static boolean isPause = false; // 暂停状态
    private int currentTime; // 当前播放进度
    private int duration; // 播放长度
    /**
     * handler用来接收消息，来发送广播更新播放时间
     */
    private Handler handler = new Handler()
    {

        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                if (mediaPlayer != null) {
                    currentTime = mediaPlayer.getCurrentPosition(); // 获取当前音乐播放的位置

                    handler.sendEmptyMessageDelayed(1, 1000);
                }

            }
        };
    };

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        nm.cancel(NOTIFY_ID);
    }

    public IBinder getBinder() {
        return mBinder;
    }

    private void playSong(String file) {
        Log.i("playSong:", file);
        try {
            Notification notification = new Notification();
            nm.notify(NOTIFY_ID, notification);
            Log.i("playSong:", "------------------------");
            mediaPlayer.reset();
            mediaPlayer.setDataSource(file);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new OnCompletionListener()
            {

                public void onCompletion(MediaPlayer arg0) {
                    nextSong();
                }
            });
        }
        catch (IOException e) {
            Log.e(getString(R.string.app_name), e.getMessage());
        }
    }

    private void nextSong() {
        Logger.e("", "NEXTSONG");
        // Check if last song or not
        if (++currentPosition >= songs.size()) {
            currentPosition = 0;
            nm.cancel(NOTIFY_ID);
        }
        else {
            playSong(songs.get(currentPosition));
        }
    }

    private void prevSong() {
        if (mediaPlayer.getCurrentPosition() < 3000 && currentPosition >= 1) {
            playSong(songs.get(--currentPosition));
        }
        else {
            playSong(songs.get(currentPosition));
        }
    }

    /**
     * 暂停音乐
     */
    private void pauseSong() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
    }

    private void resumeSong() {
        if (isPause) {
            mediaPlayer.start();
            isPause = false;
        }
    }

    /**
     * 停止音乐
     */
    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 播放音乐
     * 
     * @param position
     */
    // private void play(int currentTime) {
    // try {
    // mpr.reset();// 把各项参数恢复到初始状态
    // mpr.setDataSource(path);
    // mpr.prepare(); // 进行缓冲
    // mpr.setOnPreparedListener(new PreparedListener(currentTime));// 注册一个监听器
    // handler.sendEmptyMessage(1);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    private final PoPoInterface.Stub mBinder = new PoPoInterface.Stub()
    {

        public void playFile(int position) throws DeadObjectException {
            Log.i("playFile:", Integer.toString(position));
            try {
                currentPosition = position;
                playSong(songs.get(position));
            }
            catch (IndexOutOfBoundsException e) {
                Log.e(getString(R.string.app_name), e.getMessage());
            }
        }

        public void addSongPlaylist(String song) throws DeadObjectException {
            songs.add(song);
        }

        public void addSongAllPlaylist(List<String> song) throws DeadObjectException {
            songs.addAll(song);
        }

        public void clearPlaylist() throws DeadObjectException {
            songs.clear();
        }

        public void skipBack() throws DeadObjectException {
            prevSong();

        }

        public void skipForward() throws DeadObjectException {
            nextSong();
        }

        public void pause() throws DeadObjectException {
//            Notification notification = new Notification();
//            nm.notify(NOTIFY_ID, notification);
            if (!isPause) {
                pauseSong();
            }
            else {
                resumeSong();
            }
        }

        public void stop() throws DeadObjectException {
            nm.cancel(NOTIFY_ID);
            mediaPlayer.stop();
        }
        @Override
        public boolean isPause() throws DeadObjectException {
            // TODO Auto-generated method stub
            return isPause;
        }

        @Override
        public int getPosition() throws DeadObjectException {
            // TODO Auto-generated method stub
            return currentPosition;
        }

        @Override
        public int getCurrentDuration() throws RemoteException {
            // TODO Auto-generated method stub
            return mediaPlayer.getCurrentPosition();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return mBinder;
    }

}