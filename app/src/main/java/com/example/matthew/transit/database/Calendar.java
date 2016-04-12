package com.example.matthew.transit.database;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Calendar extends RealmObject {
    @Required
    private String serviceId;

    @Required
    private String monday;

    @Required
    private String tuesday;

    @Required
    private String wednesday;

    @Required
    private String thursday;

    @Required
    private String friday;

    @Required
    private String saturday;

    @Required
    private String sunday;

    @Required
    private String startDate;

    @Required
    private String endDate;
}
