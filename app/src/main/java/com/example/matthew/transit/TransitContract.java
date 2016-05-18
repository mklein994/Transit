package com.example.matthew.transit;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by matthew on 29/04/16.
 */
public final class TransitContract {

    public static final String CONTENT_TYPE_APP_BASE = "provider";

    public static final String CONTENT_TYPE_BASE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + CONTENT_TYPE_APP_BASE;
    public static final String CONTENT_ITEM_TYPE_BASE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + CONTENT_TYPE_APP_BASE;

    //public static final String CONTENT_TYPE_BASE = "vnd.android.cursor.dir/vnd." + CONTENT_TYPE_APP_BASE;
    //public static final String CONTENT_ITEM_TYPE_BASE = "vnd.android.cursor.item/vnd." + CONTENT_TYPE_APP_BASE;

    public static final String CONTENT_AUTHORITY = "com.example.matthew.transit.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public TransitContract() {
    }

    public static String makeContentType(String id) {
        if (id != null) {
            return CONTENT_TYPE_BASE + id;
        } else {
            return null;
        }
    }

    public static String makeContentItemType(String id) {
        if (id != null) {
            return CONTENT_ITEM_TYPE_BASE + id;
        } else {
            return null;
        }
    }

    interface AgencyColumns {

        String AGENCY_NAME = "agency_name";
        String AGENCY_URL = "agency_url";
        String AGENCY_TIMEZONE = "agency_timezone";
        String AGENCY_LANG = "agency_lang";
        String AGENCY_PHONE = "agency_phone";
    }

    interface CalendarColumns {

        String SERVICE_ID = "service_id";
        String MONDAY = "monday";
        String TUESDAY = "tuesday";
        String WEDNESDAY = "wednesday";
        String THURSDAY = "thursday";
        String FRIDAY = "friday";
        String SATURDAY = "saturday";
        String SUNDAY = "sunday";
        String START_DATE = "start_date";
        String END_DATE = "end_date";
    }

    interface CalendarDateColumns {

        String SERVICE_ID = "service_id";
        String DATE = "date";
        String EXCEPTION_TYPE = "exception_type";
    }

    interface FareAttributeColumns {

        String FARE_ID = "fare_id";
        String PRICE = "price";
        String CURRENCY_TYPE = "currency_type";
        String PAYMENT_METHOD = "payment_method";
        String TRANSFERS = "transfers";
        String TRANSFER_DURATION = "transfer_duration";
    }

    interface FareRuleColumns {

        String FARE_ID = "fare_id";
        String ROUTE_ID = "route_id";
    }

    //FeedInfo

    //Frequency

    interface RouteColumns {

        String ROUTE_ID = "route_id";
        String ROUTE_SHORT_NAME = "route_short_name";
        String ROUTE_LONG_NAME = "route_long_name";
        String ROUTE_TYPE = "route_type";
        String ROUTE_COLOR = "route_color";
        String ROUTE_TEXT_COLOR = "route_text_color";
    }

    interface ShapeColumns {

        String SHAPE_ID = "shape_id";
        String SHAPE_PT_LAT = "shape_pt_lat";
        String SHAPE_PT_LON = "shape_pt_lon";
        String SHAPE_PT_SEQUENCE = "shape_pt_sequence";
    }

    interface StopColumns {

        String STOP_ID = "stop_id";
        String STOP_CODE = "stop_code";
        String STOP_NAME = "stop_name";
        String STOP_LAT = "stop_lat";
        String STOP_LON = "stop_lon";
        String STOP_URL = "stop_url";
    }

    interface StopTimeColumns {

        String TRIP_ID = "trip_id";
        String ARRIVAL_TIME = "arrival_time";
        String DEPARTURE_TIME = "departure_time";
        String STOP_ID = "stop_id";
        String STOP_SEQUENCE = "stop_sequence";
    }

    //Transfer

    interface TripColumns {

        String ROUTE_ID = "route_id";
        String SERVICE_ID = "service_id";
        String TRIP_ID = "trip_id";
        String TRIP_HEADSIGN = "trip_headsign";
        String DIRECTION_ID = "direction_id";
        String BLOCK_ID = "block_id";
        String SHAPE_ID = "shape_id";
        String WHEELCHAIR_ACCESSIBLE = "wheelchair_accessible";
    }

    public static class Agency implements AgencyColumns, BaseColumns {

        public static final String CONTENT_TYPE_ID = "agency";

        public static final int AGENCY_NAME_INDEX = 0;
        public static final int AGENCY_URL_INDEX = 1;
        public static final int AGENCY_TIMEZONE_INDEX = 2;
        public static final int AGENCY_LANG_INDEX = 3;
        public static final int AGENCY_PHONE_INDEX = 4;
    }

    public static class Calendar implements CalendarColumns, BaseColumns {

        public static final String CONTENT_TYPE_ID = "calendar";

        public static final int SERVICE_ID_INDEX = 0;
        public static final int MONDAY_INDEX = 1;
        public static final int TUESDAY_INDEX = 2;
        public static final int WEDNESDAY_INDEX = 3;
        public static final int THURSDAY_INDEX = 4;
        public static final int FRIDAY_INDEX = 5;
        public static final int SATURDAY_INDEX = 6;
        public static final int SUNDAY_INDEX = 7;
        public static final int START_DATE_INDEX = 8;
        public static final int END_DATE_INDEX = 9;
    }

    public static class CalendarDate implements CalendarDateColumns, BaseColumns {

        public static final String CONTENT_TYPE_ID = "calendar_date";

        public static final int SERVICE_ID_INDEX = 0;
        public static final int DATE_INDEX = 1;
        public static final int EXCEPTION_TYPE_INDEX = 2;
    }

    public static class FareAttribute implements FareAttributeColumns, BaseColumns {

        public static final String CONTENT_TYPE_ID = "fare_attribute";

        public static final int FARE_ID_INDEX = 0;
        public static final int PRICE_INDEX = 1;
        public static final int CURRENCY_TYPE_INDEX = 2;
        public static final int PAYMENT_METHOD_INDEX = 3;
        public static final int TRANSFERS_INDEX = 4;
        public static final int TRANSFER_DURATION_INDEX = 5;
    }

    public static class FareRule implements FareRuleColumns, BaseColumns {

        public static final String CONTENT_TYPE_ID = "fare_rule";

        public static final int FARE_ID_INDEX = 0;
        public static final int ROUTE_ID_INDEX = 1;
    }

    //FeedInfo

    //Frequency

    public static class Route implements RouteColumns, BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath("route").build();

        public static final String CONTENT_TYPE_ID = "route";

        public static final int ROUTE_ID_INDEX = 0;
        public static final int ROUTE_SHORT_NAME_INDEX = 1;
        public static final int ROUTE_LONG_NAME_INDEX = 2;
        public static final int ROUTE_TYPE_INDEX = 3;
        public static final int ROUTE_COLOR_INDEX = 4;
        public static final int ROUTE_TEXT_COLOR_INDEX = 5;

        public static String getRouteId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildRouteUri(String routeId) {
            return CONTENT_URI.buildUpon().appendPath(routeId).build();
        }
    }

    public static class Shape implements ShapeColumns, BaseColumns {

        public static final String CONTENT_TYPE_ID = "shape";

        public static final int SHAPE_ID_INDEX = 0;
        public static final int SHAPE_PT_LAT_INDEX = 1;
        public static final int SHAPE_PT_LON_INDEX = 2;
        public static final int SHAPE_PT_SEQUENCE_INDEX = 3;

        public static String getShapeId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class Stop implements StopColumns, BaseColumns {

        public static final String CONTENT_TYPE_ID = "stop";

        public static final int STOP_ID_INDEX = 0;
        public static final int STOP_CODE_INDEX = 1;
        public static final int STOP_NAME_INDEX = 2;
        public static final int STOP_LAT_INDEX = 3;
        public static final int STOP_LON_INDEX = 4;
        public static final int STOP_URL_INDEX = 5;

        public static String getStopId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class StopTime implements StopTimeColumns, BaseColumns {

        public static final String CONTENT_TYPE_ID = "stop_time";

        public static final int TRIP_ID_INDEX = 0;
        public static final int ARRIVAL_TIME_INDEX = 1;
        public static final int DEPARTURE_TIME_INDEX = 2;
        public static final int STOP_ID_INDEX = 3;
        public static final int STOP_SEQUENCE_INDEX = 4;

        public static String getStopTimeId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    //Transfer

    public static class Trip implements TripColumns, BaseColumns {

        public static final String CONTENT_TYPE_ID = "trip";

        public static final int ROUTE_ID_INDEX = 0;
        public static final int SERVICE_ID_INDEX = 1;
        public static final int TRIP_ID_INDEX = 2;
        public static final int TRIP_HEADSIGN_INDEX = 3;
        public static final int DIRECTION_ID_INDEX = 4;
        public static final int BLOCK_ID_INDEX = 5;
        public static final int SHAPE_ID_INDEX = 6;
        public static final int WHEELCHAIR_ACCESSIBLE_INDEX = 7;
    }

    public static class SearchRoutes {
        public static final String PATH_SEARCH_ROUTES = "search_routes";

        public static final String CONTENT_TYPE_ID = "search_routes";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SEARCH_ROUTES).build();
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
