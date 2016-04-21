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
public class Route extends RealmObject {
    public static final int ROUTE_ID = 0;
    public static final int ROUTE_SHORT_NAME = 1;
    public static final int ROUTE_LONG_NAME = 2;
    public static final int ROUTE_TYPE = 3;
    public static final int ROUTE_URL = 4;
    public static final int ROUTE_COLOR = 5;
    public static final int ROUTE_TEXT_COLOR = 6;
    @PrimaryKey
    @CsvCell(columnName = "route_id")
    private String routeId;
    @Ignore
    @CsvIgnore
    @CsvCell(columnName = "agency_id")
    private String agencyId;
    @Required
    @CsvCell(columnName = "route_short_name")
    private String routeShortName;
    @Required
    @CsvCell(columnName = "route_long_name")
    private String routeLongName;
    @Ignore
    @CsvIgnore
    @CsvCell(columnName = "route_desc")
    private String routeDesc;
    // required
    @CsvCell(columnName = "route_type")
    private int routeType;

    @CsvCell(columnName = "route_url")
    private String routeUrl;

    @CsvCell(columnName = "route_color")
    private String routeColor;

    @CsvCell(columnName = "route_text_color")
    private String routeTextColor;

    private RealmList<FareRule> fareRules;
    private RealmList<FareAttribute> fareAttributes;
    private RealmList<Trip> trips;
    private RealmList<Shape> shapes;
    private RealmList<Calendar> calendars;
    private RealmList<CalendarDate> calendarDates;

    public Route() {
    }

    public Route(String[] fields) {
        this.routeId = fields[ROUTE_ID];
        this.routeShortName = fields[ROUTE_SHORT_NAME];
        this.routeLongName = fields[ROUTE_LONG_NAME];
        this.routeUrl = fields[ROUTE_URL];
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

    public void setRouteType(Integer routeType) {
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

    public RealmList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(RealmList<Trip> trips) {
        this.trips = trips;
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
}
