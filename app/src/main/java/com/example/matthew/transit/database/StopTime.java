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

    // required
    private int stopSequence;

    @Ignore
    private String stopHeadsign;

    @Ignore
    private Byte pickupType;

    @Ignore
    private Byte dropOffType;

    @Ignore
    private Double shapeDistTraveled;

    @Ignore
    private Byte timepoint;
}
