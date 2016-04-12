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
public class Trip extends RealmObject {
    @Required
    @CsvCell(columnName = "route_id")
    private String routeId;

    @Required
    @CsvCell(columnName = "service_id")
    private String serviceId;

    @PrimaryKey
    @CsvCell(columnName = "trip_id")
    private String tripId;

    @CsvCell(columnName = "trip_headsign")
    private String tripHeadsign;

    @Ignore
    @CsvCell(columnName = "trip_short_name")
    private String tripShortName;

    @CsvCell(columnName = "direction_id")
    private Byte directionId;

    @CsvCell(columnName = "block_id")
    private String blockId;

    @CsvCell(columnName = "shape_id")
    private String shapeId;

    @CsvCell(columnName = "wheelchair_accessible")
    private Byte wheelchairAccessible;

    @Ignore
    @CsvCell(columnName = "bikes_allowed")
    private Byte bikesAllowed;

    @CsvIgnore
    private RealmList<Stop> stops;
    @CsvIgnore
    private RealmList<StopTime> stopTimes;
    @CsvIgnore
    private Calendar calendar;
    @CsvIgnore
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
