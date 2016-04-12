package com.example.matthew.transit.database;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvFile;
import org.csveed.annotations.CsvIgnore;
import org.csveed.bean.ColumnNameMapper;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
@CsvFile(mappingStrategy = ColumnNameMapper.class, separator = ',')
public class Agency extends RealmObject {
    // should be the primary key, except that it can be null.
    @Ignore
    @CsvCell(columnName = "agency_id")
    private String agencyId;

    @Required
    @CsvCell(columnName = "agency_name")
    private String agencyName;

    @Required
    @CsvCell(columnName = "agency_url")
    private String agencyUrl;

    @Required
    @CsvCell(columnName = "agency_timezone")
    private String agencyTimezone;

    @CsvCell(columnName = "agency_lang")
    private String agencyLang;

    @CsvCell(columnName = "agency_phone")
    private String agencyPhone;

    @Ignore
    @CsvCell(columnName = "agency_fare_url")
    private String agencyFareUrl;

    @Ignore
    @CsvCell(columnName = "agency_email")
    private String agencyEmail;

    @Ignore
    @CsvIgnore
    private RealmList<Route> routes;

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyUrl() {
        return agencyUrl;
    }

    public void setAgencyUrl(String agencyUrl) {
        this.agencyUrl = agencyUrl;
    }

    public String getAgencyTimezone() {
        return agencyTimezone;
    }

    public void setAgencyTimezone(String agencyTimezone) {
        this.agencyTimezone = agencyTimezone;
    }

    public String getAgencyLang() {
        return agencyLang;
    }

    public void setAgencyLang(String agencyLang) {
        this.agencyLang = agencyLang;
    }

    public String getAgencyPhone() {
        return agencyPhone;
    }

    public void setAgencyPhone(String agencyPhone) {
        this.agencyPhone = agencyPhone;
    }

    public String getAgencyFareUrl() {
        return agencyFareUrl;
    }

    public void setAgencyFareUrl(String agencyFareUrl) {
        this.agencyFareUrl = agencyFareUrl;
    }

    public String getAgencyEmail() {
        return agencyEmail;
    }

    public void setAgencyEmail(String agencyEmail) {
        this.agencyEmail = agencyEmail;
    }

    public RealmList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(RealmList<Route> routes) {
        this.routes = routes;
    }
}
