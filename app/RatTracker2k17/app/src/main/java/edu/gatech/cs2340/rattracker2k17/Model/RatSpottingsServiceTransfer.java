package edu.gatech.cs2340.rattracker2k17.Model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.gatech.cs2340.rattracker2k17.Service.Utility;

/**
 * Service transfer handler
 * @author Justin Z
 * @version 1.0
 */
public class RatSpottingsServiceTransfer implements Serializable {

    private static final String LOG_ID = "RatSpottingsServiceTran";

    /**
     * Constructor - initialize with default values
     */
    public RatSpottingsServiceTransfer() {
        ratSpottings = new ArrayList<>();
        callDate = Calendar.getInstance();

        try {
            Log.d(LOG_ID, "Created at... " + Utility.getDateString(callDate));
        } catch (NoSuchFieldException ex) {
            Log.d(LOG_ID, "Fields were missing from the calendar object");
        }
    }

    /**
     * Constructor - with list of rats
     * @param rats list of rats
     */
    public RatSpottingsServiceTransfer(List<RatSpotting> rats) {
        ratSpottings = rats;
        callDate = Calendar.getInstance();
        Log.d(LOG_ID, "Created at... " + callDate.toString());
    }

    private final List<RatSpotting> ratSpottings;

    /**
     * Get rat spottings
     * @return the list of rat spottings
     */
    public List<RatSpotting> getRatSpottings() {
        Log.d(LOG_ID, "RatSpottings have been requested.");
        return ratSpottings;
    }

    private final Calendar callDate;
}
