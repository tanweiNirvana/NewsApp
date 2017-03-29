package com.example.a46146.newsapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 46146 on 2017/3/28.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<News> mNewsList;
    private Activity activity;

    public NewsAdapter(List<News> newsList, Activity activity) {
        this.mNewsList = newsList;
        this.activity = activity;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View newsView;
        TextView newsTitle;
        TextView newsAuthor;

        public ViewHolder(View view) {
            super(view);
            newsView = view;
            newsTitle = (TextView) view.findViewById(R.id.news_title);
            newsAuthor = (TextView) view.findViewById(R.id.news_date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                News news = mNewsList.get(position);
                Uri webURL = Uri.parse(news.getmWebUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, webURL);
                activity.startActivity(websiteIntent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news = mNewsList.get(position);
        holder.newsTitle.setText(news.getmTitle());
        holder.newsAuthor.setText(news.getmDate());
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
}
