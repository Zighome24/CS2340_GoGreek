package edu.gatech.cs2340.rattracker2k17.Service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
            ratSpotting.setKey(Long.toString(RatSpotting.getNextKey()));
        }
        Log.d(LOG_ID, "Adding rat: " + ratSpotting.getKey() + " to the database");
        mDatabase.child(ratSpotting.getKey()).updateChildren(ratSpotting.toMap())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(LOG_ID, "The RatSpotting has been added to the database.");
                        } else {
                            Log.d(LOG_ID, task.getException().getMessage());
                        }
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

    public DatabaseReference getRatSpotting(String ratID) {
        Log.d(LOG_ID, "Querying Firebase to look for ratspotting with id: " + ratID);
        return mDatabase.child(ratID);
    }

    public Query getRecentRatSpottings() {
        Log.d(LOG_ID, "Querying Firebase to look for recent ratdata");
        return mDatabase.orderByValue().getRef().orderByKey().limitToLast(50);
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
