package com.example.matthew.transit.model;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvIgnore;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class FareRule extends RealmObject {
    public static final int FARE_ID = 0;
    public static final int ROUTE_ID = 1;
    // added to create a composite key from fareId and routeId
    @PrimaryKey
    @CsvIgnore
    private String fareRoutePK;
    @Required
    @CsvCell(columnName = "fare_id")
    private String fareId;
    @CsvCell(columnName = "route_id")
    private String routeId;
    @Ignore
    @CsvIgnore
    @CsvCell(columnName = "origin_id")
    private String originId;
    @Ignore
    @CsvIgnore
    @CsvCell(columnName = "destination_id")
    private String destinationId;
    @Ignore
    @CsvIgnore
    @CsvCell(columnName = "contains_id")
    private String containsId;

    private Route route;
    private FareAttribute fareAttribute;

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

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public FareAttribute getFareAttribute() {
        return fareAttribute;
    }

    public void setFareAttribute(FareAttribute fareAttribute) {
        this.fareAttribute = fareAttribute;
    }
}
