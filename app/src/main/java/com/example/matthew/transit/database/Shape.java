package com.example.matthew.transit.database;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Shape extends RealmObject {
    @Required
    private String shapeId;

    // required
    private double shapePtLat;

    // required
    private double shapePtLon;

    // required
    private int shapePtSequence;

    @Ignore
    private Double shapeDistTraveled;
}
