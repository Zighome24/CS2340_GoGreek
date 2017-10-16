package edu.gatech.cs2340.rattracker2k17.Service;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;

/**
 * Created by Justin on 10/12/2017.
 */

public class RatSpottingBL {

    private static final String LOG_ID = "RatSpottingBL";

    private DatabaseReference mDatabase;

    /**
     * Constructor - instantiates the mDatabase value to the ratspotting portion of the database
     */
    public RatSpottingBL() {
        mDatabase = FirebaseDatabase.getInstance().getReference("ratspottings/");
    }

    /**
     * addNewRatSpotting - adds a new rat spotting to the database;
     * @param ratSpotting the rat spotting we want to add
     */
    public void addNewRatSpotting(RatSpotting ratSpotting) {
        if (Utility.isNullOrWhitespace(ratSpotting.getKey())) {
            ratSpotting.setKey(Integer.toString(RatSpotting.getNextKey()));
        }
    }

    /**
     * updateRatSpotting - updates a certain rat spotting in the database;
     * @param ratSpotting the rat spotting we want to update
     */
    public void updateRatSpotting(RatSpotting ratSpotting) {
        mDatabase.child(ratSpotting.getKey()).updateChildren(ratSpotting.toMap());
    }

    public static void getCurrentKey() {
        FirebaseDatabase.getInstance().getReference("ratspottings/")
                .orderByKey().limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(LOG_ID, dataSnapshot.toString());
                        int key = 0;
                        key++;
                        RatSpotting.setNextKey(key);
                        Log.d(LOG_ID,
                                "The generated next key for the RatSpotting is "
                                    + Integer.toString(key));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(LOG_ID, "The Value Event Listener for RatSpottingBL:getCurrentKey() "
                            + "has been canceled with error: " + databaseError.getMessage());
                    }
                });
    }
}
