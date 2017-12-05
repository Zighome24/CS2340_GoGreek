package edu.gatech.cs2340.rattracker2k17.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import edu.gatech.cs2340.rattracker2k17.Data.Types;
import edu.gatech.cs2340.rattracker2k17.Service.Utility;

/**
 * Created by Justin on 12/4/2017.
 */

public class UserLogReport extends LogReport {

    private static final String LOG_ID = "UserLogReport";
    protected FirebaseUser user;

    public UserLogReport(Types.Logging log, @NonNull FirebaseUser user) {
        super(log);
        this.user = user;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("UID", user.getUid());
        map.put("Message", toString());
        return map;
    }

    @Override
    public String toString() {
        String time = "[Time Unavailable]";
        try {
            time = Utility.getDateString(date);
        } catch (NoSuchFieldException ex) {
            Log.d(LOG_ID, "Bad date was sent to Utility, date: " + date.toString());
        }
        return time + " :: UID: " + (user != null ? user.getUid() : "UNAVAILABLE") + " " + log.getMessage();
    }
}
