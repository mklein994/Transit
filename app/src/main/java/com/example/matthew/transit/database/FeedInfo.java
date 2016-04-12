package com.example.matthew.transit.database;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvDate;

import java.util.Date;

import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class FeedInfo {
    @Required
    @CsvCell(columnName = "feed_publisher_name")
    private String feedPublisherName;

    @Required
    @CsvCell(columnName = "feed_publisher_url")
    private String feedPublisherUrl;

    @Required
    @CsvCell(columnName = "feed_lang")
    private String feedLang;

    @CsvCell(columnName = "feed_start_date")
    @CsvDate(format = "yyyyMMdd")
    private Date feedStartDate;

    @CsvCell(columnName = "feed_end_date")
    @CsvDate(format = "yyyyMMdd")
    private Date feedEndDate;

    @CsvCell(columnName = "feed_version")
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
