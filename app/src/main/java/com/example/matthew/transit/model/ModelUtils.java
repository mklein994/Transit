package com.example.matthew.transit.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by matthew on 14/04/16.
 */
class ModelUtils {
    public static Date parseDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
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

    public static Byte parseByte(String byteToBeParsed) {
        Byte parsedByte;

        if (byteToBeParsed.isEmpty()) {
            parsedByte = null;
        } else {
            try {
                parsedByte = Byte.parseByte(byteToBeParsed);
            } catch (NumberFormatException e) {
                parsedByte = null;
            }
        }

        return parsedByte;


    }
}
