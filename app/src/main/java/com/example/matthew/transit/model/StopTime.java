package com.example.matthew.transit.model;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvDate;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class StopTime extends RealmObject {
    private static final int TRIP_ID = 0;
    private static final int ARRIVAL_TIME = 1;
    private static final int DEPARTURE_TIME = 2;
    private static final int STOP_ID = 3;
    private static final int STOP_SEQUENCE = 4;

    // added as a composite key between tripId and stopSequence
    @PrimaryKey
    private String stopTimePK;
    @Required
    @CsvCell(columnName = "trip_id")
    private String tripId;
    @Required
    @CsvCell(columnName = "arrival_time")
    @CsvDate(format = "HH:mm:ss")
    private String arrivalTime;
    @Required
    @CsvCell(columnName = "departure_time")
    @CsvDate(format = "HH:mm:ss")
    private String departureTime;
    @Required
    @CsvCell(columnName = "stop_id")
    private String stopId;
    // required
    @CsvCell(columnName = "stop_sequence")
    private int stopSequence;
    @Ignore
    @CsvCell(columnName = "stop_headsign")
    private String stopHeadsign;
    @Ignore
    @CsvCell(columnName = "pickup_type")
    private Byte pickupType;
    @Ignore
    @CsvCell(columnName = "drop_off_type")
    private Byte dropOffType;
    @Ignore
    @CsvCell(columnName = "shape_dist_traveled")
    private Double shapeDistTraveled;
    @Ignore
    private Byte timepoint;

    private Stop stop;
    private Trip trip;

    public StopTime() {
    }

    public StopTime(String[] fields) {
        this.tripId = fields[TRIP_ID];
        this.arrivalTime = fields[ARRIVAL_TIME];
        this.departureTime = fields[DEPARTURE_TIME];
        this.stopId = fields[STOP_ID];
        this.stopSequence = ModelUtils.parseInt(fields[STOP_SEQUENCE]);
        setStopTimePK(this.tripId, this.stopSequence);
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public int getStopSequence() {
        return stopSequence;
    }

    public void setStopSequence(int stopSequence) {
        this.stopSequence = stopSequence;
    }

    public String getStopHeadsign() {
        return stopHeadsign;
    }

    public void setStopHeadsign(String stopHeadsign) {
        this.stopHeadsign = stopHeadsign;
    }

    public Byte getPickupType() {
        return pickupType;
    }

    public void setPickupType(Byte pickupType) {
        this.pickupType = pickupType;
    }

    public Byte getDropOffType() {
        return dropOffType;
    }

    public void setDropOffType(Byte dropOffType) {
        this.dropOffType = dropOffType;
    }

    public Double getShapeDistTraveled() {
        return shapeDistTraveled;
    }

    public void setShapeDistTraveled(Double shapeDistTraveled) {
        this.shapeDistTraveled = shapeDistTraveled;
    }

    public Byte getTimepoint() {
        return timepoint;
    }

    public void setTimepoint(Byte timepoint) {
        this.timepoint = timepoint;
    }

    public String getStopTimePK() {
        return stopTimePK;
    }

    public void setStopTimePK(String tripId, int stopSequence) {
        this.stopTimePK = tripId + stopSequence;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
