package com.nathan.myapps.bean.ablum;

import java.io.Serializable;
import java.util.List;

import com.google.gson.JsonObject;


public class PicListJson<T> implements Serializable {

    /**
     * @author nathan
     */
    private static final long serialVersionUID = 0000000003L;
    
    public String err_code;
    public String err_msg;
    public PicData<T> data;
    
    @SuppressWarnings("hiding")
    public class PicData<T> implements Serializable
    {

        /**
         * @author nathan
         */
        private static final long serialVersionUID = 0000000005L;
        
        public List<T> pictures;
        public String stream_next;
        
    }
}
