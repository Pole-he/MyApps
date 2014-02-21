package com.nathan.myapps.bean.music;

import java.io.Serializable;

import com.nathan.myapps.bean.BaseData;


public class ListSong<T> extends BaseData implements Serializable {
    /**
     * @author nathan
     */
    private static final long serialVersionUID = 1100000002L;
    
    public String code;
    
    public T data;
    
}
