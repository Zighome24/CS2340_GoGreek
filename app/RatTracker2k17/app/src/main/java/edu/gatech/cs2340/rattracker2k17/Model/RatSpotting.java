package edu.gatech.cs2340.rattracker2k17.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.gatech.cs2340.rattracker2k17.Service.RatSpottingBL;

/**
 * Created by Chris on 10/9/2017.
 */

public class RatSpotting implements Serializable {

    //Static Properties
    private static int nextKey = -1;
    public static void generateNextKey() {
        RatSpottingBL.getCurrentKey();
    }
    public static int getNextKey() {
        return nextKey;
    }
    public static void setNextKey(int nextKey) {
        RatSpotting.nextKey = nextKey;
    }

    //Properties
    private String key;
    public void setKey(String key) { this.key = key;}
    public String getKey() { return key;}

    private String date;
    public void setDate(String date) { this.date = date;}
    public String getDate() { return date;}

    private String locationType;
    public void setLocationType(String locationType) { this.locationType = locationType;}
    public String getLocationType() { return locationType;}

    private int zip;
    public void setZip(int zip) { this.zip = zip;}
    public int getZip() { return zip;}

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
     * @param date the date of the rat spotting
     * @param locationType the type of location where the rat was spotted
     * @param zip the zipcode of the rat spotting
     * @param address the address where the rat was spotted
     * @param city the city where the rat was spotted
     * @param borough the borough of new york where the rat was spotted
     * @param lat the latitude where the rat spotting occurred
     * @param lon the longitude where the rat spotting occurred
     */
    public RatSpotting(String key, String date, String locationType, int zip,
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
     * toMap() - maps the properties of a rat spotting to a {String, Object} mapping for use in
     * the Firebase database.
     * @return the map of properties for a ratSpotting instance
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("date", date);
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
     * toString - the toString override for a ratSpotting instance
     * @return the string representation of a ratSpotting instance
     */
    @Override
    public String toString() {
        return date + " " + locationType + " " + borough;
    }
}
