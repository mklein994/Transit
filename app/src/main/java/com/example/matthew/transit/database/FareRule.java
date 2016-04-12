package com.example.matthew.transit.database;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvIgnore;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class FareRule extends RealmObject {
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

    public RealmList<FareAttribute> getFareAttributes() {
        return fareAttributes;
    }

    public void setFareAttributes(RealmList<FareAttribute> fareAttributes) {
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
}
