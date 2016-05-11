package com.example.matthew.transit;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.matthew.transit.TransitContract.AgencyColumns;
import com.example.matthew.transit.TransitContract.CalendarColumns;
import com.example.matthew.transit.TransitContract.CalendarDateColumns;
import com.example.matthew.transit.TransitContract.FareAttributeColumns;
import com.example.matthew.transit.TransitContract.FareRuleColumns;
import com.example.matthew.transit.TransitContract.RouteColumns;
import com.example.matthew.transit.TransitContract.ShapeColumns;
import com.example.matthew.transit.TransitContract.StopColumns;
import com.example.matthew.transit.TransitContract.StopTimeColumns;
import com.example.matthew.transit.TransitContract.TripColumns;

/**
 * Created by matthew on 29/04/16.
 */
public class TransitDatabaseHelper extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "transit.db";
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
        String SERVICE_ID = " REFERENCES " + Tables.CALENDAR + "(" + CalendarColumns.SERVICE_ID + ")";
        //String CALENDAR_DATE_SERVICE_ID = " REFERENCES " + Tables.CALENDAR_DATE + "(" + CalendarDateColumns.SERVICE_ID + ")";
        String TRIP_ID = " REFERENCES " + Tables.TRIP + "(" + TripColumns.TRIP_ID + ")";
        String ROUTE_ID = " REFERENCES " + Tables.ROUTE + "(" + RouteColumns.ROUTE_ID + ")";
        String FARE_ID = " REFERENCES " + Tables.FARE_ATTRIBUTE + "(" + FareAttributeColumns.FARE_ID + ")";
        String SHAPE_ID = " REFERENCES " + Tables.SHAPE + "(" + ShapeColumns.SHAPE_ID + ")";
        String STOP_ID = " REFERENCES " + Tables.STOP + "(" + StopColumns.STOP_ID + ")";
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
            BaseColumns._ID + PRIMARY_KEY +
            AgencyColumns.AGENCY_NAME + TEXT_TYPE + COMMA_SEP +
            AgencyColumns.AGENCY_URL + TEXT_TYPE + COMMA_SEP +
            AgencyColumns.AGENCY_TIMEZONE + TEXT_TYPE + COMMA_SEP +
            AgencyColumns.AGENCY_LANG + TEXT_TYPE + COMMA_SEP +
            AgencyColumns.AGENCY_PHONE + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_AGENCIES =
            "DROP TABLE IF EXISTS " + Tables.AGENCY;

    private static final String SQL_CREATE_CALENDARS = CREATE_TABLE + Tables.CALENDAR + " (" +
            BaseColumns._ID + PRIMARY_KEY +
            CalendarColumns.SERVICE_ID + TEXT_TYPE + COMMA_SEP +
            CalendarColumns.MONDAY + INTEGER_TYPE + COMMA_SEP +
            CalendarColumns.TUESDAY + INTEGER_TYPE + COMMA_SEP +
            CalendarColumns.WEDNESDAY + INTEGER_TYPE + COMMA_SEP +
            CalendarColumns.THURSDAY + INTEGER_TYPE + COMMA_SEP +
            CalendarColumns.FRIDAY + INTEGER_TYPE + COMMA_SEP +
            CalendarColumns.SATURDAY + INTEGER_TYPE + COMMA_SEP +
            CalendarColumns.SUNDAY + INTEGER_TYPE + COMMA_SEP +
            CalendarColumns.START_DATE + NUMERIC_TYPE + COMMA_SEP +
            CalendarColumns.END_DATE + NUMERIC_TYPE + COMMA_SEP +
            "UNIQUE (" + CalendarColumns.SERVICE_ID + ") ON CONFLICT REPLACE" +
            " )";

    private static final String SQL_DELETE_CALENDARS =
            "DROP TABLE IF EXISTS " + Tables.CALENDAR;

    private static final String SQL_CREATE_CALENDAR_DATES = CREATE_TABLE + Tables.CALENDAR_DATE + " (" +
            BaseColumns._ID + PRIMARY_KEY +
            CalendarDateColumns.SERVICE_ID + TEXT_TYPE + COMMA_SEP +
            CalendarDateColumns.DATE + NUMERIC_TYPE + COMMA_SEP +
            CalendarDateColumns.EXCEPTION_TYPE + INTEGER_TYPE + COMMA_SEP +
            "UNIQUE (" + CalendarDateColumns.SERVICE_ID + ") ON CONFLICT REPLACE" +
            " )";

    private static final String SQL_DELETE_CALENDAR_DATES =
            "DROP TABLE IF EXISTS " + Tables.CALENDAR_DATE;

    private static final String SQL_CREATE_FARE_ATTRIBUTES = CREATE_TABLE + Tables.FARE_ATTRIBUTE + " (" +
            BaseColumns._ID + PRIMARY_KEY +
            FareAttributeColumns.FARE_ID + TEXT_TYPE + COMMA_SEP +
            FareAttributeColumns.PRICE + REAL_TYPE + COMMA_SEP +
            FareAttributeColumns.CURRENCY_TYPE + TEXT_TYPE + COMMA_SEP +
            FareAttributeColumns.PAYMENT_METHOD + INTEGER_TYPE + COMMA_SEP +
            FareAttributeColumns.TRANSFERS + INTEGER_TYPE + COMMA_SEP +
            FareAttributeColumns.TRANSFER_DURATION + INTEGER_TYPE + COMMA_SEP +
            "UNIQUE (" + FareAttributeColumns.FARE_ID + ") ON CONFLICT REPLACE" +
            " )";

    private static final String SQL_DELETE_FARE_ATTRIBUTES =
            "DROP TABLE IF EXISTS " + Tables.FARE_ATTRIBUTE;

    private static final String SQL_CREATE_FARE_RULES = CREATE_TABLE + Tables.FARE_RULE + " (" +
            BaseColumns._ID + PRIMARY_KEY +
            FareRuleColumns.FARE_ID + TEXT_TYPE + References.FARE_ID + COMMA_SEP +
            FareRuleColumns.ROUTE_ID + TEXT_TYPE + References.ROUTE_ID + COMMA_SEP +
            "UNIQUE (" + FareRuleColumns.FARE_ID + COMMA_SEP + FareRuleColumns.ROUTE_ID + ") ON CONFLICT REPLACE" +
            " )";

    private static final String SQL_DELETE_FARE_RULES =
            "DROP TABLE IF EXISTS " + Tables.FARE_RULE;

    private static final String SQL_CREATE_ROUTES = CREATE_TABLE + Tables.ROUTE + " (" +
            BaseColumns._ID + PRIMARY_KEY +
            RouteColumns.ROUTE_ID + TEXT_TYPE + COMMA_SEP +
            RouteColumns.ROUTE_SHORT_NAME + TEXT_TYPE + COMMA_SEP +
            RouteColumns.ROUTE_LONG_NAME + TEXT_TYPE + COMMA_SEP +
            RouteColumns.ROUTE_TYPE + INTEGER_TYPE + COMMA_SEP +
            RouteColumns.ROUTE_COLOR + TEXT_TYPE + COMMA_SEP +
            RouteColumns.ROUTE_TEXT_COLOR + TEXT_TYPE + COMMA_SEP +
            "UNIQUE (" + RouteColumns.ROUTE_ID + ") ON CONFLICT REPLACE" +
            " )";

    private static final String SQL_DELETE_ROUTES =
            "DROP TABLE IF EXISTS " + Tables.ROUTE;

    private static final String SQL_CREATE_SHAPES = CREATE_TABLE + Tables.SHAPE + " (" +
            BaseColumns._ID + PRIMARY_KEY +
            ShapeColumns.SHAPE_ID + TEXT_TYPE + COMMA_SEP +
            ShapeColumns.SHAPE_PT_LAT + REAL_TYPE + COMMA_SEP +
            ShapeColumns.SHAPE_PT_LON + REAL_TYPE + COMMA_SEP +
            ShapeColumns.SHAPE_PT_SEQUENCE + INTEGER_TYPE + COMMA_SEP +
            "UNIQUE (" + ShapeColumns.SHAPE_ID + ") ON CONFLICT REPLACE" +
            " )";

    private static final String SQL_DELETE_SHAPES =
            "DROP TABLE IF EXISTS " + Tables.SHAPE;

    private static final String SQL_CREATE_STOPS = CREATE_TABLE + Tables.STOP + " (" +
            BaseColumns._ID + PRIMARY_KEY +
            StopColumns.STOP_ID + TEXT_TYPE + COMMA_SEP +
            StopColumns.STOP_CODE + TEXT_TYPE + COMMA_SEP +
            StopColumns.STOP_NAME + TEXT_TYPE + COMMA_SEP +
            StopColumns.STOP_LAT + REAL_TYPE + COMMA_SEP +
            StopColumns.STOP_LON + REAL_TYPE + COMMA_SEP +
            StopColumns.STOP_URL + TEXT_TYPE + COMMA_SEP +
            "UNIQUE (" + StopColumns.STOP_ID + ") ON CONFLICT REPLACE" +
            " )";

    private static final String SQL_DELETE_STOPS =
            "DROP TABLE IF EXISTS " + Tables.STOP;

    private static final String SQL_CREATE_STOP_TIMES = CREATE_TABLE + Tables.STOP_TIME + " (" +
            BaseColumns._ID + PRIMARY_KEY +
            StopTimeColumns.TRIP_ID + TEXT_TYPE + References.TRIP_ID + COMMA_SEP +
            StopTimeColumns.ARRIVAL_TIME + NUMERIC_TYPE + COMMA_SEP +
            StopTimeColumns.DEPARTURE_TIME + NUMERIC_TYPE + COMMA_SEP +
            StopTimeColumns.STOP_ID + TEXT_TYPE + References.STOP_ID + COMMA_SEP +
            StopTimeColumns.STOP_SEQUENCE + INTEGER_TYPE +
            //"UNIQUE (" + CalendarDate.SERVICE_ID + ") ON CONFLICT REPLACE" +
            " )";

    private static final String SQL_DELETE_STOP_TIMES =
            "DROP TABLE IF EXISTS " + Tables.STOP_TIME;

    private static final String SQL_CREATE_TRIPS = CREATE_TABLE + Tables.TRIP + " (" +
            BaseColumns._ID + PRIMARY_KEY +
            TripColumns.ROUTE_ID + TEXT_TYPE + References.ROUTE_ID + COMMA_SEP +
            TripColumns.SERVICE_ID + TEXT_TYPE + References.SERVICE_ID + COMMA_SEP +
            TripColumns.TRIP_ID + TEXT_TYPE + COMMA_SEP +
            TripColumns.TRIP_HEADSIGN + TEXT_TYPE + COMMA_SEP +
            TripColumns.DIRECTION_ID + INTEGER_TYPE + COMMA_SEP +
            TripColumns.BLOCK_ID + TEXT_TYPE + COMMA_SEP +
            TripColumns.SHAPE_ID + TEXT_TYPE + References.SHAPE_ID + COMMA_SEP +
            TripColumns.WHEELCHAIR_ACCESSIBLE + INTEGER_TYPE + COMMA_SEP +
            "UNIQUE (" + TripColumns.TRIP_ID + ") ON CONFLICT REPLACE" +
            " )";

    private static final String SQL_DELETE_TRIPS =
            "DROP TABLE IF EXISTS " + Tables.TRIP;

    public TransitDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, CUR_DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        Agencies

        Calendars
        CalendarDates
        FareAttributes
        Routes
        Shapes
        Stops

        FareRules
        Trips

        StopTimes
         */

        try {
            db.execSQL(SQL_CREATE_AGENCIES);

            db.execSQL(SQL_CREATE_CALENDARS);
            db.execSQL(SQL_CREATE_CALENDAR_DATES);
            db.execSQL(SQL_CREATE_FARE_ATTRIBUTES);
            db.execSQL(SQL_CREATE_ROUTES);
            db.execSQL(SQL_CREATE_SHAPES);
            db.execSQL(SQL_CREATE_STOPS);

            db.execSQL(SQL_CREATE_FARE_RULES);
            db.execSQL(SQL_CREATE_TRIPS);

            db.execSQL(SQL_CREATE_STOP_TIMES);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(SQL_DELETE_STOP_TIMES);

            db.execSQL(SQL_DELETE_TRIPS);
            db.execSQL(SQL_DELETE_FARE_RULES);

            db.execSQL(SQL_DELETE_STOPS);
            db.execSQL(SQL_DELETE_SHAPES);
            db.execSQL(SQL_DELETE_ROUTES);
            db.execSQL(SQL_DELETE_FARE_ATTRIBUTES);
            db.execSQL(SQL_DELETE_CALENDAR_DATES);
            db.execSQL(SQL_DELETE_CALENDARS);

            db.execSQL(SQL_DELETE_AGENCIES);

            // start over
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
