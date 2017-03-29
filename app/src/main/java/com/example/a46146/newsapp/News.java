package com.example.a46146.newsapp;

/**
 * Created by 46146 on 2017/3/28.
 */

public class News {
    private String mTitle;
    private String mWebUrl;
    private String mDate;

    public News(String mTitle, String mWebUrl, String mDate) {
        this.mTitle = mTitle;
        this.mWebUrl = mWebUrl;
        this.mDate = mDate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmWebUrl() {
        return mWebUrl;
    }

    public String getmDate() {
        return mDate;
    }
}
