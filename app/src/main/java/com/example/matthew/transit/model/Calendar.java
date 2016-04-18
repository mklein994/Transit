package com.example.matthew.transit.model;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvDate;
import org.csveed.annotations.CsvIgnore;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Calendar extends RealmObject {

    private static final int SERVICE_ID = 0;
    private static final int MONDAY = 1;
    private static final int TUESDAY = 2;
    private static final int WEDNESDAY = 3;
    private static final int THURSDAY = 4;
    private static final int FRIDAY = 5;
    private static final int SATURDAY = 6;
    private static final int SUNDAY = 7;
    private static final int START_DATE = 8;
    private static final int END_DATE = 9;
    @PrimaryKey
    @CsvCell(columnName = "service_id")
    private String serviceId;
    // required
    private byte monday;
    // required
    private byte tuesday;
    // required
    private byte wednesday;
    // required
    private byte thursday;
    // required
    private byte friday;
    // required
    private byte saturday;
    // required
    private byte sunday;
    @Required
    @CsvCell(columnName = "start_date")
    @CsvDate(format = "yyyyMMdd")
    private Date startDate;
    @Required
    @CsvCell(columnName = "end_date")
    @CsvDate(format = "yyyyMMdd")
    private Date endDate;

    @CsvIgnore
    private RealmList<Route> routes;
    @CsvIgnore
    private RealmList<CalendarDate> calendarDates;
    @CsvIgnore
    private RealmList<Trip> trips;

    public Calendar() {
    }

    public Calendar(String[] fields) {
        this.serviceId = fields[SERVICE_ID];
        this.monday = ModelUtils.parseByte(fields[MONDAY]);
        this.tuesday = ModelUtils.parseByte(fields[TUESDAY]);
        this.wednesday = ModelUtils.parseByte(fields[WEDNESDAY]);
        this.thursday = ModelUtils.parseByte(fields[THURSDAY]);
        this.friday = ModelUtils.parseByte(fields[FRIDAY]);
        this.saturday = ModelUtils.parseByte(fields[SATURDAY]);
        this.sunday = ModelUtils.parseByte(fields[SUNDAY]);
        this.startDate = ModelUtils.parseDate(fields[START_DATE]);
        this.endDate = ModelUtils.parseDate(fields[END_DATE]);
    }

    @CsvIgnore
    private Shape shape;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public byte getMonday() {
        return monday;
    }

    public void setMonday(byte monday) {
        this.monday = monday;
    }

    public byte getTuesday() {
        return tuesday;
    }

    public void setTuesday(byte tuesday) {
        this.tuesday = tuesday;
    }

    public byte getWednesday() {
        return wednesday;
    }

    public void setWednesday(byte wednesday) {
        this.wednesday = wednesday;
    }

    public byte getThursday() {
        return thursday;
    }

    public void setThursday(byte thursday) {
        this.thursday = thursday;
    }

    public byte getFriday() {
        return friday;
    }

    public void setFriday(byte friday) {
        this.friday = friday;
    }

    public byte getSaturday() {
        return saturday;
    }

    public void setSaturday(byte saturday) {
        this.saturday = saturday;
    }

    public byte getSunday() {
        return sunday;
    }

    public void setSunday(byte sunday) {
        this.sunday = sunday;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public RealmList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(RealmList<Trip> trips) {
        this.trips = trips;
    }

    public RealmList<CalendarDate> getCalendarDates() {
        return calendarDates;
    }

    public void setCalendarDates(RealmList<CalendarDate> calendarDates) {
        this.calendarDates = calendarDates;
    }

    public RealmList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(RealmList<Route> routes) {
        this.routes = routes;
    }
}
