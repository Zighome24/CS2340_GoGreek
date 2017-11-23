package edu.gatech.cs2340.rattracker2k17.Service;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;

/**
 * Logic of Rat spotting
 * @author Justin Z
 * @version 1.0
 */
@SuppressWarnings("FeatureEnvy")
public class RatSpottingBL {

    private static final String LOG_ID = "RatSpottingBL";

    private final DatabaseReference mDatabase;

    /**
     * Constructor - instantiates the mDatabase value to the ratspotting portion of the database
     */
    public RatSpottingBL() {
        mDatabase = FirebaseDatabase.getInstance().getReference("ratspottings/");
    }

    /**
     * Constructor - constructs the class, used only in testing environments
     * @param thing
     */
    public RatSpottingBL(boolean thing) {
        mDatabase = null;
    }

    /**
     * addNewRatSpotting - adds a new rat spotting to the database;
     * @param ratSpotting the rat spotting we want to add
     */
    public void addNewRatSpotting(RatSpotting ratSpotting) {
        if (Utility.isNullOrWhitespace(ratSpotting.getKey())) {
            ratSpotting.setKey(Long.toString(RatSpotting.getNextKey()));
        }
        Log.d(LOG_ID, "Adding rat: " + ratSpotting.getKey() + " to the database");
        mDatabase.child(ratSpotting.getKey()).updateChildren(ratSpotting.toMap())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(LOG_ID, "The RatSpotting has been added to the database.");
                    } else {
                        Log.d(LOG_ID, task.getException().getMessage());
                    }
                });
    }

    /**
     * updateRatSpotting - updates a certain rat spotting in the database;
     * @param ratSpotting the rat spotting we want to update
     */
    public void updateRatSpotting(RatSpotting ratSpotting) {
        mDatabase.child(ratSpotting.getKey()).updateChildren(ratSpotting.toMap());
    }

    /**
     * getRatSpotting - obtains a ratspotting based on a unique ID
     * @param ratID the unique ID of the ratspotting we are looking for
     * @return the database reference to the rat spotting we are looking for
     */
    public DatabaseReference getRatSpotting(String ratID) {
        Log.d(LOG_ID, "Querying Firebase to look for ratspotting with id: " + ratID);
        return mDatabase.child(ratID);
    }

    /**
     * getRecentRatSpottings - returns the last 50 rat spottings based on date
     * @return a Query providing the datasnapshot of the 50 most recent rat spottings
     */
    public Query getRecentRatSpottings() {
        Log.d(LOG_ID, "Querying Firebase to look for recent ratdata");
        return mDatabase.limitToLast(50).orderByChild("date");
    }

    /**
     * Get spottings in range
     * @param from start date
     * @param to end date
     * @param limit num results
     * @return the query
     */
    public Query getRatSpottingsBetween(Calendar from, Calendar to, int limit) {
        Query query;
        if (from == null && to == null) {
            query = mDatabase.orderByChild("date");
        } else if (from == null) {
            query = mDatabase.orderByChild("date").endAt(to.getTimeInMillis());
        } else if (to == null) {
            query = mDatabase.orderByChild("date").startAt(from.getTimeInMillis());
        } else {
            query = mDatabase.orderByChild("date")
                    .startAt(from.getTimeInMillis()).endAt(to.getTimeInMillis());
        }
        if (limit < 0) {
            return query;
        } else if (limit == 0) {
            return query.limitToLast(50);
        } else {
            return query.limitToLast(limit);
        }
    }

    /**
     * Parses the given rat data into a map with the month/year and the number of rats spottings that month.
     * @param rats The passed in ratspottings
     * @return A map containing the months and the corresponding number of ratspottings
     * @throws InvalidParameterException
     */
    @SuppressWarnings("FeatureEnvy")
    public Map<String, Integer> parseRatData(List<RatSpotting> rats) throws InvalidParameterException {
        if (rats == null) {
            throw new InvalidParameterException("The passed list to parseRatData cannot be null");
        }
        //Log.d(LOG_ID, "onCreate():parseRatData(): has been started");
        Map<String, Integer> map = new HashMap<>();
        // Sort ratSpottings in descending order
        rats.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        //Log.d(LOG_ID, "ratSpottings size(): " + rats.size());
        for (int i = 0; i < rats.size();) {
            //Log.d(LOG_ID, rats.get(i).toString());
            Calendar date = rats.get(i).getDate();
            int year = date.get(Calendar.YEAR);
            RatSpotting rat = rats.get(i);
            while (year == rat.getDate().get(Calendar.YEAR) && i < rats.size()) {
                //Log.d(LOG_ID, Integer.toString(i));
                String key = String.format(Locale.getDefault(),"%s %d",
                        rat.getDate().getDisplayName(
                                Calendar.MONTH, Calendar.LONG, Locale.getDefault()),
                        year);
                if (map.containsKey(key)) {
                    map.put(key, map.get(key) + 1);
                } else {
                    map.put(key, 1);
                }
                i++;
                if (i < rats.size()) {
                    rat = rats.get(i);
                }
            }
        }
        //Log.d(LOG_ID, "parseRatData():map: " + map.toString());
        return map;
    }

    /**
     * getCurrentKey() an asynchronous method called at startup to make sure
     * we have retrieved the next key in the
     */
    public static void getCurrentKey() {
        FirebaseDatabase.getInstance().getReference()
                .child("nextUniqueKey").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(LOG_ID, "getCurrentKey():onDataChange():dataSnapshot -> "
                        + dataSnapshot.toString());
                RatSpotting.setNextKey(dataSnapshot.getValue() != null
                        ? (Long) dataSnapshot.getValue() : 1);
                Log.d(LOG_ID, "The next key has been set to " + RatSpotting.seeNextKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(LOG_ID, "getCurrentKey():onCancelled() the request has been cancelled, "
                        + "the given error is " + databaseError.getMessage());
            }
        });
        Log.d(LOG_ID, "Query for highest key has been made. Waiting response...");
    }

    /**
     * pushCurrentKey() - pushes the current unique key for RatSpottings to the server
     */
    public static void pushCurrentKey() {
        FirebaseDatabase.getInstance().getReference().child("nextUniqueKey")
                .setValue(RatSpotting.seeNextKey());
    }
}
