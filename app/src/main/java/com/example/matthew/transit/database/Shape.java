package com.example.matthew.transit.database;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvIgnore;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by matthew on 11/04/16.
 */
public class Shape extends RealmObject {
    @PrimaryKey
    @CsvCell(columnName = "shape_id")
    private String shapeId;

    // required
    @CsvCell(columnName = "shape_pt_lat")
    private double shapePtLat;

    // required
    @CsvCell(columnName = "shape_pt_lon")
    private double shapePtLon;

    // required
    @CsvCell(columnName = "shape_pt_sequence")
    private int shapePtSequence;

    @Ignore
    @CsvCell(columnName = "shape_dist_traveled")
    private Double shapeDistTraveled;

    @CsvIgnore
    private RealmList<Trip> trips;
    @CsvIgnore
    private RealmList<Route> routes;
    @CsvIgnore
    private RealmList<Calendar> calendars;

    public String getShapeId() {
        return shapeId;
    }

    public void setShapeId(String shapeId) {
        this.shapeId = shapeId;
    }

    public double getShapePtLat() {
        return shapePtLat;
    }

    public void setShapePtLat(double shapePtLat) {
        this.shapePtLat = shapePtLat;
    }

    public double getShapePtLon() {
        return shapePtLon;
    }

    public void setShapePtLon(double shapePtLon) {
        this.shapePtLon = shapePtLon;
    }

    public int getShapePtSequence() {
        return shapePtSequence;
    }

    public void setShapePtSequence(int shapePtSequence) {
        this.shapePtSequence = shapePtSequence;
    }

    public Double getShapeDistTraveled() {
        return shapeDistTraveled;
    }

    public void setShapeDistTraveled(Double shapeDistTraveled) {
        this.shapeDistTraveled = shapeDistTraveled;
    }

    public RealmList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(RealmList<Trip> trips) {
        this.trips = trips;
    }

    public RealmList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(RealmList<Route> routes) {
        this.routes = routes;
    }

    public RealmList<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(RealmList<Calendar> calendars) {
        this.calendars = calendars;
    }
}
