package com.nathan.myapps.bean.music;

import java.io.Serializable;

import java.util.Date;
import java.util.List;



public class MusicItem implements Serializable {

    /**
     * @author nathan
     */
    private static final long serialVersionUID = 0000000006L;
    public int comment_count;
    public String create_at;
    public int favorite_count;
    public String id;
    public Song song;
    public User user;
    public String songlistname;
    public String songlistid;
    public String tweet;
    public String type;
    public String[] pics;  
    public List<SongList> songlist;
}
