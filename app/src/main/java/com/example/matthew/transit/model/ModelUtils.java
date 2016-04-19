package com.example.matthew.transit.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by matthew on 14/04/16.
 */
class ModelUtils {
    public static Date parseDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CANADA);
        Date parsedDate = null;
        try {
            parsedDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }

    public static Integer parseInt(String integer) {
        Integer parsedInteger;
        if (integer.isEmpty()) {
            parsedInteger = null;
        } else {
            try {
                parsedInteger = Integer.parseInt(integer);
            } catch (NumberFormatException e) {
                parsedInteger = null;
            }
        }
        return parsedInteger;
    }

    public static Date parseTime(String timeToBeParsed) {
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss", Locale.CANADA);
        Date parsedTime = null;
        try {
            parsedTime = simpleTimeFormat.parse(timeToBeParsed);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedTime;
    }

    public static boolean parseBoolean(String booleanToBeParsed) throws IllegalArgumentException {
        int booleanAsInt = parseInt(booleanToBeParsed);
        if (booleanAsInt == 0) {
            return false;
        } else if (booleanAsInt == 1) {
            return true;
        } else {
            throw new IllegalArgumentException("boolean to be parsed should be a 1 or 0.");
        }
    }
}
