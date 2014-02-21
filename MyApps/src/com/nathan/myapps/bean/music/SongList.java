package com.nathan.myapps.bean.music;

import java.io.Serializable;

import com.nathan.myapps.bean.BaseData;


public class SongList extends BaseData implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1100000002L;

    public String _id;
    public String pick_count;
    public String singer_id;
    public String singer_name;
    public String song_id;
    public String song_name;
}
