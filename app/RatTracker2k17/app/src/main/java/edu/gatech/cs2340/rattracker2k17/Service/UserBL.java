package edu.gatech.cs2340.rattracker2k17.Service;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import edu.gatech.cs2340.rattracker2k17.Model.User;

/**
 * User controller logic
 * @author Justin Z
 * @version 1.0
 */

public class UserBL {

    private static final String TAG = "UserBL";
    private final DatabaseReference mDataBase;

    /**
     * Constructor - Initializes the UserBL and saves the database reference to the
     * users portion of the database.
     */
    public UserBL() {
        mDataBase = FirebaseDatabase.getInstance().getReference("users/");
    }

    /**
     * addNewUser - adds a new user to the database
     * @param user the user we would like to add to the database
     */
    public void addNewUser(User user) {
        mDataBase.child(user.getUserID()).updateChildren(user.toMap());
    }

    /**
     * updateUser - updates the user in the database
     * @param user the user we would like to add to the database
     */
    public void updateUser(User user) {
        mDataBase.child(user.getUserID()).updateChildren(user.toMap());
    }

    /**
     * method that obtains an asynchronous query that can retrieve the user with a given UID
     * @param uID the user ID of the user we want to obtain
     * @return an asynchronous query that can be used to obtain the user.
     */
    public Query getUser(String uID) {
        return mDataBase.orderByChild(uID).limitToFirst(1);
    }
}
