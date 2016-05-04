package com.example.matthew.transit.model;

/**
 * Created by matthew on 11/04/16.
 */
public class Trip {

    private static final int ROUTE_ID = 0;
    private static final int SERVICE_ID = 1;
    private static final int TRIP_ID = 2;
    private static final int TRIP_HEADSIGN = 3;
    private static final int DIRECTION_ID = 4;
    private static final int BLOCK_ID = 5;
    private static final int SHAPE_ID = 6;
    private static final int WHEELCHAIR_ACCESSIBLE = 7;
    // required
    private String routeId;
    // required
    private String serviceId;
    // primary key
    private String tripId;
    private String tripHeadsign;
    // ignore
    private String tripShortName;
    private Boolean directionId;
    private String blockId;
    private String shapeId;
    private Integer wheelchairAccessible;
    // ignore
    private Byte bikesAllowed;

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
}
