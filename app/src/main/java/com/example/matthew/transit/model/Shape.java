package com.example.matthew.transit.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Shape extends RealmObject {
    private static final int SHAPE_ID = 0;
    private static final int SHAPE_PT_LAT = 1;
    private static final int SHAPE_PT_LON = 2;
    private static final int SHAPE_PT_SEQUENCE = 3;
    // added manually to create a composite key between shapeId and ptSequence.
    @PrimaryKey
    private String shapePK;
    @Required
    private String shapeId;
    // required
    private double shapePtLat;
    // required
    private double shapePtLon;
    // required
    private int shapePtSequence;
    @Ignore
    private Double shapeDistTraveled;

    private RealmList<Trip> trips;
    private RealmList<Route> routes;
    private RealmList<Calendar> calendars;
    private RealmList<CalendarDate> calendarDates;

    public Shape(String[] fields) {
        this.shapeId = fields[SHAPE_ID];
        this.shapePtLat = Double.parseDouble(fields[SHAPE_PT_LAT]);
        this.shapePtLon = Double.parseDouble(fields[SHAPE_PT_LON]);
        this.shapePtSequence = ModelUtils.parseInt(fields[SHAPE_PT_SEQUENCE]);
        setShapePK(this.shapeId, this.shapePtSequence);
    }

    public Shape() {
    }

    public String getShapePK() {
        return shapePK;
    }

    public void setShapePK(String shapeId, int shapePtSequence) {
        this.shapePK = shapeId + shapePtSequence;
    }

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

    public RealmList<CalendarDate> getCalendarDates() {
        return calendarDates;
    }

    public void setCalendarDates(RealmList<CalendarDate> calendarDates) {
        this.calendarDates = calendarDates;
    }
}
