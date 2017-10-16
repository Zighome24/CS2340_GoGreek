package edu.gatech.cs2340.rattracker2k17.Controller;

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
    private TextView txt_key, txt_date, txt_location, txt_address,
            txt_city, txt_zip, txt_borough, txt_lat, txt_long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editableratspotting);

        Button btn_AddRat = findViewById(R.id.btn_actionRat);
        btn_AddRat.setText(R.string.addRatSpotting);

        txt_key = findViewById(R.id.txt_RSKey);

        txt_date = findViewById(R.id.txt_RSDate);

        txt_location = findViewById(R.id.txt_RSLocationType);

        txt_address = findViewById(R.id.txt_RSAddress);

        txt_city = findViewById(R.id.txt_RSCity);

        txt_zip = findViewById(R.id.txt_RSZip);

        txt_borough = findViewById(R.id.txt_RSBorough);

        txt_lat = findViewById(R.id.txt_RSLat);

        txt_long = findViewById(R.id.txt_RSLong);

        btn_AddRat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatSpottingBL ratSpottingBL = new RatSpottingBL();
                // TODO: 10/15/2017 ADD the creation of a rat and then pass it to the ratspottingBL
                RatSpotting rat = new RatSpotting(
                        txt_key.getText().toString(), txt_date.getText().toString(),
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
            }
        });
    }
}
