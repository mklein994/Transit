package com.example.matthew.transit.database;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Agency extends RealmObject {
    @Ignore
    private String agencyId;

    @Required
    private String agencyName;

    @Required
    private String agencyUrl;

    @Required
    private String agencyTimezone;

    private String agencyLang;

    private String agencyPhone;

    @Ignore
    private String agencyFareUrl;

    @Ignore
    private String agencyEmail;
}
