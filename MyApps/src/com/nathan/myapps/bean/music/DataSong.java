package com.nathan.myapps.bean.music;

import java.io.Serializable;
import java.util.List;

public class DataSong implements Serializable {

    /**
     * @author nathan
     */
    private static final long serialVersionUID = 1110000002L;

    public String ablum_name;

    public String pick_count;
    public String singer_id;
    public String singer_name;
    public String song_id;
    public String song_name;

    public List<SongItem> audition_list;
    public List<SongItem> url_list;
}
