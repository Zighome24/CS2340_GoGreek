package edu.gatech.cs2340.rattracker2k17.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.Model.RatSpottingsServiceTransfer;
import edu.gatech.cs2340.rattracker2k17.R;
import edu.gatech.cs2340.rattracker2k17.Service.RatSpottingBL;
import edu.gatech.cs2340.rattracker2k17.Service.Utility;

/**
 * Controller for date selection screen
 * @author Justin Z
 * @version 1.0
 */

public class DateSelectionScreenMapController extends AppCompatActivity {

    private static final String LOG_ID = "DateSelectionScreenCon";

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapdateselectionscreen);

        progressBar = findViewById(R.id.prog_DateSelect);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, WelcomeScreenController.class));
    }



    /**
     * Perform action on view
     * @param view the actionable view
     */
    @SuppressWarnings("FeatureEnvy")
    public void action(View view) {
        TextView txt_fromDate, txt_toDate;
        Calendar fromDate = null, toDate = null;

        txt_fromDate = findViewById(R.id.txt_date1);
        txt_toDate = findViewById(R.id.txt_date2);

        if (txt_fromDate.getText() != null
                && !Utility.isNullOrWhitespace(txt_fromDate.getText().toString())) {
            Log.d(LOG_ID, "%%" + txt_fromDate.getText().toString() + "%%");
            fromDate = Utility.parseStringDate(txt_fromDate.getText().toString());
        }

        if (txt_toDate.getText() != null
                && !Utility.isNullOrWhitespace(txt_toDate.getText().toString())) {
            Log.d(LOG_ID, "%%" + txt_toDate.getText().toString() + "%%");
            toDate = Utility.parseStringDate(txt_toDate.getText().toString());
        }
        final int option = R.id.btn_dateToMap;
        Log.d(LOG_ID, "The selected view was " + option);
        RatSpottingBL ratBL = new RatSpottingBL();
        progressBar.setVisibility(View.VISIBLE);
        ratBL.getRatSpottingsBetween(fromDate, toDate, 100)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<RatSpotting> rats = new ArrayList<>();
                    Log.d(LOG_ID, "Incoming Rat Data");
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        RatSpotting rat = Utility.getRatSpottingFromSnapshot(data);
                        rats.add(rat);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("rats", new RatSpottingsServiceTransfer(rats));

                    Intent intentMap = new Intent(DateSelectionScreenMapController.this,
                            MapScreenController.class);
                    intentMap.putExtras(bundle);
                    startActivity(intentMap);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(LOG_ID, "The request for RatSpottingsBetween() has been canceled, "
                        + "message: " + databaseError.getDetails());
                }
        });
    }

    /**
     * Get today
     * @param view the view to be to
     */
    public void today(View view) {
        Calendar calendar = Calendar.getInstance();
        String time =
                (Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)).length() == 1 ?
                        "0" + Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)) :
                        Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)))
                        + ":" + (Integer.toString(calendar.get(Calendar.MINUTE)).length() == 1 ?
                        "0" + Integer.toString(calendar.get(Calendar.MINUTE)) :
                        Integer.toString(calendar.get(Calendar.MINUTE)));
        String date = String.format(Locale.getDefault(), "%d/%d/%d %s",
                (1 + calendar.get(Calendar.MONTH)),
                calendar.get(Calendar.DATE),
                calendar.get(Calendar.YEAR),
                time);
        TextView txt_From = findViewById(R.id.txt_date1), txt_To = findViewById(R.id.txt_date2);
        switch (view.getId()) {
            case R.id.btn_date1:
                txt_From.setText(date);
                break;
            case R.id.btn_date2:
                txt_To.setText(date);
                break;
            default:
                Log.d(LOG_ID, "The given id from the calling view did not match any options,"
                    + " it was " + Integer.toString(view.getId()));
        }
    }
}
