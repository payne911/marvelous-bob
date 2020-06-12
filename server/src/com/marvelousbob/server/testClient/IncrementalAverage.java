package com.marvelousbob.server.testClient;

public class IncrementalAverage {
    private double current;
    private int iterator;

    public IncrementalAverage(int current) {
        this.current = current;
        this.iterator = 1;
    }

    public IncrementalAverage() {
        this(0);
    }

    public double getAverage() {
        return current;
    }

    public void addToRunningAverage(long num) {
        current = current + (num - current) / (double) iterator++;
    }
}
