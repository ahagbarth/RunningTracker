package com.example.runningtracker;

import android.widget.TextView;

public class Runs {
    int id;
    String title;
    String instructions;
    Float rating;



    public Runs() {

    }

    public Runs(int id, String title, String instructions, Float rating) {
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.rating = rating;

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
    public Float getRating() {
        return rating;
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
    public void setRating(Float rating) {
        this.rating = rating;
    }
}
