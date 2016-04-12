package com.example.matthew.transit.database;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Transfer extends RealmObject {
    @Required
    private String fromStopId;

    @Required
    private String toStopId;

    @Required
    private String transferType;

    private String minTransferTime;
}
