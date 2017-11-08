package edu.gatech.cs2340.rattracker2k17.Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import edu.gatech.cs2340.rattracker2k17.Service.RatSpottingBL;
import edu.gatech.cs2340.rattracker2k17.Service.Utility;

/** Rat spotting model
 * @author Justin Z
 * @version 1.0
 */
public class RatSpotting implements Serializable {

    //Static Properties
    // TODO: 10/31/2017 Need to convert the currentNextKey thing into a transaction with Firebase
    private static long nextKey = -1;

    /**
     * Get the key for indexing
     */
    public static void generateNextKey() {
        RatSpottingBL.getCurrentKey();
    }

    /**
     * Calc next key for indexing
     * @return the key
     */
    public static long getNextKey() {
        nextKey++;
        RatSpottingBL.pushCurrentKey();
        return nextKey - 1;
    }

    /**
     * Peak key
     * @return the key
     */
    public static long seeNextKey() {
        return nextKey;
    }

    /**
     * Set key
     * @param nextKey new key
     */
    public static void setNextKey(long nextKey) {
        RatSpotting.nextKey = nextKey;
    }

    private static final String LOG_ID = "RatSpotting";

    //Properties
    private String key;

    /**
     * Set key
     * @param key to be set
     */
    public void setKey(String key) { this.key = key;}

    /**
     * Key getter
     * @return the key
     */
    public String getKey() { return key;}

    private Calendar date;

    /**
     * Set date
     * @param millidate date in ms since epoch
     */
    public void setDate(long millidate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millidate);
        date = calendar;
    }

    /**
     * date getter
     * @return the date
     */
    public Calendar getDate() { return date;}

    private String locationType;

    /**
     * Set location type
     * @param locationType type
     */
    public void setLocationType(String locationType) { this.locationType = locationType;}

    /**
     * Get location type
     * @return the location type
     */
    public CharSequence getLocationType() { return locationType;}

    private long zip;

    /**
     * Set zip
     * @param zip new zip
     */
    public void setZip(long zip) { this.zip = zip;}

    /**
     * Get zip
     * @return the zip
     */
    public long getZip() { return zip;}

    private String address;

    /**
     * Set address
     * @param address the address
     */
    public void setAddress(String address) { this.address = address;}

    /**
     * Get address
     * @return the address
     */
    public CharSequence getAddress() { return address;}

    private String city;

    /**
     * Set city
     * @param city to be set
     */
    public void setCity(String city) { this.city = city;}

    /**
     * Get city
     * @return city
     */
    public CharSequence getCity() { return city;}

    private String borough;

    /**
     * Set borough
     * @param borough the borough
     */
    public void setBorough(String borough) { this.borough = borough;}

    /**
     * Get borough
     * @return the borough
     */
    public String getBorough() {return borough;}

    private double lat;

    /**
     * Set lat
     * @param lat the coordiante
     */
    public void setLat(double lat) { this.lat = lat;}

    /**
     * Get lat
     * @return the lat
     */
    public double getLat() { return lat;}

    private double lon;

    /**
     * Set long
     * @param lon the coordinate
     */
    public void setLong(double lon) { this.lon = lon;}

    /**
     * Get long
     * @return the long
     */
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

    /**
     * String getter
     * @return Date as string
     */
    public CharSequence getDateString() {
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
        return key + " : " + getDateString() + " : " + locationType + " : " + borough + " : Lat= "
                + lat + " : Long= " + lon;
    }
}
