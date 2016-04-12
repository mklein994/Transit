package com.example.matthew.transit.database;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Trip extends RealmObject {
    @Required
    private String routeId;

    @Required
    private String serviceId;

    @PrimaryKey
    private String tripId;

    private String tripHeadsign;

    @Ignore
    private String tripShortName;

    private Byte directionId;

    private String blockId;

    private String shapeId;

    private Byte wheelchairAccessible;

    @Ignore
    private Byte bikesAllowed;
}
