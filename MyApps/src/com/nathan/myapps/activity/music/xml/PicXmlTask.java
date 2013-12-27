package com.nathan.myapps.activity.music.xml;

import java.util.List;

import com.nathan.myapps.utils.Logger;

import android.os.AsyncTask;

public class PicXmlTask extends AsyncTask<String, Void, List<PicXml>> {

    public static final String FEED_BACK_SHARE = "FEED_BACK";
    private IFeedback mFeedback;
    private boolean mIsSuccess = false;

    public PicXmlTask(IFeedback feedback) {
        this.mFeedback = feedback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected List<PicXml> doInBackground(String... params) {

        Logger.e("", params[0] + "");
        List<PicXml> list = null;
        try {
            list = PullXml.getPicData(params[0]);
        }
        catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (list.size() > 0) {
            mIsSuccess = true;
        }
        return list != null ? list : null;
    }

    @Override
    protected void onPostExecute(List<PicXml> result) {
        super.onPostExecute(result);

        mFeedback.onFeedback(FEED_BACK_SHARE, mIsSuccess, result);

    }
}
