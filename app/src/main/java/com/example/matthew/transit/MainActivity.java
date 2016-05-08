package com.example.matthew.transit;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteTransactionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

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
import com.example.matthew.transit.TransitDatabaseHelper.Tables;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();
    private static final String FILE_NAME = "gtfs.zip";
    private static final String DATABASE_NAME = "transit.db";
    private static DownloadManager manager = null;
    private static long downloadReference;
    private final BroadcastReceiver onNotificationClick = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(MainActivity.this, "You clicked me!", Toast.LENGTH_SHORT).show();
        }
    };
    private final BroadcastReceiver onComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            processDownload();
        }
    };
    private SharedPreferences settings;

    private void insertAgencies(SQLiteDatabase db, CSVReader reader) {
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

    private void insertCalendars(SQLiteDatabase db, CSVReader reader) {
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

    private void insertCalendarDates(SQLiteDatabase db, CSVReader reader) {
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

    private void insertFareAttributes(SQLiteDatabase db, CSVReader reader) {
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

    private void insertRoutes(SQLiteDatabase db, CSVReader reader) {
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

    private void insertShapes(SQLiteDatabase db, CSVReader reader) {
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

    private void insertStops(SQLiteDatabase db, CSVReader reader) {
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

    private void insertFareRules(SQLiteDatabase db, CSVReader reader) {
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

    private void insertTrips(SQLiteDatabase db, CSVReader reader) {
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

    private void insertStopTimes(SQLiteDatabase db, CSVReader reader) {
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

    /**
     * Check if the download exists
     *
     * @return the previously downloaded file (from this app) or null if it doesn't exist
     */
    private boolean downloadExists() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadReference);
        boolean downloadExists;
        try (Cursor cursor = manager.query(query)) {

            downloadExists = false;

            // check that the cursor managed to find the download
            if (cursor.moveToFirst()) {
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    String fileName = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));

                    //Log.d("fileName", fileName);

                    File file = new File(fileName);

                    downloadExists = file.exists();
                } else {
                    downloadExists = false;
                    Log.e("Download Error", "Download file status is " + status);
                }
            }
            //cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
        }
        return downloadExists;
    }

    /**
     * Start the download
     */
    private void startDownload() {
        final String TRANSIT_FEEDS_KEY = this.getString(R.string.transit_feeds_key);

        final Uri.Builder builder = new Uri.Builder();

        // a larger feed (≈33MB)
        //.appendQueryParameter("feed", "trimet/43")
        builder.scheme("http")
                .authority("api.transitfeeds.com")
                .appendPath("v1")
                .appendPath("getLatestFeedVersion")
                .appendQueryParameter("feed", "winnipeg-transit/23")
                .appendQueryParameter("key", TRANSIT_FEEDS_KEY);

        // download the data
        SharedPreferences.Editor editor = settings.edit();

        // add the download to DownloadManager's queue
        downloadReference = manager.enqueue(new DownloadManager.Request(builder.build())
                .setDescription("Downloading Today's Transit Data…")
                .setTitle("Getting the latest transit information")
                //.setVisibleInDownloadsUi(false)
                .setVisibleInDownloadsUi(true)
                .setDestinationInExternalFilesDir(this, null, FILE_NAME)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE));

        // save a reference to the download to shared preferences
        editor.putLong("lastTransitDownload", downloadReference).apply();

        // register a broadcast receiver to notify when the download is complete
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        // manage when the user clicks on the notification
        // TODO: 02/04/16 perhaps when the notification is clicked, the download will pause?
        registerReceiver(onNotificationClick, new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));
    }

    private void processDownload() {
        ParcelFileDescriptor pfd;

        try {
            // open a parcel file descriptor using the file just downloaded
            pfd = manager.openDownloadedFile(downloadReference);

            File[] files = extractFiles(pfd);

            processFiles(files);

            pfd.close();

        } catch (FileNotFoundException e) {
            Log.e(TAG, "processDownload: File was not found.", e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "processDownload: Error closing parcel file descriptor", e);
            e.printStackTrace();
        }
    }

    /**
     * Extract the the files from the zip file given
     *
     * @param pfd the zip file to extract
     * @return the extracted files
     */
    private File[] extractFiles(ParcelFileDescriptor pfd) {
        ArrayList<File> files = new ArrayList<>();

        // open the zip file
        try {
            try (ZipInputStream zipInput = new ZipInputStream(new BufferedInputStream(new ParcelFileDescriptor.AutoCloseInputStream(pfd)))) {

                ZipEntry zipEntry;

                // loop through each file within the zip file
                while ((zipEntry = zipInput.getNextEntry()) != null) {

                    File file = new File(getFilesDir(), zipEntry.getName());
                    if (!file.getName().equals("LICENCE")) {
                        files.add(file);
                        Log.d("File name", file.getName());

                        // save each file
                        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file, false))) {

                            byte[] buffer = new byte[1024];
                            int count;

                            while ((count = zipInput.read(buffer)) != -1) {
                                out.write(buffer, 0, count);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File[] fileList = new File[files.size()];
        fileList = files.toArray(fileList);
        return fileList;
    }

    private CSVReader getCSVReader(File file) {
        CSVReader reader = null;
        try {
            reader = new CSVReaderBuilder(new FileReader(file))
                    .withSkipLines(1)
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reader;
    }

    private void processFiles(File[] files) {

        // delete the database; start over
        deleteDatabase(DATABASE_NAME);

        TransitDatabaseHelper mTransitDatabaseHelper = new TransitDatabaseHelper(getApplicationContext(), DATABASE_NAME);
        SQLiteDatabase db = mTransitDatabaseHelper.getWritableDatabase();

        ArrayList<String> fileNames = new ArrayList<>();
        // FIXME: 16/04/16 CSVReader is final, so setting it won't change anything about it, including the number of lines read.
        final ArrayList<CSVReader> readers = new ArrayList<>();

        for (File file :
                files) {
            fileNames.add(file.getName());
            readers.add(getCSVReader(file));
        }

        final int AGENCY_INDEX = (fileNames.indexOf("agency.txt"));
        final int STOP_TIMES_INDEX = (fileNames.indexOf("stop_times.txt"));
        final int TRIPS_INDEX = (fileNames.indexOf("trips.txt"));
        final int FARE_RULES_INDEX = (fileNames.indexOf("fare_rules.txt"));
        final int STOPS_INDEX = (fileNames.indexOf("stops.txt"));
        final int SHAPES_INDEX = (fileNames.indexOf("shapes.txt"));
        final int ROUTES_INDEX = (fileNames.indexOf("routes.txt"));
        final int FARE_ATTRIBUTES_INDEX = (fileNames.indexOf("fare_attributes.txt"));
        final int CALENDAR_DATES_INDEX = (fileNames.indexOf("calendar_dates.txt"));
        final int CALENDARS_INDEX = (fileNames.indexOf("calendar.txt"));

        SQLiteTransactionListener transactionListener = new SQLiteTransactionListener() {
            @Override
            public void onBegin() {
                // TODO: 07/05/16 Perhaps show a loading indicator? Like a progress bar?
                Log.d(TAG, "onBegin: Import started.");
            }

            @Override
            public void onCommit() {
                Log.d(TAG, "onCommit: Import committed!");
            }

            @Override
            public void onRollback() {
                Log.w(TAG, "onRollback: Import failed.");
            }
        };

        db.beginTransactionWithListener(transactionListener);
        try {

            insertAgencies(db, readers.get(AGENCY_INDEX));

            db.beginTransaction();
            insertCalendars(db, readers.get(CALENDARS_INDEX));
            insertCalendarDates(db, readers.get(CALENDAR_DATES_INDEX));
            insertFareAttributes(db, readers.get(FARE_ATTRIBUTES_INDEX));
            insertRoutes(db, readers.get(ROUTES_INDEX));
            insertShapes(db, readers.get(SHAPES_INDEX));
            insertStops(db, readers.get(STOPS_INDEX));
            db.setTransactionSuccessful();
            db.endTransaction();

            db.beginTransaction();
            insertFareRules(db, readers.get(FARE_RULES_INDEX));
            insertTrips(db, readers.get(TRIPS_INDEX));
            db.setTransactionSuccessful();
            db.endTransaction();

            db.beginTransaction();
            insertStopTimes(db, readers.get(STOP_TIMES_INDEX));
            db.setTransactionSuccessful();
            db.endTransaction();

            db.setTransactionSuccessful();
        } finally {
            while (db.inTransaction()) {
                db.endTransaction();
                Log.d(TAG, "processFiles: transaction ended.");
            }
            db.close();
            Log.d(TAG, "processFiles: database closed.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences("settings", 0);

        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        downloadReference = settings.getLong("lastTransitDownload", -1);

        //if download exists...
        if (downloadExists()) {
            processDownload();
        } else {
            // later calls processDownload
            startDownload();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(onComplete);
        unregisterReceiver(onNotificationClick);
    }
}
