package edu.gatech.cs2340.rattracker2k17.Model;

import edu.gatech.cs2340.rattracker2k17.Data.Types;

/**
 * Created by Justin on 9/22/2017.
 *
 * Represents the model for a user of the application, containing the users information,
 * security level, and userID (a unique identifier for every user).
 */

public class User {

    //Constants
    public static final String NO_UID = "NO_UID";

    //Properties
    private String firstName;
    public void setFirstName(String firstName) { this.firstName = firstName;}
    public String getFirstName() { return this.firstName;}

    private String lastName;
    public void setLastName(String lastName) { this.lastName = lastName;}
    public String getLastName() { return this.lastName;}

    private String email;
    public void setEmail(String email) { this.email = email;}
    public String getEmail() { return this.email;}

    private Types.SecurityLevel securityLevel;
    public void setSecurityLevel(Types.SecurityLevel securityLevel) { this.securityLevel = securityLevel;}
    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = Types.SecurityLevel.parseSecurityLevel(securityLevel);
    }
    public Types.SecurityLevel getSecurityLevel() { return this.securityLevel;}

    private String userID;
    public void setUserID(String userID) { this.userID = userID;}
    public String getUserID() { return this.userID;}

    private String password;
    public void setPassword(String password) { this.password = password;}
    public String getPassword() { return this.password;}

    public User() {}

    public User(String firstName, String lastName, String email,
                Types.SecurityLevel securityLevel, String userID, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.securityLevel = securityLevel;
        this.userID = userID;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "First Name: " + firstName + ", Last Name: " + lastName + ", Email: " + email
                + ", UserID: " + userID;
    }
}