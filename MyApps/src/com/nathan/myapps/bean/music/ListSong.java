package com.nathan.myapps.bean.music;

import java.io.Serializable;


public class ListSong<T> implements Serializable {
    /**
     * @author nathan
     */
    private static final long serialVersionUID = 1100000002L;
    
    public String code;
    
    public T data;
    
}
