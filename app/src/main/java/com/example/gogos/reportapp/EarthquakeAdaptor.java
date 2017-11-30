package com.example.gogos.reportapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gogos on 2017-11-28.
 */

public class EarthquakeAdaptor extends ArrayAdapter<Earthquake> {

    public EarthquakeAdaptor(Context context, List<Earthquake> earthquakeList)
    {
        super(context, 0, earthquakeList);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View listItemsView = convertView;
        if (listItemsView == null){
            listItemsView = LayoutInflater.from(getContext())
                    .inflate(R.layout.earthquake_list_item, parent, false);
        }
        Earthquake currentEarthquake = getItem(position);
        TextView magnitudeView = (TextView) listItemsView.findViewById(R.id.magnitude);
        magnitudeView.setText(currentEarthquake.getMagnitude());
        TextView locationView = (TextView) listItemsView.findViewById(R.id.location);
        locationView.setText(currentEarthquake.getLocation());
        TextView dataView = (TextView) listItemsView.findViewById(R.id.data);
        dataView.setText(currentEarthquake.getData());
        return listItemsView;
    }
}
