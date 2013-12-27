package com.nathan.myapps.activity.music.service;
interface PoPoInterface {
    void clearPlaylist();
    void addSongPlaylist( in String song );
    
    void addSongAllPlaylist( in List<String> song );
    void playFile( in int position );
    boolean isPause();
    int getPosition();
    int getCurrentDuration();
    void pause();
    void stop();
    void skipForward();
    void skipBack();
}