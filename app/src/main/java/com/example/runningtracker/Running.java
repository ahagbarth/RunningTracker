package com.example.runningtracker;

public class Running {
        protected RunningState state;


    public enum RunningState {
        STOPPED,
        PAUSED,
        RUNNING,
        ERROR
    }

    public Running() {
        this.state = RunningState.STOPPED;
    }

    public RunningState getState() {
        return this.state;
    }



}
