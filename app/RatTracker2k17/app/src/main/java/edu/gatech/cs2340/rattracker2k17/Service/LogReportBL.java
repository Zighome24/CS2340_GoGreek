package edu.gatech.cs2340.rattracker2k17.Service;

import com.google.android.gms.tasks.Task;

import edu.gatech.cs2340.rattracker2k17.Model.LogReport;

/**
 * Created by Justin on 12/4/2017.
 */

public class LogReportBL extends DatabaseBL {

    public Task pushReport(LogReport log) {
        return updateDB(log.getLocation(), log.toMap());
    }
}
