package com.nathan.myapps.utils;

import com.nathan.myapps.R;
import com.nathan.myapps.app.msg.AppMsg;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class MsgUtils {

    private Activity act;
    private String msg;

    public MsgUtils(Activity act, String msg) {
        this.act = act;
        this.msg = msg;
    }

    public void showAppMsg(int i) {
        final AppMsg.Style style;
        switch (i) {
        case 1:
            style = AppMsg.STYLE_ALERT;
            break;
        case 2:
            style = AppMsg.STYLE_CONFIRM;
            break;
        case 3:
            style = AppMsg.STYLE_INFO;
            break;
        case 4:
            style = new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.custom);
            break;

        default:
            return;
        }

        // create {@link AppMsg} with specify type
        AppMsg appMsg = AppMsg.makeText(act, msg, style);
        // LinearLayout.LayoutParams params = new
        // LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
        // LayoutParams.WRAP_CONTENT);
        // params.setMargins(5, 25, 5, 5);
        // appMsg.setLayoutParams(params);
        appMsg.show();
    }
}
