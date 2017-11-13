package edu.gatech.cs2340.rattracker2k17;

import org.junit.Before;
import org.junit.Test;

import static edu.gatech.cs2340.rattracker2k17.Service.Utility.isNullOrWhitespace;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.gatech.cs2340.rattracker2k17.*;


/**
 * Created by 16nworthington on 11/13/2017.
 */

public class NullOrWhitespaceTest {
    private static final int TIMEOUT = 200;
    private CharSequence name;
    private CharSequence empty;
    private CharSequence white;
    private CharSequence n;

    @Before
    public void setUp() {
        name = "Nicholas Worthington";
        empty = "";
        white = "        ";
        n = null;
    }

    @Test(timeout = TIMEOUT)
    public void testNullOrWhitespace() {
        assertFalse(isNullOrWhitespace(name));
        assertTrue(isNullOrWhitespace(empty));
        assertTrue(isNullOrWhitespace(white));
        assertTrue(isNullOrWhitespace(n));
    }
}
