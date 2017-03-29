package com.example.a46146.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private RecyclerView recyclerView;
    private View loadingIndicator;
    private TextView empty;
    private SwipeRefreshLayout swipeRefresh;
    private static NewsAdapter newsAdapter;
    private static NetworkInfo networkInfo;
    private static String url = "https://content.guardianapis.com/search?api-key=fbfeb107-924e-4d31-8d1b-3e69c80af705";
    private static int ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadingIndicator = findViewById(R.id.loading_indicator);
        empty = (TextView) findViewById(R.id.empty);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        getData();
        //refresh
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        loadingIndicator.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
        recyclerView.setAdapter(null);
        if (data != null && !data.isEmpty()) {
            newsAdapter = new NewsAdapter(data, this);
            recyclerView.setAdapter(newsAdapter);
        } else {
            empty.setVisibility(View.VISIBLE);
            empty.setText(R.string.no_news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
    }

    private void getData(){
        if (networkInfo != null && networkInfo.isConnected()) {
            empty.setVisibility(View.GONE);
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(ID++, null, MainActivity.this);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            empty.setText(R.string.on_internet);
        }
    }
}
