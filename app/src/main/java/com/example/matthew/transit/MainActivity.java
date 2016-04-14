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
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmConfiguration;

public class MainActivity extends Activity {
    private static final HashMap<String, String> FILE_CLASS_MAP = createFileClassMap();
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

    private static HashMap<String, String> createFileClassMap() {
        HashMap<String, String> fileClassMap = new HashMap<>();

        fileClassMap.put("agency.txt", "Agency");
        fileClassMap.put("calendar_dates.txt", "CalendarDate");
        fileClassMap.put("calendar.txt", "Calendar");
        fileClassMap.put("fare_attributes.txt", "FareAttribute");
        fileClassMap.put("fare_rules.txt", "FareRule");
        fileClassMap.put("feed_info.txt", "FeedInfo");
        fileClassMap.put("frequencies.txt", "Frequency");
        fileClassMap.put("shapes.txt", "Shape");
        fileClassMap.put("stops.txt", "Stop");
        fileClassMap.put("stop_times.txt", "StopTime");
        fileClassMap.put("transfers.txt", "Transfer");
        fileClassMap.put("trips.txt", "Trip");

        return fileClassMap;
    }

    private void processDownload() {
        ParcelFileDescriptor pfd = null;

        try {
            // open a parcel file descriptor using the file just downloaded
            pfd = manager.openDownloadedFile(downloadReference);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<File> files = extractFiles(pfd);
        processFiles(files);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences("settings", 0);

        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        downloadReference = settings.getLong("lastTransitDownload", -1);

        //if download exists...
        if (downloadExists() != null) {
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
    private File downloadExists() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadReference);
        Cursor cursor = manager.query(query);

        File file = null;

        // check that the cursor managed to find the download
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                String fileName = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                Log.d("fileName", fileName);
                file = new File(fileName);
            }
            //cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
        }

        return file;
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
                .setVisibleInDownloadsUi(false)
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

    /**
     * process each csv file into an array and add to the database
     *
     * @param files csv files to be processed
     */
    private void processFiles(ArrayList<File> files) {

        try (DynamicRealm realm = DynamicRealm.getInstance(new RealmConfiguration.Builder(getApplicationContext()).build())) {

            ArrayList<DynamicRealmObject> allClasses = new ArrayList<>();

            //region beginTransaction()
            // start the transaction
            //realm.beginTransaction();

            for (File file :
                    files) {
                try {
                    try (CSVReader reader = new CSVReader(new FileReader(file))) {

                        String[] headers = getHeaders(reader);


                        String className = FILE_CLASS_MAP.get(file.getName());

                        Log.d("Class description", "class name: " + className + " headers: " + Arrays.toString(headers));
                        ArrayList<DynamicRealmObject> classList = new ArrayList<>();
                        String[] nextLine;

                        try {
                            // loop through each line
                            while ((nextLine = reader.readNext()) != null) {

                                // FIXME: 13/04/16 In this case, the transaction is opened and closed for each and every record. Should this happen? Most likely not.
                                // TODO: 13/04/16 Check this out for ideas:
                                // Import strategy: https://github.com/DanielGrech/sydtrip/blob/exp/otp_routing/android/data/src/main/java/com/dgsd/android/data/importer/ImportManager.java
                                // Build a constructor using String[]: https://github.com/DanielGrech/sydtrip/blob/exp/otp_routing/android/data/src/main/java/com/dgsd/android/data/model/DbRoute.java
                                realm.beginTransaction();
                                // create an instance of the class to hold the record
                                DynamicRealmObject classInstance =
                                        new DynamicRealmObject(realm.createObject(className));

                                // e.g. January 4, 2016 => 20160104
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

                                // loop through the headers and dynamically set the class's properties
                                for (int i = 0; i < headers.length; i++) {
                                    switch (classInstance.getFieldType(headers[i])) {
                                        case INTEGER:
                                            classInstance.setInt(headers[i], Integer.parseInt(nextLine[i]));
                                            break;
                                        case BOOLEAN:
                                            classInstance.setBoolean(headers[i], Boolean.parseBoolean(nextLine[i]));
                                            break;
                                        case STRING:
                                            classInstance.setString(headers[i], nextLine[i]);
                                            break;
                                        //case BINARY: classInstance.setString(headers[i], nextLine[i]); break;
                                        //case UNSUPPORTED_TABLE: classInstance.setString(headers[i], nextLine[i]); break;
                                        //case UNSUPPORTED_MIXED: classInstance.setString(headers[i], nextLine[i]); break;
                                        case DATE:
                                            classInstance.setDate(headers[i], dateFormat.parse(nextLine[i]));
                                            break;
                                        case FLOAT:
                                            classInstance.setFloat(headers[i], Float.parseFloat(nextLine[i]));
                                            break;
                                        case DOUBLE:
                                            classInstance.setDouble(headers[i], Double.parseDouble(nextLine[i]));
                                            break;
                                        //case OBJECT: classInstance.setString(headers[i], nextLine[i]); break;
                                        //case LIST: classInstance.setString(headers[i], nextLine[i]); break;
                                        default:
                                            Log.d("Insert Error", String.format("field type: %s i: %d", classInstance.getFieldType(headers[i]), i));
                                    }
                                }
                                realm.commitTransaction();

                                classList.add(classInstance);
                            }
                        } catch (IOException | ParseException e) {
                            e.printStackTrace();
                        }

                        allClasses.addAll(classList);

                        //region case switch statement
                    /*
                    switch (file.getName()) {
                        case "agency.txt":
                            //parseClass(className, headers, realm, reader, headers, realm);
                            insertClass(className, reader, headers, realm);
                            break;
                        case "calendar_dates.txt":
                            insertCalendarDates(reader, headers, realm);
                            break;
                        case "calendar.txt":
                            insertCalendars(reader, headers, realm);
                            break;
                        case "fare_attributes.txt":
                            insertFareAttributes(reader, headers, realm);
                            break;
                        case "fare_rules.txt":
                            insertFareRules(reader, headers, realm);
                            break;
                        case "feed_info.txt":
                            insertFeedInfo(reader, headers, realm);
                            break;
                        case "frequencies.txt":
                            insertFrequencies(reader, headers, realm);
                            break;
                        case "routes.txt":
                            insertRoutes(reader, headers, realm);
                            break;
                        case "shapes.txt":
                            insertShapes(reader, headers, realm);
                            break;
                        case "stops.txt":
                            insertStops(reader, headers, realm);
                            break;
                        case "stop_times.txt":
                            insertStopTimes(reader, headers, realm);
                            break;
                        case "transfers.txt":
                            insertTransfers(reader, headers, realm);
                            break;
                        case "trips.txt":
                            insertTrips(reader, headers, realm);
                            break;
                        default:
                            break;
                    }
                    */
                        //endregion

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.d("Update results", String.valueOf(allClasses.size()));

            // commit all changes
            //realm.commitTransaction();
            //endregion
        }
    }

    private String[] getHeaders(CSVReader reader) {
        String[] headers = null;

        try {
            headers = reader.readNext();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return headers;
    }

    @NonNull
    private ArrayList<File> extractFiles(ParcelFileDescriptor param) {
        ArrayList<File> files = new ArrayList<>();

        // open the zip file
        try (ZipInputStream zipInput = new ZipInputStream(new BufferedInputStream(new ParcelFileDescriptor.AutoCloseInputStream(param)))) {

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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;

        //parseFiles(files);
    }
}
