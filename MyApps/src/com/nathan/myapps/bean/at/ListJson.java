package com.nathan.myapps.bean.at;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nathan.myapps.bean.BaseData;


public class ListJson<T> extends BaseData implements Serializable {

    /**
     * @author nathan
     */
    private static final long serialVersionUID = 0000000002L;
    
    public List<T> feature;
    
    public List<T> list;

}
