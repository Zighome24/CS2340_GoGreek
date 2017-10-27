package edu.gatech.cs2340.rattracker2k17.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class DateSelectionScreenController extends AppCompatActivity {

    private static final String LOG_ID = "DateSelectionScreenCon";

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateselectionscreen);

        progressBar = findViewById(R.id.prog_DateSelect);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void toMap(View view) {
        TextView txt_fromDate, txt_toDate;
        Calendar fromDate = null, toDate = null;
        boolean fromDateExists = false, toDateExists = false;

        txt_fromDate = findViewById(R.id.txt_date1);
        txt_toDate = findViewById(R.id.txt_date2);

        if (txt_fromDate.getText() != null
                || !Utility.isNullOrWhitespace(txt_fromDate.getText().toString())) {
            fromDate = Utility.parseStringDate(txt_fromDate.getText().toString());
            fromDateExists = true;
        }

        if (txt_toDate.getText() != null
                || !Utility.isNullOrWhitespace(txt_toDate.getText().toString())) {
            toDate = Utility.parseStringDate(txt_toDate.getText().toString());
            toDateExists = true;
        }

        Log.d(LOG_ID, "From Date: " + Utility.getDateString(fromDate)
                + " in Millis: " + fromDate.getTimeInMillis());
        Log.d(LOG_ID, "To Date: " + Utility.getDateString(toDate)
                + " in Millis: " + toDate.getTimeInMillis());

        RatSpottingBL ratBL = new RatSpottingBL();
        progressBar.setVisibility(View.VISIBLE);
        ratBL.getRatSpottingsBetween(fromDate, toDate, 5)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<RatSpotting> rats = new ArrayList<>();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Log.d(LOG_ID, data.toString());
                        RatSpotting rat = Utility.getRatSpottingFromSnapshot(data);
                        Log.d(LOG_ID, rat.toString());
                        rats.add(rat);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("rats", new RatSpottingsServiceTransfer(rats));
                    Intent intent = new Intent(DateSelectionScreenController.this,
                            MapScreenController.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(LOG_ID, "The request for RatSpottingsBetween() has been canceled, "
                        + "message: " + databaseError.getDetails());
                }
        });
    }

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
