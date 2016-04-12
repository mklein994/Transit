package com.example.matthew.transit.database;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Stop extends RealmObject {
    @Required
    private String stopId;

    private String stopCode;

    @Required
    private String stopName;

    @Ignore
    private String stopDesc;

    // required
    private double stopLat;

    // required
    private double stopLon;

    @Ignore
    private String zoneId;

    private String stopUrl;

    @Ignore
    private byte locationType;

    @Ignore
    private String parentStation;

    @Ignore
    private String stopTimezone;

    @Ignore
    private byte wheelchairBoarding;
}
