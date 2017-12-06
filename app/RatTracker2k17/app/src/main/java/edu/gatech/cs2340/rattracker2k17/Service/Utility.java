package edu.gatech.cs2340.rattracker2k17.Service;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

import edu.gatech.cs2340.rattracker2k17.Data.Types;
import edu.gatech.cs2340.rattracker2k17.Model.LogReport;
import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.Model.User;

/**
 * Utility functions
 * @author Justin Z
 * @version 1.0
 */

public class Utility {

    private static final String LOG_ID = "Utility";
    /**
     * isNullOrWhitespace - checks to see if the string is null or whitespace
     * @param s the string we are analyzing
     * @return true if the string is empty, contains only whitespace, or is null
     *         otherwise returns false
     */
    public static boolean isNullOrWhitespace(CharSequence s) {
        if (s == null)
            return true;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Parse string
     * @param s the date
     * @return Calendar type for date
     */
    public static Calendar parseStringDate(String s) {
        try {
            Calendar calendar = Calendar.getInstance();
            String[] parts = s.split(" ");
            String[] d = parts[0].split("/");
            int month = Integer.parseInt(d[0]);
            int day = Integer.parseInt(d[1]);
            int year = Integer.parseInt(d[2]);


            if (!(parts.length < 2)) {
                int hour, minute;
                String[] time = parts[1].split(":");
                minute = Integer.parseInt(time[1]);
                hour = Integer.parseInt(time[0]);
                if (parts.length > 2) {
                    if (parts[2].equalsIgnoreCase("AM")) {
                        if (Integer.parseInt(time[0]) == 12) {
                            hour = 0;
                        }
                    } else {
                        hour = (hour != 12) ? hour + 12 : hour;
                    }
                }
                calendar.set(year, month - 1, day, hour, minute);
            } else {
                calendar.set(year, month - 1, day);
            }
            Log.d(LOG_ID, "Successfully set the date to: " + calendar.toString());
            return calendar;
        } catch (NullPointerException ex) {
            Log.d(LOG_ID, "There has been an error while establishing the date: "
                    + ex.getMessage());
            Log.d(LOG_ID, "Returning the date as today's date");
            return Calendar.getInstance();
        }
    }

    /**
     * getRatSpottingFromSnapshot - parses a data input into a RatSpotting instance.
     * @param data The data returned from the Firebase Query
     * @return a RatSpotting instance created from the parsed data
     */
    public static RatSpotting getRatSpottingFromSnapshot(DataSnapshot data) {
        Calendar calendar = Calendar.getInstance();
        Long date = (Long) data.child("date").getValue();
        if (date != null) {
            calendar.setTimeInMillis(date);
        }
        return new RatSpotting(
                data.getKey(),
                calendar,
                (String) data.child("locationType").getValue(),
                Long.parseLong(data.child("zip").getValue() != null
                        ? data.child("zip").getValue().toString() : "10000"),
                (String) data.child("address").getValue(),
                (String) data.child("city").getValue(),
                (String) data.child("borough").getValue(),
                Double.parseDouble(data.child("latitude")
                        .getValue() != null ?
                        data.child("latitude").getValue().toString() : "0.0"),
                Double.parseDouble(data.child("longitude")
                        .getValue() != null ?
                        data.child("longitude").getValue().toString() : "0.0")
        );
    }

    /**
     * getUserFromSnapshot - parses a data input into a User instance.
     * @param data The data returned from the Firebase Query
     * @return a User instance created from the parsed data
     */
    public static User getUserFromSnapshot(DataSnapshot data) {
        return new User(
                (String) data.child("firstName").getValue(),
                (String) data.child("lastName").getValue(),
                (String) data.child("email").getValue(),
                Types.SecurityLevel.parseSecurityLevel((long) data.child("securityLevel").getValue()),
                (String) data.child("userID").getValue(),
                (String) data.child("password").getValue()
        );
    }

    /**
     * Turn Calendar to String
     * @param date the date
     * @return String representation
     * @throws NoSuchFieldException
     */
    public static String getDateString(Calendar date) throws NoSuchFieldException {

        // calender rolls over date, so being safe
        if (date ==null || !date.isSet(Calendar.MONTH) || !date.isSet(Calendar.DATE)
                || !date.isSet(Calendar.YEAR) || !date.isSet(Calendar.HOUR_OF_DAY)
                || !date.isSet(Calendar.MINUTE)) {

            throw new NoSuchFieldException("Month, date, year, hour, and minute must be non-null");
        }

        String time =
                (Integer.toString(date.get(Calendar.HOUR_OF_DAY)).length() == 1 ?
                        "0" + Integer.toString(date.get(Calendar.HOUR_OF_DAY)) :
                        Integer.toString(date.get(Calendar.HOUR_OF_DAY)))
                        + ":" + (Integer.toString(date.get(Calendar.MINUTE)).length() == 1 ?
                        "0" + Integer.toString(date.get(Calendar.MINUTE)) :
                        Integer.toString(date.get(Calendar.MINUTE)));
        return String.format(Locale.getDefault(), "%d/%d/%d %s",
                date.get(Calendar.MONTH),
                date.get(Calendar.DATE),
                date.get(Calendar.YEAR),
                time);
    }

    public static void submitLoggingReport(LogReport logReport) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child(logReport.getLocation()).push().updateChildren(logReport.toMap());
    }
}
