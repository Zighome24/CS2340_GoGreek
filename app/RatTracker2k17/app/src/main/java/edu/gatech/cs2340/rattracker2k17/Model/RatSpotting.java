package edu.gatech.cs2340.rattracker2k17.Model;

import java.io.Serializable;

/**
 * Created by Chris on 10/9/2017.
 */

public class RatSpotting implements Serializable {

    private int key;
    public void setKey(int key) { this.key = key;}
    public int getKey() { return key;}

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

    public RatSpotting(int key, String date, String locationType, int zip,
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

    public RatSpotting() {}

    @Override
    public String toString() {
        return date + " " + locationType + " " + borough;
     }
}
