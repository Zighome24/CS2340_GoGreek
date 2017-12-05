package edu.gatech.cs2340.rattracker2k17.Model;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Map;

import edu.gatech.cs2340.rattracker2k17.Data.Types;

/**
 * Created by Justin on 11/30/2017.
 */

public abstract class LogReport {

    protected Types.Logging log;
    public void setLog(Types.Logging log) {
        this.log = log;
    }
    public String getLocation() { return log.getLocation(); }

    protected Calendar date;

    LogReport(Types.Logging log) {
        this.log = log;
        this.date = Calendar.getInstance();
    }

    public abstract Map<String, Object> toMap();
}
