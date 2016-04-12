package com.example.matthew.transit.database;

import org.csveed.annotations.CsvCell;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by matthew on 11/04/16.
 */
public class Transfer extends RealmObject {
    @Required
    @CsvCell(columnName = "from_stop_id")
    private String fromStopId;

    @Required
    @CsvCell(columnName = "to_stop_id")
    private String toStopId;

    // required
    @CsvCell(columnName = "transfer_type")
    private byte transferType;

    @CsvCell(columnName = "min_transfer_time")
    private Integer minTransferTime;

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
