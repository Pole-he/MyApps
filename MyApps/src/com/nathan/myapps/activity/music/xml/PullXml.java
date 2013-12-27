package com.nathan.myapps.activity.music.xml;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class PullXml {

    public static List<PicXml> getPicData(String path) throws Exception {
        URL url = new URL(path);
        PicXml pic = null;
        List<PicXml> picList = null;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        if (conn.getResponseCode() == 200) {
            InputStream inputstream = conn.getInputStream();
            System.out.println("网络连接成功");

            XmlPullParser xml = Xml.newPullParser();
            xml.setInput(inputstream, "UTF-8");
            int event = xml.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                // 开始解析文档
                case XmlPullParser.START_DOCUMENT:
                    picList = new ArrayList<PicXml>();
                    break;
                case XmlPullParser.START_TAG:

                    String value = xml.getName();
                    if (value.equals("pic")) {
                        pic = new PicXml();
                        pic.type = xml.getAttributeValue(0);
                        pic.uid2 = xml.getAttributeValue(1);
                        pic.uid3 = xml.getAttributeValue(2);
                        pic.uid0 = xml.getAttributeValue(3);
                        pic.uid1 = xml.getAttributeValue(4);
                        pic.id = xml.getAttributeValue(5);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xml.getName().equals("pic")) {
                        picList.add(pic);
                        pic = null;
                    }
                    break;
                }
                // 解析下一个对象
                event = xml.next();
            }
        }
        return picList;
    }

}
