package com.example.matthew.transit.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */ //region Frequency model (not used)
public class Frequency extends RealmObject {
    @Required
    private String tripId;

    @Required
    private String startTime;

    @Required
    private String endTime;

    @Required
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
