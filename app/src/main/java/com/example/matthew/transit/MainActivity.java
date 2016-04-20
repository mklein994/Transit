package com.example.matthew.transit;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Toast;

import com.example.matthew.transit.model.Agency;
import com.example.matthew.transit.model.Calendar;
import com.example.matthew.transit.model.CalendarDate;
import com.example.matthew.transit.model.FareAttribute;
import com.example.matthew.transit.model.FareRule;
import com.example.matthew.transit.model.Route;
import com.example.matthew.transit.model.Shape;
import com.example.matthew.transit.model.Stop;
import com.example.matthew.transit.model.StopTime;
import com.example.matthew.transit.model.Trip;
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
        if (!realm.isClosed()) {
            realm.close();
        }
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

    private void processFiles(File[] files) {
        try (Realm realm = Realm.getDefaultInstance()) {

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

            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    try {
                        ModelManager.importAgencies(bgRealm, readers.get(AGENCY_INDEX));
                        Log.d(TAG, String.format("processFiles: Agencies imported: %d", bgRealm.where(Agency.class).findAll().size()));

                        ModelManager.importCalendars(bgRealm, readers.get(CALENDARS_INDEX));
                        Log.d(TAG, String.format("processFiles: calendars imported: %d", bgRealm.where(Calendar.class).findAll().size()));
                        ModelManager.importCalendarDates(bgRealm, readers.get(CALENDAR_DATES_INDEX));
                        Log.d(TAG, String.format("processFiles: calendar dates imported: %d", bgRealm.where(CalendarDate.class).findAll().size()));
                        ModelManager.importFareAttributes(bgRealm, readers.get(FARE_ATTRIBUTES_INDEX));
                        Log.d(TAG, String.format("processFiles: fare attributes imported: %d", bgRealm.where(FareAttribute.class).findAll().size()));
                        ModelManager.importRoutes(bgRealm, readers.get(ROUTES_INDEX));
                        Log.d(TAG, String.format("processFiles: routes imported: %d", bgRealm.where(Route.class).findAll().size()));
                        ModelManager.importShapes(bgRealm, readers.get(SHAPES_INDEX));
                        Log.d(TAG, String.format("processFiles: shapes imported: %d", bgRealm.where(Shape.class).findAll().size()));
                        ModelManager.importStops(bgRealm, readers.get(STOPS_INDEX));
                        Log.d(TAG, String.format("processFiles: stops imported: %d", bgRealm.where(Stop.class).findAll().size()));

                        ModelManager.importFareRules(bgRealm, readers.get(FARE_RULES_INDEX));
                        Log.d(TAG, String.format("processFiles: fare rules imported: %d", bgRealm.where(FareRule.class).findAll().size()));
                        ModelManager.importTrips(bgRealm, readers.get(TRIPS_INDEX));
                        Log.d(TAG, String.format("processFiles: trips imported: %d", bgRealm.where(Trip.class).findAll().size()));

                        ModelManager.importStopTimes(bgRealm, readers.get(STOP_TIMES_INDEX));
                        Log.d(TAG, String.format("processFiles: stop times imported: %d", bgRealm.where(StopTime.class).findAll().size()));
                    } catch (IOException e) {
                        Log.d(TAG, "execute: Import failed, there was an exception from parsing the csv files.");
                        e.printStackTrace();
                    }
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: test query: find fare attributes where route is 44: " +
                            realm.where(FareAttribute.class).equalTo("routes.routeId", "44").findFirst());

                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Log.d(TAG, "onError: Error occurred while importing!");
                    error.printStackTrace();
                }
            });
        }
    }

    /*
    class DatabaseImportTask extends AsyncTask<File, Void, Void> {

        @Override
        protected Void doInBackground(File... files) {
            processFiles(files);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "onPostExecute: Import Successful!");
        }
    }
    */
}
