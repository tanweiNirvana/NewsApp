package com.example.a46146.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by 46146 on 2017/3/28.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mURL;

    public NewsLoader(Context context, String url) {
        super(context);
        this.mURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mURL == null) {
            return null;
        }
        List<News> newsList = QueryUtils.fetchNewsData(mURL);
        return newsList;
    }
}
