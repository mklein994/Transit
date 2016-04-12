package com.example.matthew.transit.database;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Route extends RealmObject {
    @PrimaryKey
    private String routeId;

    @Ignore
    private String agencyId;

    @Required
    private String routeShortName;

    @Required
    private String routeLongName;

    @Ignore
    private String routeDesc;

    // required
    private byte routeType;

    private String routeUrl;

    private String routeColor;

    private String routeTextColor;

    private RealmList<Trip> trips;

    @Ignore
    private Agency agency;
}
