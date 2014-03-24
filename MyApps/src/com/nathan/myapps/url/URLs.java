package com.nathan.myapps.url;

public class URLs {

    public static class ANIME_TASTE {

        public static String anime_v3_feature = "http://i.animetaste.net/api/animelist_v3/?api_key=%s&timestamp=%d&feature=1&access_token=%s";
        public static String anime_v3 = "http://i.animetaste.net/api/animelist_v3/?api_key=%s&timestamp=%d&order=random&limit=%d&access_token=%s";
        public static String anime_v2 = "http://i.animetaste.net/api/animelist_v2/?page=%d&timestamp=%d&api_key=%s&access_token=%s";
    }

    public static class ABLUM {

        public static String url = "http://m.xiangce.baidu.com/mobileapp/get-square-tag-pictures?guid=%s&mac=%s&version=%s&appid=%s&secret_key=%s&api_key=%s&language=%s&version=%s&mac=%s&imei=%s&tag_id=%d&stream_start=%d&stream_size=%d&picture_quality=%s";
    }

    public static class MUSIC {
        public static String music_list_url = "http://v1.ard.q.itlily.com/share/celebrities";
        public static String found_url = "http://v1.ard.q.itlily.com/share/user_timeline";
        public static String music_url = "http://ting.hotchanson.com/v2/songs/download?song_id=%s";
        public static String music_url_end = "&f=f5000&uid=356440046688758&app=ttpod&v=v6.4.3.2013120510&rom=tmous%252Fhtc_pyramid%252Fpyramid%253A4.0.3%252FIML74K%252F356011.14%253Auser%252Frelease-keys&s=s200&hid=3342781044190974&active=0&tid=204025860&splus=4.1.2%252F16&imsi=460023071207498&mid=Sensation&net=2";
        public static String song_pic = "http://picdown.ttpod.cn/picsearch?title=%s&artist=%s&filename=&mediatype=&x=540&y=960&maxcount=2147483647&song_id=%s&singer_id=%s&auto=1&s=s200";
        public static String pic_main_url = "http://pic.ttpod.cn/";
    }
    
    public static class MIUI{
        public static String data_url = "http://market.xiaomi.com/thm/millionwp/items?offset=%d&len=40";
        public static String pic_url = "http://i2.market.mi-img.com/thumbnail/jpeg/w236q80/ThemeMarket/fc74f80e-be35-415f-a310-b124048174a5/a.jpg";
    }
}
