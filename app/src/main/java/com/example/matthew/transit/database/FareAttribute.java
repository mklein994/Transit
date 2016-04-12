package com.example.matthew.transit.database;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class FareAttribute extends RealmObject {
    @Required
    private String fareId;

    @Required
    private String price;

    @Required
    private String currencyType;

    @Required
    private String paymentMethod;

    @Required
    private String transfers;

    private String transferDuration;
}
