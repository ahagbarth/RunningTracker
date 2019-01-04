package com.example.runningtracker;

import android.widget.TextView;

public class Runs {
    int id;
    String title;
    String instructions;
    String averageSpeed;



    public Runs() {

    }

    public Runs(int id, String title, String instructions, String averageSpeed) {
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.averageSpeed= averageSpeed;

    }


    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getInstructions() {
        return instructions;
    }
    public String getAverageSpeed() {
        return averageSpeed;
    }


    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public void setAverageSpeed(String averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
