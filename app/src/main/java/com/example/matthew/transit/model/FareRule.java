package com.example.matthew.transit.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class FareRule extends RealmObject {
    private static final int FARE_ID = 0;
    private static final int ROUTE_ID = 1;
    // added to create a composite key from fareId and routeId
    @PrimaryKey
    private String fareRoutePK;
    @Required
    private String fareId;
    private String routeId;
    @Ignore
    private String originId;
    @Ignore
    private String destinationId;
    @Ignore
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
