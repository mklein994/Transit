package com.example.matthew.transit.database;

import org.csveed.annotations.CsvCell;
import org.csveed.annotations.CsvIgnore;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class FareAttribute extends RealmObject {
    @PrimaryKey
    @CsvCell(columnName = "fare_id")
    private String fareId;

    // required
    private double price;

    @Required
    @CsvCell(columnName = "currency_type")
    private String currencyType;

    // required
    @CsvCell(columnName = "payment_method")
    private byte paymentMethod;

    // required
    private byte transfers;

    @CsvCell(columnName = "transfer_duration")
    private Integer transferDuration;

    @CsvIgnore
    private RealmList<FareRule> fareRules;
    @CsvIgnore
    private RealmList<Route> routes;

    public String getFareId() {
        return fareId;
    }

    public void setFareId(String fareId) {
        this.fareId = fareId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public byte getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(byte paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public byte getTransfers() {
        return transfers;
    }

    public void setTransfers(byte transfers) {
        this.transfers = transfers;
    }

    public Integer getTransferDuration() {
        return transferDuration;
    }

    public void setTransferDuration(Integer transferDuration) {
        this.transferDuration = transferDuration;
    }

    public RealmList<FareRule> getFareRules() {
        return fareRules;
    }

    public void setFareRules(RealmList<FareRule> fareRules) {
        this.fareRules = fareRules;
    }

    public RealmList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(RealmList<Route> routes) {
        this.routes = routes;
    }
}
