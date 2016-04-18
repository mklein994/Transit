package com.example.matthew.transit.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Route extends RealmObject {
    private static final int ROUTE_ID = 0;
    private static final int ROUTE_SHORT_NAME = 1;
    private static final int ROUTE_LONG_NAME = 2;
    private static final int ROUTE_TYPE = 3;
    private static final int ROUTE_COLOR = 4;
    private static final int ROUTE_TEXT_COLOR = 5;
    @PrimaryKey
    private String routeId;
    @Ignore
    private String agencyId;
    @Required
    private String routeShortName;
    @Required
    private String routeLongName;
    @Ignore
    private String routeDesc;
    // required
    private byte routeType;
    private String routeUrl;
    private String routeColor;
    private String routeTextColor;
    private RealmList<Trip> trips;
    @Ignore
    private Agency agency;
    private RealmList<FareRule> fareRules;
    private RealmList<FareAttribute> fareAttributes;
    private RealmList<Shape> shapes;
    private RealmList<Calendar> calendars;
    private RealmList<CalendarDate> calendarDates;
    private RealmList<Stop> stops;

    public Route() {
    }

    public Route(String[] fields) {
        this.routeId = fields[ROUTE_ID];
        this.routeShortName = fields[ROUTE_SHORT_NAME];
        this.routeLongName = fields[ROUTE_LONG_NAME];
        this.routeType = ModelUtils.parseByte((fields[ROUTE_TYPE]));
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

    public RealmList<FareRule> getFareRules() {
        return fareRules;
    }

    public void setFareRules(RealmList<FareRule> fareRules) {
        this.fareRules = fareRules;
    }

    public RealmList<FareAttribute> getFareAttributes() {
        return fareAttributes;
    }

    public void setFareAttributes(RealmList<FareAttribute> fareAttributes) {
        this.fareAttributes = fareAttributes;
    }

    public RealmList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(RealmList<Shape> shapes) {
        this.shapes = shapes;
    }

    public RealmList<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(RealmList<Calendar> calendars) {
        this.calendars = calendars;
    }

    public RealmList<CalendarDate> getCalendarDates() {
        return calendarDates;
    }

    public void setCalendarDates(RealmList<CalendarDate> calendarDates) {
        this.calendarDates = calendarDates;
    }

    public RealmList<Stop> getStops() {
        return stops;
    }

    public void setStops(RealmList<Stop> stops) {
        this.stops = stops;
    }
}
