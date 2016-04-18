package com.example.matthew.transit.model;

import org.csveed.annotations.CsvCell;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class FareAttribute extends RealmObject {
    private static final int FARE_ID = 0;
    private static final int PRICE = 1;
    private static final int CURRENCY_TYPE = 2;
    private static final int PAYMENT_METHOD = 3;
    private static final int TRANSFERS = 4;
    private static final int TRANSFER_DURATION = 5;
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
    // GTFS requires it, but an empty string is allowed.
    private Byte transfers;

    @CsvCell(columnName = "transfer_duration")
    private Integer transferDuration;

    public FareAttribute() {
    }

    public FareAttribute(String[] fields) {
        this.fareId = fields[FARE_ID];
        this.price = Double.parseDouble(fields[PRICE]);
        this.currencyType = fields[CURRENCY_TYPE];
        this.paymentMethod = ModelUtils.parseByte(fields[PAYMENT_METHOD]);
        this.transfers = ModelUtils.parseByte(fields[TRANSFERS]);
        this.transferDuration = ModelUtils.parseInt(fields[TRANSFER_DURATION]);
    }

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

    public Byte getTransfers() {
        return transfers;
    }

    public void setTransfers(Byte transfers) {
        this.transfers = transfers;
    }

    public Integer getTransferDuration() {
        return transferDuration;
    }

    public void setTransferDuration(Integer transferDuration) {
        this.transferDuration = transferDuration;
    }
}
