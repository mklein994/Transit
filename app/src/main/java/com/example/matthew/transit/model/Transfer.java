package com.example.matthew.transit.model;

/**
 * Created by matthew on 11/04/16.
 */
public class Transfer {
    // required
    private String fromStopId;
    // required
    private String toStopId;
    // required
    private byte transferType;
    private Integer minTransferTime;

    public Transfer() {
    }

    public String getFromStopId() {
        return fromStopId;
    }

    public void setFromStopId(String fromStopId) {
        this.fromStopId = fromStopId;
    }

    public String getToStopId() {
        return toStopId;
    }

    public void setToStopId(String toStopId) {
        this.toStopId = toStopId;
    }

    public byte getTransferType() {
        return transferType;
    }

    public void setTransferType(byte transferType) {
        this.transferType = transferType;
    }

    public Integer getMinTransferTime() {
        return minTransferTime;
    }

    public void setMinTransferTime(Integer minTransferTime) {
        this.minTransferTime = minTransferTime;
    }
}
