package com.nathan.myapps.utils;

import java.security.MessageDigest;

public class MD5 {

    static final String HEXES = "0123456789abcdef";

    public static String digest(String paramString) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("md5");
            localMessageDigest.update(paramString.getBytes());
            String str2 = getHex(localMessageDigest.digest());
            return str2;
        }
        catch (Exception localException) {
            while (true) {
                localException.printStackTrace();
                String str1 = null;
            }
        }
    }

    private static String getHex(byte[] paramArrayOfByte) {
        String str;
        if (paramArrayOfByte == null) {
            str = null;
            return str;
        }
        StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
        int i = paramArrayOfByte.length;
        for (int j = 0;; j++) {
            if (j >= i) {
                str = localStringBuilder.toString();
                break;
            }
            int k = paramArrayOfByte[j];
            localStringBuilder.append("0123456789abcdef".charAt((k & 0xF0) >> 4)).append(
                    "0123456789abcdef".charAt(k & 0xF));
        }
        return localStringBuilder.toString();
    }
}