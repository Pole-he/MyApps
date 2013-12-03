package com.nathan.myapps.bean.at;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ListJson<T> implements Serializable {

    /**
     * @author nathan
     */
    private static final long serialVersionUID = 0000000002L;
    
    public List<T> feature;
    
    public List<T> list;

}
