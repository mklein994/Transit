package com.example.matthew.transit.model;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by matthew on 20/04/16.
 */
public class RelationshipManager {
    private static final String TAG = RelationshipManager.class.getName();

    public static void setOneToManyRelationships(Realm realm) {

        // set calendar and calendar date in parallel
        RealmResults<Calendar> calendars = realm.allObjects(Calendar.class);
        Log.d(TAG, "setOneToManyRelationships: calendars: " + calendars.size());

        for (int i = 0; i < calendars.size(); i++) {
            Calendar calendar = calendars.get(i);
            //CalendarDate calendarDate = calendarDates.get(i);
            RealmResults<Trip> matchingTrips = realm.where(Trip.class)
                    .equalTo("serviceId", calendar.getServiceId())
                    .findAll();
            calendar.setTrips((RealmList<Trip>) matchingTrips.subList(0, matchingTrips.size()));
            //calendarDate.setTrips(calendar.getTrips());
            RealmResults<CalendarDate> calendarDates = realm.where(CalendarDate.class)
                    .equalTo("serviceId", calendar.getServiceId())
                    .findAll();
            calendar.setCalendarDates((RealmList<CalendarDate>) calendarDates.subList(0, calendarDates.size()));
            realm.copyToRealmOrUpdate(calendar);
            //realm.copyToRealmOrUpdate(calendarDate);
        }
        Log.d(TAG, "setOneToManyRelationships: calendars updated");

        //RealmResults<CalendarDate> calendarDates = realm.allObjects(CalendarDate.class);


        for (Shape shape : realm.allObjects(Shape.class)) {
            // trips
            RealmResults<Trip> trips = realm.where(Trip.class)
                    .equalTo("shapeId", shape.getShapeId())
                    .findAll();
            shape.setTrips((RealmList<Trip>) trips.subList(0, trips.size()));
        }
        Log.d(TAG, "setOneToManyRelationships: shapes updated");

        for (Stop stop : realm.allObjects(Stop.class)) {
            // trips
            RealmResults<StopTime> stopTimes = realm.where(StopTime.class)
                    .equalTo("stopId", stop.getStopId())
                    .findAll();
            stop.setStopTimes((RealmList<StopTime>) stopTimes.subList(0, stopTimes.size()));
        }
        Log.d(TAG, "setOneToManyRelationships: stops updated");

        for (FareAttribute fareAttribute : realm.allObjects(FareAttribute.class)) {
            // fare rules
            RealmResults<FareRule> fareRules = realm.where(FareRule.class)
                    .equalTo("fareId", fareAttribute.getFareId())
                    .findAll();
            fareAttribute.setFareRules((RealmList<FareRule>) fareRules.subList(0, fareRules.size()));
        }
        Log.d(TAG, "setOneToManyRelationships: fare attributes updated");

        for (Route route : realm.allObjects(Route.class)) {
            // fare rules
            RealmResults<FareRule> fareRules = realm.where(FareRule.class)
                    .equalTo("routeId", route.getRouteId())
                    .findAll();
            route.setFareRules((RealmList<FareRule>) fareRules.subList(9, fareRules.size()));
            // trips
            RealmResults<Trip> trips = realm.where(Trip.class)
                    .equalTo("routeId", route.getRouteId())
                    .findAll();
            route.setTrips((RealmList<Trip>) trips.subList(9, trips.size()));
        }
        Log.d(TAG, "setOneToManyRelationships: routes updated");

        for (Trip trip : realm.allObjects(Trip.class)) {
            RealmResults<StopTime> stopTimes = realm.where(StopTime.class)
                    .equalTo("tripId", trip.getTripId())
                    .findAll();
            trip.setStopTimes((RealmList<StopTime>) stopTimes.subList(0, stopTimes.size()));
        }
        Log.d(TAG, "setOneToManyRelationships: trips updated");
    }
}
