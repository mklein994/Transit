package com.example.matthew.transit.model;

/**
 * Created by matthew on 11/04/16.
 */
public class Agency {

    private static final int AGENCY_NAME = 0;
    private static final int AGENCY_URL = 1;
    private static final int AGENCY_TIMEZONE = 2;
    private static final int AGENCY_LANG = 3;
    private static final int AGENCY_PHONE = 4;

    // should be the primary key, except that it can be null.
    // ignore
    private String agencyId;
    // required
    private String agencyName;
    // required
    private String agencyUrl;

    // required
    private String agencyTimezone;

    private String agencyLang;

    private String agencyPhone;
    // ignore
    private String agencyFareUrl;
    // ignore
    private String agencyEmail;

    public Agency() {
    }

    public Agency(String[] fields) {
        this.agencyName = fields[AGENCY_NAME];
        this.agencyUrl = fields[AGENCY_URL];
        this.agencyTimezone = fields[AGENCY_TIMEZONE];
        this.agencyLang = fields[AGENCY_LANG];
        this.agencyPhone = fields[AGENCY_PHONE];
    }

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
}
