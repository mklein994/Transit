package com.example.matthew.transit;

/**
 * Created by matthew on 09/05/16.
 */
public enum TransitUriEnum {
    // <code>, <path>, <content type>, <single or many>, <table>
    AGENCIES(100, "agency", TransitContract.Agency.CONTENT_TYPE_ID, false, TransitDatabaseHelper.Tables.AGENCY),
    AGENCY_ID(101, "agency/*", TransitContract.Agency.CONTENT_TYPE_ID, true, null),
    CALENDARS(200, "calendar", TransitContract.Calendar.CONTENT_TYPE_ID, false, TransitDatabaseHelper.Tables.CALENDAR),
    CALENDAR_ID(201, "calendar/*", TransitContract.Calendar.CONTENT_TYPE_ID, true, null),
    CALENDAR_DATES(300, "calendar_date", TransitContract.CalendarDate.CONTENT_TYPE_ID, false, TransitDatabaseHelper.Tables.CALENDAR_DATE),
    CALENDAR_DATE_ID(301, "calendar_date/*", TransitContract.CalendarDate.CONTENT_TYPE_ID, true, null),
    FARE_ATTRIBUTES(400, "fare_attribute", TransitContract.FareAttribute.CONTENT_TYPE_ID, false, TransitDatabaseHelper.Tables.FARE_ATTRIBUTE),
    FARE_ATTRIBUTE_ID(401, "fare_attribute/*", TransitContract.FareAttribute.CONTENT_TYPE_ID, true, null),
    FARE_RULES(500, "fare_rule", TransitContract.FareRule.CONTENT_TYPE_ID, false, TransitDatabaseHelper.Tables.FARE_RULE),
    FARE_RULE_ID(501, "fare_rule/*", TransitContract.FareRule.CONTENT_TYPE_ID, true, null),
    ROUTES(600, "route", TransitContract.Route.CONTENT_TYPE_ID, false, TransitDatabaseHelper.Tables.ROUTE),
    ROUTE_ID(601, "route/*", TransitContract.Route.CONTENT_TYPE_ID, true, null),
    SEARCH_ROUTES(602, "search_routes", TransitContract.Route.CONTENT_TYPE_ID, false, TransitDatabaseHelper.Tables.ROUTE),
    SHAPES(700, "shape", TransitContract.Shape.CONTENT_TYPE_ID, false, TransitDatabaseHelper.Tables.SHAPE),
    SHAPE_ID(701, "shape/*", TransitContract.Shape.CONTENT_TYPE_ID, true, null),
    STOP_TIMES(800, "stop_time", TransitContract.StopTime.CONTENT_TYPE_ID, false, TransitDatabaseHelper.Tables.STOP_TIME),
    STOP_TIME_ID(801, "stop_time/*", TransitContract.StopTime.CONTENT_TYPE_ID, true, null),
    TRIPS(900, "trip", TransitContract.Trip.CONTENT_TYPE_ID, false, TransitDatabaseHelper.Tables.TRIP),
    TRIP_ID(901, "trip/*", TransitContract.Trip.CONTENT_TYPE_ID, true, null);

    public int code;

    public String path;

    public String contentType;

    public String table;

    TransitUriEnum(int code, String path, String contentTypeId, boolean item, String table) {
        this.code = code;

        this.path = path;

        this.contentType = item ? TransitContract.makeContentItemType(contentTypeId)
                : TransitContract.makeContentType(contentTypeId);

        this.table = table;
    }
}
