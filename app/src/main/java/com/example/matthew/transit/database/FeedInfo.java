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

    public String getFeedPublisherName() {
        return feedPublisherName;
    }

    public void setFeedPublisherName(String feedPublisherName) {
        this.feedPublisherName = feedPublisherName;
    }

    public String getFeedPublisherUrl() {
        return feedPublisherUrl;
    }

    public void setFeedPublisherUrl(String feedPublisherUrl) {
        this.feedPublisherUrl = feedPublisherUrl;
    }

    public String getFeedLang() {
        return feedLang;
    }

    public void setFeedLang(String feedLang) {
        this.feedLang = feedLang;
    }

    public Date getFeedStartDate() {
        return feedStartDate;
    }

    public void setFeedStartDate(Date feedStartDate) {
        this.feedStartDate = feedStartDate;
    }

    public Date getFeedEndDate() {
        return feedEndDate;
    }

    public void setFeedEndDate(Date feedEndDate) {
        this.feedEndDate = feedEndDate;
    }

    public String getFeedVersion() {
        return feedVersion;
    }

    public void setFeedVersion(String feedVersion) {
        this.feedVersion = feedVersion;
    }
}
