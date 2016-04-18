package com.example.matthew.transit.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Trip extends RealmObject {

    private static final int ROUTE_ID = 0;
    private static final int SERVICE_ID = 1;
    private static final int TRIP_ID = 2;
    private static final int TRIP_HEADSIGN = 3;
    private static final int DIRECTION_ID = 4;
    private static final int BLOCK_ID = 5;
    private static final int SHAPE_ID = 6;
    private static final int WHEELCHAIR_ACCESSIBLE = 7;
    @Required
    private String routeId;
    @Required
    private String serviceId;
    @PrimaryKey
    private String tripId;
    private String tripHeadsign;
    @Ignore
    private String tripShortName;
    private Byte directionId;
    private String blockId;
    private String shapeId;
    private Byte wheelchairAccessible;
    @Ignore
    private Byte bikesAllowed;
    private RealmList<Stop> stops;

    private RealmList<StopTime> stopTimes;
    private Shape shape;
    private Calendar calendar;
    private CalendarDate calendarDate;
    private Route route;

    public Trip() {
    }

    public Trip(String[] fields) {
        this.routeId = fields[ROUTE_ID];
        this.serviceId = fields[SERVICE_ID];
        this.tripId = fields[TRIP_ID];
        this.tripHeadsign = fields[TRIP_HEADSIGN];
        this.directionId = Byte.valueOf(fields[DIRECTION_ID]);
        this.blockId = fields[BLOCK_ID];
        this.shapeId = fields[SHAPE_ID];
        this.wheelchairAccessible = Byte.valueOf(fields[WHEELCHAIR_ACCESSIBLE]);
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTripHeadsign() {
        return tripHeadsign;
    }

    public void setTripHeadsign(String tripHeadsign) {
        this.tripHeadsign = tripHeadsign;
    }

    public String getTripShortName() {
        return tripShortName;
    }

    public void setTripShortName(String tripShortName) {
        this.tripShortName = tripShortName;
    }

    public Byte getDirectionId() {
        return directionId;
    }

    public void setDirectionId(Byte directionId) {
        this.directionId = directionId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getShapeId() {
        return shapeId;
    }

    public void setShapeId(String shapeId) {
        this.shapeId = shapeId;
    }

    public Byte getWheelchairAccessible() {
        return wheelchairAccessible;
    }

    public void setWheelchairAccessible(Byte wheelchairAccessible) {
        this.wheelchairAccessible = wheelchairAccessible;
    }

    public Byte getBikesAllowed() {
        return bikesAllowed;
    }

    public void setBikesAllowed(Byte bikesAllowed) {
        this.bikesAllowed = bikesAllowed;
    }

    public RealmList<StopTime> getStopTimes() {
        return stopTimes;
    }

    public void setStopTimes(RealmList<StopTime> stopTimes) {
        this.stopTimes = stopTimes;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public CalendarDate getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(CalendarDate calendarDate) {
        this.calendarDate = calendarDate;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
