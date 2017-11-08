package edu.gatech.cs2340.rattracker2k17.Model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Justin on 10/27/2017.
 */

public class RatSpottingsServiceTransfer implements Serializable {

    private static final String LOG_ID = "RatSpottingsServiceTran";

    public RatSpottingsServiceTransfer() {
        ratSpottings = new ArrayList<>();
        callDate = Calendar.getInstance();
        Log.d(LOG_ID, "Created at... " + callDate.toString());
    }

    public RatSpottingsServiceTransfer(List<RatSpotting> rats) {
        ratSpottings = rats;
        callDate = Calendar.getInstance();
        Log.d(LOG_ID, "Created at... " + callDate.toString());
    }

    private final List<RatSpotting> ratSpottings;
    public List<RatSpotting> getRatSpottings() {
        Log.d(LOG_ID, "RatSpottings have been requested.");
        return ratSpottings;
    }

    private final Calendar callDate;
}
