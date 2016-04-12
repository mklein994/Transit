package com.example.matthew.transit.database;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Trip extends RealmObject {
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
    private Calendar calendar;
    private Route route;

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

    public RealmList<Stop> getStops() {
        return stops;
    }

    public void setStops(RealmList<Stop> stops) {
        this.stops = stops;
    }

    public RealmList<StopTime> getStopTimes() {
        return stopTimes;
    }

    public void setStopTimes(RealmList<StopTime> stopTimes) {
        this.stopTimes = stopTimes;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
