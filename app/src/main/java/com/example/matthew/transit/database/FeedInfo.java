package com.example.matthew.transit.database;

import java.util.Date;

import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class FeedInfo {
    @Required
    private String feedPublisherName;

    @Required
    private String feedPublisherUrl;

    @Required
    private String feedLang;

    private Date feedStartDate;

    private Date feedEndDate;

    private String feedVersion;
}
