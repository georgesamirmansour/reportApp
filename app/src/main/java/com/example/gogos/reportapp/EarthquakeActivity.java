package com.example.gogos.reportapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2017-10-27&endtime=2017-11-28";
    ArrayList<Earthquake> earthquakesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {

        @Override
        protected ArrayList<Earthquake> doInBackground(String... urls) {

            earthquakesArrayList = QueryUtils.fetchEarthquakeData(urls[0]);
            return earthquakesArrayList;
        }

        protected void onPostExecute(Earthquake result) {
            EarthquakeAdaptor earthquakeAdaptor = new EarthquakeAdaptor(getApplicationContext(), earthquakesArrayList);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(earthquakeAdaptor);
        }
    }
}
