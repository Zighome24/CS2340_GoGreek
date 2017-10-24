package edu.gatech.cs2340.rattracker2k17.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.gatech.cs2340.rattracker2k17.Model.RatSpotting;
import edu.gatech.cs2340.rattracker2k17.R;
import edu.gatech.cs2340.rattracker2k17.Service.RatSpottingBL;

/**
 * Created by wepperson on 9/24/17.
 */

public class WelcomeScreenController extends AppCompatActivity {

    private static final String LOG_ID = "WelcomeScreenController";
    private FirebaseAuth mAuth;
    private RatSpottingAdapter ratAdapter;
    private ListView listView;
    private RatSpottingBL ratSpottingBL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen);

        listView = findViewById(R.id.list_view);
        ratAdapter = new RatSpottingAdapter(this, new ArrayList<RatSpotting>());

        ratSpottingBL = new RatSpottingBL();

        mAuth = FirebaseAuth.getInstance();
        getRatData();

        Log.d(LOG_ID, "WelcomeScreenController:onCreate: welcome screen created");
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(ratAdapter);
    }

    private class RatSpottingAdapter extends ArrayAdapter<RatSpotting> {
        private RatSpottingAdapter(Context context, ArrayList<RatSpotting> spots) {
            super(context, 0, spots);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final RatSpotting spot = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_rat, parent, false);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(LOG_ID, "Clicking on a certain rat spotting: " + spot.toString());
                        Intent intent = new Intent(WelcomeScreenController.this, DetailRatScreenController.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("spotting", spot);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            TextView rsDate = convertView.findViewById(R.id.rsDate);
            TextView rsLocationType = convertView.findViewById(R.id.rsLocationType);
            TextView rsBorough = convertView.findViewById(R.id.rsBorough);

            rsDate.setText("Key: " + spot.getKey() + "    ");
            rsLocationType.setText("Zip: " + Long.toString(spot.getZip()) + "    ");
            rsBorough.setText("Borough: " + spot.getBorough());

            return convertView;
        }
    }

    /**
     * getRatData - queries the server for rat data and places it on the screen once the request
     * is returned
     */
    public void getRatData() {
        Log.d(LOG_ID, "Importing Rat Data...");
        ratSpottingBL.getRecentRatSpottings().addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(LOG_ID, dataSnapshot.toString());
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Log.d(LOG_ID, data.toString());
                            RatSpotting rat = getRatSpottingFromSnapshot(data);
                            Log.d(LOG_ID, rat.toString());
                            ratAdapter.add(rat);
                        }
                        listView.setAdapter(ratAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(LOG_ID, databaseError.getMessage());
                    }
                }
        );
    }


    /**
     * logout - logs the user out and returns to the homescreen
     * @param view - the view object that is calling the logout method()
     */
    public void logout(View view) {
        mAuth.signOut();
        Intent intent = new Intent(this, HomeScreenController.class);
        startActivity(intent);
        RatSpottingBL.pushCurrentKey();
        finish();
    }

    /**
     * newRatSpotting - changes the activity to the rat-spotting creation screen
     * @param view - the view object that is calling the newRatSpotting() method
     */
    public void newRatSpotting(View view) {
        Intent intent = new Intent(this, NewRatSpottingController.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_ID, "Returned to Welcome Screen Controller");
        if (data != null && data.getExtras() != null) {
            RatSpotting rat = (RatSpotting) data.getExtras().getSerializable("ratspotting");
            if (rat != null) {
                Log.d(LOG_ID, "NewRatSpottingController has returned RatSpotting: "
                        + rat.getKey() + " toString(): " + rat.toString());
                ratAdapter.add(rat);
            } else {
                Log.d(LOG_ID, "NewRatSpottingController has returned a null RatSpotting");
            }

        }
    }

    private RatSpotting getRatSpottingFromSnapshot(DataSnapshot data) {
        return new RatSpotting(
                data.getKey(),
                (String) data.child("Date").getValue(),
                (String) data.child("LocationType").getValue(),
                Long.parseLong(data.child("Zip").getValue() != null
                        ? data.child("Zip").getValue().toString() : "10000"),
                (String) data.child("Address").getValue(),
                (String) data.child("City").getValue(),
                (String) data.child("Borough").getValue(),
                Double.parseDouble(data.child("Latitude")
                        .getValue() != null ?
                        data.child("Latitude").getValue().toString() : "0.0"),
                Double.parseDouble(data.child("Longitude")
                        .getValue() != null ?
                        data.child("Latitude").getValue().toString() : "0.0")
        );
    }

    public void viewMap(View view) {
        Intent intent = new Intent(this, MapScreenController.class);
        startActivity(intent);
    }
}

