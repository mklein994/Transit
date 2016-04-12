package com.example.matthew.transit.database;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvDate;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */ //region Frequency model (not used)
public class Frequency extends RealmObject {
    @Required
    @CsvCell(columnName = "trip_id")
    private String tripId;

    @Required
    @CsvCell(columnName = "start_time")
	@CsvDate(format = "HH:mm:ss")
    private String startTime;

    @Required
    @CsvCell(columnName = "end_time")
	@CsvDate(format = "HH:mm:ss")
    private String endTime;

    @Required
    @CsvCell(columnName = "headway_secs")
    private String headwaySecs;

    @CsvCell(columnName = "exact_times")
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
