package com.example.matthew.transit.database;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class CalendarDate extends RealmObject {
    @Required
    private String serviceId;

    @Required
    private String date;

    @Required
    private String exceptionType;
}
