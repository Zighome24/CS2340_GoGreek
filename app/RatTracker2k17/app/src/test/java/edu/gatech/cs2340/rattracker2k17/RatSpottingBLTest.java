package edu.gatech.cs2340.rattracker2k17;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.Service.RatSpottingBL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Justin on 11/13/2017.
 */

public class RatSpottingBLTest {

    private RatSpottingBL ratBL = new RatSpottingBL(false);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Checks to see if parseRatData actually works correctly
     * Test
     */
    @Test
    public void doesParseRatData() {
        Tuple gen = generateRatData();
        assertEquals(gen.map, ratBL.parseRatData(gen.rats));
    }

    /**
     * Checks to make sure passing a null parameter throws an exception
     */
    @Test
    public void testNullParseRatData() {
        List<RatSpotting> rats = null;
        thrown.expect(InvalidParameterException.class);
        thrown.expectMessage("The passed list to parseRatData cannot be null");
        ratBL.parseRatData(rats);
    }

    /**
     * Checks to make sure passing an empty list provides an empty map
     */
    @Test
    public void testEmptyParseRatData() {
        List<RatSpotting> rats = new LinkedList<>();
        assertTrue(ratBL.parseRatData(rats).isEmpty());
    }

    /**
     * Generates both a list of randomized ratspottings and what their map should be
     * @return an object containing the list of ratspottings and mapping it should have
     */
    private Tuple generateRatData() {
        Tuple val = new Tuple();
        List<RatSpotting> rats = new ArrayList<>();
        Map<String, Integer> maps = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(
                    (long) (Math.random()*1300000000000L + Math.random()*200000000000L));
            String key = String.format(Locale.getDefault(),"%s %d",
                    date.getDisplayName(
                            Calendar.MONTH, Calendar.LONG, Locale.getDefault()),
                            date.get(Calendar.YEAR));
            if (maps.containsKey(key)) {
                maps.put(key, maps.get(key) + 1);
            } else {
                maps.put(key, 1);
            }
            rats.add(new RatSpotting(
                    "", date, "", 0, "",
                    "", "", 0.0, 0.0));
        }
        val.map = maps;
        val.rats = rats;
        return val;
    }

    private class Tuple {
        public Map<String, Integer> map;
        public List<RatSpotting> rats;
    }
}
