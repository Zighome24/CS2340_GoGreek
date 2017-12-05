package edu.gatech.cs2340.rattracker2k17;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.junit.Assert.*;

import edu.gatech.cs2340.rattracker2k17.Controller.NewRatSpottingController;

/**
 * Created by amandabillings on 11/13/17.
 */

public class RatSpottingTest {
    private CharSequence date;
    private CharSequence location;
    private CharSequence address;
    private CharSequence city;
    private CharSequence zip;
    private CharSequence borough;
    private CharSequence latitude;
    private CharSequence longitude;
    private CharSequence nullDate;
    private CharSequence nullLocation;
    private CharSequence nullAddress;
    private CharSequence nullCity;
    private CharSequence nullZip;
    private CharSequence nullBorough;
    private CharSequence nullLatitude;
    private CharSequence nullLongitude;
    private static final int TIMEOUT = 2000;
    private NewRatSpottingController spotting;

    @Before
    public void setUp() {

        date = "11/13/2017";
        location = "Street Sidewalk";
        address = "15 Rat Lane";
        city = "Atlanta";
        zip = "30313";
        borough = "Test Borough";
        latitude = "32 23 43";
        longitude = "54 22 05";
        nullDate = null;
        nullLocation = null;
        nullAddress = null;
        nullCity = null;
        nullZip = null;
        nullBorough = null;
        nullLatitude = null;
        nullLongitude = null;
        spotting = new NewRatSpottingController();
    }

    @Test(timeout = TIMEOUT)
    public void checkForSingleNull() {
        try {

            Method method = NewRatSpottingController.class.getDeclaredMethod(
                    "Single null in ratSpotting", boolean.class);
            method.setAccessible(true);

            boolean dateNull = (boolean) method.invoke(
                    spotting, nullDate, location, address, city, zip, borough, latitude, longitude);
            boolean locationNull = (boolean) method.invoke(
                    spotting, date, nullLocation, address, city, zip, borough, latitude, longitude);
            boolean addressNull = (boolean) method.invoke(
                    spotting, date, location, nullAddress, city, zip, borough, latitude, longitude);
            boolean cityNull = (boolean) method.invoke(
                    spotting, date, location, address, nullCity, zip, borough, latitude, longitude);
            boolean zipNull = (boolean) method.invoke(
                    spotting, date, location, address, city, nullZip, borough, latitude, longitude);
            boolean boroughNull = (boolean) method.invoke(
                    spotting, date, location, address, city, zip, nullBorough, latitude, longitude);
            boolean latitudeNull = (boolean) method.invoke(
                    spotting, date, location, address, city, zip, borough, nullLatitude, longitude);
            boolean longitudeNull = (boolean) method.invoke(
                    spotting, date, location, address, city, zip, borough, latitude, nullLongitude);
            boolean noNull = (boolean) method.invoke(
                    spotting, date, location, address, city, zip, borough, latitude, longitude);

            assertFalse(dateNull);
            assertFalse(locationNull);
            assertFalse(addressNull);
            assertFalse(cityNull);
            assertFalse(zipNull);
            assertFalse(boroughNull);
            assertFalse(latitudeNull);
            assertFalse(longitudeNull);
            assertTrue(noNull);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }


    }

}
