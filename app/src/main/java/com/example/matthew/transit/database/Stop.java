package com.example.matthew.transit.database;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvIgnore;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Stop extends RealmObject {
    @PrimaryKey
    @CsvCell(columnName = "stop_id")
    private String stopId;

    @CsvCell(columnName = "stop_code")
    private String stopCode;

    @Required
    @CsvCell(columnName = "stop_name")
    private String stopName;

    @Ignore
    @CsvCell(columnName = "stop_desc")
    private String stopDesc;

    // required
    @CsvCell(columnName = "stop_lat")
    private double stopLat;

    // required
    @CsvCell(columnName = "stop_lon")
    private double stopLon;

    @Ignore
    @CsvCell(columnName = "zone_id")
    private String zoneId;

    @CsvCell(columnName = "stop_url")
    private String stopUrl;

    @Ignore
    @CsvCell(columnName = "location_type")
    private byte locationType;

    @Ignore
    @CsvCell(columnName = "parent_station")
    private String parentStation;

    @Ignore
    @CsvCell(columnName = "stop_timezone")
    private String stopTimezone;

    @Ignore
    @CsvCell(columnName = "wheelchair_boarding")
    private byte wheelchairBoarding;

    @CsvIgnore
    private RealmList<Trip> trips;
    @CsvIgnore
    private RealmList<StopTime> stopTimes;

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getStopCode() {
        return stopCode;
    }

    public void setStopCode(String stopCode) {
        this.stopCode = stopCode;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getStopDesc() {
        return stopDesc;
    }

    public void setStopDesc(String stopDesc) {
        this.stopDesc = stopDesc;
    }

    public double getStopLat() {
        return stopLat;
    }

    public void setStopLat(double stopLat) {
        this.stopLat = stopLat;
    }

    public double getStopLon() {
        return stopLon;
    }

    public void setStopLon(double stopLon) {
        this.stopLon = stopLon;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getStopUrl() {
        return stopUrl;
    }

    public void setStopUrl(String stopUrl) {
        this.stopUrl = stopUrl;
    }

    public byte getLocationType() {
        return locationType;
    }

    public void setLocationType(byte locationType) {
        this.locationType = locationType;
    }

    public String getParentStation() {
        return parentStation;
    }

    public void setParentStation(String parentStation) {
        this.parentStation = parentStation;
    }

    public String getStopTimezone() {
        return stopTimezone;
    }

    public void setStopTimezone(String stopTimezone) {
        this.stopTimezone = stopTimezone;
    }

    public byte getWheelchairBoarding() {
        return wheelchairBoarding;
    }

    public void setWheelchairBoarding(byte wheelchairBoarding) {
        this.wheelchairBoarding = wheelchairBoarding;
    }

    public RealmList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(RealmList<Trip> trips) {
        this.trips = trips;
    }

    public RealmList<StopTime> getStopTimes() {
        return stopTimes;
    }

    public void setStopTimes(RealmList<StopTime> stopTimes) {
        this.stopTimes = stopTimes;
    }
}
