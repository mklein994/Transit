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
public class Route extends RealmObject {
    @PrimaryKey
    @CsvCell(columnName = "route_id")
    private String routeId;

    @Ignore
    @CsvCell(columnName = "agency_id")
    private String agencyId;

    @Required
    @CsvCell(columnName = "route_short_name")
    private String routeShortName;

    @Required
    @CsvCell(columnName = "route_long_name")
    private String routeLongName;

    @Ignore
    @CsvCell(columnName = "route_desc")
    private String routeDesc;

    // required
    @CsvCell(columnName = "route_type")
    private byte routeType;

    @CsvCell(columnName = "route_url")
    private String routeUrl;

    @CsvCell(columnName = "route_color")
    private String routeColor;

    @CsvCell(columnName = "route_text_color")
    private String routeTextColor;

    @CsvIgnore
    private RealmList<Trip> trips;

    @Ignore
    @CsvIgnore
    private Agency agency;

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getRouteShortName() {
        return routeShortName;
    }

    public void setRouteShortName(String routeShortName) {
        this.routeShortName = routeShortName;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    public void setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }

    public String getRouteDesc() {
        return routeDesc;
    }

    public void setRouteDesc(String routeDesc) {
        this.routeDesc = routeDesc;
    }

    public byte getRouteType() {
        return routeType;
    }

    public void setRouteType(byte routeType) {
        this.routeType = routeType;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }

    public String getRouteColor() {
        return routeColor;
    }

    public void setRouteColor(String routeColor) {
        this.routeColor = routeColor;
    }

    public String getRouteTextColor() {
        return routeTextColor;
    }

    public void setRouteTextColor(String routeTextColor) {
        this.routeTextColor = routeTextColor;
    }

    public RealmList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(RealmList<Trip> trips) {
        this.trips = trips;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }
}
