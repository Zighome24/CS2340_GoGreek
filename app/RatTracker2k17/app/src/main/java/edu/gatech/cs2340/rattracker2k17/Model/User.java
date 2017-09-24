package edu.gatech.cs2340.rattracker2k17.Model;

import edu.gatech.cs2340.rattracker2k17.Data.Types;

/**
 * Created by Justin on 9/22/2017.
 */

public class User {

    //Properties
    private String firstName;
    private void setFirstName(String firstName) { this.firstName = firstName;}
    //private String getFirstName() { return this.firstName;}

    private String lastName;
    private void setLastName(String lastName) { this.lastName = lastName;}
    //private String getLastName() { return this.lastName;}

    private String userName;
    private void setUserName(String userName) { this.userName = userName;}
    //private String getUserName() { return this.userName;}

    private Types.SecurityLevel securityLevel;
    private void setSecurityLevel(Types.SecurityLevel securityLevel) { this.securityLevel = securityLevel;}

    /**
     * Creates a new user and syncs their informaton with the database.
     * @return Whether or not the User was properly configured and added
     */
    public boolean newUser() {
        return false;
    }

}