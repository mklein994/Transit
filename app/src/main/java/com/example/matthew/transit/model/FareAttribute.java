package com.example.matthew.transit.model;

/**
 * Created by matthew on 11/04/16.
 */
public class FareAttribute {
    private static final int FARE_ID = 0;
    private static final int PRICE = 1;
    private static final int CURRENCY_TYPE = 2;
    private static final int PAYMENT_METHOD = 3;
    private static final int TRANSFERS = 4;
    private static final int TRANSFER_DURATION = 5;
    // primary key
    private String fareId;
    // required
    private double price;
    // required
    private String currencyType;
    // required
    private boolean paymentMethod;
    // GTFS requires it, but an empty string is allowed.
    private Integer transfers;

    private Integer transferDuration;

    public FareAttribute() {
    }

    public FareAttribute(String[] fields) {
        this.fareId = fields[FARE_ID];
        this.price = Double.parseDouble(fields[PRICE]);
        this.currencyType = fields[CURRENCY_TYPE];
        this.paymentMethod = ModelUtils.parseBoolean(fields[PAYMENT_METHOD]);
        this.transfers = ModelUtils.parseInt(fields[TRANSFERS]);
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

    public boolean getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(boolean paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getTransfers() {
        return transfers;
    }

    public void setTransfers(Integer transfers) {
        this.transfers = transfers;
    }

    public Integer getTransferDuration() {
        return transferDuration;
    }

    public void setTransferDuration(Integer transferDuration) {
        this.transferDuration = transferDuration;
    }
}
