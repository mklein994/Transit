package com.example.matthew.transit;

import android.content.UriMatcher;
import android.net.Uri;
import android.util.SparseArray;

/**
 * Created by matthew on 09/05/16.
 */
public class TransitProviderUriMatcher {
    private UriMatcher mUriMatcher;

    private SparseArray<TransitUriEnum> mEnumsMap = new SparseArray<>();

    public TransitProviderUriMatcher() {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        buildUriMatcher();
    }

    private void buildUriMatcher() {
        final String authority = TransitContract.CONTENT_AUTHORITY;

        TransitUriEnum[] uris = TransitUriEnum.values();

        for (TransitUriEnum uri : uris) {
            mUriMatcher.addURI(authority, uri.path, uri.code);
        }

        buildEnumsMap();
    }

    private void buildEnumsMap() {
        TransitUriEnum[] uris = TransitUriEnum.values();

        for (TransitUriEnum uri : uris) {
            mEnumsMap.put(uri.code, uri);
        }
    }

    public TransitUriEnum matchUri(Uri uri) {
        final int code = mUriMatcher.match(uri);
        try {
            return matchCode(code);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    public TransitUriEnum matchCode(int code) {
        TransitUriEnum transitUriEnum = mEnumsMap.get(code);

        if (transitUriEnum != null) {
            return transitUriEnum;
        } else {
            throw new UnsupportedOperationException("Unknown uri with code " + code);
        }
    }
}
