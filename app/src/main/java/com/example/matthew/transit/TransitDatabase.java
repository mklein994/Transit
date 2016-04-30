package com.example.matthew.transit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.matthew.transit.TransitContract.Agency;
import com.example.matthew.transit.TransitContract.Calendar;
import com.example.matthew.transit.TransitContract.CalendarDate;
import com.example.matthew.transit.TransitContract.FareAttribute;
import com.example.matthew.transit.TransitContract.FareRule;
import com.example.matthew.transit.TransitContract.Route;
import com.example.matthew.transit.TransitContract.Shape;
import com.example.matthew.transit.TransitContract.Stop;
import com.example.matthew.transit.TransitContract.StopTime;
import com.example.matthew.transit.TransitContract.Trip;

/**
 * Created by matthew on 29/04/16.
 */
public class TransitDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "transit.db";

    private static final int CUR_DATABASE_VERSION = 1;

    private final Context mContext;

    interface Tables {
        String AGENCY = "agency";
        String CALENDAR = "calendar";
        String CALENDAR_DATE = "calendar_date";
        String FARE_ATTRIBUTE = "fare_attribute";
        String FARE_RULE = "fare_rule";
        String ROUTE = "route";
        String SHAPE = "shape";
        String STOP = "stop";
        String STOP_TIME = "stop_time";
        String TRIP = "trip";
    }

    private interface References {
        String SERVICE_ID = " REFERENCES " + Tables.CALENDAR + "(" + Calendar.SERVICE_ID + ")";
        //String CALENDAR_DATE_SERVICE_ID = " REFERENCES " + Tables.CALENDAR_DATE + "(" + CalendarDate.SERVICE_ID + ")";
        String TRIP_ID = " REFERENCES " + Tables.TRIP + "(" + Trip.TRIP_ID + ")";
        String ROUTE_ID = " REFERENCES " + Tables.ROUTE + "(" + Route.ROUTE_ID + ")";
        String FARE_ID = " REFERENCES " + Tables.FARE_ATTRIBUTE + "(" + FareAttribute.FARE_ID + ")";
        String SHAPE_ID = " REFERENCES " + Tables.SHAPE + "(" + Shape.SHAPE_ID + ")";
        String STOP_ID = " REFERENCES " + Tables.STOP + "(" + Stop.STOP_ID + ")";
    }

    // helpers to prevent typos
    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String COMMA_SEP = ",";

    // data types
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String TEXT_TYPE = " TEXT";
    // numeric is a catchall
    private static final String NUMERIC_TYPE = " NUMERIC";

    private static final String PRIMARY_KEY = INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP;

    private static final String SQL_CREATE_AGENCIES = CREATE_TABLE + Tables.AGENCY + " (" +
            Agency._ID + PRIMARY_KEY +
            Agency.AGENCY_NAME + TEXT_TYPE + COMMA_SEP +
            Agency.AGENCY_URL + TEXT_TYPE + COMMA_SEP +
            Agency.AGENCY_TIMEZONE + TEXT_TYPE + COMMA_SEP +
            Agency.AGENCY_LANG + TEXT_TYPE + COMMA_SEP +
            Agency.AGENCY_PHONE + TEXT_TYPE +
            " )";

    private static final String SQL_CREATE_CALENDARS = CREATE_TABLE + Tables.CALENDAR + " (" +
            Calendar._ID + PRIMARY_KEY +
            Calendar.SERVICE_ID + TEXT_TYPE + COMMA_SEP +
            Calendar.MONDAY + INTEGER_TYPE + COMMA_SEP +
            Calendar.TUESDAY + INTEGER_TYPE + COMMA_SEP +
            Calendar.WEDNESDAY + INTEGER_TYPE + COMMA_SEP +
            Calendar.THURSDAY + INTEGER_TYPE + COMMA_SEP +
            Calendar.FRIDAY + INTEGER_TYPE + COMMA_SEP +
            Calendar.SATURDAY + INTEGER_TYPE + COMMA_SEP +
            Calendar.SUNDAY + INTEGER_TYPE + COMMA_SEP +
            Calendar.START_DATE + NUMERIC_TYPE + COMMA_SEP +
            Calendar.END_DATE + NUMERIC_TYPE +
            " )";

    private static final String SQL_CREATE_CALENDAR_DATES = CREATE_TABLE + Tables.CALENDAR_DATE + " (" +
            CalendarDate._ID + PRIMARY_KEY +
            CalendarDate.SERVICE_ID + TEXT_TYPE + COMMA_SEP +
            CalendarDate.DATE + NUMERIC_TYPE + COMMA_SEP +
            CalendarDate.EXCEPTION_TYPE + INTEGER_TYPE +
            " )";

    private static final String SQL_CREATE_FARE_ATTRIBUTES = CREATE_TABLE + Tables.FARE_ATTRIBUTE + " (" +
            FareAttribute._ID + PRIMARY_KEY +
            FareAttribute.FARE_ID + TEXT_TYPE + COMMA_SEP +
            FareAttribute.PRICE + REAL_TYPE + COMMA_SEP +
            FareAttribute.CURRENCY_TYPE + TEXT_TYPE + COMMA_SEP +
            FareAttribute.PAYMENT_METHOD + INTEGER_TYPE + COMMA_SEP +
            FareAttribute.TRANSFERS + INTEGER_TYPE + COMMA_SEP +
            FareAttribute.TRANSFER_DURATION + INTEGER_TYPE +
            " )";

    private static final String SQL_CREATE_FARE_RULES = CREATE_TABLE + Tables.FARE_RULE + " (" +
            FareRule._ID + PRIMARY_KEY +
            FareRule.FARE_ID + TEXT_TYPE + References.FARE_ID + COMMA_SEP +
            FareRule.ROUTE_ID + TEXT_TYPE + References.ROUTE_ID +
            " )";

    private static final String SQL_CREATE_ROUTES = CREATE_TABLE + Tables.ROUTE + " (" +
            Route._ID + PRIMARY_KEY +
            Route.ROUTE_ID + TEXT_TYPE + COMMA_SEP +
            Route.ROUTE_SHORT_NAME + TEXT_TYPE + COMMA_SEP +
            Route.ROUTE_LONG_NAME + TEXT_TYPE + COMMA_SEP +
            Route.ROUTE_TYPE + INTEGER_TYPE + COMMA_SEP +
            Route.ROUTE_COLOR + TEXT_TYPE + COMMA_SEP +
            Route.ROUTE_TEXT_COLOR + TEXT_TYPE +
            " )";

    private static final String SQL_CREATE_SHAPES = CREATE_TABLE + Tables.SHAPE + " (" +
            Shape._ID + PRIMARY_KEY +
            Shape.SHAPE_ID + TEXT_TYPE + COMMA_SEP +
            Shape.SHAPE_PT_LAT + REAL_TYPE + COMMA_SEP +
            Shape.SHAPE_PT_LON + REAL_TYPE + COMMA_SEP +
            Shape.SHAPE_PT_SEQUENCE + INTEGER_TYPE +
            " )";

    private static final String SQL_CREATE_STOPS = CREATE_TABLE + Tables.STOP + " (" +
            Stop._ID + PRIMARY_KEY +
            Stop.STOP_ID + TEXT_TYPE + COMMA_SEP +
            Stop.STOP_CODE + TEXT_TYPE + COMMA_SEP +
            Stop.STOP_NAME + TEXT_TYPE + COMMA_SEP +
            Stop.STOP_LAT + REAL_TYPE + COMMA_SEP +
            Stop.STOP_LON + REAL_TYPE + COMMA_SEP +
            Stop.STOP_URL + TEXT_TYPE +
            " )";

    private static final String SQL_CREATE_STOP_TIMES = CREATE_TABLE + Tables.STOP_TIME + " (" +
            StopTime._ID + PRIMARY_KEY +
            StopTime.TRIP_ID + TEXT_TYPE + References.TRIP_ID + COMMA_SEP +
            StopTime.ARRIVAL_TIME + NUMERIC_TYPE + COMMA_SEP +
            StopTime.DEPARTURE_TIME + NUMERIC_TYPE + COMMA_SEP +
            StopTime.STOP_ID + TEXT_TYPE + References.STOP_ID + COMMA_SEP +
            StopTime.STOP_SEQUENCE + INTEGER_TYPE +
            " )";

    private static final String SQL_CREATE_TRIPS = CREATE_TABLE + Tables.TRIP + " (" +
            Trip._ID + PRIMARY_KEY +
            Trip.ROUTE_ID + TEXT_TYPE + References.ROUTE_ID + COMMA_SEP +
            Trip.SERVICE_ID + TEXT_TYPE + References.SERVICE_ID + COMMA_SEP +
            Trip.TRIP_ID + TEXT_TYPE + COMMA_SEP +
            Trip.TRIP_HEADSIGN + TEXT_TYPE + COMMA_SEP +
            Trip.DIRECTION_ID + INTEGER_TYPE + COMMA_SEP +
            Trip.BLOCK_ID + TEXT_TYPE + COMMA_SEP +
            Trip.SHAPE_ID + TEXT_TYPE + References.SHAPE_ID + COMMA_SEP +
            Trip.WHEELCHAIR_ACCESSIBLE + INTEGER_TYPE +
            " )";

    public TransitDatabase(Context context) {
        super(context, DATABASE_NAME, null, CUR_DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
