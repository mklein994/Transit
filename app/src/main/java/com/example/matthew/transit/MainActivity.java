package com.example.matthew.transit;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    private void insertValues(SQLiteDatabase db, String tableName, ContentValues values, String[] nextLine) {
        try {
            db.insertOrThrow(tableName, null, values);
        } catch (IllegalArgumentException e) {
            Log.e(TAG,
                    String.format("insertValues: insert into %s failed: %s",
                            tableName,
                            Arrays.toString(nextLine)),
                    e);
            e.printStackTrace();
        }
    }

    private void insertAgencies(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();
                values.put(Agency.AGENCY_NAME, nextLine[Agency.AGENCY_NAME_INDEX]);
                values.put(Agency.AGENCY_URL, nextLine[Agency.AGENCY_URL_INDEX]);
                values.put(Agency.AGENCY_TIMEZONE, nextLine[Agency.AGENCY_TIMEZONE_INDEX]);
                values.put(Agency.AGENCY_LANG, nextLine[Agency.AGENCY_LANG_INDEX]);
                values.put(Agency.AGENCY_PHONE, nextLine[Agency.AGENCY_PHONE_INDEX]);

                insertValues(db, Tables.AGENCY, values, nextLine);
            }
        } catch (IOException e) {
            Log.e(TAG, "insertAgencies: Error happened while parsing the csv file.", e);
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: Agencies imported.");
    }

    private void insertCalendars(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();
                values.put(Calendar.SERVICE_ID, nextLine[Calendar.SERVICE_ID_INDEX]);
                values.put(Calendar.MONDAY, nextLine[Calendar.MONDAY_INDEX]);
                values.put(Calendar.TUESDAY, nextLine[Calendar.TUESDAY_INDEX]);
                values.put(Calendar.WEDNESDAY, nextLine[Calendar.WEDNESDAY_INDEX]);
                values.put(Calendar.THURSDAY, nextLine[Calendar.THURSDAY_INDEX]);
                values.put(Calendar.FRIDAY, nextLine[Calendar.FRIDAY_INDEX]);
                values.put(Calendar.SATURDAY, nextLine[Calendar.SATURDAY_INDEX]);
                values.put(Calendar.SUNDAY, nextLine[Calendar.SUNDAY_INDEX]);
                values.put(Calendar.START_DATE, nextLine[Calendar.START_DATE_INDEX]);
                values.put(Calendar.END_DATE, nextLine[Calendar.END_DATE_INDEX]);

                insertValues(db, Tables.CALENDAR, values, nextLine);
            }
        } catch (IOException e) {
            Log.e(TAG, "insertCalendars: Error happened while parsing the csv file.", e);
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: Calendars imported.");
    }

    private void insertCalendarDates(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();
                values.put(CalendarDate.SERVICE_ID, nextLine[CalendarDate.SERVICE_ID_INDEX]);
                values.put(CalendarDate.DATE, nextLine[CalendarDate.DATE_INDEX]);
                values.put(CalendarDate.EXCEPTION_TYPE, nextLine[CalendarDate.EXCEPTION_TYPE_INDEX]);

                insertValues(db, Tables.CALENDAR_DATE, values, nextLine);
            }
        } catch (IOException e) {
            Log.e(TAG, "insertCalendarDates: Error happened while parsing the csv file.", e);
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: CalendarDates imported.");
    }

    private void insertFareAttributes(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();
                values.put(FareAttribute.FARE_ID, nextLine[FareAttribute.FARE_ID_INDEX]);
                values.put(FareAttribute.PRICE, nextLine[FareAttribute.PRICE_INDEX]);
                values.put(FareAttribute.CURRENCY_TYPE, nextLine[FareAttribute.CURRENCY_TYPE_INDEX]);
                values.put(FareAttribute.PAYMENT_METHOD, nextLine[FareAttribute.PAYMENT_METHOD_INDEX]);
                values.put(FareAttribute.TRANSFERS, nextLine[FareAttribute.TRANSFERS_INDEX]);
                values.put(FareAttribute.TRANSFER_DURATION, nextLine[FareAttribute.TRANSFER_DURATION_INDEX]);

                insertValues(db, Tables.FARE_ATTRIBUTE, values, nextLine);
            }
        } catch (IOException e) {
            Log.e(TAG, "insertFareAttributes: Error happened while parsing the csv file.", e);
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: FareAttributes imported.");
    }

    private void insertRoutes(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();

                values.put(Route.ROUTE_ID, nextLine[Route.ROUTE_ID_INDEX]);
                values.put(Route.ROUTE_SHORT_NAME, nextLine[Route.ROUTE_SHORT_NAME_INDEX]);
                values.put(Route.ROUTE_LONG_NAME, nextLine[Route.ROUTE_LONG_NAME_INDEX]);
                values.put(Route.ROUTE_TYPE, nextLine[Route.ROUTE_TYPE_INDEX]);
                values.put(Route.ROUTE_COLOR, nextLine[Route.ROUTE_COLOR_INDEX]);
                values.put(Route.ROUTE_TEXT_COLOR, nextLine[Route.ROUTE_TEXT_COLOR_INDEX]);

                insertValues(db, Tables.ROUTE, values, nextLine);
            }
        } catch (IOException e) {
            Log.e(TAG, "insertRoutes: Error happened while parsing the csv file.", e);
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: Routes imported.");
    }

    private void insertShapes(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();

                values.put(Shape.SHAPE_ID, nextLine[Shape.SHAPE_ID_INDEX]);
                values.put(Shape.SHAPE_PT_LAT, nextLine[Shape.SHAPE_PT_LAT_INDEX]);
                values.put(Shape.SHAPE_PT_LON, nextLine[Shape.SHAPE_PT_LON_INDEX]);
                values.put(Shape.SHAPE_PT_SEQUENCE, nextLine[Shape.SHAPE_PT_SEQUENCE_INDEX]);

                insertValues(db, Tables.SHAPE, values, nextLine);
            }
        } catch (IOException e) {
            Log.e(TAG, "insertShapes: Error happened while parsing the csv file.", e);
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: Shapes imported.");
    }

    private void insertStops(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();

                values.put(Stop.STOP_ID, nextLine[Stop.STOP_ID_INDEX]);
                values.put(Stop.STOP_CODE, nextLine[Stop.STOP_CODE_INDEX]);
                values.put(Stop.STOP_NAME, nextLine[Stop.STOP_NAME_INDEX]);
                values.put(Stop.STOP_LAT, nextLine[Stop.STOP_LAT_INDEX]);
                values.put(Stop.STOP_LON, nextLine[Stop.STOP_LON_INDEX]);
                values.put(Stop.STOP_URL, nextLine[Stop.STOP_URL_INDEX]);

                insertValues(db, Tables.STOP, values, nextLine);
            }
        } catch (IOException e) {
            Log.e(TAG, "insertStops: Error happened while parsing the csv file.", e);
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: Stops imported.");
    }

    private void insertFareRules(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();

                values.put(FareRule.FARE_ID, nextLine[FareRule.FARE_ID_INDEX]);
                values.put(FareRule.ROUTE_ID, nextLine[FareRule.ROUTE_ID_INDEX]);

                insertValues(db, Tables.FARE_RULE, values, nextLine);
            }
        } catch (IOException e) {
            Log.e(TAG, "insertFareRules: Error happened while parsing the csv file.", e);
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: FareRules imported.");
    }

    private void insertTrips(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();

                values.put(Trip.ROUTE_ID, nextLine[Trip.ROUTE_ID_INDEX]);
                values.put(Trip.SERVICE_ID, nextLine[Trip.SERVICE_ID_INDEX]);
                values.put(Trip.TRIP_ID, nextLine[Trip.TRIP_ID_INDEX]);
                values.put(Trip.TRIP_HEADSIGN, nextLine[Trip.TRIP_HEADSIGN_INDEX]);
                values.put(Trip.DIRECTION_ID, nextLine[Trip.DIRECTION_ID_INDEX]);
                values.put(Trip.BLOCK_ID, nextLine[Trip.BLOCK_ID_INDEX]);
                values.put(Trip.SHAPE_ID, nextLine[Trip.SHAPE_ID_INDEX]);
                values.put(Trip.WHEELCHAIR_ACCESSIBLE, nextLine[Trip.WHEELCHAIR_ACCESSIBLE_INDEX]);

                insertValues(db, Tables.TRIP, values, nextLine);
            }
        } catch (IOException e) {
            Log.e(TAG, "insertTrips: Error happened while parsing the csv file.", e);
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: Trips imported.");
    }

    private void insertStopTimes(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();

                values.put(StopTime.TRIP_ID, nextLine[StopTime.TRIP_ID_INDEX]);
                values.put(StopTime.ARRIVAL_TIME, nextLine[StopTime.ARRIVAL_TIME_INDEX]);
                values.put(StopTime.DEPARTURE_TIME, nextLine[StopTime.DEPARTURE_TIME_INDEX]);
                values.put(StopTime.STOP_ID, nextLine[StopTime.STOP_ID_INDEX]);
                values.put(StopTime.STOP_SEQUENCE, nextLine[StopTime.STOP_SEQUENCE_INDEX]);

                insertValues(db, Tables.STOP_TIME, values, nextLine);
            }
        } catch (IOException e) {
            Log.e(TAG, "insertStopTimes: Error happened while parsing the csv file.", e);
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
        Cursor cursor = manager.query(query);

        boolean downloadExists = false;

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
        ParcelFileDescriptor pfd = null;

        try {
            // open a parcel file descriptor using the file just downloaded
            pfd = manager.openDownloadedFile(downloadReference);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //ArrayList<File> files = extractFiles(pfd);
        File[] files = extractFiles(pfd);

        processFiles(files);
        //new DatabaseImportTask().execute(files);
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

        db.beginTransaction();
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
            Log.d(TAG, "processFiles: Import successful!");
        } finally {
            while (db.inTransaction()) {
                db.endTransaction();
            }
            db.close();
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
