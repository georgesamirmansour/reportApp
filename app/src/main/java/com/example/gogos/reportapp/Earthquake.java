package com.example.gogos.reportapp;

/**
 * Created by gogos on 2017-11-28.
 */

public class Earthquake {
    private String magnitude;
    private String location;
    private String data;

    public Earthquake(String magnitude, String location, String data) {
        this.magnitude = magnitude;
        this.location = location;
        this.data = data;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public String getData() {
        return data;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setData(String data) {
        this.data = data;
    }
}
