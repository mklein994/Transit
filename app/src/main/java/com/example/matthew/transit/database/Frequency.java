package com.example.matthew.transit.database;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */ //region Frequency model (not used)
public class Frequency extends RealmObject {
    @Required
    private String tripId;

    @Required
    private String startTime;

    @Required
    private String endTime;

    @Required
    private String headwaySecs;

    private String exactTimes;
}
