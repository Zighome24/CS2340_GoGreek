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

    // TODO: 10/2/2017 Add new users to the database if they don't exist already, otherwise update
    public void addNewUser(User user) {

    }
}
