package com.nathan.myapps.activity.music;
interface PoPoInterface {
    void clearPlaylist();
    void addSongPlaylist( in String song );
    
    void addSongAllPlaylist( in List<String> song );
    void playFile( in int position );
 
    void pause();
    void stop();
    void skipForward();
    void skipBack();
}