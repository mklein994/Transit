package com.example.matthew.transit.model;

/**
 * Created by matthew on 11/04/16.
 */
public class FareRule {
    private static final int FARE_ID = 0;
    private static final int ROUTE_ID = 1;
    // added to create a composite key from fareId and routeId
    // primary key
    private String fareRoutePK;
    // required
    private String fareId;
    private String routeId;
    // ignore
    private String originId;
    // ignore
    private String destinationId;
    // ignore
    private String containsId;

    public FareRule() {
    }

    public FareRule(String[] fields) {
        this.fareId = fields[FARE_ID];
        this.routeId = fields[ROUTE_ID];
        setFareRoutePK(this.fareId, this.routeId);
    }

    public String getFareId() {
        return fareId;
    }

    public void setFareId(String fareId) {
        this.fareId = fareId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getContainsId() {
        return containsId;
    }

    public void setContainsId(String containsId) {
        this.containsId = containsId;
    }

    public String getFareRoutePK() {
        return fareRoutePK;
    }

    public void setFareRoutePK(String routeId, String fareId) {
        this.fareRoutePK = routeId + fareId;
    }
}
