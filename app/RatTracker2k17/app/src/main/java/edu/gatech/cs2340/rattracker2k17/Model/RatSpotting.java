package edu.gatech.cs2340.rattracker2k17.Model;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import edu.gatech.cs2340.rattracker2k17.Service.RatSpottingBL;
import edu.gatech.cs2340.rattracker2k17.Service.Utility;

/**
 * Created by Chris on 10/9/2017.
 */

public class RatSpotting implements Serializable {

    //Static Properties
    private static long nextKey = -1;
    public static void generateNextKey() {
        RatSpottingBL.getCurrentKey();
    }
    public static long getNextKey() {
        nextKey++;
        return nextKey - 1;
    }
    public static long seeNextKey() {
        return nextKey;
    }
    public static void setNextKey(long nextKey) {
        RatSpotting.nextKey = nextKey;
    }

    private static final String LOG_ID = "RatSpotting";

    //Properties
    private String key;
    public void setKey(String key) { this.key = key;}
    public String getKey() { return key;}

    private Calendar date;
    public void setDate(long millidate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millidate);
        date = calendar;
    }
    public Calendar getDate() { return date;}

    private String locationType;
    public void setLocationType(String locationType) { this.locationType = locationType;}
    public String getLocationType() { return locationType;}

    private long zip;
    public void setZip(long zip) { this.zip = zip;}
    public long getZip() { return zip;}

    private String address;
    public void setAddress(String address) { this.address = address;}
    public String getAddress() { return address;}

    private String city;
    public void setCity(String city) { this.city = city;}
    public String getCity() { return city;}

    private String borough;
    public void setBorough(String borough) { this.borough = borough;}
    public String getBorough() {return borough;}

    private double lat;
    public void setLat(double lat) { this.lat = lat;}
    public double getLat() { return lat;}

    private double lon;
    public void setLong(double lon) { this.lon = lon;}
    public double getLong() { return lon;}

    /**
     * Constructor - creates a new RatSpotting instance with the given params
     * @param key the unique key for the
     * @param date the date of the rat spotting in a parsable string
     * @param locationType the type of location where the rat was spotted
     * @param zip the zipcode of the rat spotting
     * @param address the address where the rat was spotted
     * @param city the city where the rat was spotted
     * @param borough the borough of new york where the rat was spotted
     * @param lat the latitude where the rat spotting occurred
     * @param lon the longitude where the rat spotting occurred
     */
    public RatSpotting(String key, String date, String locationType, long zip,
                       String address, String city, String borough, double lat, double lon) {
        this.key = key;
        this.date = Utility.parseStringDate(date);
        this.locationType = locationType;
        this.zip = zip;
        this.address = address;
        this.city = city;
        this.borough = borough;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Constructor - creates a new RatSpotting instance with the given params
     * @param key the unique key for the
     * @param date the date of the rat spotting in the form of a Calendar Object
     * @param locationType the type of location where the rat was spotted
     * @param zip the zipcode of the rat spotting
     * @param address the address where the rat was spotted
     * @param city the city where the rat was spotted
     * @param borough the borough of new york where the rat was spotted
     * @param lat the latitude where the rat spotting occurred
     * @param lon the longitude where the rat spotting occurred
     */
    public RatSpotting(String key, Calendar date, String locationType, long zip,
                       String address, String city, String borough, double lat, double lon) {
        this.key = key;
        this.date = date;
        this.locationType = locationType;
        this.zip = zip;
        this.address = address;
        this.city = city;
        this.borough = borough;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Constructor - creates a new RatSpotting instance with the given params
     * @param key the unique key for the
     * @param date the date of the rat spotting in Milliseconds
     * @param locationType the type of location where the rat was spotted
     * @param zip the zipcode of the rat spotting
     * @param address the address where the rat was spotted
     * @param city the city where the rat was spotted
     * @param borough the borough of new york where the rat was spotted
     * @param lat the latitude where the rat spotting occurred
     * @param lon the longitude where the rat spotting occurred
     */
    public RatSpotting(String key, Long date, String locationType, long zip,
                       String address, String city, String borough, double lat, double lon) {
        this.key = key;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        this.date = cal;
        this.locationType = locationType;
        this.zip = zip;
        this.address = address;
        this.city = city;
        this.borough = borough;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * toMap() - maps the properties of a rat spotting to a {String, Object} mapping for use in
     * the Firebase database.
     * @return the map of properties for a ratSpotting instance
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("date", date.getTimeInMillis());
        map.put("locationType", locationType);
        map.put("zip", zip);
        map.put("address", address);
        map.put("city", city);
        map.put("borough", borough);
        map.put("latitude", lat);
        map.put("longitude", lon);
        return map;
    }

    public String getDateString() {
        String time =
                (Integer.toString(date.get(Calendar.HOUR_OF_DAY)).length() == 1 ?
                        "0" + Integer.toString(date.get(Calendar.HOUR_OF_DAY)) :
                        Integer.toString(date.get(Calendar.HOUR_OF_DAY)))
                        + ":" + (Integer.toString(date.get(Calendar.MINUTE)).length() == 1 ?
                        "0" + Integer.toString(date.get(Calendar.MINUTE)) :
                        Integer.toString(date.get(Calendar.MINUTE)));
        return String.format(Locale.getDefault(), "%d/%d/%d %s",
                (1 + date.get(Calendar.MONTH)),
                date.get(Calendar.DATE),
                date.get(Calendar.YEAR),
                time);
    }

    /**
     * toString - the toString override for a ratSpotting instance
     * @return the string representation of a ratSpotting instance
     */
    @Override
    public String toString() {
        return getDateString() + " : " + locationType + " : " + borough + " : Lat= "
                + lat + " : Long= " + lon;
    }
}
