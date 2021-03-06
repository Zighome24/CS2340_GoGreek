package edu.gatech.cs2340.rattracker2k17.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.R;

/** Controller for detail rat screen
 * @author Justin Z
 * @version 1.0
 */

public class DetailRatScreenController extends AppCompatActivity {
    private static final String LOG_ID = "DetailRatScreenContr";
    private TextView
            txt_key, txt_date, txt_location, txt_address,
            txt_city, txt_zip, txt_borough, txt_lat, txt_long;

    @SuppressWarnings("FeatureEnvy")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratspottingdetails);
        // Always pass a bundle to this class from the calling activities,
        // getExtras() will never be null
        RatSpotting spot = (RatSpotting) getIntent().getExtras().getSerializable("spotting");

        if (spot == null) {
            Log.d(LOG_ID,
                    "A ratspotting was never passed to the DetailRatScreenController.");
            Toast.makeText(this, "ERROR: A Rat Spotting was not provided to the controller",
                    Toast.LENGTH_SHORT).show();
        } else {
            txt_key = findViewById(R.id.txt_RSKey);
            txt_key.setText(spot.getKey());

            txt_date = findViewById(R.id.txt_RSDate);
            txt_date.setText(spot.getDateString());

            txt_location = findViewById(R.id.txt_RSLocationType);
            txt_location.setText(spot.getLocationType());

            txt_address = findViewById(R.id.txt_RSAddress);
            txt_address.setText(spot.getAddress());

            txt_city = findViewById(R.id.txt_RSCity);
            txt_city.setText(spot.getCity());

            txt_zip = findViewById(R.id.txt_RSZip);
            txt_zip.setText(Long.toString(spot.getZip()));

            txt_borough = findViewById(R.id.txt_RSBorough);
            txt_borough.setText(spot.getBorough());

            txt_lat = findViewById(R.id.txt_RSLat);
            txt_lat.setText(Double.toString(spot.getLat()));

            txt_long = findViewById(R.id.txt_RSLong);
            txt_long.setText(Double.toString(spot.getLong()));
        }
    }
}
