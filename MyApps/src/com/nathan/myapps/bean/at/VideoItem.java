package com.nathan.myapps.bean.at;

import java.io.Serializable;
import java.util.Date;



public class VideoItem implements Serializable {

    /**
     * @author nathana
     */
    private static final long serialVersionUID = 0000000001L;
    // 作者
    public String Author;
    // 简介
    public String Brief;
    // 类别
    public String Category;
    // 详细图片
    public String DetailPic;
    // 特辑
    public String Feature;
    // 列表Pic
    public String HomePic;
    // item id
    public String Id;
    // 插入的时间
    public Date InsertTime;
    // 发布人
    public String Name;
    // 发布
    public String Publish;
    // 更新时间
    public Date UpdatedTime;
    // 视频路径
    public String VideoUrl;
    // 年份
    public String Year;

}
