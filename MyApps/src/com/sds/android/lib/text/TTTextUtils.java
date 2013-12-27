package com.sds.android.lib.text;

public class TTTextUtils {

    static {

        System.loadLibrary("tttextutils");

    }

    public static native String decryptLyricKey(String paramString1, String paramString2,
            int paramInt);

    public static native String decryptPictureKey(int paramInt1, int paramInt2, int paramInt3,
            String paramString);
}
