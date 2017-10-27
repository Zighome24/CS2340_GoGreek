package edu.gatech.cs2340.rattracker2k17.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.Model.RatSpottingsServiceTransfer;
import edu.gatech.cs2340.rattracker2k17.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Chris on 10/24/17.
 */

public class MapScreenController extends AppCompatActivity implements OnMapReadyCallback {

    private static final String LOG_ID = "MapScreenController";

    private List<RatSpotting> ratSpottings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapscreen);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (getIntent().getExtras() != null) {
            RatSpottingsServiceTransfer ratTransfer = (RatSpottingsServiceTransfer)
                    getIntent().getExtras().getSerializable("rats");
            if (ratTransfer != null) {
                ratSpottings = ratTransfer.getRatSpottings();
            } else {
                Log.d(LOG_ID, "The RatSpottingServiceTransfer was null.");
            }
        } else {
            Log.d(LOG_ID, "The calling Intent did not pass in any extras");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Move the map to New York
        LatLng newYork = new LatLng(40.7128, -74.0060);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(newYork));

        if (ratSpottings == null) {
            Log.d(LOG_ID,
                    "The ratSpottings list was never set to the transferred RatSpotting(s)");
        } else {
            for (RatSpotting rat : ratSpottings) {
                if (!(rat.getLat() == 0.0 || rat.getLong() == 0.0)) {
                    LatLng ratLL = new LatLng(rat.getLat(), rat.getLong());
                    googleMap.addMarker(new MarkerOptions().position(ratLL).title(rat.getKey()));
                }

            }
        }
    }


}
