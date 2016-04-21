package com.example.matthew.transit;

import com.example.matthew.transit.model.Agency;
import com.example.matthew.transit.model.Calendar;
import com.example.matthew.transit.model.CalendarDate;
import com.example.matthew.transit.model.FareAttribute;
import com.example.matthew.transit.model.FareRule;
import com.example.matthew.transit.model.ModelUtils;
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

    private static final String TAG = ModelManager.class.getName();

    public static void importAgencies(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        Agency agency;
        while ((nextLine = reader.readNext()) != null) {
            // not primary key, so can't update
            agency = realm.createObject(Agency.class);
            agency.setAgencyName(nextLine[Agency.AGENCY_NAME]);
            agency.setAgencyUrl(nextLine[Agency.AGENCY_URL]);
            agency.setAgencyTimezone(nextLine[Agency.AGENCY_TIMEZONE]);
            agency.setAgencyLang(nextLine[Agency.AGENCY_LANG]);
            agency.setAgencyPhone(nextLine[Agency.AGENCY_PHONE]);
            //realm.copyToRealm(new Agency(nextLine));
        }

    }

    public static void importCalendars(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        Calendar calendar;
        while ((nextLine = reader.readNext()) != null) {
            calendar = realm.createObject(Calendar.class);
            calendar.setServiceId(nextLine[Calendar.SERVICE_ID]);
            calendar.setMonday(ModelUtils.parseBoolean(nextLine[Calendar.MONDAY]));
            calendar.setTuesday(ModelUtils.parseBoolean(nextLine[Calendar.TUESDAY]));
            calendar.setWednesday(ModelUtils.parseBoolean(nextLine[Calendar.WEDNESDAY]));
            calendar.setThursday(ModelUtils.parseBoolean(nextLine[Calendar.THURSDAY]));
            calendar.setFriday(ModelUtils.parseBoolean(nextLine[Calendar.FRIDAY]));
            calendar.setSaturday(ModelUtils.parseBoolean(nextLine[Calendar.SATURDAY]));
            calendar.setSunday(ModelUtils.parseBoolean(nextLine[Calendar.SUNDAY]));
            calendar.setStartDate(ModelUtils.parseDate(nextLine[Calendar.START_DATE]));
            calendar.setEndDate(ModelUtils.parseDate(nextLine[Calendar.END_DATE]));
            //realm.copyToRealmOrUpdate(new Calendar(nextLine));
        }

    }

    public static void importCalendarDates(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        CalendarDate calendarDate;
        while ((nextLine = reader.readNext()) != null) {
            calendarDate = realm.createObject(CalendarDate.class);
            calendarDate.setServiceId(nextLine[CalendarDate.SERVICE_ID]);
            calendarDate.setDate(ModelUtils.parseDate(nextLine[CalendarDate.DATE]));
            calendarDate.setExceptionType(ModelUtils.parseInt(nextLine[CalendarDate.EXCEPTION_TYPE]));
            calendarDate.setCalendarPK(calendarDate.getServiceId(), calendarDate.getDate());
            calendarDate.setCalendar(realm.where(Calendar.class)
                    .equalTo("serviceId", calendarDate.getServiceId())
                    .findFirst());
            //realm.copyToRealmOrUpdate(new CalendarDate(nextLine));
        }

    }

    public static void importFareAttributes(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        FareAttribute fareAttribute;
        while ((nextLine = reader.readNext()) != null) {
            fareAttribute = realm.createObject(FareAttribute.class);
            fareAttribute.setFareId(nextLine[FareAttribute.FARE_ID]);
            fareAttribute.setPrice(Double.parseDouble(nextLine[FareAttribute.PRICE]));
            fareAttribute.setCurrencyType(nextLine[FareAttribute.CURRENCY_TYPE]);
            fareAttribute.setPaymentMethod(ModelUtils.parseBoolean(nextLine[FareAttribute.PAYMENT_METHOD]));
            fareAttribute.setTransfers(ModelUtils.parseInt(nextLine[FareAttribute.TRANSFERS]));
            fareAttribute.setTransferDuration(ModelUtils.parseInt(nextLine[FareAttribute.TRANSFER_DURATION]));
            //realm.copyToRealmOrUpdate(new FareAttribute(nextLine));
        }

    }

    public static void importRoutes(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        Route route;
        while ((nextLine = reader.readNext()) != null) {
            route = realm.createObject(Route.class);
            route.setRouteId(nextLine[Route.ROUTE_ID]);
            route.setRouteShortName(nextLine[Route.ROUTE_SHORT_NAME]);
            route.setRouteLongName(nextLine[Route.ROUTE_LONG_NAME]);
            route.setRouteUrl(nextLine[Route.ROUTE_URL]);
            route.setRouteType(ModelUtils.parseInt((nextLine[Route.ROUTE_TYPE])));
            route.setRouteColor(nextLine[Route.ROUTE_COLOR]);
            route.setRouteTextColor(nextLine[Route.ROUTE_TEXT_COLOR]);
            //realm.copyToRealmOrUpdate(new Route(nextLine));
        }

    }

    public static void importShapes(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        Shape shape;
        while ((nextLine = reader.readNext()) != null) {
            shape = realm.createObject(Shape.class);
            shape.setShapeId(nextLine[Shape.SHAPE_ID]);
            shape.setShapePtLat(Double.parseDouble(nextLine[Shape.SHAPE_PT_LAT]));
            shape.setShapePtLon(Double.parseDouble(nextLine[Shape.SHAPE_PT_LON]));
            shape.setShapePtSequence(ModelUtils.parseInt(nextLine[Shape.SHAPE_PT_SEQUENCE]));
            shape.setShapePK(shape.getShapeId(), shape.getShapePtSequence());
        }

    }

    public static void importStops(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        Stop stop;
        while ((nextLine = reader.readNext()) != null) {
            stop = realm.createObject(Stop.class);
            stop.setStopId(nextLine[Stop.STOP_ID]);
            stop.setStopCode(nextLine[Stop.STOP_CODE]);
            stop.setStopName(nextLine[Stop.STOP_NAME]);
            stop.setStopLat(Double.parseDouble(nextLine[Stop.STOP_LAT]));
            stop.setStopLon(Double.parseDouble(nextLine[Stop.STOP_LON]));
            stop.setStopUrl(nextLine[Stop.STOP_URL]);
            //realm.copyToRealmOrUpdate(new Stop(nextLine));
        }

    }

    public static void importFareRules(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        FareRule fareRule;
        while ((nextLine = reader.readNext()) != null) {
            //FareRule fareRule = new FareRule(nextLine);
            fareRule = realm.createObject(FareRule.class);
            fareRule.setFareId(nextLine[FareRule.FARE_ID]);
            fareRule.setRouteId(nextLine[FareRule.ROUTE_ID]);
            fareRule.setFareRoutePK(fareRule.getFareId(), fareRule.getRouteId());
            // route
            fareRule.setRoute(realm.where(Route.class)
                    .equalTo("routeId", fareRule.getRouteId())
                    .findFirst());
            // fare attribute
            fareRule.setFareAttribute(realm.where(FareAttribute.class)
                    .equalTo("fareId", fareRule.getFareId())
                    .findFirst());
            //realm.copyToRealmOrUpdate(fareRule);
        }
    }

    public static void importTrips(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        Trip trip;
        while ((nextLine = reader.readNext()) != null) {
            //Trip trip = new Trip(nextLine);
            trip = realm.createObject(Trip.class);
            trip.setRouteId(nextLine[Trip.ROUTE_ID]);
            trip.setServiceId(nextLine[Trip.SERVICE_ID]);
            trip.setTripId(nextLine[Trip.TRIP_ID]);
            trip.setTripHeadsign(nextLine[Trip.TRIP_HEADSIGN]);
            trip.setDirectionId(ModelUtils.parseBoolean(nextLine[Trip.DIRECTION_ID]));
            trip.setBlockId(nextLine[Trip.BLOCK_ID]);
            trip.setShapeId(nextLine[Trip.SHAPE_ID]);
            trip.setWheelchairAccessible(ModelUtils.parseInt(nextLine[Trip.WHEELCHAIR_ACCESSIBLE]));
            // shape
            trip.setShape(realm.where(Shape.class)
                    .equalTo("shapeId", trip.getShapeId())
                    .findFirst());
            // calendar
            trip.setCalendar(realm.where(Calendar.class)
                    .equalTo("serviceId", trip.getServiceId())
                    .findFirst());
            // calendar date
            trip.setCalendarDate(realm.where(CalendarDate.class)
                    .equalTo("serviceId", trip.getServiceId())
                    .findFirst());
            // route
            trip.setRoute(realm.where(Route.class)
                    .equalTo("routeId", trip.getRouteId())
                    .findFirst());
            //realm.copyToRealmOrUpdate(trip);
        }
    }

    public static void importStopTimes(Realm realm, CSVReader reader) throws IOException {
        String[] nextLine;

        StopTime stopTime;
        while ((nextLine = reader.readNext()) != null) {
            //stopTime = new StopTime(nextLine);
            stopTime = realm.createObject(StopTime.class);
            stopTime.setTripId(nextLine[StopTime.TRIP_ID]);
            stopTime.setArrivalTime(nextLine[StopTime.ARRIVAL_TIME]);
            stopTime.setDepartureTime(nextLine[StopTime.DEPARTURE_TIME]);
            stopTime.setStopId(nextLine[StopTime.STOP_ID]);
            stopTime.setStopSequence(ModelUtils.parseInt(nextLine[StopTime.STOP_SEQUENCE]));
            stopTime.setStopTimePK(stopTime.getTripId(), stopTime.getStopSequence());
            // stop
            stopTime.setStop(realm.where(Stop.class)
                    .equalTo("stopId", stopTime.getStopId())
                    .findFirst());
            // trip
            stopTime.setTrip(realm.where(Trip.class)
                    .equalTo("tripId", stopTime.getTripId())
                    .findFirst());
            //realm.copyToRealmOrUpdate(stopTime);
        }
    }
}
