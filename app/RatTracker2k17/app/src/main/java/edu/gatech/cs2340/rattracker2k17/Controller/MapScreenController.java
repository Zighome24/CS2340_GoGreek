package edu.gatech.cs2340.rattracker2k17.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.Model.RatSpottingsServiceTransfer;
import edu.gatech.cs2340.rattracker2k17.R;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/** Controller for map
 * @author Chris O
 * @version 1.0
 */
public class MapScreenController extends AppCompatActivity implements OnMapReadyCallback {

    private static final String LOG_ID = "MapScreenController";
    private FusedLocationProviderClient mFusedLocationClient;
    private List<RatSpotting> ratSpottings;
    private LatLng userLL;

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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @SuppressWarnings("FeatureEnvy")
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        // Move the map to New York
        LatLng newYork = new LatLng(40.7128, -74.0060);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(newYork));
        //googleMap.setMinZoomPreference(7f);
        googleMap.setMaxZoomPreference(17f);

        if (ratSpottings == null) {
            Log.d(LOG_ID,
                    "The ratSpottings list was never set to the transferred RatSpotting(s)");
        } else {
            for (RatSpotting rat : ratSpottings) {
                if (!(rat.getLat() == 0.0 || rat.getLong() == 0.0)) {
                    LatLng ratLL = new LatLng(rat.getLat(), rat.getLong());
                    googleMap.addMarker(new MarkerOptions().position(ratLL).title(rat.toString()));
                }

            }
        }
        try {
            Log.d(LOG_ID, "Attempting to grab the user's location now");
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location ->
            {
                Log.d(LOG_ID, ":: GETTING USER LOCATION :: location: " + location.toString());
                if (location != null) {
                    userLL = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.addMarker(
                            new MarkerOptions().position(userLL).title("YOU")
                                    .icon(BitmapDescriptorFactory.
                                            defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                }
            }).addOnFailureListener(this, e -> {
                Log.d(LOG_ID, ":: FAILED TO GET USER LOCATION :: exception: " + e.getMessage());
            });
        } catch (SecurityException ex) {
            Log.d(LOG_ID, "Security Exception thrown with message: " + ex.getMessage());
        }
    }
}
