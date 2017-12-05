package edu.gatech.cs2340.rattracker2k17;

import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import edu.gatech.cs2340.rattracker2k17.Service.Utility;
import static org.junit.Assert.assertEquals;


/**
 * Created by wepperson on 11/13/17.
 */

public class UtilityJUnitTest {

    private static final int TIMEOUT = 2000;
    private Calendar cal;

    @Before
    public void setUp() {
        cal = Calendar.getInstance();
        //cal.set(2017, 11, 8, 10, 11);
    }

    @Test(timeout = TIMEOUT)
    public void testConvert() throws NoSuchFieldException {

        // mm/dd/yyyy mm:hh

        cal.set(2017, 11, 8, 18, 11);
        assertEquals("11/8/2017 18:11", Utility.getDateString(cal));

        cal.set(1950, 0, 8, 10, 11);
        assertEquals("0/8/1950 10:11", Utility.getDateString(cal));

        cal.set(1950, 1, 1, 10, 11);
        assertEquals("1/1/1950 10:11", Utility.getDateString(cal));

        cal.set(1900, 8, 8, 10, 60);
        assertEquals("8/8/1900 11:00", Utility.getDateString(cal));

        cal.set(1900, 8, 8, 10, 61);
        assertEquals("8/8/1900 11:01", Utility.getDateString(cal));

        cal.set(1900, 8, 8, 24, 61);
        assertEquals("8/9/1900 01:01", Utility.getDateString(cal));

        cal.set(1900, -8, 8, 24, 61);
        assertEquals("4/9/1899 01:01", Utility.getDateString(cal));

    }

    @Test(timeout = TIMEOUT, expected = NoSuchFieldException.class)
    public void testNull() throws NoSuchFieldException {
        cal.set(2017, 11, 8, 18, 11);
        cal.clear();
        Utility.getDateString(cal);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchFieldException.class)
    public void testYearNull() throws NoSuchFieldException {
        cal.set(2017, 11, 8, 18, 11);
        cal.clear(Calendar.YEAR);
        Utility.getDateString(cal);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchFieldException.class)
    public void testMonthNull() throws NoSuchFieldException {
        cal.set(2017, 11, 8, 18, 11);
        cal.clear(Calendar.MONTH);
        Utility.getDateString(cal);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchFieldException.class)
    public void testDateNull() throws NoSuchFieldException {
        cal.set(2017, 11, 8, 18, 11);
        cal.clear(Calendar.DATE);
        Utility.getDateString(cal);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchFieldException.class)
    public void testHourNull() throws NoSuchFieldException {
        cal.set(2017, 11, 8, 18, 11);
        cal.clear(Calendar.HOUR_OF_DAY);
        Utility.getDateString(cal);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchFieldException.class)
    public void testMinuteNull() throws NoSuchFieldException {
        cal.set(2017, 11, 8, 18, 11);
        cal.clear(Calendar.MINUTE);
        Utility.getDateString(cal);
    }
}
