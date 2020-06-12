package com.marvelousbob.client.network.test;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class IncrementalAverage {

    @Getter
    private long min, max;
    @Getter
    private double average;
    private AtomicInteger counter = new AtomicInteger(0);

    public List<Float> minData = Collections.synchronizedList(new ArrayList<>());
    public List<Float> maxData = Collections.synchronizedList(new ArrayList<>());
    public List<Float> avgData = Collections.synchronizedList(new ArrayList<>());
    public List<Float> pingData = Collections.synchronizedList(new ArrayList<>());


    public IncrementalAverage(int current) {
        this.average = current;
        this.min = Long.MAX_VALUE;
        this.max = 0;
    }

    public IncrementalAverage() {
        this(0);
    }

    public void addToRunningAverage(long sentMsgTimestamp) {
        long delta = System.currentTimeMillis() - sentMsgTimestamp;
        pingData.add((float) delta);
        evaluateMin(delta);
        evaluateMax(delta);
        average = average + (delta - average) / counter.incrementAndGet();
        avgData.add((float) average);
    }

    public void evaluateMin(long num) {
        min = Math.min(min, num);
        minData.add((float) min);
    }

    public void evaluateMax(long num) {
        max = Math.max(max, num);
        maxData.add((float) max);
    }

    @Override
    public String toString() {
        return ("""
                min: %d ms
                max: %d ms
                average: %.3f ms
                counter: %d""").formatted(min, max, average, counter.get());
    }
}
