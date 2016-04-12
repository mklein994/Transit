package com.example.matthew.transit.database;

import java.util.Date;

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
}
