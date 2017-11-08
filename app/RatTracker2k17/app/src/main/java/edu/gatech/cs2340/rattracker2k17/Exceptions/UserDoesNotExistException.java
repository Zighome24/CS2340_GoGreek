package edu.gatech.cs2340.rattracker2k17.Exceptions;

/**
 * Exception for non-existent users
 * @author Justin Z
 * @version 1.0
 */

public class UserDoesNotExistException extends Exception {

    private static final String TAG = "UserDoesNotExist: ";

    /**
     * Constructor
     * @param message the message
     */
    public UserDoesNotExistException(String message) {
        super(TAG + "the user does not exist.");
    }
}
