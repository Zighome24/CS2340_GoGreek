package edu.gatech.cs2340.rattracker2k17;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;


import edu.gatech.cs2340.rattracker2k17.Data.Types;
import edu.gatech.cs2340.rattracker2k17.Model.User;

/**
 * Created by wepperson on 11/13/17.
 */

public class UserJUnitTest {

    private static final int TIMEOUT = 2000;
    private User u1;


    @Before
    public void setUp() {
        u1 = new User("Will", "Epperson",
                "willepp@gatech.edu", Types.SecurityLevel.Admin,
                "willepp", "password");

        Map<String, Object> testMap = new HashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testUserMap() {
        Map<String, Object> map = u1.toMap();

        // make sure the User added to map
        Set keys = map.keySet();
        assertTrue("Email in HashMap", keys.contains("email"));
        assertTrue("First name in HashMap", keys.contains("firstName"));
        assertTrue("Last name in HashMap", keys.contains("lastName"));
        assertTrue("Security level in HashMap", keys.contains("securityLevel"));

        assertEquals(map.get("email"), "willepp@gatech.edu");
        assertEquals(map.get("firstName"), "Will");
        assertEquals(map.get("lastName"), "Epperson");

        int level = (int) map.get("securityLevel");
        assertEquals(level, 25);

        // make sure no extra data
        assertFalse("Random in HashMap", keys.contains("city"));
        assertNull(map.get("city"));


    }
}
