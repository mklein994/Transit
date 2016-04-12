package com.example.matthew.transit.database;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class FareRule extends RealmObject {
    @Required
    private String fareId;

    private String routeId;

    @Ignore
    private String originId;

    @Ignore
    private String destinationId;

    @Ignore
    private String containsId;

    private RealmList<FareAttribute> fareAttributes;
    private RealmList<Route> routes;
    private RealmList<Trip> trips;
}
