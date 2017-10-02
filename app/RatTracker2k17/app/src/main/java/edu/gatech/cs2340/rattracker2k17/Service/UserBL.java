package edu.gatech.cs2340.rattracker2k17.Service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.gatech.cs2340.rattracker2k17.Model.User;

/**
 * Created by Justin on 10/1/2017.
 */

public class UserBL {

    private DatabaseReference mDataBase;

    public UserBL() {
        mDataBase = FirebaseDatabase.getInstance().getReference();
    }

    public UserBL(DatabaseReference database) {
        mDataBase = database;
    }

    public void addNewUser(User user) {

    }
}
