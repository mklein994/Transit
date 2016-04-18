package com.example.matthew.transit;

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

import java.io.IOException;

import io.realm.Realm;

/**
 * Created by matthew on 16/04/16.
 */

public class ModelManager {

    public static void importAgencies(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            realm.copyToRealmOrUpdate(new Agency(nextLine));
        }

    }

    public static void importCalendars(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            realm.copyToRealmOrUpdate(new Calendar(nextLine));
        }

    }

    public static void importCalendarDates(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            realm.copyToRealmOrUpdate(new CalendarDate(nextLine));
        }

    }

    public static void importFareAttributes(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            realm.copyToRealmOrUpdate(new FareAttribute(nextLine));
        }

    }

    public static void importRoutes(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            realm.copyToRealmOrUpdate(new Route(nextLine));
        }

    }

    public static void importShapes(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            realm.copyToRealmOrUpdate(new Shape(nextLine));
        }

    }

    public static void importStops(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            realm.copyToRealmOrUpdate(new Stop(nextLine));
        }

    }

    public static void importFareRules(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            realm.copyToRealmOrUpdate(new FareRule(nextLine));
        }
    }

    public static void importTrips(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            realm.copyToRealmOrUpdate(new Trip(nextLine));
        }
    }

    public static void importStopTimes(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            realm.copyToRealmOrUpdate(new StopTime(nextLine));
        }
    }
}
