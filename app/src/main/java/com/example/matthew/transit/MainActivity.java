package com.example.matthew.transit;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
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

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();
    private static final String FILE_NAME = "gtfs.zip";
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
        deleteDatabase(TransitDatabaseHelper.DATABASE_NAME);

        final TransitDatabaseHelper mTransitDatabaseHelper = new TransitDatabaseHelper(getApplicationContext());
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

            TransitDatabaseManager.insertAgencies(db, readers.get(AGENCY_INDEX));

            db.beginTransaction();
            TransitDatabaseManager.insertCalendars(db, readers.get(CALENDARS_INDEX));
            TransitDatabaseManager.insertCalendarDates(db, readers.get(CALENDAR_DATES_INDEX));
            TransitDatabaseManager.insertFareAttributes(db, readers.get(FARE_ATTRIBUTES_INDEX));
            TransitDatabaseManager.insertRoutes(db, readers.get(ROUTES_INDEX));
            TransitDatabaseManager.insertShapes(db, readers.get(SHAPES_INDEX));
            TransitDatabaseManager.insertStops(db, readers.get(STOPS_INDEX));
            db.setTransactionSuccessful();
            db.endTransaction();

            db.beginTransaction();
            TransitDatabaseManager.insertFareRules(db, readers.get(FARE_RULES_INDEX));
            TransitDatabaseManager.insertTrips(db, readers.get(TRIPS_INDEX));
            db.setTransactionSuccessful();
            db.endTransaction();

            db.beginTransaction();
            TransitDatabaseManager.insertStopTimes(db, readers.get(STOP_TIMES_INDEX));
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

        db = mTransitDatabaseHelper.getReadableDatabase();
        try {
            db.beginTransaction();
            String sql = "select t.trip_id\n" +
                    "	  ,t.service_id\n" +
                    "	  ,t.trip_headsign\n" +
                    "	  ,t.direction_id\n" +
                    "	  ,t.block_id\n" +
                    "	  ,t.shape_id\n" +
                    "	  ,t.wheelchair_accessible\n" +
                    "	  ,s.stop_name\n" +
                    "	  ,st.arrival_time\n" +
                    "from route as r\n" +
                    "inner join trip as t\n" +
                    "	on r.route_id = t.route_id\n" +
                    "inner join calendar as c\n" +
                    "	on t.service_id = c.service_id\n" +
                    "left join calendar_date as cd\n" +
                    "	on t.service_id = cd.service_id\n" +
                    "inner join stop_time as st\n" +
                    "	on t.trip_id = st.trip_id\n" +
                    "inner join stop as s\n" +
                    "	on st.stop_id = s.stop_id\n" +
                    "where r.route_short_name = ?\n" +
                    "and c.monday = ?\n" +
                    "and cd.date <> ?\n" +
                    "and t.direction_id = ?\n" +
                    "and s.stop_name like ?\n" +
                    "and strftime('%H:%M:%S', st.arrival_time) < time(?)";

            String[] sqlArgs = new String[] {
                    "", "", "", "", "", ""
            };

            try (Cursor cursor = db.rawQuery(sql, sqlArgs)) {
                cursor.moveToFirst();
                Log.v(TAG, DatabaseUtils.dumpCursorToString(cursor));
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean DEVELOPER_MODE = false;

        if (DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    //.detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        settings = getSharedPreferences("settings", 0);

        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        downloadReference = settings.getLong("lastTransitDownload", -1);

        //if download exists...
        if (downloadExists()) {
            if (!getDatabasePath(TransitDatabaseHelper.DATABASE_NAME).exists()) {
                processDownload();
            }
        } else {
            // later calls processDownload
            startDownload();
        }

        if (savedInstanceState == null) {
            CursorLoaderListFragment routes = new CursorLoaderListFragment();
            routes.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, routes).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(onComplete);
        unregisterReceiver(onNotificationClick);
    }
}
