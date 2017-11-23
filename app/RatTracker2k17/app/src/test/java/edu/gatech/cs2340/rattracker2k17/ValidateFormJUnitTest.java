package edu.gatech.cs2340.rattracker2k17;

import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.junit.Assert.*;

import edu.gatech.cs2340.rattracker2k17.Controller.LogInScreenController;

/**
 * Created by Chris on 11/13/17.
 */

public class ValidateFormJUnitTest {

    private static final int TIMEOUT = 2000;
    private String nullEmail;
    private String nullPass;
    private String email;
    private String password;
    private LogInScreenController login;

    @Before
    public void setUp() {
        nullEmail = null;
        nullPass = null;
        email = "cob@gatech.edu";
        password = "password";
        login = new LogInScreenController();
    }

    @Test(timeout = TIMEOUT)
    public void testValidateForm() {
        try {

            Method method = LogInScreenController.class.getDeclaredMethod("validateForm", boolean.class);
            method.setAccessible(true);

            boolean twoNull = (boolean) method.invoke(login, nullEmail, nullPass);
            boolean emailNull = (boolean) method.invoke(login, nullEmail, password);
            boolean passNull = (boolean) method.invoke(login, email, nullPass);
            boolean noNull = (boolean) method.invoke(login, email, password);

            assertFalse(twoNull);
            assertFalse(emailNull);
            assertFalse(passNull);
            assertTrue(noNull);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }


    }

}
