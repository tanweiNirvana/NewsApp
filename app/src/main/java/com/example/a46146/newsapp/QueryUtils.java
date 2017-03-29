package com.example.a46146.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 46146 on 2017/3/28.
 */

public class QueryUtils {
    public static List<News> fetchNewsData(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        URL url = createUrl(json);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("debug", "" + jsonResponse);
        List<News> newsList = extractFeatureFromJson(jsonResponse);
        return newsList;
    }

    private static List<News> extractFeatureFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        List<News> newsList = new ArrayList<>();
        JSONObject baseJsonResponse = null;
        try {
            baseJsonResponse = new JSONObject(jsonResponse);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            Log.d("debug", "" + results.length());
            for (int i = 0; i < results.length(); i++) {
                JSONObject currentNews = results.getJSONObject(i);
                String title = null;
                String date = null;
                String webUrl = null;
                if (currentNews.has("webTitle")) {
                    title = currentNews.getString("webTitle");
                } else {
                    title = "**";
                }
                if (currentNews.has("webPublicationDate")) {
                    date = currentNews.getString("webPublicationDate");
                    Log.d("debug", i + ":" + date);
                    date = date.replace("T", " ");
                    date = date.replace("Z", "");
                    Log.d("debug", i + ":" + date);
                } else {
                    date = "**";
                }
                if (currentNews.has("webUrl")) {
                    webUrl = currentNews.getString("webUrl");
                } else {
                    webUrl = "**";
                }
                News news = new News(title, webUrl, date);
                newsList.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            Log.d("debug", "" + urlConnection.getResponseCode());
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;
            try {
                line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output.toString();
    }

    public static URL createUrl(String json) {
        URL url = null;
        try {
            url = new URL(json);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
