package edu.gatech.cs2340.rattracker2k17.Exceptions;

/**
 * Created by Justin on 10/12/2017.
 */

public class UserDoesNotExistException extends Exception {

    private static final String TAG = "UserDoesNotExist: ";

    public UserDoesNotExistException(String message) {
        super(TAG + "the user does not exist.");
    }
}
