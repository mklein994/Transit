package com.example.matthew.transit.database;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class FareAttribute extends RealmObject {
    @Required
    private String fareId;

    // required
    private double price;

    @Required
    private String currencyType;

    // required
    private byte paymentMethod;

    // required
    private byte transfers;

    private Integer transferDuration;
}
