package com.example.matthew.transit.model;

/**
 * Created by matthew on 11/04/16.
 */ //region Frequency model (not used)
public class Frequency {
    // required
    private String tripId;

    // required
    private String startTime;

    // required
    private String endTime;

    // required
    private String headwaySecs;

    private Byte exactTimes;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHeadwaySecs() {
        return headwaySecs;
    }

    public void setHeadwaySecs(String headwaySecs) {
        this.headwaySecs = headwaySecs;
    }

    public Byte getExactTimes() {
        return exactTimes;
    }

    public void setExactTimes(Byte exactTimes) {
        this.exactTimes = exactTimes;
    }
}
