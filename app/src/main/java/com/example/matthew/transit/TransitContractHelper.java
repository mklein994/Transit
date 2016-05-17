package com.example.matthew.transit;

import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by matthew on 17/05/16.
 */
public class TransitContractHelper {
    public static final String QUERY_PARAMETER_DISTINCT = "distinct";

    public static boolean isQueryDistinct(Uri uri) {
        return !TextUtils.isEmpty(uri.getQueryParameter(QUERY_PARAMETER_DISTINCT));
    }
}
