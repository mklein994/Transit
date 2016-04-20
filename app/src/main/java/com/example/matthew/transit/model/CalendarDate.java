package com.example.matthew.transit.model;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvDate;
import org.csveed.annotations.CsvIgnore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class CalendarDate extends RealmObject {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CANADA);
    private static final int SERVICE_ID = 0;
    private static final int DATE = 1;
    private static final int EXCEPTION_TYPE = 2;

    // added manually as a foreign key between serviceId and date
    @CsvIgnore
    @PrimaryKey
    private String calendarPK;
    @Required
    @CsvCell(columnName = "service_id")
    private String serviceId;
    @Required
    @CsvCell(columnName = "date")
    @CsvDate(format = "yyyyMMdd")
    private Date date;
    // required
    @CsvCell(columnName = "exception_type")
    private int exceptionType;

    private RealmList<Trip> trips;
    private RealmList<Route> routes;
    private RealmList<Shape> shapes;


    public CalendarDate(String[] fields) {
        this.serviceId = fields[SERVICE_ID];
        this.date = ModelUtils.parseDate(fields[DATE]);
        this.exceptionType = ModelUtils.parseInt(fields[EXCEPTION_TYPE]);
        setCalendarPK(this.serviceId, this.date);
    }

    public CalendarDate() {
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(byte exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getCalendarPK() {
        return calendarPK;
    }

    public void setCalendarPK(String serviceId, Date date) {
        this.calendarPK = serviceId + dateFormat.format(date);
    }

    public RealmList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(RealmList<Trip> trips) {
        this.trips = trips;
    }

    public RealmList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(RealmList<Route> routes) {
        this.routes = routes;
    }

    public RealmList<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(RealmList<Shape> shapes) {
        this.shapes = shapes;
    }
}
