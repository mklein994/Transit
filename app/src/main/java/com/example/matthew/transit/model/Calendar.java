package com.example.matthew.transit.model;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvDate;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Calendar extends RealmObject {

    private static final int SERVICE_ID = 0;
    private static final int MONDAY = 1;
    private static final int TUESDAY = 2;
    private static final int WEDNESDAY = 3;
    private static final int THURSDAY = 4;
    private static final int FRIDAY = 5;
    private static final int SATURDAY = 6;
    private static final int SUNDAY = 7;
    private static final int START_DATE = 8;
    private static final int END_DATE = 9;
    @PrimaryKey
    @CsvCell(columnName = "service_id")
    private String serviceId;
    // required
    private boolean monday;
    // required
    private boolean tuesday;
    // required
    private boolean wednesday;
    // required
    private boolean thursday;
    // required
    private boolean friday;
    // required
    private boolean saturday;
    // required
    private boolean sunday;
    @Required
    @CsvCell(columnName = "start_date")
    @CsvDate(format = "yyyyMMdd")
    private Date startDate;
    @Required
    @CsvCell(columnName = "end_date")
    @CsvDate(format = "yyyyMMdd")
    private Date endDate;

    public Calendar() {
    }

    public Calendar(String[] fields) {
        this.serviceId = fields[SERVICE_ID];
        this.monday = ModelUtils.parseBoolean(fields[MONDAY]);
        this.tuesday = ModelUtils.parseBoolean(fields[TUESDAY]);
        this.wednesday = ModelUtils.parseBoolean(fields[WEDNESDAY]);
        this.thursday = ModelUtils.parseBoolean(fields[THURSDAY]);
        this.friday = ModelUtils.parseBoolean(fields[FRIDAY]);
        this.saturday = ModelUtils.parseBoolean(fields[SATURDAY]);
        this.sunday = ModelUtils.parseBoolean(fields[SUNDAY]);
        this.startDate = ModelUtils.parseDate(fields[START_DATE]);
        this.endDate = ModelUtils.parseDate(fields[END_DATE]);
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public boolean getMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean getThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean getFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean getSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
