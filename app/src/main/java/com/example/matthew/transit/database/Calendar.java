package com.example.matthew.transit.database;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Calendar extends RealmObject {
    @PrimaryKey
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
    private Date startDate;

    @Required
    private Date endDate;

    private RealmList<Route> routes;
    private RealmList<CalendarDate> calendarDates;
    private RealmList<Trip> trips;
    private Shape shape;
}
