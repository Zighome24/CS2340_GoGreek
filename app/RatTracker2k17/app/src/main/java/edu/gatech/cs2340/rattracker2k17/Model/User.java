package edu.gatech.cs2340.rattracker2k17.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.gatech.cs2340.rattracker2k17.Data.Types;

/**
 * Created by Justin on 9/22/2017.
 *
 * Represents the model for a user of the application, containing the users information,
 * security level, and userID (a unique identifier for every user).
 */

public class User implements Serializable {

    //Constants
    public static final String NO_UID = "NO_UID";

    //Properties
    private String firstName;
    private static User guest = new User(
            "Guest", "","", Types.SecurityLevel.Guest,
            "guest", "");
    public static User getGuest() {
        return guest;
    }

    /**
     * Setter for first name
     * @param firstName name
     */
    public void setFirstName(String firstName) { this.firstName = firstName;}

    /**
     * Getter for first name
     * @return name
     */
    public String getFirstName() { return this.firstName;}

    private String lastName;

    /**
     * Setter for last name
     * @param lastName name
     */
    public void setLastName(String lastName) { this.lastName = lastName;}

    /**
     * Getter for last name
     * @return name
     */
    public String getLastName() { return this.lastName;}

    private String email;

    /**
     * Set email
     * @param email the email
     */
    public void setEmail(String email) { this.email = email;}

    /**
     * Get email
     * @return email
     */
    public String getEmail() { return this.email;}

    private Types.SecurityLevel securityLevel;

    /**
     * Set security level
     * @param securityLevel security level type
     */
    public void setSecurityLevel(Types.SecurityLevel securityLevel) {
        this.securityLevel = securityLevel;
    }

    /**
     * Set security level
     * @param securityLevel int representing security level
     */
    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = Types.SecurityLevel.parseSecurityLevel(securityLevel);
    }

    /**
     * Getter for security level
     * @return the level
     */
    public Types.SecurityLevel getSecurityLevel() { return this.securityLevel;}

    private String userID;

    /**
     * Set ID
     * @param userID string ID
     */
    public void setUserID(String userID) { this.userID = userID;}

    /**
     * Get ID
     * @return the ID
     */
    public String getUserID() { return this.userID;}

    private String password;

    /**
     * Set password
     * @param password the new password
     */
    public void setPassword(String password) { this.password = password;}

    /**
     * Get password
     * @return the password
     */
    public String getPassword() { return this.password;}

    /**
     * Constructor for User
     * @param firstName name
     * @param lastName name
     * @param email email
     * @param securityLevel level
     * @param userID ID
     * @param password user's password
     */
    public User(String firstName, String lastName, String email,
                Types.SecurityLevel securityLevel, String userID, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.securityLevel = securityLevel;
        this.userID = userID;
        this.password = password;
    }

    /**
     * Constructor for user for minimal attributes
     * @param email email
     * @param password password
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "First Name: " + firstName + ", Last Name: " + lastName + ", Email: " + email
                + ", UserID: " + userID;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean canAddSpottings() { return securityLevel.getLevel() >= 5; }

    /**
     * Map User to map
     * @return map
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("securityLevel", securityLevel.getLevel());

        return map;
    }
}