package edu.gatech.cs2340.rattracker2k17.Data;

/**
 * Created by Justin on 9/23/2017.
 */

public class Types {

    public enum SecurityLevel {
        Guest(1),
        User(5),
        Admin(25),
        Unknown(-1);
        //  If you create a new Security Level make sure you add it to the parseSecurityLevel
        //  method, so the method does not break our app.

        private int _level;
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


}


