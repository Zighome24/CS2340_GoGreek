package edu.gatech.cs2340.rattracker2k17.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.R;

public class DetailRatScreenController extends AppCompatActivity {

    private TextView
            txt_key, txt_date, txt_location, txt_address,
            txt_city, txt_zip, txt_borough, txt_lat, txt_long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratspottingdetails);
        RatSpotting spot = (RatSpotting) getIntent().getExtras().getSerializable("spotting");

        txt_key = findViewById(R.id.txt_RSKey);
        txt_key.setText(spot.getKey());

        txt_date = findViewById(R.id.txt_RSDate);
        txt_date.setText(spot.getDate());

        txt_location = findViewById(R.id.txt_RSLocationType);
        txt_location.setText(spot.getLocationType());

        txt_address = findViewById(R.id.txt_RSAddress);
        txt_address.setText(spot.getAddress());

        txt_city = findViewById(R.id.txt_RSCity);
        txt_city.setText(spot.getCity());

        txt_zip = findViewById(R.id.txt_RSZip);
        txt_zip.setText(Integer.toString(spot.getZip()));

        txt_borough = findViewById(R.id.txt_RSBorough);
        txt_borough.setText(spot.getBorough());

        txt_lat = findViewById(R.id.txt_RSLat);
        txt_lat.setText(Double.toString(spot.getLat()));

        txt_long = findViewById(R.id.txt_RSLong);
        txt_long.setText(Double.toString(spot.getLong()));
    }
}
