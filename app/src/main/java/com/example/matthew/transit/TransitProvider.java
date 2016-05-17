package com.example.matthew.transit;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by matthew on 09/05/16.
 */
public class TransitProvider extends ContentProvider {

    private static final String TAG = TransitProvider.class.getSimpleName();

    private TransitDatabaseHelper mOpenHelper;

    private TransitProviderUriMatcher mUriMatcher;

    @Override
    public boolean onCreate() {
        mOpenHelper = new TransitDatabaseHelper(getContext());
        mUriMatcher = new TransitProviderUriMatcher();
        return true;
    }

    private void deleteDatabase() {
        mOpenHelper.close();
        Context context = getContext();
        TransitDatabaseHelper.deleteDatabase(context);
        mOpenHelper = new TransitDatabaseHelper(getContext());
    }

    @Override
    public String getType(Uri uri) {
        TransitUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);
        return matchingUriEnum.contentType;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        TransitUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);

        switch (matchingUriEnum) {
            default: {
                final SelectionBuilder builder = buildExpandedSelection(uri, matchingUriEnum.code);

                boolean distinct = TransitContractHelper.isQueryDistinct(uri);

                Cursor cursor = builder
                        .where(selection, selectionArgs)
                        .query(db, distinct, projection, sortOrder, null);

                Context context = getContext();
                if (null != context) {
                    cursor.setNotificationUri(context.getContentResolver(), uri);
                }
                return cursor;
            }
        }
    }

    private SelectionBuilder buildExpandedSelection(Uri uri, int code) {
        final SelectionBuilder builder = new SelectionBuilder();
        TransitUriEnum matchingUriEnum = mUriMatcher.matchUri(uri);

        switch (matchingUriEnum) {
            case AGENCIES:
            case CALENDARS:
            case CALENDAR_DATES:
            case FARE_ATTRIBUTES:
            case FARE_RULES:
            case ROUTES:
            case SHAPES:
            case STOP_TIMES:
            case TRIPS:
                return builder.table(matchingUriEnum.table);
            default: {
                throw new UnsupportedOperationException("Unknown uri for " + uri);
            }
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
