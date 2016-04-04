package com.example.matthew.transit;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends Activity {
    private static final String FILE_NAME = "gtfs.zip";
    private static DownloadManager manager = null;
    private static long downloadReference = -1L;

    private final BroadcastReceiver onNotificationClick = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(MainActivity.this, "You clicked me!", Toast.LENGTH_SHORT).show();
        }
    };

    private final BroadcastReceiver onComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ParcelFileDescriptor pfd = null;
            try {
                // open a parcel file descriptor using the file just downloaded
                pfd = manager.openDownloadedFile(downloadReference);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            extractFile(pfd);
        }
    };

    private void extractFile(ParcelFileDescriptor pfd) {
        try {
            // open the zip file
            try (ZipInputStream zipInputStream = new ZipInputStream(new ParcelFileDescriptor.AutoCloseInputStream(pfd))) {

                ZipEntry zipEntry;

                // loop through each file within the zip file
                while ((zipEntry = zipInputStream.getNextEntry()) != null) {

                    File file = new File(getFilesDir(), zipEntry.getName());

                    // save each file
                    try (FileOutputStream fileOutputStream = new FileOutputStream(file, false)) {
                        for (int c = zipInputStream.read(); c != -1; c = zipInputStream.read()) {
                            fileOutputStream.write(c);
                        }
                        zipInputStream.closeEntry();
                    }
                }
            }

            pfd.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String TRANSIT_FEEDS_KEY = this.getString(R.string.transit_feeds_key);

        final Uri.Builder builder = new Uri.Builder();

        builder.scheme("http")
                .authority("api.transitfeeds.com")
                .appendPath("v1")
                .appendPath("getLatestFeedVersion")
                .appendQueryParameter("feed", "winnipeg-transit/23")
                .appendQueryParameter("key", TRANSIT_FEEDS_KEY);

        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        // download the data
        downloadTransitData(builder.build());

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

    private void downloadTransitData(Uri uri) {
        downloadReference = manager.enqueue(new DownloadManager.Request(uri)
                .setDescription("Downloading Today's Transit Data…")
                .setTitle("Getting the latest transit information")
                .setVisibleInDownloadsUi(false)
                .setDestinationInExternalFilesDir(MainActivity.this, null, FILE_NAME)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE));
    }
}
