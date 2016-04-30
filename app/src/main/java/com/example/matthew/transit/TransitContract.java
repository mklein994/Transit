package com.example.matthew.transit;

import android.provider.BaseColumns;

/**
 * Created by matthew on 29/04/16.
 */
public final class TransitContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public TransitContract() {
    }

    public static abstract class Agency implements BaseColumns {

        public static final String AGENCY_NAME = "agency_name";
        public static final String AGENCY_URL = "agency_url";
        public static final String AGENCY_TIMEZONE = "agency_timezone";
        public static final String AGENCY_LANG = "agency_lang";
        public static final String AGENCY_PHONE = "agency_phone";
    }

    public static abstract class Calendar implements BaseColumns {

        public static final String SERVICE_ID = "service_id";
        public static final String MONDAY = "monday";
        public static final String TUESDAY = "tuesday";
        public static final String WEDNESDAY = "wednesday";
        public static final String THURSDAY = "thursday";
        public static final String FRIDAY = "friday";
        public static final String SATURDAY = "saturday";
        public static final String SUNDAY = "sunday";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
    }

    public static abstract class CalendarDate implements BaseColumns {

        public static final String SERVICE_ID = "service_id";
        public static final String DATE = "date";
        public static final String EXCEPTION_TYPE = "exception_type";
    }

    public static abstract class FareAttribute implements BaseColumns {

        public static final String FARE_ID = "fare_id";
        public static final String PRICE = "price";
        public static final String CURRENCY_TYPE = "currency_type";
        public static final String PAYMENT_METHOD = "payment_method";
        public static final String TRANSFERS = "transfers";
        public static final String TRANSFER_DURATION = "transfer_duration";
    }

    public static abstract class FareRule implements BaseColumns {

        public static final String FARE_ID = "fare_id";
        public static final String ROUTE_ID = "route_id";
    }

    //FeedInfo

    //Frequency

    public static abstract class Route implements BaseColumns {

        public static final String ROUTE_ID = "route_id";
        public static final String ROUTE_SHORT_NAME = "route_short_name";
        public static final String ROUTE_LONG_NAME = "route_long_name";
        public static final String ROUTE_TYPE = "route_type";
        public static final String ROUTE_COLOR = "route_color";
        public static final String ROUTE_TEXT_COLOR = "route_text_color";
    }

    public static abstract class Shape implements BaseColumns {

        public static final String SHAPE_ID = "shape_id";
        public static final String SHAPE_PT_LAT = "shape_pt_lat";
        public static final String SHAPE_PT_LON = "shape_pt_lon";
        public static final String SHAPE_PT_SEQUENCE = "shape_pt_sequence";
    }

    public static abstract class Stop implements BaseColumns {

        public static final String STOP_ID = "stop_id";
        public static final String STOP_CODE = "stop_code";
        public static final String STOP_NAME = "stop_name";
        public static final String STOP_LAT = "stop_lat";
        public static final String STOP_LON = "stop_lon";
        public static final String STOP_URL = "stop_url";
    }

    public static abstract class StopTime implements BaseColumns {

        public static final String TRIP_ID = "trip_id";
        public static final String ARRIVAL_TIME = "arrival_time";
        public static final String DEPARTURE_TIME = "departure_time";
        public static final String STOP_ID = "stop_id";
        public static final String STOP_SEQUENCE = "stop_sequence";
    }

    //Transfer

    public static abstract class Trip implements BaseColumns {

        public static final String ROUTE_ID = "route_id";
        public static final String SERVICE_ID = "service_id";
        public static final String TRIP_ID = "trip_id";
        public static final String TRIP_HEADSIGN = "trip_headsign";
        public static final String DIRECTION_ID = "direction_id";
        public static final String BLOCK_ID = "block_id";
        public static final String SHAPE_ID = "shape_id";
        public static final String WHEELCHAIR_ACCESSIBLE = "wheelchair_accessible";
    }

    /*
    public static final String TABLE_NAME = "agency";
    public static final String TABLE_NAME = "calendar";
    public static final String TABLE_NAME = "calendar_date";
    public static final String TABLE_NAME = "fare_attribute";
    public static final String TABLE_NAME = "fare_rule";
    public static final String TABLE_NAME = "route";
    public static final String TABLE_NAME = "shape";
    public static final String TABLE_NAME = "stop";
    public static final String TABLE_NAME = "stop_time";
    public static final String TABLE_NAME = "trip";
     */
}
