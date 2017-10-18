package edu.gatech.cs2340.rattracker2k17.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.R;
import edu.gatech.cs2340.rattracker2k17.Service.RatSpottingBL;

public class NewRatSpottingController extends AppCompatActivity {

    private static final String LOG_ID = "NewRatSpottingContr.";
    private TextView txt_date, txt_location, txt_address,
            txt_city, txt_zip, txt_borough, txt_lat, txt_long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editableratspotting);

        Log.d(LOG_ID, getCallingActivity() != null ?
                getCallingActivity().getShortClassName() : "Does not have a calling class");

        Button btn_AddRat = findViewById(R.id.btn_actionRat);
        btn_AddRat.setText(R.string.addRatSpotting);

        txt_date = findViewById(R.id.txt_ERSDate);

        txt_location = findViewById(R.id.txt_ERSLocationType);

        txt_address = findViewById(R.id.txt_ERSAddress);

        txt_city = findViewById(R.id.txt_ERSCity);

        txt_zip = findViewById(R.id.txt_ERSZip);

        txt_borough = findViewById(R.id.txt_ERSBorough);

        txt_lat = findViewById(R.id.txt_ERSLat);

        txt_long = findViewById(R.id.txt_ERSLong);

        btn_AddRat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatSpottingBL ratSpottingBL = new RatSpottingBL();
                // TODO: 10/18/2017 Add a validation check for the inputs
                RatSpotting rat = new RatSpotting(
                        Long.toString(RatSpotting.getNextKey()), txt_date.getText().toString(),
                        txt_location.getText().toString(),
                        Integer.parseInt(txt_zip.getText().toString()),
                        txt_address.getText().toString(),
                        txt_city.getText().toString(), txt_borough.getText().toString(),
                        Double.parseDouble(txt_lat.getText().toString()),
                        Double.parseDouble(txt_long.getText().toString()));
                Log.d(LOG_ID, "onClick: creating a new rat spotting: " + rat.toString());
                try {
                    ratSpottingBL.addNewRatSpotting(rat);
                } catch (Exception e) {
                    Log.d(LOG_ID, e.getMessage());
                }
                Log.d("CHECK THIS OUT", getCallingActivity().getClassName());
                switch (getCallingActivity().getClassName()) {
                    case "edu.gatech.cs2340.rattracker2k17.Controller.WelcomeScreenController":
                        startActivity(new Intent(NewRatSpottingController.this,
                                WelcomeScreenController.class));
                    default:
                        Log.d(LOG_ID, "The class " + getCallingActivity().getClassName()
                                + " called NewRatSpottingController but does not have"
                                + " a switch case to launch the activity. Please add one");
                }
            }
        });
    }
}