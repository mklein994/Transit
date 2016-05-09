package com.example.matthew.transit;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.matthew.transit.TransitContract.Agency;
import com.example.matthew.transit.TransitContract.Calendar;
import com.example.matthew.transit.TransitContract.CalendarDate;
import com.example.matthew.transit.TransitContract.FareAttribute;
import com.example.matthew.transit.TransitContract.FareRule;
import com.example.matthew.transit.TransitContract.Route;
import com.example.matthew.transit.TransitContract.Shape;
import com.example.matthew.transit.TransitContract.Stop;
import com.example.matthew.transit.TransitDatabaseHelper.Tables;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.util.Arrays;

import static com.example.matthew.transit.TransitContract.StopTime;
import static com.example.matthew.transit.TransitContract.Trip;

/**
 * Created by matthew on 08/05/16.
 */
public class TransitDatabaseManager {
    private static final String TAG = TransitDatabaseManager.class.getSimpleName();

    static void insertAgencies(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine = null;

        String sql = "INSERT OR REPLACE INTO " + Tables.AGENCY + " ( "
                + Agency.AGENCY_NAME + ", "
                + Agency.AGENCY_URL + ", "
                + Agency.AGENCY_TIMEZONE + ", "
                + Agency.AGENCY_LANG + ", "
                + Agency.AGENCY_PHONE
                + " ) VALUES ( ?, ?, ?, ?, ? )";

        try (SQLiteStatement agenciesInsertStatement = db.compileStatement(sql)) {
            while ((nextLine = reader.readNext()) != null) {

                agenciesInsertStatement.bindString(1, nextLine[Agency.AGENCY_NAME_INDEX]);
                agenciesInsertStatement.bindString(2, nextLine[Agency.AGENCY_URL_INDEX]);
                agenciesInsertStatement.bindString(3, nextLine[Agency.AGENCY_TIMEZONE_INDEX]);
                agenciesInsertStatement.bindString(4, nextLine[Agency.AGENCY_LANG_INDEX]);
                agenciesInsertStatement.bindString(5, nextLine[Agency.AGENCY_PHONE_INDEX]);
                agenciesInsertStatement.executeInsert();
                agenciesInsertStatement.clearBindings();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            Log.e(TAG, "insertAgencies: sql exception occurred.", e);
            Log.d(TAG, "insertAgencies: nextLine: " + Arrays.toString(nextLine));
            e.printStackTrace();
        }

        Log.d(TAG, "processFiles: Agencies imported.");
    }

    static void insertCalendars(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine = null;

        String sql = "INSERT OR REPLACE INTO " + Tables.CALENDAR + " ( "
                + Calendar.SERVICE_ID + ", "
                + Calendar.MONDAY + ", "
                + Calendar.TUESDAY + ", "
                + Calendar.WEDNESDAY + ", "
                + Calendar.THURSDAY + ", "
                + Calendar.FRIDAY + ", "
                + Calendar.SATURDAY + ", "
                + Calendar.SUNDAY + ", "
                + Calendar.START_DATE + ", "
                + Calendar.END_DATE
                + " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

        try (SQLiteStatement insertCalendarsStatement = db.compileStatement(sql)) {
            while ((nextLine = reader.readNext()) != null) {

                insertCalendarsStatement.bindString(1, nextLine[Calendar.SERVICE_ID_INDEX]);
                insertCalendarsStatement.bindString(2, nextLine[Calendar.MONDAY_INDEX]);
                insertCalendarsStatement.bindString(3, nextLine[Calendar.TUESDAY_INDEX]);
                insertCalendarsStatement.bindString(4, nextLine[Calendar.WEDNESDAY_INDEX]);
                insertCalendarsStatement.bindString(5, nextLine[Calendar.THURSDAY_INDEX]);
                insertCalendarsStatement.bindString(6, nextLine[Calendar.FRIDAY_INDEX]);
                insertCalendarsStatement.bindString(7, nextLine[Calendar.SATURDAY_INDEX]);
                insertCalendarsStatement.bindString(8, nextLine[Calendar.SUNDAY_INDEX]);
                insertCalendarsStatement.bindString(9, nextLine[Calendar.START_DATE_INDEX]);
                insertCalendarsStatement.bindString(10, nextLine[Calendar.END_DATE_INDEX]);
                insertCalendarsStatement.executeInsert();
                insertCalendarsStatement.clearBindings();
            }
        } catch (SQLException e) {
            Log.e(TAG, "insertCalendars: sql exception occurred.", e);
            Log.d(TAG, "insertCalendars: nextLine: " + Arrays.toString(nextLine));
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "insertCalendars: Error happened while parsing the csv file.", e);
            e.printStackTrace();
        }

        Log.d(TAG, "processFiles: Calendars imported.");
    }

    static void insertCalendarDates(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine = null;

        String sql = "INSERT OR REPLACE INTO " + Tables.CALENDAR_DATE + " ( "
                + CalendarDate.SERVICE_ID + ", "
                + CalendarDate.DATE + ", "
                + CalendarDate.EXCEPTION_TYPE
                + " ) VALUES ( ?, ?, ? )";

        try (SQLiteStatement insertCalendarDatesStatement = db.compileStatement(sql)) {
            while ((nextLine = reader.readNext()) != null) {

                insertCalendarDatesStatement.bindString(1, nextLine[CalendarDate.SERVICE_ID_INDEX]);
                insertCalendarDatesStatement.bindString(2, nextLine[CalendarDate.DATE_INDEX]);
                insertCalendarDatesStatement.bindString(3, nextLine[CalendarDate.EXCEPTION_TYPE_INDEX]);
                insertCalendarDatesStatement.executeInsert();
                insertCalendarDatesStatement.clearBindings();
            }
        } catch (SQLException e) {
            Log.e(TAG, "insertCalendarDates: sql exception occurred.", e);
            Log.d(TAG, "insertCalendarDates: nextLine: " + Arrays.toString(nextLine));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "processFiles: CalendarDates imported.");
    }

    static void insertFareAttributes(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine = null;

        String sql = "INSERT OR REPLACE INTO " + Tables.FARE_ATTRIBUTE + " ( "
                + FareAttribute.FARE_ID + ", "
                + FareAttribute.PRICE + ", "
                + FareAttribute.CURRENCY_TYPE + ", "
                + FareAttribute.PAYMENT_METHOD + ", "
                + FareAttribute.TRANSFERS + ", "
                + FareAttribute.TRANSFER_DURATION
                + " ) VALUES ( ?, ?, ?, ?, ?, ? )";

        try (SQLiteStatement insertFareAttributesStatement = db.compileStatement(sql)) {
            while ((nextLine = reader.readNext()) != null) {

                insertFareAttributesStatement.bindString(1, nextLine[FareAttribute.FARE_ID_INDEX]);
                insertFareAttributesStatement.bindString(2, nextLine[FareAttribute.PRICE_INDEX]);
                insertFareAttributesStatement.bindString(3, nextLine[FareAttribute.CURRENCY_TYPE_INDEX]);
                insertFareAttributesStatement.bindString(4, nextLine[FareAttribute.PAYMENT_METHOD_INDEX]);
                insertFareAttributesStatement.bindString(5, nextLine[FareAttribute.TRANSFERS_INDEX]);
                insertFareAttributesStatement.bindString(6, nextLine[FareAttribute.TRANSFER_DURATION_INDEX]);
                insertFareAttributesStatement.executeInsert();
                insertFareAttributesStatement.clearBindings();
            }
        } catch (SQLException e) {
            Log.e(TAG, "insertFareAttributes: sql exception occurred.", e);
            Log.d(TAG, "insertFareAttributes: nextLine: " + Arrays.toString(nextLine));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "processFiles: FareAttributes imported.");
    }

    static void insertRoutes(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine = null;

        String sql = "INSERT OR REPLACE INTO " + Tables.ROUTE + " ( "
                + Route.ROUTE_ID + ", "
                + Route.ROUTE_SHORT_NAME + ", "
                + Route.ROUTE_LONG_NAME + ", "
                + Route.ROUTE_TYPE + ", "
                + Route.ROUTE_COLOR + ", "
                + Route.ROUTE_TEXT_COLOR
                + " ) VALUES ( ?, ?, ?, ?, ?, ? )";

        try (SQLiteStatement insertRoutesStatement = db.compileStatement(sql)) {
            while ((nextLine = reader.readNext()) != null) {

                insertRoutesStatement.bindString(1, nextLine[Route.ROUTE_ID_INDEX]);
                insertRoutesStatement.bindString(2, nextLine[Route.ROUTE_SHORT_NAME_INDEX]);
                insertRoutesStatement.bindString(3, nextLine[Route.ROUTE_LONG_NAME_INDEX]);
                insertRoutesStatement.bindString(4, nextLine[Route.ROUTE_TYPE_INDEX]);
                insertRoutesStatement.bindString(5, nextLine[Route.ROUTE_COLOR_INDEX]);
                insertRoutesStatement.bindString(6, nextLine[Route.ROUTE_TEXT_COLOR_INDEX]);
                insertRoutesStatement.executeInsert();
                insertRoutesStatement.clearBindings();
            }

        } catch (SQLException e) {
            Log.e(TAG, "insertRoutes: sql exception occurred.", e);
            Log.d(TAG, "insertRoutes: nextLine: " + Arrays.toString(nextLine));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "processFiles: Routes imported.");
    }

    static void insertShapes(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine = null;

        String sql = "INSERT OR REPLACE INTO " + Tables.SHAPE + " ( "
                + Shape.SHAPE_ID + ", "
                + Shape.SHAPE_PT_LAT + ", "
                + Shape.SHAPE_PT_LON + ", "
                + Shape.SHAPE_PT_SEQUENCE
                + " ) VALUES ( ?, ?, ?, ? )";

        try (SQLiteStatement insertShapesStatement = db.compileStatement(sql)) {
            while ((nextLine = reader.readNext()) != null) {

                insertShapesStatement.bindString(1, nextLine[Shape.SHAPE_ID_INDEX]);
                insertShapesStatement.bindString(2, nextLine[Shape.SHAPE_PT_LAT_INDEX]);
                insertShapesStatement.bindString(3, nextLine[Shape.SHAPE_PT_LON_INDEX]);
                insertShapesStatement.bindString(4, nextLine[Shape.SHAPE_PT_SEQUENCE_INDEX]);
                insertShapesStatement.executeInsert();
                insertShapesStatement.clearBindings();
            }
        } catch (SQLException e) {
            Log.e(TAG, "insertShapes: sql exception occurred.", e);
            Log.d(TAG, "insertShapes: nextLine: " + Arrays.toString(nextLine));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "processFiles: Shapes imported.");
    }

    static void insertStops(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine = null;

        String sql = "INSERT OR REPLACE INTO " + Tables.STOP + " ( "
                + Stop.STOP_ID + ", "
                + Stop.STOP_CODE + ", "
                + Stop.STOP_NAME + ", "
                + Stop.STOP_LAT + ", "
                + Stop.STOP_LON + ", "
                + Stop.STOP_URL
                + " ) VALUES ( ?, ?, ?, ?, ?, ? )";

        try (SQLiteStatement insertStopsStatement = db.compileStatement(sql)) {
            while ((nextLine = reader.readNext()) != null) {

                insertStopsStatement.bindString(1, nextLine[Stop.STOP_ID_INDEX]);
                insertStopsStatement.bindString(2, nextLine[Stop.STOP_CODE_INDEX]);
                insertStopsStatement.bindString(3, nextLine[Stop.STOP_NAME_INDEX]);
                insertStopsStatement.bindString(4, nextLine[Stop.STOP_LAT_INDEX]);
                insertStopsStatement.bindString(5, nextLine[Stop.STOP_LON_INDEX]);
                insertStopsStatement.bindString(6, nextLine[Stop.STOP_URL_INDEX]);
                insertStopsStatement.executeInsert();
                insertStopsStatement.clearBindings();
            }
        } catch (SQLException e) {
            Log.e(TAG, "insertStops: sql exception occurred.", e);
            Log.d(TAG, "insertStops: nextLine: " + Arrays.toString(nextLine));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "processFiles: Stops imported.");
    }

    static void insertFareRules(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine = null;

        String sql = "INSERT OR REPLACE INTO " + Tables.FARE_RULE + " ( "
                + FareRule.FARE_ID + ", "
                + FareRule.ROUTE_ID
                + " ) VALUES ( ?, ? )";

        try (SQLiteStatement insertFareRulesStatement = db.compileStatement(sql)) {
            while ((nextLine = reader.readNext()) != null) {

                insertFareRulesStatement.bindString(1, nextLine[FareRule.FARE_ID_INDEX]);
                insertFareRulesStatement.bindString(2, nextLine[FareRule.ROUTE_ID_INDEX]);
                insertFareRulesStatement.executeInsert();
                insertFareRulesStatement.clearBindings();
            }

        } catch (SQLException e) {
            Log.e(TAG, "insertFareRules: sql exception occurred.", e);
            Log.d(TAG, "insertFareRules: nextLine: " + Arrays.toString(nextLine));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "processFiles: FareRules imported.");
    }

    static void insertTrips(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine = null;

        String sql = "INSERT OR REPLACE INTO " + Tables.TRIP + " ( "
                + Trip.ROUTE_ID + ", "
                + Trip.SERVICE_ID + ", "
                + Trip.TRIP_ID + ", "
                + Trip.TRIP_HEADSIGN + ", "
                + Trip.DIRECTION_ID + ", "
                + Trip.BLOCK_ID + ", "
                + Trip.SHAPE_ID + ", "
                + Trip.WHEELCHAIR_ACCESSIBLE
                + " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";

        try (SQLiteStatement insertTripsStatement = db.compileStatement(sql)) {
            while ((nextLine = reader.readNext()) != null) {

                insertTripsStatement.bindString(1, nextLine[Trip.ROUTE_ID_INDEX]);
                insertTripsStatement.bindString(2, nextLine[Trip.SERVICE_ID_INDEX]);
                insertTripsStatement.bindString(3, nextLine[Trip.TRIP_ID_INDEX]);
                insertTripsStatement.bindString(4, nextLine[Trip.TRIP_HEADSIGN_INDEX]);
                insertTripsStatement.bindString(5, nextLine[Trip.DIRECTION_ID_INDEX]);
                insertTripsStatement.bindString(6, nextLine[Trip.BLOCK_ID_INDEX]);
                insertTripsStatement.bindString(7, nextLine[Trip.SHAPE_ID_INDEX]);
                insertTripsStatement.bindString(8, nextLine[Trip.WHEELCHAIR_ACCESSIBLE_INDEX]);
                insertTripsStatement.executeInsert();
                insertTripsStatement.clearBindings();
            }
        } catch (SQLException e) {
            Log.e(TAG, "insertTrips: sql exception occurred.", e);
            Log.d(TAG, "insertTrips: nextLine: " + Arrays.toString(nextLine));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "processFiles: Trips imported.");
    }

    static void insertStopTimes(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine = null;

        String sql = "INSERT OR REPLACE INTO " + Tables.STOP_TIME + " ( "
                + StopTime.TRIP_ID + ", "
                + StopTime.ARRIVAL_TIME + ", "
                + StopTime.DEPARTURE_TIME + ", "
                + StopTime.STOP_ID + ", "
                + StopTime.STOP_SEQUENCE
                + " ) VALUES ( ?, ?, ?, ?, ? )";

        try (SQLiteStatement insertStopTimesStatement = db.compileStatement(sql)) {
            while ((nextLine = reader.readNext()) != null) {

                insertStopTimesStatement.bindString(1, nextLine[StopTime.TRIP_ID_INDEX]);
                insertStopTimesStatement.bindString(2, nextLine[StopTime.ARRIVAL_TIME_INDEX]);
                insertStopTimesStatement.bindString(3, nextLine[StopTime.DEPARTURE_TIME_INDEX]);
                insertStopTimesStatement.bindString(4, nextLine[StopTime.STOP_ID_INDEX]);
                insertStopTimesStatement.bindString(5, nextLine[StopTime.STOP_SEQUENCE_INDEX]);
                insertStopTimesStatement.executeInsert();
                insertStopTimesStatement.clearBindings();
            }
        } catch (SQLException e) {
            Log.e(TAG, "insertStopTimes: sql exception occurred.", e);
            Log.d(TAG, "insertStopTimes: nextLine: " + Arrays.toString(nextLine));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "processFiles: StopTimes imported.");
    }
}
