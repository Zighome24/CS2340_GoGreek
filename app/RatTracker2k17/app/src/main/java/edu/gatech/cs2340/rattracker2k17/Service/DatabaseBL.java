package edu.gatech.cs2340.rattracker2k17.Service;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

/**
 * Created by Justin on 12/4/2017.
 */

public class DatabaseBL {
    private DatabaseReference mDb;

    public DatabaseBL() {
        mDb = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseBL(DatabaseReference db) {
        mDb = db;
    }

    Task updateDB(String dbLocation, Map<String, Object> children) {
        return mDb.child(dbLocation).updateChildren(children);
    }
}
