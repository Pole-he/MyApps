package com.nathan.myapps.url;

public class URLs {

    public static class ANIME_TASTE {

        public static String anime_v3_feature = "http://i.animetaste.net/api/animelist_v3/?api_key=%s&timestamp=%d&feature=1&access_token=%s";
        public static String anime_v3 = "http://i.animetaste.net/api/animelist_v3/?api_key=%s&timestamp=%d&order=random&limit=%d&access_token=%s";
        public static String anime_v2 = "http://i.animetaste.net/api/animelist_v2/?api_key=%s&timestamp=%d&page=%d&access_token=%s";
    }

    public static class ABLUM {

        public static String url = "http://m.xiangce.baidu.com/mobileapp/get-square-tag-pictures?guid=%s&mac=%s&version=%s&appid=%s&secret_key=%s&api_key=%s&language=%s&version=%s&mac=%s&imei=%s&tag_id=%d&stream_start=%d&stream_size=%d&picture_quality=%s";
    }

    public static class MUSIC {

        public static String found_url = "http://v1.ard.q.itlily.com/share/get_celebrities";

    }
}
