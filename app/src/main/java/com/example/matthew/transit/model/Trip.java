package com.example.matthew.transit.model;

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

    public static final int ROUTE_ID = 0;
    public static final int SERVICE_ID = 1;
    public static final int TRIP_ID = 2;
    public static final int TRIP_HEADSIGN = 3;
    public static final int DIRECTION_ID = 4;
    public static final int BLOCK_ID = 5;
    public static final int SHAPE_ID = 6;
    public static final int WHEELCHAIR_ACCESSIBLE = 7;
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
    @CsvIgnore
    @CsvCell(columnName = "trip_short_name")
    private String tripShortName;
    @CsvCell(columnName = "direction_id")
    private Boolean directionId;
    @CsvCell(columnName = "block_id")
    private String blockId;
    @CsvCell(columnName = "shape_id")
    private String shapeId;
    @CsvCell(columnName = "wheelchair_accessible")
    private Integer wheelchairAccessible;
    @Ignore
    @CsvIgnore
    @CsvCell(columnName = "bikes_allowed")
    private Byte bikesAllowed;

    private RealmList<StopTime> stopTimes;
    private RealmList<Stop> stops;

    private Calendar calendar;
    private CalendarDate calendarDate;
    private Route route;
    private Shape shape;

    public Trip() {
    }

    public Trip(String[] fields) {
        this.routeId = fields[ROUTE_ID];
        this.serviceId = fields[SERVICE_ID];
        this.tripId = fields[TRIP_ID];
        this.tripHeadsign = fields[TRIP_HEADSIGN];
        this.directionId = ModelUtils.parseBoolean(fields[DIRECTION_ID]);
        this.blockId = fields[BLOCK_ID];
        this.shapeId = fields[SHAPE_ID];
        this.wheelchairAccessible = ModelUtils.parseInt(fields[WHEELCHAIR_ACCESSIBLE]);
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

    public Boolean getDirectionId() {
        return directionId;
    }

    public void setDirectionId(Boolean directionId) {
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

    public Integer getWheelchairAccessible() {
        return wheelchairAccessible;
    }

    public void setWheelchairAccessible(Integer wheelchairAccessible) {
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

    public RealmList<Stop> getStops() {
        return stops;
    }

    public void setStops(RealmList<Stop> stops) {
        this.stops = stops;
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

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
