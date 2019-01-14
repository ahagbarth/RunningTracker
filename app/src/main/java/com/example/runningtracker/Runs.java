package com.example.runningtracker;

import android.widget.TextView;

public class Runs {
    int id;
    String distance;
    String instructions;
    String averageSpeed;


    float initialLongitude;
    float initialLatitude;
    float endLongitude;
    float endLatitude;

    String Date;



    public Runs() {

    }

    public Runs(int id, String distance, String instructions, String averageSpeed, float initialLongitude, float initialLatitude, float endLongitude, float endLatitude, String Date) {
        this.id = id;
        this.distance = distance;
        this.instructions = instructions;
        this.averageSpeed= averageSpeed;
        this.initialLongitude = initialLongitude;
        this.initialLatitude = initialLatitude;
        this.endLongitude = endLongitude;
        this.endLatitude = endLatitude;
        this.Date = Date;

    }


    public int getId() {
        return id;
    }
    public String getDistance() {
        return distance;
    }
    public String getInstructions() {
        return instructions;
    }
    public String getAverageSpeed() {
        return averageSpeed;
    }

    public float getInitialLongitude() {
        return initialLongitude;
    }

    public void setInitialLongitude(float initialLongitude) {
        this.initialLongitude = initialLongitude;
    }

    public float getInitialLatitude() {
        return initialLatitude;
    }

    public void setInitialLatitude(float initialLatitude) {
        this.initialLatitude = initialLatitude;
    }

    public float getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(float endLongitude) {
        this.endLongitude = endLongitude;
    }

    public float getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(float endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public void setAverageSpeed(String averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
