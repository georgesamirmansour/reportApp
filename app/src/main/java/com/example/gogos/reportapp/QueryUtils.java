package com.example.gogos.reportapp;

/**
 * Created by gogos on 2017-11-28.
 */

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
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    public static final ArrayList<Earthquake> earthquakeArrayList = new ArrayList<>();

    private QueryUtils() {
    }

    public static ArrayList<Earthquake> extractEarthquakeFeatures(String earthquakeJSON) {
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        try {
            JSONObject baseJsonObject = new JSONObject(earthquakeJSON);
            JSONArray featuresArray = baseJsonObject.getJSONArray("features");
            for (int i = 0; featuresArray.length() > i; i++) {
                JSONObject firstFeature = featuresArray.getJSONObject(i);
                JSONObject properties = firstFeature.getJSONObject("properties");
                String magnitude = properties.getString("mag");
                String location = properties.getString("place");
                String date = properties.getString("time");
                earthquakeArrayList.add(new Earthquake(magnitude, location, date));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "problem parsing the earthquake json results", e);
        }
        return earthquakeArrayList;
    }

    public static ArrayList<Earthquake> fetchEarthquakeData(String requestUrls) {
        URL url = createUrl(requestUrls);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "error closing input stream: ", e);
        }
        return extractEarthquakeFeatures(jsonResponse);
    }

    private static URL createUrl(String stringUrls) {
        URL url = null;
        try {
            url = new URL(stringUrls);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "error with creating URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = " ";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "problem retrieving the earthquake JSON results. ", e);
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

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
