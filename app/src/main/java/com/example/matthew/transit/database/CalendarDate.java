package com.example.matthew.transit.database;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class CalendarDate extends RealmObject {
    @Required
    private String serviceId;

    @Required
    private Date date;

    // required
    private byte exceptionType;

    private RealmList<Trip> trips;
    private RealmList<Calendar> calendars;

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

    public byte getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(byte exceptionType) {
        this.exceptionType = exceptionType;
    }

    public RealmList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(RealmList<Trip> trips) {
        this.trips = trips;
    }

    public RealmList<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(RealmList<Calendar> calendars) {
        this.calendars = calendars;
    }
}
