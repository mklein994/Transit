package com.example.matthew.transit.database;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class StopTime extends RealmObject {
    @Required
    private String tripId;

    @Required
    private String arrivalTime;

    @Required
    private String departureTime;

    @Required
    private String stopId;

    @Required
    private String stopSequence;

    @Ignore
    private String stopHeadsign;

    @Ignore
    private String pickupType;

    @Ignore
    private String dropOffType;

    @Ignore
    private String shapeDistTraveled;

    @Ignore
    private String timepoint;
}
