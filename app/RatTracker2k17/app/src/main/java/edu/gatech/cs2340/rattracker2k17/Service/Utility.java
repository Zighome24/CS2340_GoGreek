package edu.gatech.cs2340.rattracker2k17.Service;

/**
 * Created by Justin on 10/3/2017.
 */

public class Utility {

    /**
     * isNullOrWhitespace - checks to see if the string is null or whitespace
     * @param s the string we are analyzing
     * @return true if the string is empty, contains only whitespace, or is null
     */
    public static boolean isNullOrWhitespace(String s) {
        if (s == null)
            return true;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
