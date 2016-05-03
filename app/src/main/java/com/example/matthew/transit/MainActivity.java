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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.realm.Realm;

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
    private Realm realm;
    private SharedPreferences settings;

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

            insertCalendars(db, readers.get(CALENDARS_INDEX));
            insertCalendarDates(db, readers.get(CALENDAR_DATES_INDEX));
            insertFareAttributes(db, readers.get(FARE_ATTRIBUTES_INDEX));
            insertRoutes(db, readers.get(ROUTES_INDEX));
            insertShapes(db, readers.get(SHAPES_INDEX));
            insertStops(db, readers.get(STOPS_INDEX));

            insertFareRules(db, readers.get(FARE_RULES_INDEX));
            insertTrips(db, readers.get(TRIPS_INDEX));

            insertStopTimes(db, readers.get(STOP_TIMES_INDEX));

            db.setTransactionSuccessful();
            Log.d(TAG, "processFiles: Import successful!");
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private void insertAgencies(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        long newRowId = 0;
        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();
                values.put(TransitContract.Agency.AGENCY_NAME, nextLine[TransitContract.Agency.AGENCY_NAME_INDEX]);
                values.put(TransitContract.Agency.AGENCY_URL, nextLine[TransitContract.Agency.AGENCY_URL_INDEX]);
                values.put(TransitContract.Agency.AGENCY_TIMEZONE, nextLine[TransitContract.Agency.AGENCY_TIMEZONE_INDEX]);
                values.put(TransitContract.Agency.AGENCY_LANG, nextLine[TransitContract.Agency.AGENCY_LANG_INDEX]);
                values.put(TransitContract.Agency.AGENCY_PHONE, nextLine[TransitContract.Agency.AGENCY_PHONE_INDEX]);

                newRowId = db.insert(
                        TransitDatabaseHelper.Tables.AGENCY,
                        null,
                        values);
            }
        } catch (IOException e) {
            Log.e(TAG, String.format("insertAgencies: Error happened inserting row with ID %d.", newRowId));
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: Agencies imported.");
    }

    private void insertCalendars(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        long newRowId = 0;
        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();
                values.put(TransitContract.Calendar.SERVICE_ID, nextLine[TransitContract.Calendar.SERVICE_ID_INDEX]);
                values.put(TransitContract.Calendar.MONDAY, nextLine[TransitContract.Calendar.MONDAY_INDEX]);
                values.put(TransitContract.Calendar.TUESDAY, nextLine[TransitContract.Calendar.TUESDAY_INDEX]);
                values.put(TransitContract.Calendar.WEDNESDAY, nextLine[TransitContract.Calendar.WEDNESDAY_INDEX]);
                values.put(TransitContract.Calendar.THURSDAY, nextLine[TransitContract.Calendar.THURSDAY_INDEX]);
                values.put(TransitContract.Calendar.FRIDAY, nextLine[TransitContract.Calendar.FRIDAY_INDEX]);
                values.put(TransitContract.Calendar.SATURDAY, nextLine[TransitContract.Calendar.SATURDAY_INDEX]);
                values.put(TransitContract.Calendar.SUNDAY, nextLine[TransitContract.Calendar.SUNDAY_INDEX]);
                values.put(TransitContract.Calendar.START_DATE, nextLine[TransitContract.Calendar.START_DATE_INDEX]);
                values.put(TransitContract.Calendar.END_DATE, nextLine[TransitContract.Calendar.END_DATE_INDEX]);

                newRowId = db.insert(
                        TransitDatabaseHelper.Tables.CALENDAR,
                        null,
                        values);
            }
        } catch (IOException e) {
            Log.e(TAG, String.format("insertCalendars: Error happened inserting row with ID %d.", newRowId));
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: Calendars imported.");
    }

    private void insertCalendarDates(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        long newRowId = 0;
        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();
                values.put(TransitContract.CalendarDate.SERVICE_ID, nextLine[TransitContract.CalendarDate.SERVICE_ID_INDEX]);
                values.put(TransitContract.CalendarDate.DATE, nextLine[TransitContract.CalendarDate.DATE_INDEX]);
                values.put(TransitContract.CalendarDate.EXCEPTION_TYPE, nextLine[TransitContract.CalendarDate.EXCEPTION_TYPE_INDEX]);

                newRowId = db.insert(
                        TransitDatabaseHelper.Tables.CALENDAR_DATE,
                        null,
                        values);
            }
        } catch (IOException e) {
            Log.e(TAG, String.format("insertCalendarDates: Error happened inserting row with ID %d.", newRowId));
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: CalendarDates imported.");
    }

    private void insertFareAttributes(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        long newRowId = 0;
        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();
                values.put(TransitContract.FareAttribute.FARE_ID, nextLine[TransitContract.FareAttribute.FARE_ID_INDEX]);
                values.put(TransitContract.FareAttribute.PRICE, nextLine[TransitContract.FareAttribute.PRICE_INDEX]);
                values.put(TransitContract.FareAttribute.CURRENCY_TYPE, nextLine[TransitContract.FareAttribute.CURRENCY_TYPE_INDEX]);
                values.put(TransitContract.FareAttribute.PAYMENT_METHOD, nextLine[TransitContract.FareAttribute.PAYMENT_METHOD_INDEX]);
                values.put(TransitContract.FareAttribute.TRANSFERS, nextLine[TransitContract.FareAttribute.TRANSFERS_INDEX]);
                values.put(TransitContract.FareAttribute.TRANSFER_DURATION, nextLine[TransitContract.FareAttribute.TRANSFER_DURATION_INDEX]);

                newRowId = db.insert(
                        TransitDatabaseHelper.Tables.FARE_ATTRIBUTE,
                        null,
                        values);
            }
        } catch (IOException e) {
            Log.e(TAG, String.format("insertFareAttributes: Error happened inserting row with ID %d.", newRowId));
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: FareAttributes imported.");
    }

    private void insertRoutes(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        long newRowId = 0;
        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();

                values.put(TransitContract.Route.ROUTE_ID, nextLine[TransitContract.Route.ROUTE_ID_INDEX]);
                values.put(TransitContract.Route.ROUTE_SHORT_NAME, nextLine[TransitContract.Route.ROUTE_SHORT_NAME_INDEX]);
                values.put(TransitContract.Route.ROUTE_LONG_NAME, nextLine[TransitContract.Route.ROUTE_LONG_NAME_INDEX]);
                values.put(TransitContract.Route.ROUTE_TYPE, nextLine[TransitContract.Route.ROUTE_TYPE_INDEX]);
                values.put(TransitContract.Route.ROUTE_COLOR, nextLine[TransitContract.Route.ROUTE_COLOR_INDEX]);
                values.put(TransitContract.Route.ROUTE_TEXT_COLOR, nextLine[TransitContract.Route.ROUTE_TEXT_COLOR_INDEX]);

                newRowId = db.insert(
                        TransitDatabaseHelper.Tables.ROUTE,
                        null,
                        values);
            }
        } catch (IOException e) {
            Log.e(TAG, String.format("insertRoutes: Error happened inserting row with ID %d.", newRowId));
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: Routes imported.");
    }

    private void insertShapes(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        long newRowId = 0;
        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();

                values.put(TransitContract.Shape.SHAPE_ID, nextLine[TransitContract.Shape.SHAPE_ID_INDEX]);
                values.put(TransitContract.Shape.SHAPE_PT_LAT, nextLine[TransitContract.Shape.SHAPE_PT_LAT_INDEX]);
                values.put(TransitContract.Shape.SHAPE_PT_LON, nextLine[TransitContract.Shape.SHAPE_PT_LON_INDEX]);
                values.put(TransitContract.Shape.SHAPE_PT_SEQUENCE, nextLine[TransitContract.Shape.SHAPE_PT_SEQUENCE_INDEX]);

                newRowId = db.insert(
                        TransitDatabaseHelper.Tables.SHAPE,
                        null,
                        values);
            }
        } catch (IOException e) {
            Log.e(TAG, String.format("insertShapes: Error happened inserting row with ID %d.", newRowId));
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: Shapes imported.");
    }

    private void insertStops(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        long newRowId = 0;
        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();

                values.put(TransitContract.Stop.STOP_ID, nextLine[TransitContract.Stop.STOP_ID_INDEX]);
                values.put(TransitContract.Stop.STOP_CODE, nextLine[TransitContract.Stop.STOP_CODE_INDEX]);
                values.put(TransitContract.Stop.STOP_NAME, nextLine[TransitContract.Stop.STOP_NAME_INDEX]);
                values.put(TransitContract.Stop.STOP_LAT, nextLine[TransitContract.Stop.STOP_LAT_INDEX]);
                values.put(TransitContract.Stop.STOP_LON, nextLine[TransitContract.Stop.STOP_LON_INDEX]);
                values.put(TransitContract.Stop.STOP_URL, nextLine[TransitContract.Stop.STOP_URL_INDEX]);

                newRowId = db.insert(
                        TransitDatabaseHelper.Tables.STOP,
                        null,
                        values);
            }
        } catch (IOException e) {
            Log.e(TAG, String.format("insertStops: Error happened inserting row with ID %d.", newRowId));
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: Stops imported.");
    }

    private void insertFareRules(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        long newRowId = 0;
        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();

                values.put(TransitContract.FareRule.FARE_ID, nextLine[TransitContract.FareRule.FARE_ID_INDEX]);
                values.put(TransitContract.FareRule.ROUTE_ID, nextLine[TransitContract.FareRule.ROUTE_ID_INDEX]);

                newRowId = db.insert(
                        TransitDatabaseHelper.Tables.FARE_RULE,
                        null,
                        values);
            }
        } catch (IOException e) {
            Log.e(TAG, String.format("insertFareRules: Error happened inserting row with ID %d.", newRowId));
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: FareRules imported.");
    }

    private void insertTrips(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        long newRowId = 0;
        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();

                values.put(TransitContract.Trip.ROUTE_ID, nextLine[TransitContract.Trip.ROUTE_ID_INDEX]);
                values.put(TransitContract.Trip.SERVICE_ID, nextLine[TransitContract.Trip.SERVICE_ID_INDEX]);
                values.put(TransitContract.Trip.TRIP_ID, nextLine[TransitContract.Trip.TRIP_ID_INDEX]);
                values.put(TransitContract.Trip.TRIP_HEADSIGN, nextLine[TransitContract.Trip.TRIP_HEADSIGN_INDEX]);
                values.put(TransitContract.Trip.DIRECTION_ID, nextLine[TransitContract.Trip.DIRECTION_ID_INDEX]);
                values.put(TransitContract.Trip.BLOCK_ID, nextLine[TransitContract.Trip.BLOCK_ID_INDEX]);
                values.put(TransitContract.Trip.SHAPE_ID, nextLine[TransitContract.Trip.SHAPE_ID_INDEX]);
                values.put(TransitContract.Trip.WHEELCHAIR_ACCESSIBLE, nextLine[TransitContract.Trip.WHEELCHAIR_ACCESSIBLE_INDEX]);

                newRowId = db.insert(
                        TransitDatabaseHelper.Tables.TRIP,
                        null,
                        values);
            }
        } catch (IOException e) {
            Log.e(TAG, String.format("insertTrips: Error happened inserting row with ID %d.", newRowId));
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: Trips imported.");
    }

    private void insertStopTimes(SQLiteDatabase db, CSVReader reader) {
        String[] nextLine;

        long newRowId = 0;
        try {
            while ((nextLine = reader.readNext()) != null) {
                ContentValues values = new ContentValues();

                values.put(TransitContract.StopTime.TRIP_ID, nextLine[TransitContract.StopTime.TRIP_ID_INDEX]);
                values.put(TransitContract.StopTime.ARRIVAL_TIME, nextLine[TransitContract.StopTime.ARRIVAL_TIME_INDEX]);
                values.put(TransitContract.StopTime.DEPARTURE_TIME, nextLine[TransitContract.StopTime.DEPARTURE_TIME_INDEX]);
                values.put(TransitContract.StopTime.STOP_ID, nextLine[TransitContract.StopTime.STOP_ID_INDEX]);
                values.put(TransitContract.StopTime.STOP_SEQUENCE, nextLine[TransitContract.StopTime.STOP_SEQUENCE_INDEX]);

                newRowId = db.insert(
                        TransitDatabaseHelper.Tables.STOP_TIME,
                        null,
                        values);
            }
        } catch (IOException e) {
            Log.e(TAG, String.format("insertStopTimes: Error happened inserting row with ID %d.", newRowId));
            e.printStackTrace();
        }
        Log.d(TAG, "processFiles: StopTimes imported.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences("settings", 0);

        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        downloadReference = settings.getLong("lastTransitDownload", -1);

        //realm = Realm.getDefaultInstance();

        //if download exists...
        if (downloadExists()) {
            processDownload();
        } else {
            // later calls processDownload
            startDownload();
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(onComplete);
        unregisterReceiver(onNotificationClick);
    }

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
}
