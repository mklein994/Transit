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
public class FareRule extends RealmObject {
    private static final int FARE_ID = 0;
    private static final int ROUTE_ID = 1;
    // added to create a composite key from fareId and routeId
    @PrimaryKey
    private String fareRoutePK;

    @Required
    @CsvCell(columnName = "fare_id")
    private String fareId;
    @CsvCell(columnName = "route_id")
    private String routeId;
    @Ignore
    @CsvCell(columnName = "origin_id")
    private String originId;
    @Ignore
    @CsvCell(columnName = "destination_id")
    private String destinationId;
    @Ignore
    @CsvCell(columnName = "contains_id")
    private String containsId;

    @CsvIgnore
    private RealmList<FareAttribute> fareAttributes;
    @CsvIgnore
    private RealmList<Route> routes;
    @CsvIgnore
    private RealmList<Trip> trips;

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

    public RealmList<FareAttribute> getFareAttributes() {
        return fareAttributes;
    }

    public setFareAttributes(RealmList<FareAttribute> fareAttributes) {
        this.fareAttributes = fareAttributes;
    }

    public RealmList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(RealmList<Route> routes) {
        this.routes = routes;
    }

    public RealmList<Trip> getTrips() {
        return trips;
    }
    
    public void setTrips(RealmList<Trip> trips) {
        this.trips = trips;
    }

    public void setFareRoutePK(String routeId, String fareId) {
        this.fareRoutePK = routeId + fareId;
    }
}
