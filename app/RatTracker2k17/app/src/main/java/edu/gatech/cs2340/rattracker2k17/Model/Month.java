package edu.gatech.cs2340.rattracker2k17.Model;

import java.util.List;

/**
 * Created by Justin on 11/7/2017.
 */

public class Month {

    private String month;
    public String getMonth() { return month; }

    private String year;
    public String getYear() { return year; }

    private int ratspottings;
    public int getRatspottings() { return ratspottings; }

    private List<RatSpotting> ratSpottings;
    public List<RatSpotting> getRatSpottings() { return ratSpottings; }


    public Month(String month, String year, int rats) {
        this.month = month;
        this.year = year;
        ratspottings = rats;
    }

    public Month(String month, String year, int rats, List<RatSpotting> ratSpottings) {
        this(month, year, rats);
        this.ratSpottings = ratSpottings;
    }
}
