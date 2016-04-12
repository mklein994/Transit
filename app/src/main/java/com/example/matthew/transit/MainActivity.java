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
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends Activity {
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

    private void processDownload() {
        ParcelFileDescriptor pfd = null;

        try {
            // open a parcel file descriptor using the file just downloaded
            pfd = manager.openDownloadedFile(downloadReference);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<File> files = extractFiles(pfd);

        parseFiles(files);
    }

    /**
     * Extract the downloaded zip files
     *
     * @param pfd ParcelFileDescriptor of the downloaded zip file
     * @return an array of the extracted files
     */
    private ArrayList<File> extractFiles(ParcelFileDescriptor pfd) {
        ArrayList<File> files = new ArrayList<>();

        // open the zip file
        try (ZipInputStream zipInput = new ZipInputStream(new BufferedInputStream(new ParcelFileDescriptor.AutoCloseInputStream(pfd)))) {

            ZipEntry zipEntry;

            // loop through each file within the zip file
            while ((zipEntry = zipInput.getNextEntry()) != null) {

                File file = new File(getFilesDir(), zipEntry.getName());
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return files;
    }

    /**
     * process each csv file into an array and add to the database
     *
     * @param files csv files to be processed
     */
    private void parseFiles(ArrayList<File> files) {

        for (File file :
                files) {
            try {
                try (CSVReader reader = new CSVReaderBuilder(new FileReader(file)).build()) {

                    // get the headers
                    String[] headers = reader.readNext();

                    // get all the records for the current file
                    List records = reader.readAll();

                    Log.d("Records for file", String.format("Records for file %s", file.getName()));
                }

            } catch (FileNotFoundException e) {
                Log.e("CSV Parsing", "file not found");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("CSV Parsing", String.format("%s failed to parse.", file.getName()));
                e.printStackTrace();
            }
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
}
