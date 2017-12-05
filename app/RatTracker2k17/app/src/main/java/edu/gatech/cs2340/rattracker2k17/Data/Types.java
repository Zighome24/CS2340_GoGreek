package edu.gatech.cs2340.rattracker2k17.Data;

/**
 * Type definitions
 * @author Justin Z
 * @version 1.0
 */

public class Types {

    public enum SecurityLevel {
        Guest(1),
        User(5),
        Admin(25),
        Unknown(-1);
        //  If you create a new Security Level make sure you add it to the parseSecurityLevel
        //  method, so the method does not break our app.

        private final int _level;

        /**
         * Get level
         * @return the level
         */
        public int getLevel() {return _level;}

        SecurityLevel(int level) {
            _level = level;
        }

        /**
         * Parses an integer value for the representative SecurityLevel enum
         * @param level the integer representation of the security level you are trying to obtain
         * @return the SecurityLevel enumerated value
         */
        public static SecurityLevel parseSecurityLevel(int level) {
            switch(level) {
                case 1: return Guest;
                case 5: return User;
                case 25: return Admin;
                default: return Unknown;
            }

        }
    }

    public enum Logging {
        Login("/logs/users/login", "logged in."),
        Logout("/logs/users/logout", "logged out"),
        UserCreation("/logs/users", "was created"),
        Create("logs/create", "created a ratspotting"),
        Delete("logs/delete", "deleted a ratspotting"),
        Other("logs/other", "");

        private String _location, _message;

        public String getLocation() {return _location;}

        public String getMessage() {return _message;}

        Logging(String location, String message) { _location = location; _message = message;}

    }


}


