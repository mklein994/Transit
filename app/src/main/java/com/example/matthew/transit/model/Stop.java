package com.example.matthew.transit.model;

/**
 * Created by matthew on 11/04/16.
 */
public class Stop {
    private static final int STOP_ID = 0;
    private static final int STOP_CODE = 1;
    private static final int STOP_NAME = 2;
    private static final int STOP_LAT = 3;
    private static final int STOP_LON = 4;
    private static final int STOP_URL = 5;
    // primary key
    private String stopId;
    private String stopCode;
    // required
    private String stopName;
    // ignore
    private String stopDesc;
    // required
    private double stopLat;
    // required
    private double stopLon;
    // ignore
    private String zoneId;
    private String stopUrl;
    // ignore
    private byte locationType;
    // ignore
    private String parentStation;
    // ignore
    private String stopTimezone;
    // ignore
    private byte wheelchairBoarding;

    public Stop() {
    }

    public Stop(String[] fields) {
        this.stopId = fields[STOP_ID];
        this.stopCode = fields[STOP_CODE];
        this.stopName = fields[STOP_NAME];
        this.stopLat = Double.parseDouble(fields[STOP_LAT]);
        this.stopLon = Double.parseDouble(fields[STOP_LON]);
        this.stopUrl = fields[STOP_URL];
    }

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
}
