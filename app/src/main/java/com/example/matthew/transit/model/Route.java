package com.example.matthew.transit.model;

/**
 * Created by matthew on 11/04/16.
 */
public class Route {
    private static final int ROUTE_ID = 0;
    private static final int ROUTE_SHORT_NAME = 1;
    private static final int ROUTE_LONG_NAME = 2;
    private static final int ROUTE_TYPE = 3;
    private static final int ROUTE_COLOR = 4;
    private static final int ROUTE_TEXT_COLOR = 5;
    // primary key
    private String routeId;
    // ignore
    private String agencyId;
    // required
    private String routeShortName;
    // required
    private String routeLongName;
    // ignore
    private String routeDesc;
    // required
    private int routeType;

    private String routeUrl;

    private String routeColor;

    private String routeTextColor;

    public Route() {
    }

    public Route(String[] fields) {
        this.routeId = fields[ROUTE_ID];
        this.routeShortName = fields[ROUTE_SHORT_NAME];
        this.routeLongName = fields[ROUTE_LONG_NAME];
        this.routeType = ModelUtils.parseInt((fields[ROUTE_TYPE]));
        this.routeColor = fields[ROUTE_COLOR];
        this.routeTextColor = fields[ROUTE_TEXT_COLOR];
    }

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

    public long getRouteType() {
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
}
