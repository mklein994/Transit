package com.example.matthew.transit.database;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by matthew on 11/04/16.
 */
public class Shape extends RealmObject {
    @PrimaryKey
    private String shapeId;

    // required
    private double shapePtLat;

    // required
    private double shapePtLon;

    // required
    private int shapePtSequence;

    @Ignore
    private Double shapeDistTraveled;

    private RealmList<Trip> trips;
    private RealmList<Route> routes;
    private RealmList<Calendar> calendars;
}
