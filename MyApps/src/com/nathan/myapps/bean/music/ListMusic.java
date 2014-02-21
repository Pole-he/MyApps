package com.nathan.myapps.bean.music;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nathan.myapps.bean.BaseData;


public class ListMusic<T> extends BaseData implements Serializable {

    /**
     * @author nathan
     */
    private static final long serialVersionUID = 1000000002L;
    
    public List<T> data;
    
    public String code;
    
    public String msg;

}
